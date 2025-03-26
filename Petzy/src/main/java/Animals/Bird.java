package Animals;

import java.io.Serializable;

public class Bird extends Animal implements Serializable {
  public Bird(String petName) {
    super(petName, 100, 100, 100, 100, 100);
  }
}