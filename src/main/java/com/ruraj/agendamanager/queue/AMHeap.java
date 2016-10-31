package com.ruraj.agendamanager.queue;

import com.ruraj.agendamanager.rule.Rule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruraj on 10/22/16.
 */
public class AMHeap {
  private List<Rule> ruleList = new ArrayList<>();

  public void add(List<Rule> rules) {
    ruleList.addAll(rules);
  }

  public void add(Rule rule) {
    this.ruleList.add(rule);
  }

  public Rule delete() {
    Rule deleted = ruleList.get(0);
    ruleList.set(0, ruleList.get(ruleList.size() - 1));
    ruleList.remove(ruleList.size() -1 );
    return deleted;
  }

  public List<Rule> asList() {
    return ruleList;
  }

  public void heapify() {
    for (int i = (ruleList.size() - 1)/2; i >= 0; i--) {
      heapifyImpl(i);
    }
  }

  private void heapifyImpl(int i) {
    int left = (2 * i) + 1;
    int right = (2 * i) + 2;
    int heapSize = ruleList.size();
    int largest;

    if (left < heapSize && ruleList.get(left).getPriority() > ruleList.get(i).getPriority()) {
      largest = left;
    } else {
      largest = i;
    }

    if (right < heapSize && ruleList.get(right).getPriority() > ruleList.get(largest).getPriority()) {
      largest = right;
    }

    if (largest != i) {
      Rule temp = ruleList.get(i);
      ruleList.set(i, ruleList.get(largest));
      ruleList.set(largest, temp);
      heapifyImpl(largest);
    }
  }

  public Rule get() {
    if (ruleList.size() > 0) {
      return ruleList.get(0);
    } else {
      return null;
    }
  }
}
