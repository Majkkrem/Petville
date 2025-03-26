package Animals;

import java.io.Serializable;

public class Dog extends Animal implements Serializable {
  public Dog(String petName) {
    super(petName, 100, 100, 100, 100, 100);
  }
}