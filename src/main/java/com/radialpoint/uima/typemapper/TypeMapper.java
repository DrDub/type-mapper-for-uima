package com.radialpoint.uima.typemapper;

import javax.xml.bind.JAXBException;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CASException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

public class TypeMapper extends JCasAnnotator_ImplBase {

  private Rules rules;

  @Override
  public void initialize(UimaContext context) throws ResourceInitializationException {
    super.initialize(context);

    // TODO: put the path to the context config
    String fileName = "typeMapperConfig.xml";
    try {
      this.rules = RulesFileLoader.loadRulesFromFile(fileName);
    } catch (JAXBException e) {
      throw new ResourceInitializationException(e);
    }
  }

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    if (this.rules == null) {
      return;
    }

    for (Rule rule : this.rules.getRuleList()) {
      try {
        RuleProcessor.processRule(rule, aJCas);
      } catch (CASException e) {
        throw new AnalysisEngineProcessException(e);
      }
    }
  }
}
