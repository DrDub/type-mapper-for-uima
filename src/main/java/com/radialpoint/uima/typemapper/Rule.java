package com.radialpoint.uima.typemapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "input", "output" })
@XmlRootElement(name = "rule")
public class Rule {

  @XmlElement(required = true)
  private String input;

  @XmlElement(required = true)
  private String output;

  public String getInput() {
    return input.trim();
  }

  public String getOutput() {
    return output.trim();
  }
}
