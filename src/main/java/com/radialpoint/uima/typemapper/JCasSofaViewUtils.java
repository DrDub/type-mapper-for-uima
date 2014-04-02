package com.radialpoint.uima.typemapper;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.Type;
import org.apache.uima.jcas.JCas;

public class JCasSofaViewUtils {

  private JCasSofaViewUtils() {

  }

  public static boolean isTypeDefined(JCas aJCas, String typeString) {
    Type casType = aJCas.getTypeSystem().getType(typeString);
    return casType != null;
  }

}
