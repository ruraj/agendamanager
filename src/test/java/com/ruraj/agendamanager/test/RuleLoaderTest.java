package com.ruraj.agendamanager.test;

import com.ruraj.agendamanager.rule.Rule;
import com.ruraj.agendamanager.rule.RuleLoader;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruraj on 10/22/16.
 */
public class RuleLoaderTest {
  public List<Rule> getRules() throws IOException {
    RuleLoader loader = new RuleLoader(getClass().getClassLoader().getResource("input.txt").getPath());

    List<Rule> rules = new ArrayList<>();

    List<Rule> tRules;
    while ((tRules = loader.nextLine()) != null) {
      rules.addAll(tRules);
    }

    return rules;
  }

  @Test
  public void testRuleLoading() throws IOException {
    Assert.assertEquals("Not the number of expected rules loaded", 8, getRules().size());
  }
}
