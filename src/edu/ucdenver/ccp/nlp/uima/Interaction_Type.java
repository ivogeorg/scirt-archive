
/* First created by JCasGen Wed Aug 27 07:55:56 MDT 2014 */
package edu.ucdenver.ccp.nlp.uima;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** Originally for PPI pipeline.
 * Updated by JCasGen Wed Sep 10 11:27:18 MDT 2014
 * @generated */
public class Interaction_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Interaction_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Interaction_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Interaction(addr, Interaction_Type.this);
  			   Interaction_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Interaction(addr, Interaction_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Interaction.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("edu.ucdenver.ccp.nlp.uima.Interaction");



  /** @generated */
  final Feature casFeat_gene1;
  /** @generated */
  final int     casFeatCode_gene1;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getGene1(int addr) {
        if (featOkTst && casFeat_gene1 == null)
      jcas.throwFeatMissing("gene1", "edu.ucdenver.ccp.nlp.uima.Interaction");
    return ll_cas.ll_getRefValue(addr, casFeatCode_gene1);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setGene1(int addr, int v) {
        if (featOkTst && casFeat_gene1 == null)
      jcas.throwFeatMissing("gene1", "edu.ucdenver.ccp.nlp.uima.Interaction");
    ll_cas.ll_setRefValue(addr, casFeatCode_gene1, v);}
    
  
 
  /** @generated */
  final Feature casFeat_gene2;
  /** @generated */
  final int     casFeatCode_gene2;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getGene2(int addr) {
        if (featOkTst && casFeat_gene2 == null)
      jcas.throwFeatMissing("gene2", "edu.ucdenver.ccp.nlp.uima.Interaction");
    return ll_cas.ll_getRefValue(addr, casFeatCode_gene2);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setGene2(int addr, int v) {
        if (featOkTst && casFeat_gene2 == null)
      jcas.throwFeatMissing("gene2", "edu.ucdenver.ccp.nlp.uima.Interaction");
    ll_cas.ll_setRefValue(addr, casFeatCode_gene2, v);}
    
  
 
  /** @generated */
  final Feature casFeat_interactionKeyword;
  /** @generated */
  final int     casFeatCode_interactionKeyword;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getInteractionKeyword(int addr) {
        if (featOkTst && casFeat_interactionKeyword == null)
      jcas.throwFeatMissing("interactionKeyword", "edu.ucdenver.ccp.nlp.uima.Interaction");
    return ll_cas.ll_getRefValue(addr, casFeatCode_interactionKeyword);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setInteractionKeyword(int addr, int v) {
        if (featOkTst && casFeat_interactionKeyword == null)
      jcas.throwFeatMissing("interactionKeyword", "edu.ucdenver.ccp.nlp.uima.Interaction");
    ll_cas.ll_setRefValue(addr, casFeatCode_interactionKeyword, v);}
    
  
 
  /** @generated */
  final Feature casFeat_score;
  /** @generated */
  final int     casFeatCode_score;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getScore(int addr) {
        if (featOkTst && casFeat_score == null)
      jcas.throwFeatMissing("score", "edu.ucdenver.ccp.nlp.uima.Interaction");
    return ll_cas.ll_getIntValue(addr, casFeatCode_score);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setScore(int addr, int v) {
        if (featOkTst && casFeat_score == null)
      jcas.throwFeatMissing("score", "edu.ucdenver.ccp.nlp.uima.Interaction");
    ll_cas.ll_setIntValue(addr, casFeatCode_score, v);}
    
  
 
  /** @generated */
  final Feature casFeat_organism;
  /** @generated */
  final int     casFeatCode_organism;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getOrganism(int addr) {
        if (featOkTst && casFeat_organism == null)
      jcas.throwFeatMissing("organism", "edu.ucdenver.ccp.nlp.uima.Interaction");
    return ll_cas.ll_getRefValue(addr, casFeatCode_organism);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setOrganism(int addr, int v) {
        if (featOkTst && casFeat_organism == null)
      jcas.throwFeatMissing("organism", "edu.ucdenver.ccp.nlp.uima.Interaction");
    ll_cas.ll_setRefValue(addr, casFeatCode_organism, v);}
    
  
 
  /** @generated */
  final Feature casFeat_sentence;
  /** @generated */
  final int     casFeatCode_sentence;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getSentence(int addr) {
        if (featOkTst && casFeat_sentence == null)
      jcas.throwFeatMissing("sentence", "edu.ucdenver.ccp.nlp.uima.Interaction");
    return ll_cas.ll_getRefValue(addr, casFeatCode_sentence);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSentence(int addr, int v) {
        if (featOkTst && casFeat_sentence == null)
      jcas.throwFeatMissing("sentence", "edu.ucdenver.ccp.nlp.uima.Interaction");
    ll_cas.ll_setRefValue(addr, casFeatCode_sentence, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public Interaction_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_gene1 = jcas.getRequiredFeatureDE(casType, "gene1", "banner.types.uima.Gene", featOkTst);
    casFeatCode_gene1  = (null == casFeat_gene1) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_gene1).getCode();

 
    casFeat_gene2 = jcas.getRequiredFeatureDE(casType, "gene2", "banner.types.uima.Gene", featOkTst);
    casFeatCode_gene2  = (null == casFeat_gene2) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_gene2).getCode();

 
    casFeat_interactionKeyword = jcas.getRequiredFeatureDE(casType, "interactionKeyword", "edu.ucdenver.ccp.nlp.uima.InteractionKeyword", featOkTst);
    casFeatCode_interactionKeyword  = (null == casFeat_interactionKeyword) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_interactionKeyword).getCode();

 
    casFeat_score = jcas.getRequiredFeatureDE(casType, "score", "uima.cas.Integer", featOkTst);
    casFeatCode_score  = (null == casFeat_score) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_score).getCode();

 
    casFeat_organism = jcas.getRequiredFeatureDE(casType, "organism", "uima.cas.FSList", featOkTst);
    casFeatCode_organism  = (null == casFeat_organism) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_organism).getCode();

 
    casFeat_sentence = jcas.getRequiredFeatureDE(casType, "sentence", "opennlp.uima.Sentence", featOkTst);
    casFeatCode_sentence  = (null == casFeat_sentence) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_sentence).getCode();

  }
}



    