import Animals.Animal;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;

public class GameWindow {
  private Animal animal;

  public GameWindow(Animal animal) {
    this.animal = animal;
    createAndShowGUI();
  }

  private void createAndShowGUI() {
    JFrame frame = new JFrame("Animals.Animal Stats");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(300, 200);

    JPanel panel = new JPanel(new GridLayout(5, 2));

    panel.add(new JLabel("Energy:"));
    panel.add(new JLabel(String.valueOf(animal.getEnergy())));

    panel.add(new JLabel("Food:"));
    panel.add(new JLabel(String.valueOf(animal.getFood())));

    panel.add(new JLabel("Water:"));
    panel.add(new JLabel(String.valueOf(animal.getWater())));

    panel.add(new JLabel("Health:"));
    panel.add(new JLabel(String.valueOf(animal.getHealth())));

    panel.add(new JLabel("Mood:"));
    panel.add(new JLabel(String.valueOf(animal.getMood())));

    frame.add(panel);
    frame.setVisible(true);
  }
}