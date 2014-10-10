package edu.ucdenver.ccp.nlp;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import banner.eval.BANNER;
import banner.postprocessing.PostProcessor;
import banner.tagging.CRFTagger;
import banner.tagging.dictionary.DictionaryTagger;
import banner.tokenization.Tokenizer;
import banner.types.Mention;
import banner.types.Sentence;
import banner.types.uima.Gene;
import dragon.nlp.tool.Tagger;
import dragon.nlp.tool.lemmatiser.EngLemmatiser;

public class BannerGeneAnnotator extends JCasAnnotator_ImplBase {
  private static final int iLineSepLen = System.getProperty("line.separator")
      .length();

  Tokenizer tokenizer;
  DictionaryTagger dictionary;
  HierarchicalConfiguration config;
  // Dataset dataset;
  EngLemmatiser lemmatiser;
  Tagger posTagger;
  CRFTagger tagger;
  PostProcessor postProcessor;

  @Override
  public void process(JCas jcas) throws AnalysisEngineProcessException {
    String docText = jcas.getDocumentText();

    // TODO (ivogeorg) Line separator property
    // System.out.println("BANNER GENE ANNOTATOR:LINE SEPARATOR LENGTH: "
    // .concat(String.valueOf(iLineSepLen)));

    /**
     * ivogeorg, 2014-09-12 splits at next line separator, doesn't return it
     * note that the length of the line separator is OS dependent (2 for Win, 1
     * for others)
     */
    // TODO (ivogeorg) This should not be hardcoded
    System.setProperty("line.separator", "\n");

    Scanner sc = new Scanner(docText);
    int count = 0;
    while (sc.hasNextLine()) {
      String origLine = sc.nextLine();
      String line = origLine.trim();

      /**
       * ivogeorg, 2014-09-12 verify the trimming operation doesn't do anything
       */

//      if (origLine.compareTo(line) != 0) {
//        System.out.println("------------ LINE TRIMMED ------------");
//      }

      if (line.length() > 0) {
        // String[] split = line.split("\\t");
        Sentence sentence = new Sentence(Integer.toString(count), "", line);
        Sentence sentence2 = BANNER.process(tagger, tokenizer, postProcessor,
            sentence);

        /**
         * ivogeorg, 2014-09-12 verify that the two sentences have the same text
         */
//        if (sentence.getText().compareTo(sentence2.getText()) != 0) {
//          System.out
//              .println(" ++++++++++++++++ SENTENCE TEXT CHANGED ++++++++++++++++++++");
//        }

//        StringBuilder currentLine = new StringBuilder();
//        currentLine.append("LINE: ");
//        currentLine.append(line);
//        currentLine.append("\n");
//        System.out.println(currentLine.toString());

        for (Mention mention : sentence2.getMentions()) {

//          StringBuilder mentions = new StringBuilder();
//          mentions.append(mention.getEntityType());
//          mentions.append("\t");
//          mentions.append(mention.getStartChar());
//          mentions.append("\t");
//          mentions.append(mention.getEndChar());
//          mentions.append("\t");
//          mentions.append(mention.getText());
//
//          mentions.append("\n");
//          mentions.append("\t");
//          mentions.append(String.valueOf(count + mention.getStartChar()));
//          mentions.append("\t");
//          mentions.append(String.valueOf(count + mention.getEndChar()));
//
//          mentions.append("\n");
//          mentions.append("ANNO: ");
//          mentions.append(docText.substring(count + mention.getStartChar(),
//              count + mention.getEndChar()));
//
//          mentions.append("\n");
//          System.out.println(mentions.toString());

          Gene g = new Gene(jcas, count + mention.getStartChar(), count
              + mention.getEndChar());
          // TODO (ivogeorg) set the gene id to the uniprot/ensembl code
          g.setId("");
          g.addToIndexes();
        }

//        StringBuilder origText = new StringBuilder();
//        origText.append("TEXT: ");
//        /**
//         * ivogeorg, 2014-09-14 if the line gets trimmed above, the offsets are
//         * wrong
//         */
//        // origText.append(docText.substring(count, count + line.length() + 1));
//        origText
//            .append(docText.substring(count, count + origLine.length() + 1));
//
//        origText.append("\n");
//        System.out.println(origText.toString());

      }
      /**
       * ivogeorg, 2014-09-14
       * if the line gets trimmed above, the offsets become incorrect
       */
      count += origLine.length() + 1;
      // count += line.length() + 1;
      // count += line.length() + iLineSepLen; // line separator length is os
      // specific
    }

    /**
     * ivogeorg, 2014-09-12
     */
    sc.close();
  }

  @Override
  public void initialize(UimaContext aContext)
      throws ResourceInitializationException {
    super.initialize(aContext);

    long start = System.currentTimeMillis();
    try {
      config = new XMLConfiguration(
          (String) aContext.getConfigParameterValue("configFile"));
      // dataset = BANNER.getDataset(config);
      tokenizer = BANNER.getTokenizer(config);
      dictionary = BANNER.getDictionary(config);
      lemmatiser = BANNER.getLemmatiser(config);
      posTagger = BANNER.getPosTagger(config);
      postProcessor = BANNER.getPostProcessor(config);

      HierarchicalConfiguration localConfig = config
          .configurationAt(BANNER.class.getPackage().getName());
      String modelFilename = localConfig.getString("modelFilename");
      System.out.println("Model: " + modelFilename);
      tagger = CRFTagger.load(new File(modelFilename), lemmatiser, posTagger,
          dictionary);
      System.out.println("Loaded model: "
          + (System.currentTimeMillis() - start) + "ms");
    } catch (ConfigurationException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    /*
     * BANNER.logInput(dataset.getSentences(), config);
     * System.out.println("Completed input: " + (System.currentTimeMillis() -
     * start)); Performance performance = test(dataset, tagger, config);
     * performance.print();
     */
  }

  /*
   * private DictionaryTagger loadDictionary(UimaContext aContext) { try {
   * //String dict = aContext.getResourceFilePath("dict"); String tokenizerName
   * = aContext.getResourceFilePath("tokenizer"); tokenizer =
   * (Tokenizer)Class.forName(tokenizerName).newInstance();
   * 
   * String dictionaryName = aContext.getResourceFilePath("dictionaryTagger");
   * dictionary = (DictionaryTagger)Class.forName(dictionaryName).newInstance();
   * 
   * //configure dictionary DictionaryTagger d = new DictionaryTagger();
   * d.filterContainedMentions = (Boolean)
   * aContext.getConfigParameterValue("filterContainedMentions");
   * d.normalizeMixedCase = (Boolean)
   * aContext.getConfigParameterValue("normalizeMixedCase"); d.normalizeDigits =
   * (Boolean) aContext.getConfigParameterValue("normalizeDigits");
   * d.generate2PartVariations = (Boolean)
   * aContext.getConfigParameterValue("generate2PartVariations");
   * d.dropEndParentheticals = (Boolean)
   * aContext.getConfigParameterValue("dropEndParentheticals");
   * 
   * 
   * 
   * String dictionaryTypeName = (String)
   * aContext.getConfigParameterValue("dictionaryType");
   * 
   * 
   * 
   * 
   * String delimiter = (String) aContext.getConfigParameterValue("delimiter");
   * int column = (Integer) aContext.getConfigParameterValue("column");
   * EntityType dictionaryType = EntityType.getType(dictionaryTypeName);
   * 
   * // Load data BufferedReader reader = new BufferedReader(new
   * FileReader(dictionaryName)); String line = reader.readLine(); while (line
   * != null) { line = line.trim(); if (line.length() > 0) { if (delimiter ==
   * null) { add(line, dictionaryType); } else { // TODO Performance - don't use
   * split String[] split = line.split(delimiter); add(split[column],
   * dictionaryType); } } line = reader.readLine(); } reader.close();
   * 
   * } catch (ResourceAccessException e) { e.printStackTrace(); } catch
   * (InstantiationException e) { e.printStackTrace(); } catch
   * (IllegalAccessException e) { e.printStackTrace(); } catch
   * (ClassNotFoundException e) { e.printStackTrace(); } return null; }
   */
}
