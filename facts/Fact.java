package facts;

public class Fact {
  private static int counter = 0;
  private int id;
  private String name;

  public Fact(String name) {
    counter ++;
    id = counter;
    this.name = name;
  }

  @Override
  public String toString() {
    return "Name: "+name+", ID: "+id;
  }

  public int getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public static int getCounter() {
    return counter;
  }

}
