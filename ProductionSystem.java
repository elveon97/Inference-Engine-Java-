import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

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
    CC = equipararForward(CC);

    forwardHeader(goalName);
    int i = 0;

    while ((!CC.isEmpty()) && (FactsBase.getInstace().getFact(goalName) == null)) {
      tableCell((i++)+"", 4);
      tableCell(iterationCC(CC), 19);
      tableCell(iterationBH(), 18);

      CC = resolverForward(CC);

      tableCell(goalName, 6);
      System.out.println("|");

      if (FactsBase.getInstace().getFact(goalName) == null) {
        CC.addAll(equipararForward(CC));
      } else {
        endTable();
        return true;
      }
    }
    endTable();
    return false;
  }

  public boolean backwardChaining() {
    backwardHeader(goalName);
    boolean ban = verificarBackward(goalName);
    endTable();
    return ban;
  }

  public boolean verificarBackward(String goalName) {
    boolean verificado = false;
    if (FactsBase.getInstace().getFact(goalName) != null) {
      return true;
    } else {
      ArrayList<Rule> CC = new ArrayList<Rule>();
      CC = equipararBackward(CC, goalName);

      while ((!CC.isEmpty()) && (!verificado)) {
        tableCell(iterationCC(CC), 20);

        Rule ruleToFire = CC.get(0);
        CC.remove(0);
        ArrayList<String> NM = new ArrayList<String>();
        NM = extraerAntecedentes(ruleToFire);

        tableCell("R"+ruleToFire.getId(), 4);

        if (NM.isEmpty()) {
          tableCell(" ", 14);
          tableCell(" ", 6);
        }
        else {
          tableCell(iterationNM(NM), 14);
          tableCell(NM.get(0), 6);
        }
        tableCell(iterationBH(), 17);
        System.out.println("|");

        verificado = true;
        while ((!NM.isEmpty()) && verificado) {
          String goal = NM.get(0);
          NM.remove(0);
          verificado = verificarBackward(goal);
          if (verificado) {
            if (FactsBase.getInstace().getFact(goal) == null) FactsBase.getInstace().addFact(goal);
          }
          if (ruleToFire.parseRule(ruleToFire.getCondition())) {
            if (FactsBase.getInstace().getFact(ruleToFire.getAction()) == null) FactsBase.getInstace().addFact(ruleToFire.getAction());
            verificado = true;
          }
        }
      }
      return verificado;
    }
  }

  private ArrayList<Rule> equipararForward(ArrayList<Rule> CC) {
    ArrayList<Rule> ret = new ArrayList<Rule>();
    for (Rule r: KnowledgeBase.getInstace().getRules()) {
      if (!r.beenFired() && !CC.contains(r)) {
        if (r.parseRule(r.getCondition())) ret.add(r);
      }
    }
    return ret;
  }

  private ArrayList<Rule> equipararBackward(ArrayList<Rule> CC, String factName) {
    ArrayList<Rule> ret = new ArrayList<Rule>();
    for (Rule r: KnowledgeBase.getInstace().getRules()) {
      if (!r.beenFired() && !CC.contains(r)) {
        if (r.getAction().equals(factName)) ret.add(r);
      }
    }
    return ret;
  }

  private ArrayList<Rule> resolverForward(ArrayList<Rule> CC) {
    Rule ruleToFire = CC.get(0);
    String factToAdd = ruleToFire.getAction();
    FactsBase.getInstace().addFact(factToAdd);
    ruleToFire.setFired(true);
    tableCell("R"+ruleToFire.getId(), 4);
    tableCell(factToAdd, 4);
    CC.remove(0);
    return CC;
  }

  private ArrayList<Rule> resolverBackward(ArrayList<Rule> CC) {
    Rule ruleToFire = CC.get(0);
    String factToAdd = ruleToFire.getAction();
    FactsBase.getInstace().addFact(factToAdd);
    ruleToFire.setFired(true);
    tableCell("R"+ruleToFire.getId(), 4);
    tableCell(factToAdd, 4);
    CC.remove(0);
    return CC;
  }

  public ArrayList<String> extraerAntecedentes(Rule rule) {
    ArrayList<String> ret = new ArrayList<String>();
    Scanner s = new Scanner(rule.getCondition());
    while (s.hasNext()) {
      String str = s.next();
      if (!str.toUpperCase().equals("AND") && !str.toUpperCase().equals("OR")) {
        if (FactsBase.getInstace().getFact(str) == null) {
          ret.add(str);
        }
      }
    }
    return ret;
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

  private void backwardHeader(String goalName) {
    System.out.println("\n-- BACKWARD CHAINING --");
    System.out.println("Goal: "+goalName+"\n");
    KnowledgeBase.getInstace().printRules();
    System.out.println();
    System.out.println("BH: "+iterationBH()+"\n");
    System.out.println(" -----------------------------------------------------------------");
    System.out.println("| CC                 | R  | NM           | Meta | BH              |");
    System.out.println(" -----------------------------------------------------------------");
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

  private String iterationNM(ArrayList<String> NM) {
    String ret = "{";
    for (String s: NM) {
      ret += s+", ";
    }
    return ret.substring(0, ret.length()-2) + "}";
  }

  private void endTable() {
    System.out.println(" ------------------------------------------------------------\n");
  }
}
