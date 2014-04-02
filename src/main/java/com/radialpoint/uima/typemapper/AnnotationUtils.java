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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

public class AnnotationUtils {

  private AnnotationUtils() {
  }

  public static void createAnnotation(JCas aJCas, String annotationNameToCreate, int beginFeatureValue,
          int endFeatureValue) throws AnalysisEngineProcessException {

    try {

      // TODO: review the usage of reflection here, use the basic functionality of CAS instead
      Class<Annotation> TgtClass = (Class<Annotation>) Class.forName(annotationNameToCreate);

      Constructor<?> tgtConstr = TgtClass.getConstructor(new Class[] { JCas.class });

      Object t = tgtConstr.newInstance(new Object[] { aJCas });

      Method setBegin = TgtClass.getMethod("setBegin", Integer.TYPE);
      Method setEnd = TgtClass.getMethod("setEnd", Integer.TYPE);

      setBegin.invoke(t, beginFeatureValue);
      setEnd.invoke(t, endFeatureValue);

      if (beginFeatureValue < endFeatureValue) {
        Method addToIndexes = TgtClass.getMethod("addToIndexes", new Class[] {});
        addToIndexes.invoke(t);
      } else {
        throw new AnalysisEngineProcessException("Begin and end features do not match", null);
      }

    } catch (Exception e) {
      throw new AnalysisEngineProcessException("Annotation creation failed!", null, e);
    }
  }
}
