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

package edu.ucdenver.ccp.nlp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.impl.XmiCasSerializer;
import org.apache.uima.collection.CasConsumer_ImplBase;
import org.apache.uima.examples.SourceDocumentInformation;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceProcessException;
import org.apache.uima.util.XMLSerializer;
import org.xml.sax.SAXException;

/**
 * A simple CAS consumer that writes the CAS to XMI format.
 * <p>
 * This CAS Consumer takes one parameter:
 * <ul>
 * <li><code>OutputDirectory</code> - path to directory into which output files
 * will be written</li>
 * </ul>
 */
public class XMIWriter extends CasConsumer_ImplBase {
  /**
   * Name of configuration parameter that must be set to the path of a directory
   * into which the output files will be written.
   */
  public static final String PARAM_OUTPUTDIR = "outputDir";

  /**
   * Name of the view containing the annotations.
   */
  public static final String PARAM_OUTPUT_VIEW_NAME = "sofaToWriteOut";

  /**
   * Name of the view containing the annotations.
   */
  public static final String PARAM_OUTPUT_FILE_EXT = "outputFileExt";

  private String mOutputDirName;
  private String mViewName;
  private String mOutFileExt;

  public void initialize() throws ResourceInitializationException {
    mViewName = (String) getConfigParameterValue(PARAM_OUTPUT_VIEW_NAME);
    mOutputDirName = (String) getConfigParameterValue(PARAM_OUTPUTDIR);
    mOutFileExt = (String) getConfigParameterValue(PARAM_OUTPUT_FILE_EXT);
  }

  /**
   * Processes the CAS which was populated by the TextAnalysisEngines. <br>
   * In this case, the CAS is converted to XMI and written into the output file
   * .
   * 
   * @param aCAS
   *          a CAS which has been populated by the TAEs
   * 
   * @throws ResourceProcessException
   *           if there is an error in processing the Resource
   * 
   * @see org.apache.uima.collection.base_cpm.CasObjectProcessor#processCas(org.apache.uima.cas.CAS)
   */
  public void processCas(CAS aCAS) throws ResourceProcessException {
    /**
     * ivogeorg, 2014-08-23
     * 
     * output dirs should be relative to input dirs take the uri of the source
     * doc from the right view, extract the parent dir and check if the output
     * dir exists, create if not
     */

    JCas jcas;
    try {
      jcas = aCAS.getView(mViewName).getJCas();
    } catch (CASException e) {
      throw new ResourceProcessException(e);
    }

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
        throw new ResourceProcessException(e1);
      }
    }

    // serialize XCAS and write to output file
    try {
      writeXmi(jcas.getCas(), outFile);
    } catch (IOException e) {
      throw new ResourceProcessException(e);
    } catch (SAXException e) {
      throw new ResourceProcessException(e);
    }
  }

  /**
   * Serialize a CAS to a file in XMI format
   * 
   * @param aCas
   *          CAS to serialize
   * @param name
   *          output file
   * @throws SAXException
   *           -
   * @throws Exception
   *           -
   * 
   * @throws ResourceProcessException
   *           -
   */
  private void writeXmi(CAS aCas, File name)
      throws IOException, SAXException {
    FileOutputStream out = null;

    try {
      // write XMI
      out = new FileOutputStream(name);
      XmiCasSerializer ser = new XmiCasSerializer(aCas.getTypeSystem());
      XMLSerializer xmlSer = new XMLSerializer(out, false);
      ser.serialize(aCas, xmlSer.getContentHandler());
    } finally {
      if (out != null) {
        out.close();
      }
    }
  }
}
