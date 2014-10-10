

/* First created by JCasGen Wed Aug 27 07:55:56 MDT 2014 */
package edu.ucdenver.ccp.nlp.uima;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import banner.types.uima.Gene;
import org.apache.uima.jcas.cas.FSList;
import opennlp.uima.Sentence;
import org.apache.uima.jcas.tcas.Annotation;


/** Originally for PPI pipeline.
 * Updated by JCasGen Wed Sep 10 11:27:18 MDT 2014
 * XML source: C:/uimaj-2.6.0-bin/apache-uima/examples/descriptors/ccp/BratAnnotationWriter.xml
 * @generated */
public class Interaction extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Interaction.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Interaction() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Interaction(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Interaction(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Interaction(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** 
   * <!-- begin-user-doc -->
   * Write your own initialization here
   * <!-- end-user-doc -->
   *
   * @generated modifiable 
   */
  private void readObject() {/*default - does nothing empty block */}
     
  //*--------------*
  //* Feature: gene1

  /** getter for gene1 - gets 
   * @generated
   * @return value of the feature 
   */
  public Gene getGene1() {
    if (Interaction_Type.featOkTst && ((Interaction_Type)jcasType).casFeat_gene1 == null)
      jcasType.jcas.throwFeatMissing("gene1", "edu.ucdenver.ccp.nlp.uima.Interaction");
    return (Gene)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Interaction_Type)jcasType).casFeatCode_gene1)));}
    
  /** setter for gene1 - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setGene1(Gene v) {
    if (Interaction_Type.featOkTst && ((Interaction_Type)jcasType).casFeat_gene1 == null)
      jcasType.jcas.throwFeatMissing("gene1", "edu.ucdenver.ccp.nlp.uima.Interaction");
    jcasType.ll_cas.ll_setRefValue(addr, ((Interaction_Type)jcasType).casFeatCode_gene1, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: gene2

  /** getter for gene2 - gets 
   * @generated
   * @return value of the feature 
   */
  public Gene getGene2() {
    if (Interaction_Type.featOkTst && ((Interaction_Type)jcasType).casFeat_gene2 == null)
      jcasType.jcas.throwFeatMissing("gene2", "edu.ucdenver.ccp.nlp.uima.Interaction");
    return (Gene)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Interaction_Type)jcasType).casFeatCode_gene2)));}
    
  /** setter for gene2 - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setGene2(Gene v) {
    if (Interaction_Type.featOkTst && ((Interaction_Type)jcasType).casFeat_gene2 == null)
      jcasType.jcas.throwFeatMissing("gene2", "edu.ucdenver.ccp.nlp.uima.Interaction");
    jcasType.ll_cas.ll_setRefValue(addr, ((Interaction_Type)jcasType).casFeatCode_gene2, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: interactionKeyword

  /** getter for interactionKeyword - gets 
   * @generated
   * @return value of the feature 
   */
  public InteractionKeyword getInteractionKeyword() {
    if (Interaction_Type.featOkTst && ((Interaction_Type)jcasType).casFeat_interactionKeyword == null)
      jcasType.jcas.throwFeatMissing("interactionKeyword", "edu.ucdenver.ccp.nlp.uima.Interaction");
    return (InteractionKeyword)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Interaction_Type)jcasType).casFeatCode_interactionKeyword)));}
    
  /** setter for interactionKeyword - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setInteractionKeyword(InteractionKeyword v) {
    if (Interaction_Type.featOkTst && ((Interaction_Type)jcasType).casFeat_interactionKeyword == null)
      jcasType.jcas.throwFeatMissing("interactionKeyword", "edu.ucdenver.ccp.nlp.uima.Interaction");
    jcasType.ll_cas.ll_setRefValue(addr, ((Interaction_Type)jcasType).casFeatCode_interactionKeyword, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: score

  /** getter for score - gets 
   * @generated
   * @return value of the feature 
   */
  public int getScore() {
    if (Interaction_Type.featOkTst && ((Interaction_Type)jcasType).casFeat_score == null)
      jcasType.jcas.throwFeatMissing("score", "edu.ucdenver.ccp.nlp.uima.Interaction");
    return jcasType.ll_cas.ll_getIntValue(addr, ((Interaction_Type)jcasType).casFeatCode_score);}
    
  /** setter for score - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setScore(int v) {
    if (Interaction_Type.featOkTst && ((Interaction_Type)jcasType).casFeat_score == null)
      jcasType.jcas.throwFeatMissing("score", "edu.ucdenver.ccp.nlp.uima.Interaction");
    jcasType.ll_cas.ll_setIntValue(addr, ((Interaction_Type)jcasType).casFeatCode_score, v);}    
   
    
  //*--------------*
  //* Feature: organism

  /** getter for organism - gets 
   * @generated
   * @return value of the feature 
   */
  public FSList getOrganism() {
    if (Interaction_Type.featOkTst && ((Interaction_Type)jcasType).casFeat_organism == null)
      jcasType.jcas.throwFeatMissing("organism", "edu.ucdenver.ccp.nlp.uima.Interaction");
    return (FSList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Interaction_Type)jcasType).casFeatCode_organism)));}
    
  /** setter for organism - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setOrganism(FSList v) {
    if (Interaction_Type.featOkTst && ((Interaction_Type)jcasType).casFeat_organism == null)
      jcasType.jcas.throwFeatMissing("organism", "edu.ucdenver.ccp.nlp.uima.Interaction");
    jcasType.ll_cas.ll_setRefValue(addr, ((Interaction_Type)jcasType).casFeatCode_organism, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: sentence

  /** getter for sentence - gets 
   * @generated
   * @return value of the feature 
   */
  public Sentence getSentence() {
    if (Interaction_Type.featOkTst && ((Interaction_Type)jcasType).casFeat_sentence == null)
      jcasType.jcas.throwFeatMissing("sentence", "edu.ucdenver.ccp.nlp.uima.Interaction");
    return (Sentence)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Interaction_Type)jcasType).casFeatCode_sentence)));}
    
  /** setter for sentence - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setSentence(Sentence v) {
    if (Interaction_Type.featOkTst && ((Interaction_Type)jcasType).casFeat_sentence == null)
      jcasType.jcas.throwFeatMissing("sentence", "edu.ucdenver.ccp.nlp.uima.Interaction");
    jcasType.ll_cas.ll_setRefValue(addr, ((Interaction_Type)jcasType).casFeatCode_sentence, jcasType.ll_cas.ll_getFSRef(v));}    
  }

    