package facts;

import java.util.ArrayList;

class FactsBase {
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

  public Fact getFact(String name) {
    for (Fact f: this.facts) {
      if (f.getName().equals(name)) return f;
    }
    return null;
  }
}
