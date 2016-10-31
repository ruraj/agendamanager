package com.ruraj.agendamanager.test;

import com.ruraj.agendamanager.Agenda;
import com.ruraj.agendamanager.AgendaMan;
import com.ruraj.agendamanager.listener.AgendaListener;
import com.ruraj.agendamanager.rule.Rule;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ruraj on 10/31/16.
 */
public class AgendaManTest implements AgendaListener {

  private static AgendaMan agendaMan;
  private static ClassLoader classLoader;

  private int cycles = 0;

  private String filePath(String name) throws URISyntaxException {
    return new URI(
            classLoader.getResource(name).getPath()
    ).getPath();
  }

  @BeforeClass
  public static void prepare() {
    agendaMan = new AgendaMan();
    classLoader = AgendaManTest.class.getClassLoader();
  }

  private void testImpl(String file) {
    System.out.println("Running on " + file);
    try {
      agendaMan.run(
              filePath("test/" + file),
              this
      );
    } catch (URISyntaxException e) {
      e.printStackTrace();
      System.out.println("Continuing...");
    }
    cycles = 0;
  }

  @Test
  public void testFileI() throws URISyntaxException {
    testImpl("test1.txt");
  }

  @Test
  public void testFileII() throws URISyntaxException {
    testImpl("test2.txt");
  }

  @Test
  public void testFileIII() throws URISyntaxException {
    testImpl("test3.txt");
  }

  @Override
  public void onCycleBegin(Rule rule, Agenda agenda) {

  }

  @Override
  public void onCycleEnd(Rule rule, Agenda agenda) {
    cycles++;
    System.out.println(String.format("\nEnd of %s (Cycle %d)", rule, cycles));
    System.out.println(String.format("Remaining (%d): %s", agenda.remaining().size(), Arrays.toString(agenda.remaining().toArray())));
  }

  @Override
  public void onRuleDeleted(Rule rule, Agenda agenda) {
    System.out.println(String.format("Deleted %s (Cycle %d)", rule, cycles));
    System.out.println(String.format("Remaining (%d): %s", agenda.remaining().size(), Arrays.toString(agenda.remaining().toArray())));
  }

  @Override
  public void onRuleAdded(List<Rule> ruleList, Agenda agenda) {
    System.out.println(String.format("Added (%d): %s", ruleList.size(), Arrays.toString(ruleList.toArray())));
  }

  @Override
  public void onEmpty(Agenda agenda) {
    System.out.println(String.format("Rule list empty in cycle %d", cycles));
  }
}
