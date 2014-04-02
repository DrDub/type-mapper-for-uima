package com.radialpoint.uima.typemapper;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.collections.CollectionUtils;

/***
 * <rules> <rule> <inputType/> <outputType/> </rule> </rules>
 ***/

public class RulesFileLoader {

  private RulesFileLoader(File urlRulesFile) {
  }

  public static Rules loadRulesFromFile(String fileResourceName) throws JAXBException {

    Rules rules = null;

    // Get a stream from path
    InputStreamReader stream = null;
    InputStream inputStream = RulesFileLoader.class.getResourceAsStream(fileResourceName);
    stream = new InputStreamReader(inputStream);

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
