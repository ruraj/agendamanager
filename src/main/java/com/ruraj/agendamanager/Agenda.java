package com.ruraj.agendamanager;

import com.ruraj.agendamanager.queue.AMHeap;
import com.ruraj.agendamanager.rule.Rule;

import java.util.List;

/**
 * Created by ruraj on 10/22/16.
 */
public class Agenda {

  private AMHeap heap = new AMHeap();

  public void add(List<Rule> rules) {
    heap.add(rules);
    heap.heapify();
  }

  public void delete() {
    heap.delete();
    heap.heapify();
  }

  public Rule cycle() {
    // Get top priority rule
    Rule topRule = heap.get();

    // If there aren't any
    if (topRule == null) {
      System.out.println("Heap is empty");
      return null;
    }

    // Execute (Print) it
    System.out.println(topRule);

    return topRule;
  }
}
