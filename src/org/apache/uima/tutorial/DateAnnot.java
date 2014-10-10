/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.uima.tutorial;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

/** 
 * Updated by JCasGen Wed Aug 20 12:06:13 MDT 2014
 * XML source: C:/uimaj-2.6.0-bin/apache-uima/examples/descriptors/tutorial/ex3/TutorialDateTime.xml
 * @generated */
public class DateAnnot extends DateTimeAnnot {
  /**
   * @generated
   * @ordered
   */
  public final static int typeIndexID = JCasRegistry.register(DateAnnot.class);

  /**
   * @generated
   * @ordered
   */
  public final static int type = typeIndexID;

  /** @generated */
  public int getTypeIndexID() {return typeIndexID;}
 
  /**
   * Never called. Disable default constructor
   * 
   * @generated
   */
  protected DateAnnot() {/* intentionally empty block */}
    
  /**
   * Internal - constructor used by generator
   * 
   * @generated
   */
  public DateAnnot(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public DateAnnot(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  public DateAnnot(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }

  /** 
   * <!-- begin-user-doc --> Write your own initialization here <!-- end-user-doc -->
   *
   * @generated modifiable 
   */
  private void readObject() {
  }

}
