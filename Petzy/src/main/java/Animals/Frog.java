package Animals;

import java.io.Serializable;

public class Frog extends Animal implements Serializable {
  public Frog(String petName) {
    super(petName, 100, 0, 100, 100);  // hunger starts at 0
  }
}