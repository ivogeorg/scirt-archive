scirt-archive
=============

# Overview
This repository contains an abandoned [UIMA](https://uima.apache.org/index.html)-based corpus pipeline for annotation of scientific publications in the domain of spinal cord injury and regeneration. It is a legacy solution based on plain vanilla UIMA only (as opposed to [uimaFIT](https://uima.apache.org/uimafit.html)) and was used to create the corpus SCIRT 0.0.1. SCIRT stands for **S**pinal **C**ord **I**njury and **R**egeneration **T**exts. The repository is meant to be a frozen archive. This line of development is no longer pursued.

# Details
1. My work on SCIRT can be found [here](https://github.com/ivogeorg/scirt-archive)
2. The original PDFs as well as the processed data is [here](https://github.com/ivogeorg/scirt-archive/tree/master/data/scir-pdf)
  * brat_output contains brat-annotations (.ann files).
  * filesyswr_output contains the text scrapped by PDFBox from the PDFs.
  * xmiwr_output contains annotations in XMI format.
3. The pipeline description file is [XmlToTextCPE.xml](descriptors/ccp/XmlToTextCPE.xml), showing what collection reader, annotation, and writer stages are employed. Detailes can be found in the corresponding XML properties files in the [descriptors](descriptors) directory. Summary:
  * XML Reader Detagger (UIMA): A multi-sofa annotator that does XML detagging. Reads XML data from the input Sofa (named "originalDoc"). This data can be stored in the CAS as a string or array, or it can be a URI to a remote file. The XML is parsed using the JVM's default parser, and the plain-text content is written to a new sofa called "convertedDoc".
  * PDF To Text Converter (PDFBox): Uses org.apache.pdfbox.util.PDFTextStripper to extract the text contents from a PDF file. Sets the sofa data of the plainTextDocument view with the extracted text.
  * Sentence Detector (OpenNLP): The OpenNLP Sentence Detector can detect that a punctuation character marks the end of a sentence or not.
  * Tokenizer (OpenNLP): The interface for tokenizers, which segment a string into its tokens.
  * NER Annotator ([BANNER](http://banner.sourceforge.net/)): Gene names.
  * NER Annotator ([LINNAEUS](http://linnaeus.sourceforge.net/)): Species name recognition and normalization.
  * Interaction Keyword Annotator (CCP): See the original [publication](https://bmcbioinformatics.biomedcentral.com/articles/10.1186/1471-2105-10-233) and browse the keyword [file](resources/bmc/12859_2009_2963_MOESM1_ESM.txt).
  * Interaction Annotator (CCP): Naive interaction implementation: co-occurrence of one interaction keyword and two gene/protein names in the same sentence.
  * File System Writer (CCP): A simple cas consumer which takes the text document view and writes its sofa text to a text file, using an output directory in the parent directory of the source file and appending a text file extension to the source file name.
  * XMI Writer (UIMA):Writes the CAS to XMI format.
  * Brat Annotation Writer (CCP): Outputs annotations in the [standoff format](http://brat.nlplab.org/standoff.html) expected by the [brat](http://brat.nlplab.org/index.html) annotation and visualization tool.
4. Sources are here https://github.com/ivogeorg/scirt-archive/tree/master/src
5. There are various configuration files and resources here and there, where third-party annotator packages put and expect them.
6. The pipeline was implemented in base UIMA, not in uimaFIT. The implementation roughly follows Chapter 8 of Kumar and Tipney's "Biomedical Literature Mining" ("Mining Biological Networks from Full-Text Articles", Jan Czarnecki and Adrian J. Shepherd)

# Platform
This project was developed on Windows 8 64-bit, Eclipse Kepler 64-bit, Java 7. Eclipse workspace settings will contain Windows paths. Not run on Mac as of this version.

# Corpus
The coprus can be found under [data](data). The files can be visulized in [brat](http://brat.nlplab.org/). 
