package com.ruraj.agendamanager.test;

import com.ruraj.agendamanager.queue.AMHeap;
import com.ruraj.agendamanager.rule.Rule;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by ruraj on 10/22/16.
 */
public class HeapifyTest {
  @Test
  public void testHeapify() throws IOException {
    AMHeap heap = new AMHeap();

    heap.add(new RuleLoaderTest().testRuleLoading());

    heap.heapify();

    Rule maxRule = heap.get();

    Assert.assertEquals("Not the expected rule on top", 100, maxRule.getPriority());
  }

  @Test
  public void testDelete() throws IOException {
    AMHeap heap = new AMHeap();

    heap.add(new RuleLoaderTest().testRuleLoading());

    heap.heapify();
    heap.delete();

    Rule maxRule = heap.get();

    Assert.assertEquals("Not the expected rule on top", 12, maxRule.getPriority());
  }
}
