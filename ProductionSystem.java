import java.io.*;
import java.util.ArrayList;

import parser.Parser;
import knowledge.*;
import facts.*;

class ProductionSystem {
  String goalName;

  public ProductionSystem(String goalName) {
    this.goalName = goalName;
  }

  public boolean forwardChaining() {
    ArrayList<Rule> CC = new ArrayList<Rule>();
    CC = equiparar(CC);

    forwardHeader(goalName);
    int i = 0;

    while ((!CC.isEmpty()) && (FactsBase.getInstace().getFact(goalName) == null)) {
      tableCell((i++)+"", 4);
      tableCell(iterationCC(CC), 19);
      tableCell(iterationBH(), 18);

      CC = resolver(CC);

      tableCell(goalName, 6);
      System.out.println("|");

      if (FactsBase.getInstace().getFact(goalName) == null) {
        CC.addAll(equiparar(CC));        
      } else {
        endTable();
        return true;
      }
    }
    endTable();
    return false;
  }

  private ArrayList<Rule> equiparar(ArrayList<Rule> CC) {
    ArrayList<Rule> ret = new ArrayList<Rule>();
    for (Rule r: KnowledgeBase.getInstace().getRules()) {
      if (!r.beenFired() && !CC.contains(r)) {
        if (r.parseRule(r.getCondition())) ret.add(r);
      }
    }
    return ret;
  }

  private ArrayList<Rule> resolver(ArrayList<Rule> CC) {
    Rule ruleToFire = CC.get(0);
    String factToAdd = ruleToFire.getAction();
    FactsBase.getInstace().addFact(factToAdd);
    ruleToFire.setFired(true);
    tableCell("R"+ruleToFire.getId(), 4);
    tableCell(factToAdd, 4);
    CC.remove(0);
    return CC;
  }

  private void forwardHeader(String goalName) {
    System.out.println("\n-- FORWARD CHAINING --");
    System.out.println("Goal: "+goalName+"\n");
    KnowledgeBase.getInstace().printRules();
    System.out.println();
    System.out.println("BH: "+iterationBH()+"\n");
    System.out.println(" ------------------------------------------------------------");
    System.out.println("| IT | CC                | BH               | R  | NH | Goal |");
    System.out.println(" ------------------------------------------------------------");
  }

  private void tableCell(String cad, int w) {
    System.out.print("| ");
    while(cad.length() < w-1) {
      cad += " ";
    }
    System.out.print(cad);
  }

  private String iterationCC(ArrayList<Rule> CC) {
    String ret = "{";
    for (Rule r: CC) {
      ret += "R"+r.getId()+", ";
    }
    return ret.substring(0, ret.length()-2) + "}";
  }

  private String iterationBH() {
    String ret = "{";
    for (Fact f: FactsBase.getInstace().getFacts()) {
      ret += f.getName()+", ";
    }
    return ret.substring(0, ret.length()-2) + "}";
  }

  private void endTable() {
    System.out.println(" ------------------------------------------------------------\n");
  }
}
