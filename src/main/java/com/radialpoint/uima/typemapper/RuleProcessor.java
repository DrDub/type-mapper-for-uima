//	Type Mapper for UIMA
//	Copyright (C) 2014 Radialpoint Inc. radialpoint.com
//	
//	Licensed to the Apache Software Foundation (ASF) under one
//	or more contributor license agreements.  See the NOTICE file
//	distributed with this work for additional information
//	regarding copyright ownership.  The ASF licenses this file
//	to you under the Apache License, Version 2.0 (the
//	"License"); you may not use this file except in compliance
//	with the License.  You may obtain a copy of the License at
//	
//	  http://www.apache.org/licenses/LICENSE-2.0
//	
//	Unless required by applicable law or agreed to in writing,
//	software distributed under the License is distributed on an
//	"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
//	KIND, either express or implied.  See the License for the
//	specific language governing permissions and limitations
//	under the License.

package com.radialpoint.uima.typemapper;

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

    Type sourceType = jcas.getRequiredType(sourceTypeStr);
    Type targetType = jcas.getRequiredType(targetTypeStr);

    AnnotationIndex<Annotation> idxSrc = (AnnotationIndex<Annotation>) jcas.getAnnotationIndex(sourceType);
    Iterator<Annotation> itSrc = idxSrc.iterator();

    while (itSrc.hasNext()) {

      Annotation annotation = (Annotation) itSrc.next();

      AnnotationUtils.createAnnotation(jcas, targetType, annotation.getBegin(), annotation.getEnd());
    }

  }

}
