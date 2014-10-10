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
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.CasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.TypeSystem;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.examples.SourceDocumentInformation;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.FileUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * A multi-sofa annotator that does XML detagging. Reads XML data from the input
 * Sofa (named "xmlDocument"); this data can be stored in the CAS as a string or
 * array, or it can be a URI to a remote file. The XML is parsed using the JVM's
 * default parser, and the plain-text content is written to a new sofa called
 * "plainTextDocument".
 */
public class XMLReaderDetagger extends CasAnnotator_ImplBase {
  /**
   * Name of optional configuration parameter that contains the name of an XML
   * tag that appears in the input file. Only text that falls within this XML
   * tag will be considered part of the "document" that it is added to the CAS
   * by this CAS Initializer. If not specified, the entire file will be
   * considered the document.
   */
  public static final String PARAM_XMLTAG = "XmlTagContainingText";

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

  private SAXParserFactory parserFactory = SAXParserFactory.newInstance();

  private Type sourceDocInfoType;

  private String mXmlTagContainingText = null;

  private String mInputView = null, mOutputView = null;

  private String mFileExt = null;

  @Override
  public void initialize(UimaContext aContext)
      throws ResourceInitializationException {
    super.initialize(aContext);
    // Get config param setting
    mXmlTagContainingText = (String) getContext().getConfigParameterValue(
        PARAM_XMLTAG);

    mInputView = (String) getContext()
        .getConfigParameterValue(PARAM_INPUT_VIEW);
    mOutputView = (String) getContext().getConfigParameterValue(
        PARAM_OUTPUT_VIEW);
    mFileExt = (String) getContext()
        .getConfigParameterValue(PARAM_FILE_EXT);
}

  @Override
  public void typeSystemInit(TypeSystem aTypeSystem)
      throws AnalysisEngineProcessException {
    sourceDocInfoType = aTypeSystem
        .getType("org.apache.uima.examples.SourceDocumentInformation");
  }

  @Override
  public void process(CAS aCAS) throws AnalysisEngineProcessException {
    // get the original document view
    CAS origCas = aCAS.getView(mInputView);

    // get the src doc info
    String srcDocURIStr = null;
    FSIterator<AnnotationFS> iter;
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
    String fileText;
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
        // do process
        try {
          fileText = FileUtils.file2String(srcFile);
        } catch (IOException e) {
          throw new AnalysisEngineProcessException(e);
        }

        // set the document text in the original doc view
        origCas.setDocumentText(fileText);

        // get handle to CAS view containing XML document
        InputStream xmlStream = origCas.getSofa().getSofaDataStream();

        // parse with detag handler
        DetagHandler handler = new DetagHandler();
        try {
          SAXParser parser = parserFactory.newSAXParser();
          parser.parse(xmlStream, handler);
        } catch (Exception e) {
          throw new AnalysisEngineProcessException(e);
        }

        // create the plain text view and set its document text
        CAS plainTextView = aCAS.createView(mOutputView);
        plainTextView.setDocumentText(handler.getDetaggedText());

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

  class DetagHandler extends DefaultHandler {
    private StringBuffer detaggedText = new StringBuffer();
    private boolean insideTextTag;

    public DetagHandler() {
      insideTextTag = (mXmlTagContainingText == null);
    }

    public void startElement(String uri, String localName, String qName,
        Attributes attributes) throws SAXException {
      if (qName.equalsIgnoreCase(mXmlTagContainingText)) {
        insideTextTag = true;
      }
    }

    public void endElement(String uri, String localName, String qName)
        throws SAXException {
      if (qName.equalsIgnoreCase(mXmlTagContainingText)) {
        insideTextTag = false;
      }
    }

    public void characters(char[] ch, int start, int length)
        throws SAXException {
      if (insideTextTag) {
        detaggedText.append(ch, start, length);
      }
    }

    public void ignorableWhitespace(char[] ch, int start, int length)
        throws SAXException {
      if (insideTextTag) {
        detaggedText.append(ch, start, length);
      }
    }

    String getDetaggedText() {
      return detaggedText.toString();
    }
  }
}
