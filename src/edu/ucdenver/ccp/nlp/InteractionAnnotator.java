package edu.ucdenver.ccp.nlp;

import hu.u_szeged.rgai.bio.uima.tagger.LinnaeusSpecies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import banner.types.uima.Gene;
import edu.ucdenver.ccp.nlp.uima.Interaction;
import edu.ucdenver.ccp.nlp.uima.InteractionKeyword;
import opennlp.uima.Sentence;
import opennlp.uima.Token;

public class InteractionAnnotator extends JCasAnnotator_ImplBase {

  /**
   * naive interaction implementation: co-occurrence of one interaction keyword
   * and two gene/protein names in the same sentence
   */
  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    // general approach:
    // - any number of interactionkeyword annotations might be in a sentence
    // - any number of gene annotations might be in a sentence
    // - all annotations can potentially be multi-token
    // - general case: 1+ interkwd annotations and 2+ gene annotations
    // - for each combination, compute score by token distance
    FSIterator<Annotation> iter = aJCas.getAnnotationIndex(Sentence.type)
        .iterator();
    while (iter.hasNext()) {
      Sentence sen = (Sentence) iter.next();
      FSIterator<Annotation> annos = aJCas.getAnnotationIndex()
          .subiterator(sen);
      // get the score for every combination of 1 kwd and 2 genes
      int tokenIx = 0;
      HashMap<Annotation, Integer> keywords = new HashMap(), genes = new HashMap(), species = new HashMap();
      while (annos.hasNext()) {
        Annotation a = annos.next();
        // TODO assumes all annotations are single-token
        if (a.getTypeIndexID() == Token.type) {
          tokenIx++;
        } else if (a.getTypeIndexID() == Gene.type) {
          genes.put(a, tokenIx);
        } else if (a.getTypeIndexID() == InteractionKeyword.type) {
          keywords.put(a, tokenIx);
        } else if (a.getTypeIndexID() == LinnaeusSpecies.type) {
          species.put(a, tokenIx);
        }
      }
      if (!keywords.isEmpty() && !genes.isEmpty() && genes.size() >= 2) {
        Iterator<Annotation> kwIter = keywords.keySet().iterator(), geneIter = genes
            .keySet().iterator();
        InteractionKeyword ikw = (InteractionKeyword) kwIter.next();
        Gene g1 = (Gene) geneIter.next();
        Gene g2 = (Gene) geneIter.next();
        int d1 = Math.abs(genes.get(g1) - keywords.get(ikw));
        int d2 = Math.abs(genes.get(g2) - keywords.get(ikw));
        int score = (d1 >= d2) ? d1 : d2;

        // TODO only considers the first kw and the first two genes
        // TODO doesn't get the species
        Interaction inter = new Interaction(aJCas, sen.getBegin(), sen.getEnd());
        inter.setSentence(sen);
        inter.setGene1(g1);
        inter.setGene2(g2);
        inter.setInteractionKeyword(ikw);
        inter.setScore(score);
        inter.addToIndexes();
      }
    }
  }
}
