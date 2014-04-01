package com.radialpoint.typemapper;

import com.radialpoint.typemapper.Rule;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "rule" })
@XmlRootElement(name = "rules")
public class Rules {

  // TODO: Change "rule" to "rules" to better reflect the purpose of the member
  @XmlElement(required = true)
  private List<Rule> rule;

  public List<Rule> getRuleList() {
    if (this.rule == null) {
      this.rule = new ArrayList<Rule>();
    }
    return this.rule;
  }
}
