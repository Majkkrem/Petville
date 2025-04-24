package Animals;

import java.io.Serializable;

public class Bee extends Animal implements Serializable {
  public Bee(String petName) {
    super(petName, 100, 0, 100, 100);
  }
}