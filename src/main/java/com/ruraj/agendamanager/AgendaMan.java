package com.ruraj.agendamanager;

import com.ruraj.agendamanager.rule.Rule;
import com.ruraj.agendamanager.rule.RuleLoader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Created by ruraj on 10/22/16.
 */
public class AgendaMan {
  private static final int MAX_CYCLES = 30;

  private static final String RULE_FILE = "input.txt";

  public static void main(String[] args) {

    try {
      RuleLoader ruleLoader = new RuleLoader(RULE_FILE);

      Agenda agenda = new Agenda();

      List<Rule> rules;
      int cycle = 0;
      while ( (rules = ruleLoader.nextLine()) != null || cycle != MAX_CYCLES) {
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
