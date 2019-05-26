import parser.Parser;

class Main {
  public static void main(String[] args) throws Exception {
    String path = args[0];
    String goal = args[1];
    char method = args[2].charAt(1);

    Parser.fileParser(path);

    ProductionSystem pd = new ProductionSystem(goal);

    if (method == 'f') {
      if (pd.forwardChaining()) {
        System.out.println("SUCCESS!");
      } else {
        System.out.println("FAILURE...");
      }
    } else if (method == 'b') {

    }
  }
}
