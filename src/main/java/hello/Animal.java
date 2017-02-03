package hello;

/**
 * Animal
 */
public class Animal {
  private final String name;
  private final long id;

  public Animal (String name, long id) {
    this.name = name;
    this.id = id;
  }
  public String getName() {
    return this.name;
  }
  public long getId() {
    return this.id;
  }
}