package com.ruraj.agendamanager;

import com.ruraj.agendamanager.listener.AgendaListener;
import com.ruraj.agendamanager.rule.Rule;
import com.ruraj.agendamanager.rule.RuleLoader;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ruraj on 10/22/16.
 */
public class AgendaMan implements AgendaListener {
  private static int MAX_CYCLES = 30;

  private static final String RULE_FILE = "input.txt";
  private int cycle;

  public static void main(String[] args) {
    OptionParser parser = new OptionParser( "vi:c:" );

    OptionSet options = parser.parse( args );

    String ruleFile = options.has("i") ? (String) options.valueOf("i") : RULE_FILE;
    int maxCycles = options.has("c") ? Integer.valueOf((String) options.valueOf("c")) : MAX_CYCLES;
    boolean verbose = options.has("v");

    AgendaMan agendaMan = new AgendaMan();
    agendaMan.setMaxCycles(maxCycles);
    if (verbose) {
      agendaMan.run(ruleFile, agendaMan);
    } else {
      agendaMan.run(ruleFile);
    }
  }

  public void setMaxCycles(int maxCycles) {
    MAX_CYCLES = maxCycles;
  }

  public void run(String file) {
    run(file, null);
  }

  public void run(String file, AgendaListener listener) {
    try {
      RuleLoader ruleLoader = new RuleLoader(file);

      Agenda agenda = new Agenda();
      agenda.setAgendaListener(listener);

      List<Rule> rules;
      cycle = 0;
      while (cycle != MAX_CYCLES) {
        rules = ruleLoader.nextLine();
        if (rules != null) {
          agenda.add(rules);
        }

        if (agenda.cycle() == null) {
          break;
        }

        agenda.delete();
        cycle++;
      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onCycleBegin(Rule rule, Agenda agenda) {

  }

  @Override
  public void onCycleEnd(Rule rule, Agenda agenda) {
    System.out.println(String.format("\nEnd of %s (Cycle %d)", rule, cycle));
    System.out.println(String.format("Remaining (%d): %s", agenda.remaining().size(), Arrays.toString(agenda.remaining().toArray())));
  }

  @Override
  public void onRuleDeleted(Rule rule, Agenda agenda) {
    System.out.println(String.format("Deleted %s (Cycle %d)", rule, cycle));
    System.out.println(String.format("Remaining (%d): %s", agenda.remaining().size(), Arrays.toString(agenda.remaining().toArray())));
  }

  @Override
  public void onRuleAdded(List<Rule> ruleList, Agenda agenda) {
    System.out.println(String.format("Added (%d): %s", ruleList.size(), Arrays.toString(ruleList.toArray())));
  }

  @Override
  public void onEmpty(Agenda agenda) {
    System.out.println(String.format("Rule list empty in cycle %d", cycle));
  }
}
