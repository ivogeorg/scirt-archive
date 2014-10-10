package edu.ucdenver.ccp.nlp;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.Type;
import org.apache.uima.examples.SourceDocumentInformation;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class PDFToTextConverter extends JCasAnnotator_ImplBase {
  /**
   * Name of input view containing a src doc info with document-level metadata
   * including the source file URI.
   */
  public static final String PARAM_INPUT_VIEW = "originalDoc";

  /**
   * Name of output view containing the converted text/data of the original
   * document and (FOR NOW) a copy of the src doc info
   */
  public static final String PARAM_OUTPUT_VIEW = "convertedDoc";

  /**
   * File extension identifying and differentiating the type of files this
   * converter can process.
   */
  public static final String PARAM_FILE_EXT = "fileExtension";

  /**
   * The XMI writer outputs XML 1.0 so only valid characters can be left in
   * converted view/sofa text and others removed or substituted. Invalid are
   * 
   * #x9 | #xA | #xD | [#x20-#xD7FF] | [#xE000-#xFFFD] | [#x10000-#x10FFFF]
   */
  public static final String xml10pattern = "[^" + "\u0009\r\n"
      + "\u0020-\uD7FF" + "\uE000-\uFFFD" + "\ud800\udc00-\udbff\udfff" + "]";

  private Type sourceDocInfoType;

  private String mInputView = null, mOutputView = null;

  private String mFileExt = null;

  private PDDocument pdf;
  private PDFTextStripper stripper;

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    // read parameters
    mInputView = (String) getContext()
        .getConfigParameterValue(PARAM_INPUT_VIEW);
    mOutputView = (String) getContext().getConfigParameterValue(
        PARAM_OUTPUT_VIEW);
    mFileExt = (String) getContext().getConfigParameterValue(PARAM_FILE_EXT);

    // get the src doc info type
    sourceDocInfoType = aJCas.getTypeSystem().getType(
        "org.apache.uima.examples.SourceDocumentInformation");

    // get the original document view
    JCas origCas;
    try {
      origCas = aJCas.getView(mInputView);
    } catch (CASException e2) {
      throw new AnalysisEngineProcessException(e2);
    }

    // get the src doc info
    String srcDocURIStr = null;
    FSIterator<Annotation> iter;
    try {
      iter = origCas.getAnnotationIndex(sourceDocInfoType).iterator();
    } catch (Exception e) {
      throw new AnalysisEngineProcessException(e);
    }

    if (iter.hasNext()) {
      SourceDocumentInformation srcDocInfo = (SourceDocumentInformation) iter
          .next();
      srcDocURIStr = srcDocInfo.getUri();
    }

    // get the original document text
    /**
     * ivogeorg, 2014-08-22
     * 
     * Process only files that this converter can handle. Assume the file
     * extension is indicative enough. If the extension indicates that the file
     * cannot be processed by this converter, do nothing.
     */
    // TODO cleanup these local vars
    File srcFile;
    try {
      srcFile = new File(new URI(srcDocURIStr));
    } catch (URISyntaxException e) {
      throw new AnalysisEngineProcessException(e);
    }

    String fileName = srcFile.getName();

    int i = fileName.lastIndexOf('.');
    if (i > 0) {
      String ext = fileName.substring(i + 1);
      if (ext.equalsIgnoreCase(mFileExt)) {

        try {
          pdf = PDDocument.load(srcFile);
        } catch (IOException e1) {
          throw new AnalysisEngineProcessException(e1);
        }

        // create the plain text view and set its document text
        JCas plainTextView;
        try {
          plainTextView = aJCas.createView(mOutputView);
        } catch (CASException e) {
          throw new AnalysisEngineProcessException(e);
        }

        // TODO it's possibly wasteful to create a stripper every
        // time, so should use one and user resetEngine()
        try {
          // TODO (ivogeorg) This should not be hardcoded!!!
          // stripper = new PDFTextStripper();
          stripper = new PDFTextStripper("UTF-8");
          stripper.setLineSeparator("\n"); // TODO (ivogeorg) the stripper still
                                           // produces the occasional \r\n
                                           // despite this

          /**
           * Remove the invalid characters before setting the sofa text
           */
          String possiblyIllegalInXML1_0 = stripper.getText(pdf);

          // TODO (ivogeorg) force filter carriage return \r to avoid messing up
          // the line separator and correspondingly the annotation offsets
          if (possiblyIllegalInXML1_0.indexOf('\r') >= 0) {
            // System.out
            // .println("PDF TO TEXT CONVERTER:CR ENCOUNTERED:FILTERING");
            possiblyIllegalInXML1_0 = possiblyIllegalInXML1_0.replaceAll("\r",
                "");
          }
          // System.out.println("PDF TO TEXT CONVERTER:CR FILTERED: "
          // .concat((possiblyIllegalInXML1_0.indexOf('\r') >= 0) ? "NO"
          // : "YES"));

          String legalInXML1_0 = possiblyIllegalInXML1_0.replaceAll(
              xml10pattern, "");
          plainTextView.setDocumentText(legalInXML1_0);
        } catch (Exception e) {
          throw new AnalysisEngineProcessException(e);
        }

        try {
          pdf.close();
        } catch (IOException e) {
          throw new AnalysisEngineProcessException(e);
        }

        // copy the document meta data in the new view
        // TODO might be an unnecessary duplication
        plainTextView.setDocumentLanguage(origCas.getDocumentLanguage());

        // Index the SourceDocumentInformation object, if there is one,
        // in the new sofa.
        // This is needed by the SemanticSearchCasIndexer
        iter = origCas.getAnnotationIndex(sourceDocInfoType).iterator();
        if (iter.hasNext()) {
          FeatureStructure sourceDocInfoFs = (FeatureStructure) iter.next();
          plainTextView.getIndexRepository().addFS(sourceDocInfoFs);
        }
      }
    }
  }
}
