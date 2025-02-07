import Animals.Animal;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.*;

public class GameWindow {
  private Animal animal;

  public GameWindow(Animal animal) {
    this.animal = animal;
    createAndShowGUI();
  }

  private void createAndShowGUI() {
    JFrame frame = new JFrame("Animal Stats");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(900, 600);

    JPanel panel = new JPanel(new GridLayout(1, 5));

    JLabel energyLabel = new JLabel("Energy: " + animal.getEnergy());
    energyLabel.setBorder(new LineBorder(Color.BLACK, 1, true));
    panel.add(energyLabel);

    JLabel foodLabel = new JLabel("Food: " + animal.getFood());
    foodLabel.setBorder(new LineBorder(Color.BLACK, 1, true));
    panel.add(foodLabel);

    JLabel waterLabel = new JLabel("Water: " + animal.getWater());
    waterLabel.setBorder(new LineBorder(Color.BLACK, 1, true));
    panel.add(waterLabel);

    JLabel healthLabel = new JLabel("Health: " + animal.getHealth());
    healthLabel.setBorder(new LineBorder(Color.BLACK, 1, true));
    panel.add(healthLabel);

    JLabel moodLabel = new JLabel("Mood: " + animal.getMood());
    moodLabel.setBorder(new LineBorder(Color.BLACK, 1, true));
    panel.add(moodLabel);

    frame.add(panel, BorderLayout.NORTH);
    frame.setVisible(true);
  }
}