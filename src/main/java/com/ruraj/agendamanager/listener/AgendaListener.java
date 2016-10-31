package com.ruraj.agendamanager.listener;

import com.ruraj.agendamanager.Agenda;
import com.ruraj.agendamanager.rule.Rule;

import java.util.List;

/**
 * Created by ruraj on 10/31/16.
 */
public interface AgendaListener {
  void onCycleBegin(Rule rule, Agenda agenda);
  void onCycleEnd(Rule rule, Agenda agenda);
  void onRuleDeleted(Rule rule, Agenda agenda);
  void onRuleAdded(List<Rule> ruleList, Agenda agenda);
  void onEmpty(Agenda agenda);
}
