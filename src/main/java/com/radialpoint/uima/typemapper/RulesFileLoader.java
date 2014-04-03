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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.collections.CollectionUtils;

/***
 * <rules> <rule> <inputType/> <outputType/> </rule> </rules>
 ***/

public class RulesFileLoader {

  private RulesFileLoader() {
  }

  public static Rules loadRulesFromFile(String fileResourceName) throws JAXBException, FileNotFoundException {

    Rules rules = null;

    // Get a stream from path
    FileInputStream stream = new FileInputStream  (fileResourceName);
    
    // Unmarshal
    JAXBContext jc = JAXBContext.newInstance(Rules.class, Rule.class);
    Unmarshaller unmarshaller = jc.createUnmarshaller();
    rules = (Rules) unmarshaller.unmarshal(stream);

    if ( rules == null || CollectionUtils.isEmpty(rules.getRuleList()) ) {
      throw new JAXBException("Rules list is empty. Binding error?");
    }

    return rules;
  }
}
