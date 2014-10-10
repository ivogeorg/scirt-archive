package edu.ucdenver.ccp.nlp;

import java.io.File;

import opennlp.uima.Sentence;
import opennlp.uima.Token;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.examples.SourceDocumentInformation;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

public class AnnotationStats extends JCasAnnotator_ImplBase {
  public static int countSentences = 0;
  public static int countTokens = 0;

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    
    // TODO (ivogeorg) is there a faster way to count the annotations?

    // count the sentences
    FSIterator<Annotation> sentences = aJCas.getAnnotationIndex(Sentence.type)
        .iterator();
    while (sentences.hasNext()) {
      Sentence s = (Sentence) sentences.next();
      AnnotationStats.countSentences++;
    }

    // count the tokens
    FSIterator<Annotation> tokens = aJCas.getAnnotationIndex(Token.type)
        .iterator();
    while (tokens.hasNext()) {
      Token t = (Token) tokens.next();
      AnnotationStats.countTokens++;
    }

    // if last document, print the
    FSIterator<Annotation> it = aJCas.getAnnotationIndex(
        SourceDocumentInformation.type).iterator();
    if (it.hasNext()) {
      SourceDocumentInformation fileMetaData = (SourceDocumentInformation) it
          .next();
      if (fileMetaData.getIsLastDocument()) {
        // TODO (ivogeorg) use the logger
        StringBuffer s = new StringBuffer();
        s.append(this.getClass().getName() + " reports:\n");
        s.append("Sentence count: " + String.valueOf(AnnotationStats.countSentences + "\n"));
        s.append("Token count: " + String.valueOf(AnnotationStats.countTokens + "\n"));
        System.out.println(s.toString());
      }
    }

  }

}
