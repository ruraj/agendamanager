package com.ruraj.agendamanager.rule;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ruraj on 10/22/16.
 */
public class RuleLoader {

  private final BufferedReader bufferedReader;
  private Pattern rulePattern = Pattern.compile("\\((.*?)\\)");

  public RuleLoader(String filename) throws FileNotFoundException {
    File file = new File(filename);
    bufferedReader = new BufferedReader(new FileReader(file));
  }

  public List<Rule> nextLine() throws IOException {
    List<Rule> results = new ArrayList<>();

    String line;
    line = bufferedReader.readLine();
    if (line == null) {
      return null;
    }

    results.addAll(parseLine(line));

    return results;
  }

  private List<Rule> parseLine(String line) {
    List<Rule> rules = new ArrayList<>();

    Matcher matcher = rulePattern.matcher(line);

    while (matcher.find()) {
      String match = matcher.group();
      Rule rule;
      String[] things = match.substring(1, match.length() - 1).split(",");

      rule = new Rule(
              things[0].trim(),
              Integer.valueOf(things[1].trim())
      );

      rules.add(rule);
    }

    return rules;
  }
}
