

/* First created by JCasGen Mon Aug 25 11:43:46 MDT 2014 */
package banner.types.uima;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** stores information about the source of the input
 * Updated by JCasGen Wed Sep 10 11:27:17 MDT 2014
 * XML source: C:/uimaj-2.6.0-bin/apache-uima/examples/descriptors/ccp/BratAnnotationWriter.xml
 * @generated */
public class Source extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Source.class);
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
  protected Source() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Source(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Source(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Source(JCas jcas, int begin, int end) {
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
  //* Feature: filePath

  /** getter for filePath - gets file path of the source
   * @generated
   * @return value of the feature 
   */
  public String getFilePath() {
    if (Source_Type.featOkTst && ((Source_Type)jcasType).casFeat_filePath == null)
      jcasType.jcas.throwFeatMissing("filePath", "banner.types.uima.Source");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Source_Type)jcasType).casFeatCode_filePath);}
    
  /** setter for filePath - sets file path of the source 
   * @generated
   * @param v value to set into the feature 
   */
  public void setFilePath(String v) {
    if (Source_Type.featOkTst && ((Source_Type)jcasType).casFeat_filePath == null)
      jcasType.jcas.throwFeatMissing("filePath", "banner.types.uima.Source");
    jcasType.ll_cas.ll_setStringValue(addr, ((Source_Type)jcasType).casFeatCode_filePath, v);}    
   
    
  //*--------------*
  //* Feature: text

  /** getter for text - gets text in the file or line
   * @generated
   * @return value of the feature 
   */
  public String getText() {
    if (Source_Type.featOkTst && ((Source_Type)jcasType).casFeat_text == null)
      jcasType.jcas.throwFeatMissing("text", "banner.types.uima.Source");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Source_Type)jcasType).casFeatCode_text);}
    
  /** setter for text - sets text in the file or line 
   * @generated
   * @param v value to set into the feature 
   */
  public void setText(String v) {
    if (Source_Type.featOkTst && ((Source_Type)jcasType).casFeat_text == null)
      jcasType.jcas.throwFeatMissing("text", "banner.types.uima.Source");
    jcasType.ll_cas.ll_setStringValue(addr, ((Source_Type)jcasType).casFeatCode_text, v);}    
  }

    