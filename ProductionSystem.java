import parser.Parser;
import java.io.*;

import knowledge.KnowledgeBase;
import facts.*;

class ProductionSystem {
  public static void main(String[] args) {
    try {
      Parser.fileParser("./ejemplo0.txt");

      KnowledgeBase.getInstace().printRules();
      FactsBase.getInstace().printFacts();
    } catch(IOException e) {
      System.out.println(e);
    }
  }

  public boolean forwardChaining(Fact goal) {
    
  }
}
