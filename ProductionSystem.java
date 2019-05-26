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

  public boolean forwardChaining(String goalName) {
    ArrayList<Rule> CC = equiparar();

    while ((!CC.isEmpty()) && (FactsBase.getInstace().getFact(goalName) == null)) {
      CC = resolver(CC);
      if (FactsBase.getInstace().getFact(goalName) == null) {
        CC = equiparar();
      } else {
        return true;
      }
    }
    return false;
  }

  private ArrayList<Rule> equiparar() {
    ArrayList<Rule> ret = new ArrayList<Rule>();
    for (Rule r: KnowledgeBase.getInstace().getRules()) {
      if (!r.beenFired()) {
        if (r.parseRule(r.getCondition())) ret.add(r);
      }
    }
    return ret;
  }

  private ArrayList<Rule> resolver(ArrayList<Rule> CC) {
    Rule ruleToFire = CC.get(0);
    FactsBase.getInstace().addFact(ruleToFire.getAction());
    ruleToFire.setFired(true);
    CC.remove(0);
    return CC;
  }
}
