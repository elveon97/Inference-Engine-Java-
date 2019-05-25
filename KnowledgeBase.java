import java.util.ArrayList;

class KnowledgeBase {
  private ArrayList<Rule> rules;
  private static KnowledgeBase knowledgeBase;

  private KnowledgeBase() {
    rules = new ArrayList<Rule>();
  }

  public static KnowledgeBase getInstace() {
    if (knowledgeBase == null) {
      knowledgeBase = new KnowledgeBase();
    }
    return knowledgeBase;
  }

  public void addRule(Rule rule) {
    knowledgeBase.rules.add(rule);
  }

  public void printRules() {
    for (Rule r: rules) {
      System.out.println(r);
    }
  }
}
