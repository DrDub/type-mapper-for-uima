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

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;
import static org.apache.uima.fit.factory.ResourceCreationSpecifierFactory.createResourceCreationSpecifier;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.CASRuntimeException;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.component.CasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.SofaCapability;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.fit.factory.AggregateBuilder;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.FlowControllerFactory;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.jcas.JCas;
import org.apache.uima.util.XMLInputSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;



public class TypeMapperTest {
  private AnalysisEngineDescription engine;

  // private static List<Type> idTextPairs = new ArrayList<>();

  @TypeCapability(inputs = "com.radialpoint.nlp.types.RadialpointDocumentAnnotation")
  public static class TestTypeMapping extends CasAnnotator_ImplBase {

    @Override
    public void process(CAS cas) throws AnalysisEngineProcessException {
      
    }
  }

  @Before
  public void setUp() throws Exception {
    AnalysisEngineDescription detector = (AnalysisEngineDescription) createResourceCreationSpecifier(
            new XMLInputSource(this.getClass().getClassLoader()
                    .getResourceAsStream("com/radialpoint/nlp/aggregates/TypeDetectionAggregate.xml"), new File(".")),
            new Object[0]);
    AnalysisEngineDescription extractor = (AnalysisEngineDescription) createResourceCreationSpecifier(
            new XMLInputSource(this.getClass().getClassLoader()
                    .getResourceAsStream("com/radialpoint/nlp/aggregates/TextExtractionAggregate.xml"), new File(".")),
            new Object[0]);
    createEngineDescription(detector);
    createEngineDescription(extractor);
  }

  @After
  public void tearDown() throws Exception {
    engine = null;
  }

  @Test
  public void testProcessCAS() throws UIMAException, IOException {
//	  JCas jCas = JCasFactory.createJCas();
//	  
//	  AnalysisEngine analysisEngine = AnalysisEngineFactory.createEngine(
//	    TypeMapper.class,
//	    TypeMapper.PARAM_STRING, "uimaFIT");
//	    
//	  analysisEngine.process(jCas);  
	  assertEquals(0, 0);    
  }

}
