package edu.ucdenver.ccp.nlp;

import opennlp.uima.Token;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceAccessException;
import org.apache.uima.resource.ResourceInitializationException;

import edu.ucdenver.ccp.nlp.uima.InteractionKeyword;
import edu.ucdenver.ccp.nlp.util.KeywordListResource;

public class InteractionKeywordAnnotator extends JCasAnnotator_ImplBase {
  public static final String PARAM_RESOURCE_NAME = "keywordListResourceKey";

  private String mResourceName = null;
  private KeywordListResource mSet;

  @Override
  public void initialize(UimaContext aContext)
      throws ResourceInitializationException {
    super.initialize(aContext);

    // TODO it is really unwieldy to have a parameter naming a resource
    // uimaFIT eliminates the parameter by annotating with the parameter
    // (instead of a local PARAM) and that should reduce the possible
    // resource initialization errors

    mResourceName = ((String) getContext().getConfigParameterValue(
        PARAM_RESOURCE_NAME)).trim();

    if (mResourceName == null) {
      throw new ResourceInitializationException(
          ResourceInitializationException.CONFIG_SETTING_ABSENT, new Object[] {
              "Missing parameter", PARAM_RESOURCE_NAME });
    }

    try {
      mSet = (KeywordListResource) getContext()
          .getResourceObject(mResourceName);
    } catch (ResourceAccessException e) {
      throw new ResourceInitializationException(e);
    }
  }

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    // go through all tokens: if in keyword list, add interactionkeyword
    // annotation
    String text = aJCas.getDocumentText();

    FSIterator<Annotation> iter = aJCas.getAnnotationIndex(Token.type)
        .iterator();
    while (iter.isValid()) {
      Token token = (Token) iter.get();

      if (mSet.isKeyword(text.substring(token.getBegin(), token.getEnd()))) {
        InteractionKeyword kwAnnot = new InteractionKeyword(aJCas,
            token.getBegin(), token.getEnd());
        kwAnnot.addToIndexes();
      }
      iter.next();
    }
  }
}
