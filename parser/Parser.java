package parser;

import java.io.*;
import java.util.Scanner;

import knowledge.KnowledgeBase;
import facts.FactsBase;

public class Parser {

  public static void fileParser(String path) throws IOException {
    BufferedReader bf;
    try {
      bf = new BufferedReader(new FileReader(new File(path)));
    } catch(Exception e) {
      return;
    }

    String st;
    Scanner s;
    while ((st = bf.readLine()) != null) {
        if(st.trim().length() == 0) continue;
        if(st.charAt(0) == '#') continue; // '#' are used for inline comments

        s = new Scanner(st); // Used to see wich section of the file is about to be parsed

        String aux = "";
        switch (s.next()) {
          case "RULES": // Parses all the rules of the file, until a '}'
            while (((st = bf.readLine()) != null) && (!st.equals("}")) ) {
              if(st.trim().length() == 0) continue;
              if(st.charAt(0) == '#') continue;
              aux += st;
            }
            for (String rule: aux.split(",")) { // Adds the rules to the KnowledgeBase
              KnowledgeBase.getInstace().addRule(rule);
            }
            break;

          case "FACTS": // Parses all the facts of the file, until a '}'
            while (((st = bf.readLine()) != null) && (!st.equals("}")) ) {
              if(st.trim().length() == 0) continue;
              if(st.charAt(0) == '#') continue;
              aux += st;
            }
            for (String fact: aux.split(",")) { // Adds the facts to the FactsBase
              FactsBase.getInstace().addFact(fact);
            }
            break;
        }
    }

  }

}
