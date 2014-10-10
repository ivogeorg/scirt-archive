package edu.ucdenver.ccp.nlp;

import hu.u_szeged.rgai.bio.uima.tagger.LinnaeusSpecies;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.examples.SourceDocumentInformation;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;

import edu.ucdenver.ccp.nlp.uima.Interaction;
import edu.ucdenver.ccp.nlp.uima.InteractionKeyword;
import banner.types.uima.Gene;

public class BratAnnotationWriter extends JCasAnnotator_ImplBase {
  /**
   * Name of configuration parameter that must be set to the path of a directory
   * into which the output files will be written.
   */
  public static final String PARAM_OUTPUTDIR = "outputDir";

  /**
   * Name of the view containing the annotations.
   */
  public static final String PARAM_OUTPUT_FILE_EXT = "outputFileExt";

  private String mOutputDirName;
  private String mOutFileExt;

  @Override
  public void initialize(UimaContext aContext)
      throws ResourceInitializationException {
    super.initialize(aContext);

    mOutputDirName = ((String) getContext().getConfigParameterValue(
        PARAM_OUTPUTDIR)).trim();
    if (mOutputDirName == null) {
      throw new ResourceInitializationException(
          ResourceInitializationException.CONFIG_SETTING_ABSENT, new Object[] {
              "Missing parameter", PARAM_OUTPUTDIR });
    }

    mOutFileExt = ((String) getContext().getConfigParameterValue(
        PARAM_OUTPUT_FILE_EXT)).trim();
    if (mOutFileExt == null) {
      throw new ResourceInitializationException(
          ResourceInitializationException.CONFIG_SETTING_ABSENT, new Object[] {
              "Missing parameter", PARAM_OUTPUT_FILE_EXT });
    }
  }

  @Override
  public void process(JCas jcas) throws AnalysisEngineProcessException {
    // retrieve the filename of the input file from the CAS
    FSIterator<Annotation> it = jcas.getAnnotationIndex(
        SourceDocumentInformation.type).iterator();
    File outFile = null;
    if (it.hasNext()) {
      SourceDocumentInformation fileMetaData = (SourceDocumentInformation) it
          .next();
      File inFile;
      try {
        inFile = new File(new URL(fileMetaData.getUri()).getPath());
        String outFileName = inFile.getName();
        File inFileDir = inFile.getParentFile();
        File outFileDir = new File(inFileDir, mOutputDirName);

        if (!outFileDir.exists()) {
          outFileDir.mkdirs();
        }
        outFile = new File(outFileDir, outFileName + '.' + mOutFileExt);
      } catch (Exception e1) {
        throw new AnalysisEngineProcessException(e1);
      }
    }

    // TODO (ivogeorg) What encoding is used for the output files?
//    System.out.println("BRAT ANNOTATION WRITER:FILE ENCODING: ".concat(System
//        .getProperty("file.encoding")));

    FileWriter fw = null;
    try {
      fw = new FileWriter(outFile);

      /**
       * ivogeorg, 2014-09-10
       * 
       * Write the annotations to the file. Gene/protein and interaction keyword
       * annotations are text-bound annotations (T#). Interaction annotations
       * consist of two binary relation annotations (R#). A HashMap is used to
       * keep track of the annotation labels for the relation annotations.
       */
      int annoIx = 1;
      StringBuffer bratAnno;
      String annoLabel;
      HashMap<Annotation, String> map = new HashMap<Annotation, String>();

      // write out gene annotations and put in map
      FSIterator<Annotation> iter = jcas.getAnnotationIndex(Gene.type)
          .iterator();
      while (iter.hasNext()) {
        Gene g = (Gene) iter.next();

        bratAnno = new StringBuffer();
        annoLabel = "T".concat(String.valueOf(annoIx));
        bratAnno.append(annoLabel);
        bratAnno.append('\t');
        bratAnno.append("Gene");
        bratAnno.append(' ');
        bratAnno.append(g.getBegin());
        bratAnno.append(' ');
        bratAnno.append(g.getEnd());
        bratAnno.append('\t');
        bratAnno.append(g.getCoveredText());
        bratAnno.append('\n');
        fw.write(bratAnno.toString());

        map.put((Annotation) g, annoLabel);
        annoIx++;
      }

      // write out species annotations
      iter = jcas.getAnnotationIndex(LinnaeusSpecies.type).iterator();
      while (iter.hasNext()) {
        LinnaeusSpecies g = (LinnaeusSpecies) iter.next();

        bratAnno = new StringBuffer();
        annoLabel = "T".concat(String.valueOf(annoIx));
        bratAnno.append(annoLabel);
        bratAnno.append('\t');
        bratAnno.append("Species");
        bratAnno.append(' ');
        bratAnno.append(g.getBegin());
        bratAnno.append(' ');
        bratAnno.append(g.getEnd());
        bratAnno.append('\t');
        bratAnno.append(g.getCoveredText());
        bratAnno.append('\n');
        fw.write(bratAnno.toString());

        annoIx++;
      }

      // write out interaction keyword annotations and put in map
      iter = jcas.getAnnotationIndex(InteractionKeyword.type).iterator();
      while (iter.hasNext()) {
        InteractionKeyword g = (InteractionKeyword) iter.next();

        bratAnno = new StringBuffer();
        annoLabel = "T".concat(String.valueOf(annoIx));
        bratAnno.append(annoLabel);
        bratAnno.append('\t');
        bratAnno.append("InteractionKeyword");
        bratAnno.append(' ');
        bratAnno.append(g.getBegin());
        bratAnno.append(' ');
        bratAnno.append(g.getEnd());
        bratAnno.append('\t');
        bratAnno.append(g.getCoveredText());
        bratAnno.append('\n');
        fw.write(bratAnno.toString());

        map.put((Annotation) g, annoLabel);
        annoIx++;
      }

      // write out interaction annotations
      iter = jcas.getAnnotationIndex(Interaction.type).iterator();
      bratAnno = new StringBuffer();
      while (iter.hasNext()) {
        Interaction g = (Interaction) iter.next();

        // first protein
        bratAnno = new StringBuffer();
        annoLabel = "R".concat(String.valueOf(annoIx));
        bratAnno.append(annoLabel);
        bratAnno.append('\t');
        bratAnno.append("Partner1");
        bratAnno.append(' ');
        bratAnno.append("Arg1:");
        bratAnno.append(map.get(g.getInteractionKeyword()));
        bratAnno.append(' ');
        bratAnno.append("Arg2:");
        bratAnno.append(map.get(g.getGene1()));
        bratAnno.append('\n');
        fw.write(bratAnno.toString());

        map.put((Annotation) g, annoLabel);
        annoIx++;

        // second protein
        bratAnno = new StringBuffer();
        annoLabel = "R".concat(String.valueOf(annoIx));
        bratAnno.append(annoLabel);
        bratAnno.append('\t');
        bratAnno.append("Partner2");
        bratAnno.append(' ');
        bratAnno.append("Arg1:");
        bratAnno.append(map.get(g.getInteractionKeyword()));
        bratAnno.append(' ');
        bratAnno.append("Arg2:");
        bratAnno.append(map.get(g.getGene2()));
        bratAnno.append('\n');
        fw.write(bratAnno.toString());

        map.put((Annotation) g, annoLabel);
        annoIx++;
      }

      fw.close();
    } catch (IOException e) {
      throw new AnalysisEngineProcessException(e);
    }
  }
}
