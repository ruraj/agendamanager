package com.ruraj.agendamanager.test;

import com.ruraj.agendamanager.Agenda;
import com.ruraj.agendamanager.AgendaMan;
import com.ruraj.agendamanager.listener.AgendaListener;
import com.ruraj.agendamanager.rule.Rule;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by ruraj on 10/31/16.
 */
public class RuntimePerformanceTest implements AgendaListener {

  private static AgendaMan agendaMan;
  private static ClassLoader classLoader;

  private int cycles = 0;

  private static Map<Integer, Long> results = new HashMap<>();

  private String filePath(String name) throws URISyntaxException, IOException {
    File temp = File.createTempFile("agendaman", "test");

    Files.copy(classLoader.getResourceAsStream(name), temp.toPath(), StandardCopyOption.REPLACE_EXISTING);

    return new URI(
//            classLoader.getResource(name).getPath()
            temp.getAbsolutePath()
    ).getPath();
  }

  private String filePathOnly(String name) throws URISyntaxException {
    return new URI(
            classLoader.getResource(name).getPath()
    ).getPath();
  }

  @BeforeClass
  public static void prepare() {
    agendaMan = new AgendaMan();
    // Just let it run :)
    agendaMan.setMaxCycles(Integer.MAX_VALUE);
    classLoader = RuntimePerformanceTest.class.getClassLoader();
  }

  @AfterClass
  public static void showResult() {
    System.out.println("input_size, running_time_ns");
    results.forEach((i, l) -> System.out.println(i + ", " + l));
  }

  private void testImpl(String file) {
    System.out.println("Testing " + file);

//    try {
    long startTime = System.nanoTime();
    agendaMan.run(
            file,
            this
    );
    long endTime = System.nanoTime();
    long diff = (endTime - startTime);
    System.out.println(String.format("Run time (%d cycles): %d\n", cycles, diff));
    results.put(cycles, diff);
//    } catch (URISyntaxException e) {
//      e.printStackTrace();
//      System.out.println("Continuing...");
//    }
    cycles = 0;
  }

  @Test
  public void testAll() throws URISyntaxException, IOException {
    CodeSource src = getClass().getProtectionDomain().getCodeSource();

    if (src.getLocation().toString().endsWith("jar")) {
      List<String> list = new ArrayList<String>();

      if (src != null) {
        URL jar = src.getLocation();
        ZipInputStream zip = new ZipInputStream(jar.openStream());
        ZipEntry ze = null;

        while ((ze = zip.getNextEntry()) != null) {
          String entryName = ze.getName();
          if (entryName.startsWith("runtime_test") && entryName.endsWith(".txt")) {
            list.add(entryName);
          }
        }

      }

      for (String s : list) {
        testImpl(filePath(s));
      }
    } else {
      File[] files = new File(filePathOnly("runtime_test")).listFiles((dir, name) -> {
        return name.endsWith(".txt");
      });

      for (File file : files) {
        testImpl(file.getPath());
      }
    }
  }

  @Override
  public void onCycleBegin(Rule rule, Agenda agenda) {

  }

  @Override
  public void onCycleEnd(Rule rule, Agenda agenda) {
    cycles++;
  }

  @Override
  public void onRuleDeleted(Rule rule, Agenda agenda) {
  }

  @Override
  public void onRuleAdded(List<Rule> ruleList, Agenda agenda) {
  }

  @Override
  public void onEmpty(Agenda agenda) {
    System.out.println(String.format("Rule list empty in cycle %d", cycles));
  }
}
