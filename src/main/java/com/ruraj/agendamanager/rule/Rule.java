package com.ruraj.agendamanager.rule;

/**
 * Created by ruraj on 10/22/16.
 */
public class Rule {
  private String name;
  private int priority;

  public Rule(String name, int priority) {
    this.name = name;
    this.priority = priority;
  }

  public int getPriority() {
    return priority;
  }

  @Override
  public String toString() {
    return name + "->" + priority;
  }
}
