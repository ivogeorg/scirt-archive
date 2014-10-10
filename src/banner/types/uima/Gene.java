

/* First created by JCasGen Mon Aug 25 11:43:46 MDT 2014 */
package banner.types.uima;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** Just marks the gene
 * Updated by JCasGen Wed Sep 10 11:27:17 MDT 2014
 * XML source: C:/uimaj-2.6.0-bin/apache-uima/examples/descriptors/ccp/BratAnnotationWriter.xml
 * @generated */
public class Gene extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Gene.class);
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
  protected Gene() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Gene(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Gene(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Gene(JCas jcas, int begin, int end) {
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
  //* Feature: id

  /** getter for id - gets id is the reference to a standard database such as uniprot or ensembl
   * @generated
   * @return value of the feature 
   */
  public String getId() {
    if (Gene_Type.featOkTst && ((Gene_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "banner.types.uima.Gene");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Gene_Type)jcasType).casFeatCode_id);}
    
  /** setter for id - sets id is the reference to a standard database such as uniprot or ensembl 
   * @generated
   * @param v value to set into the feature 
   */
  public void setId(String v) {
    if (Gene_Type.featOkTst && ((Gene_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "banner.types.uima.Gene");
    jcasType.ll_cas.ll_setStringValue(addr, ((Gene_Type)jcasType).casFeatCode_id, v);}    
  }

    