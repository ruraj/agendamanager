package com.ruraj.agendamanager;

import com.ruraj.agendamanager.listener.AgendaListener;
import com.ruraj.agendamanager.rule.Rule;
import com.ruraj.agendamanager.rule.RuleLoader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Created by ruraj on 10/22/16.
 */
public class AgendaMan {
  private static int MAX_CYCLES = 30;

  private static final String RULE_FILE = "input.txt";

  public static void main(String[] args) {
    new AgendaMan().run(RULE_FILE);
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
      int cycle = 0;
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
}
