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

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.Feature;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.TypeSystem;
import org.apache.uima.cas.admin.CASFactory;
import org.apache.uima.cas.admin.CASMgr;
import org.apache.uima.cas.admin.FSIndexComparator;
import org.apache.uima.cas.admin.FSIndexRepositoryMgr;
import org.apache.uima.cas.admin.TypeSystemMgr;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.apache.uima.util.CasCreationUtils;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.cas.text.AnnotationIndex;
import org.codehaus.jackson.format.InputAccessor.Std;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TypeMapperTest {

  // The functions setUp() and initCAS() were taken from
  // IndexSerializationTest.java by apache / uima-uimaj / uimaj-2.2.1 / uimaj-2.2.1-incubating
  // TODO: Go over the code and remove unused entities/lines

  public static final String ANNOT_SET_INDEX = "Annotation Set Index";

  public static final String ANNOT_BAG_INDEX = "Annotation Bag Index";

  public static final String TOKEN_TYPE = "Token";

  public static final String TOKEN_TYPE_FEAT = "type";

  public static final String TOKEN_TYPE_FEAT_Q = TOKEN_TYPE + TypeSystem.FEATURE_SEPARATOR + TOKEN_TYPE_FEAT;

  public static final String TOKEN_TYPE_TYPE = "TokenType";

  public static final String WORD_TYPE = "Word";

  public static final String SEP_TYPE = "Separator";

  public static final String EOS_TYPE = "EndOfSentence";

  public static final String SENT_TYPE = "Sentence";

  public static final String PHRASE_TYPE = "Phrase";

  private CASMgr casMgr;

  private CAS cas;

  private JCas jCas;

  private Type annotationType;

  private Type wordType;

  private Type separatorType;

  private Type eosType;

  private Type tokenType;

  private Feature tokenTypeFeature;

  private Type sentenceType;

  private Feature startFeature;

  private Feature endFeature;

  @Before
  public void setUp() throws Exception {

    casMgr = initCAS();
    cas = (CASImpl) casMgr;

    TypeSystem ts = cas.getTypeSystem();
    wordType = ts.getType(WORD_TYPE);
    // assert(wordType != null);
    separatorType = ts.getType(SEP_TYPE);
    eosType = ts.getType(EOS_TYPE);
    tokenType = ts.getType(TOKEN_TYPE);
    tokenTypeFeature = ts.getFeatureByFullName(TOKEN_TYPE_FEAT_Q);
    startFeature = ts.getFeatureByFullName(CAS.FEATURE_FULL_NAME_BEGIN);
    endFeature = ts.getFeatureByFullName(CAS.FEATURE_FULL_NAME_END);
    sentenceType = ts.getType(SENT_TYPE);
    annotationType = ts.getType(CAS.TYPE_NAME_ANNOTATION);
    assert (annotationType != null);

    jCas = cas.getJCas();
  }

  // Initialize the first CAS.
  private static CASMgr initCAS() throws ResourceInitializationException {
    // Create a CASMgr. Ensures existence of AnnotationFS type.
    CASMgr casMgr = CASFactory.createCAS();
    CasCreationUtils.setupTypeSystem(casMgr, (TypeSystemDescription) null);

    // Create a writable type system.
    TypeSystemMgr tsa = casMgr.getTypeSystemMgr();

    // Add new types and features.
    // TODO: go through it and remove the lines we do not need
    Type topType = tsa.getTopType();
    Type annotType = tsa.getType(CAS.TYPE_NAME_ANNOTATION);

    tsa.addType(SENT_TYPE, annotType);
    tsa.addType(PHRASE_TYPE, annotType);

    Type tokenType = tsa.addType(TOKEN_TYPE, annotType);
    Type tokenTypeType = tsa.addType(TOKEN_TYPE_TYPE, topType);
    tsa.addType(WORD_TYPE, tokenTypeType);
    tsa.addType(SEP_TYPE, tokenTypeType);
    tsa.addType(EOS_TYPE, tokenTypeType);
    tsa.addFeature(TOKEN_TYPE_FEAT, tokenType, tokenTypeType);

    // Commit the type system.
    ((CASImpl) casMgr).commitTypeSystem();

    // Create the Base indexes.
    try {
      casMgr.initCASIndexes();
    } catch (CASException e) {
      e.printStackTrace();
    }

    FSIndexRepositoryMgr irm = casMgr.getIndexRepositoryMgr();
    FSIndexComparator comp = irm.createComparator();
    Type annotation = tsa.getType(CAS.TYPE_NAME_ANNOTATION);
    comp.setType(annotation);
    comp.addKey(annotation.getFeatureByBaseName(CAS.FEATURE_BASE_NAME_BEGIN), FSIndexComparator.STANDARD_COMPARE);
    comp.addKey(annotation.getFeatureByBaseName(CAS.FEATURE_BASE_NAME_END), FSIndexComparator.REVERSE_STANDARD_COMPARE);
    irm.createIndex(comp, ANNOT_BAG_INDEX, FSIndex.BAG_INDEX);
    irm.createIndex(comp, ANNOT_SET_INDEX, FSIndex.SET_INDEX);

    // Commit the index repository.
    irm.commit();

    // Create the default text Sofa and return CAS view
    return (CASMgr) casMgr.getCAS().getCurrentView();
  }

  @Test(expected = AnalysisEngineProcessException.class)
  public void UnsupportedInputTypes() throws ResourceInitializationException, AnalysisEngineProcessException {

    AnalysisEngine analysisEngineWithUnsupported = AnalysisEngineFactory.createEngine(TypeMapper.class,
            TypeMapper.CONFIG_FILE_NAME,
            "src/test/resources/com/radialpoint/uima/typemapper/TypeMapperConfig_unsupported.xml");
    analysisEngineWithUnsupported.process(jCas);
  }

  @Test
  public void SupportedInputTypes() throws ResourceInitializationException, AnalysisEngineProcessException,
          CASException {

    int begin = 0, end = 5;

    Annotation sentenceAnnotation = (Annotation) cas.createAnnotation(sentenceType, begin, end);
    sentenceAnnotation.addToIndexes();

    AnalysisEngine analysisEngine = AnalysisEngineFactory.createEngine(TypeMapper.class, TypeMapper.CONFIG_FILE_NAME,
            "src/test/resources/com/radialpoint/uima/typemapper/TypeMapperConfig.xml");

    SimplePipeline.runPipeline(cas, analysisEngine);

    AnnotationIndex<AnnotationFS> annotationIdx = cas.getAnnotationIndex(sentenceType);
    assert (annotationIdx != null);

    FSIterator<AnnotationFS> fsIter = annotationIdx.iterator();

    while (fsIter.isValid()) {
      AnnotationFS annotation = fsIter.get();
      assertEquals(begin, annotation.getBegin());
      assertEquals(end, annotation.getEnd());
      fsIter.moveToNext();
    }

    assertEquals(1, annotationIdx.size());

  }
}
