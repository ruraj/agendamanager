package com.ruraj.agendamanager;

import com.ruraj.agendamanager.listener.AgendaListener;
import com.ruraj.agendamanager.queue.AMHeap;
import com.ruraj.agendamanager.rule.Rule;

import java.util.List;

/**
 * Created by ruraj on 10/22/16.
 */
public class Agenda {

  private AMHeap heap = new AMHeap();
  private AgendaListener listener;

  public void setAgendaListener(AgendaListener listener) {
    this.listener = listener;
  }

  public void add(List<Rule> rules) {
    heap.add(rules);
    heap.heapify();
    if (listener != null) {
      listener.onRuleAdded(rules, this);
    }
  }

  public void delete() {
    Rule deletedRule = heap.delete();
    heap.heapify();
    if (listener != null) {
      listener.onRuleDeleted(deletedRule, this);
    }
  }

  public List<Rule> remaining() {
    return heap.asList();
  }

  public Rule cycle() {
    // Get top priority rule
    Rule topRule = heap.get();

    // If there aren't any
    if (topRule == null) {
      if (listener != null) {
        listener.onEmpty(this);
      }
      return null;
    }

    if (listener != null) {
      listener.onCycleBegin(topRule, this);
    }

    // Execute (Print) it. Ok, nothing right now

    if (listener != null) {
      listener.onCycleEnd(topRule, this);
    }

    return topRule;
  }
}
