package knowledge;

import java.util.Scanner;

import facts.*;

public class Rule {
  private String condition;
  private String action;
  private boolean fired;
  private static int counter = 0;
  private int id;

  public Rule(String str) {
    String arr[] = str.split("-->");
    condition = arr[0].trim();
    action = arr[1].trim();
    fired = false;
    id = ++counter;
  }

  @Override
  public String toString() {
    return "R"+ id + ": " + condition + " --> " + action;
  }

  public static boolean parseRule(String condition) {
    java.util.Scanner s = new java.util.Scanner(condition);
    String str1 = s.next();
    boolean val1 = true;
    if (FactsBase.getInstace().getFact(str1) == null) {
      val1 = Boolean.parseBoolean(str1);
    }

    if (!s.hasNext()) return val1;
    String op = s.next();
    boolean val2 = FactsBase.getInstace().getFact(s.next()) == null? false : true;

    boolean res = false;
    switch (op.toUpperCase()) {
      case "AND":
        res = val1 && val2;
        break;
      case "OR":
        res = val1 || val2;
        break;
    }

    if (s.hasNext()) {
      res = parseRule(res + " " + s.nextLine());
    }
    return res;
  }

  public String getCondition() {
    return this.condition;
  }

  public String getAction() {
    return this.action;
  }

  public void setCondition(String condition) {
    this.condition = condition;
  }

  public void setAction(String action) {
    this.condition = action;
  }

  public boolean beenFired() {
    return this.fired;
  }

  public boolean setFired(boolean fired) {
    return this.fired = fired;
  }

  public int getId() {
    return this.id;
  }
}
