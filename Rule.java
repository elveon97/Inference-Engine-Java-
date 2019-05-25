
class Rule {
  String condition;
  String action;

  public static void main(String[] args) {
    new Rule("A and B --> C");
  }

  public Rule(String str) {
    String arr[] = str.split("-->");
    condition = arr[0].trim();
    action = arr[1].trim();
    System.out.println("condition: "+condition);
    System.out.println("action: "+action);
  }

  @Override
  public String toString() {
    return condition + " --> " + action;
  }

  public static boolean parseRule(String condition) {
    java.util.Scanner s = new java.util.Scanner(condition);
    boolean val1 = Boolean.parseBoolean(s.next());
    String op = s.next();
    boolean val2 = Boolean.parseBoolean(s.next());

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
}
