package facts;

import java.util.ArrayList;

public class FactsBase {
  private ArrayList<Fact> facts;
  private static FactsBase factsBase;

  private FactsBase() {
    facts = new ArrayList<Fact>();
  }

  public static FactsBase getInstace() {
    if (factsBase == null) {
      factsBase = new FactsBase();
    }
    return factsBase;
  }

  public void addFact(Fact fact) {
    this.facts.add(fact);
  }

  public void addFact(String str) {
    this.facts.add(new Fact(str));
  }

  public Fact getFact(String name) {
    for (Fact f: this.facts) {
      if (f.getName().equals(name)) return f;
    }
    return null;
  }

  public void printFacts() {
    for (Fact f: this.facts) {
      System.out.println(f);
    }
  }
}
