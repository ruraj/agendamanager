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

  /**
   * Initializes file loader with given file
   * @param filename Full path of file to load from
   * @throws FileNotFoundException
   */
  public RuleLoader(String filename) throws FileNotFoundException {
    File file = new File(filename);
    bufferedReader = new BufferedReader(new FileReader(file));
  }

  /**
   * Gets the list of rules in next line from file initialized in constructor.
   * @see RuleLoader
   * @return ArrayList of rules, null if EOF reached
   * @throws IOException
   */
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

      int priority = Integer.valueOf(things[1].trim());

      // Check if priority is between 1 and 100. Otherwise, ignore this rule.
      if (priority < 1 || priority > 100) continue;

      rule = new Rule(
              things[0].trim(),
              priority
      );

      rules.add(rule);
    }

    return rules;
  }
}
