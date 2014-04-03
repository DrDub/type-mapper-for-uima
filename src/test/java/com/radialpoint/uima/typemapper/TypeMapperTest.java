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
import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.fit.component.CasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.jcas.JCas;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;



public class TypeMapperTest {
  @Before
  public void setUp() throws Exception {
    
  }

  @After
  public void tearDown() throws Exception {
	  
  }
    

  @Test
  public void testProcessCAS() throws UIMAException, IOException {
	  JCas jCas = JCasFactory.createJCas();
	  
	  AnalysisEngine analysisEngine = AnalysisEngineFactory.createEngine(
	    TypeMapper.class,
	    TypeMapper.CONFIG_FILE_NAME, "TypeMapperConfig.xml");
	    
	  analysisEngine.process(jCas);  
	  
	  assertEquals(0, 0);    
  }

}
