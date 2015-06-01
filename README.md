scirt-archive
=============

# Overview
This repository contains a first trial version of a UIMA-based corpus pipeline for scientific publications. It is a legacy solution based on UIMA only (as opposed to uimaFIT) and was used to create the corpus SCIRT 0.0.1. The repository is meant to be a frozen archive. Subsequent development of the coprus pipeline and the SCIRT corpus can be found in https://github.com/UCDenver-ccp/scirt-dev.

# Details
1. My work on SCIRT can be found [here](https://github.com/UCDenver-ccp/scirt-archive)
2. The original PDFs as well as the processed data is [here](https://github.com/UCDenver-ccp/scirt-archive/tree/master/data/scir-pdf)
  * brat_output contains brat-annotations (.ann files).
  * filesyswr_output contains the text scrapped by PDFBox from the PDFs.
  * xmiwr_output contains annotations in XMI format.
3. The pipeline description file is [XmlToTextCPE.xml](../blob/master/descriptors/ccp/XmlToTextCPE.xml). You can see what collection reader, annotation, and writer stages are employed and where to find them.
4. Sources are here https://github.com/UCDenver-ccp/scirt-archive/tree/master/src
5. There are various configuration files and resources here and there, where third-party annotator packages put and expect them.
6. The pipeline was implemented in base UIMA, not in uimaFIT. The implementation roughly follows Chapter 8 of Kumar and Tipney's "Biomedical Literature Mining" ("Mining Biological Networks from Full-Text Articles", Jan Czarnecki and Adrian J. Shepherd)

# Platform
This project was developed on Windows 8 64-bit, Eclipse Kepler 64-bit, Java 7. Eclipse workspace settings will contain Windows paths. Not run on Mac as of this version.

# Corpus
The coprus can be found under [data](../tree/master/data). The files can be visulized in [brat](http://brat.nlplab.org/). 
