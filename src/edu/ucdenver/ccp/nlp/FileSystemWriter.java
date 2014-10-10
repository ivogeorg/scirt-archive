package edu.ucdenver.ccp.nlp;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.examples.SourceDocumentInformation;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.util.FileUtils;

public class FileSystemWriter extends JCasAnnotator_ImplBase {
  // TODO put parameters in descriptor, then move to uimaFIT
  public static final String PARAM_OUTPUT_FILE_EXT = "outputFileExt";
  public static final String PARAM_OUTPUT_DIR = "outputDir";
  public static final String PARAM_OUTPUT_VIEW_NAME = "sofaToWriteOut";

  private File srcFile, oFile, oDir;
  private String srcDocURI, oFileName, oSubDirName, targetViewName;
  private String mOutFileExt;

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {

    // TODO Put these in the initilize() method

    // Extract the output sub-directory parameter
    oSubDirName = ((String) getContext().getConfigParameterValue(
        PARAM_OUTPUT_DIR)).trim();

    // An output directory should be specified in the descriptor
    // TODO put messages in resources
    if (oSubDirName == null) {
      throw new AnalysisEngineProcessException(
          AnalysisEngineProcessException.ANNOTATOR_EXCEPTION, new Object[] {
              "Missing parameter", PARAM_OUTPUT_DIR });
    }

    targetViewName = ((String) getContext().getConfigParameterValue(
        PARAM_OUTPUT_VIEW_NAME)).trim();

    if (targetViewName == null) {
      throw new AnalysisEngineProcessException(
          AnalysisEngineProcessException.ANNOTATOR_EXCEPTION, new Object[] {
              "Missing parameter", PARAM_OUTPUT_VIEW_NAME });
    }

    mOutFileExt = ((String) getContext().getConfigParameterValue(
        PARAM_OUTPUT_FILE_EXT)).trim();

    // if missing parameter, default to 'txt' extension
    if (mOutFileExt == null) {
      mOutFileExt = "txt";
    }

    // get the file uri from the sourcedocumentinformation annotation
    // Note: need to extract it from the right view with an annotation
    // index iterator!

    // TODO The view might not have been created if no annotator could
    // process the file
    /**
     * ivogeorg, 2014-08-22
     * 
     * Handle the pass-through case where the expected view containing the sofa
     * data to write to the file system is not present.
     */
    JCas viewJCas = null;
    try {
      Iterator<JCas> iter = aJCas.getViewIterator();
      JCas view;
      while (iter.hasNext()) {
        view = iter.next();
        if (view.getViewName().equalsIgnoreCase(targetViewName)) {
          viewJCas = view;
          break;
        }
      }
    } catch (CASException e) {
      throw new AnalysisEngineProcessException(e);
    }

    srcDocURI = null;
    FSIterator<Annotation> iter;
    /**
     * If the processed file view exists, proceed with writing out the file.
     */
    if (viewJCas != null) {
      iter = viewJCas.getAnnotationIndex(SourceDocumentInformation.type)
          .iterator();

      if (iter.hasNext()) {
        SourceDocumentInformation srcDocInfo = (SourceDocumentInformation) iter
            .next();
        srcDocURI = srcDocInfo.getUri();
      }

      // get the parent directory of the source file
      try {
        srcFile = new File(new URI(srcDocURI));
      } catch (URISyntaxException e) {
        throw new AnalysisEngineProcessException(e);
      }

      // check if the output directory exists, create if not
      oDir = new File(srcFile.getParent(), oSubDirName);
      if (!oDir.exists()) {
        if (!oDir.mkdirs()) {
          throw new AnalysisEngineProcessException(
              AnalysisEngineProcessException.ANNOTATOR_EXCEPTION, new Object[] {
                  oDir.getPath(), PARAM_OUTPUT_DIR });
        }
      }

      // append a new extension to the file name (for now, just handling text)
      oFileName = srcFile.getName() + '.' + mOutFileExt;
      oFile = new File(oDir, oFileName);

      // create file if it doesn't exist
      try {
        if (!oFile.exists()) {
          oFile.createNewFile();
        }
      } catch (IOException e) {
        throw new AnalysisEngineProcessException(e);
      }

      // write the output view's text to the file (overwritten if exists)
      try {
        // TODO (ivogeorg) the line separator is os dependent and not all
        // upstream tools handle it consistently to avoid annotation offset
        // drift
        // System.setProperty("line.separator", "\n");
//        String lineSep = System.getProperty("line.separator");
//        lineSep = lineSep.replace('\r', 'R');
//        lineSep = lineSep.replace('\n', 'N');
//        System.out.println("FILE SYSTEM WRITER:LINE SEPARATOR: "
//            .concat(lineSep));

        FileUtils.saveString2File(aJCas.getView(targetViewName)
            .getDocumentText(), oFile);
      } catch (Exception e) {
        throw new AnalysisEngineProcessException(e);
      }
    }
  }
}
