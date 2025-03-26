package Animals;

import java.io.Serializable;

public class Cat extends Animal implements Serializable {
  public Cat(String petName) {
    super(petName, 100, 100, 100, 100, 100);
  }
}