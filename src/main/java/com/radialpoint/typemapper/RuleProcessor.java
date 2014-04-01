package com.radialpoint.typemapper;

import java.util.Iterator;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

public class RuleProcessor {

  private RuleProcessor() {
  }

  public static void processRule(Rule rule, JCas jcas) throws AnalysisEngineProcessException, CASException {
    String sourceTypeStr = rule.getInput();

    String targetTypeStr = rule.getOutput();

    Type typeSrc = jcas.getRequiredType(sourceTypeStr);
    AnnotationIndex<Annotation> idxSrc = (AnnotationIndex<Annotation>) jcas.getAnnotationIndex(typeSrc);
    Iterator<Annotation> itSrc = idxSrc.iterator();

    while (itSrc.hasNext()) {

      Annotation annotation = (Annotation) itSrc.next();

      AnnotationUtils.createAnnotation(jcas, targetTypeStr, annotation.getBegin(), annotation.getEnd());
    }

  }

}
