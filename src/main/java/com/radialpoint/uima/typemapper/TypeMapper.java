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

import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.bind.JAXBException;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CASException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

public class TypeMapper extends JCasAnnotator_ImplBase {

  public static final String CONFIG_FILE_NAME = "config-file-name";

  @ConfigurationParameter(name = CONFIG_FILE_NAME)
  private String configFileName;

  private Rules rules;

  @Override
  public void initialize(UimaContext context) throws ResourceInitializationException {
    super.initialize(context);

    try {
      InputStream stream = this.getClass().getResourceAsStream(configFileName);
      if (stream != null)
        this.rules =  RulesFileLoader.loadRulesFromStream(stream);
      else
        this.rules = RulesFileLoader.loadRulesFromFile(configFileName);
    } catch (JAXBException | FileNotFoundException e) {
      throw new ResourceInitializationException(e);
    }
  }

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {

    for (Rule rule : this.rules.getRuleList()) {
      try {
        RuleProcessor.processRule(rule, aJCas);
      } catch (CASException e) {
        throw new AnalysisEngineProcessException(e);
      }
    }
  }
}
