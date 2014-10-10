package edu.ucdenver.ccp.nlp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import org.apache.uima.resource.DataResource;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.SharedResourceObject;

public class KeywordListResource_impl implements KeywordListResource,
    SharedResourceObject {
  private Set<String> mSet = new HashSet<String>();

  @Override
  public void load(DataResource aData) throws ResourceInitializationException {
    InputStream inStr = null;
    try {
      // open input stream to data
      inStr = aData.getInputStream();
      // read each line
      BufferedReader reader = new BufferedReader(new InputStreamReader(inStr));
      String line;
      while ((line = reader.readLine()) != null) {
        // one keyword per line
        mSet.add(line.trim());
      }
    } catch (IOException e) {
      throw new ResourceInitializationException(e);
    } finally {
      if (inStr != null) {
        try {
          inStr.close();
        } catch (IOException e) {
          // TODO Log this as level INFO
        }
      }
    }
  }

  @Override
  public boolean isKeyword(String aWord) {
    return mSet.contains(aWord);
  }

}
