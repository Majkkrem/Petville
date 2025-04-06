package Animals;

import java.io.Serializable;

public class Rabbit extends Animal implements Serializable {
  public Rabbit(String petName) {
    super(petName, 100, 0, 100, 100);
  }
}