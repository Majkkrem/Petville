import Animals.Animal;
import Animals.Bird;
import Animals.Cat;
import Animals.Dog;
import Animals.Rabbit;

import javax.swing.*;
import java.awt.*;

public class MainMenu {
  private JFrame frame;
  private JTextField playerNameField;
  private JTextField petNameField;
  private JComboBox<String> animalSelector;

  public MainMenu() {
    createAndShowGUI();
  }

  private void createAndShowGUI() {
    frame = new JFrame("Main Menu");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(800, 600);
    frame.setLocationRelativeTo(null);
    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    frame.setResizable(true);

    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel playerNameLabel = new JLabel("Ad your name:");
    playerNameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
    playerNameField = new JTextField();
    playerNameField.setPreferredSize(new Dimension(300, 40));
    playerNameField.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel petNameLabel = new JLabel("Name your pet:");
    petNameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
    petNameField = new JTextField();
    petNameField.setPreferredSize(new Dimension(300, 40));
    petNameField.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel label = new JLabel("Choose your pet:", SwingConstants.CENTER);
    label.setFont(new Font("Arial", Font.PLAIN, 16));
    String[] animals = {"Dog", "Cat", "Bird", "Rabbit"};
    animalSelector = new JComboBox<>(animals);
    animalSelector.setPreferredSize(new Dimension(300, 40));
    animalSelector.setAlignmentX(Component.CENTER_ALIGNMENT);

    JButton startButton = new JButton("Start Game");
    startButton.setFont(new Font("Arial", Font.BOLD, 16));
    startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    startButton.addActionListener(e -> startGame());

    panel.add(Box.createRigidArea(new Dimension(0, 40)));
    panel.add(playerNameLabel);
    panel.add(playerNameField);
    panel.add(Box.createRigidArea(new Dimension(0, 20)));
    panel.add(petNameLabel);
    panel.add(petNameField);
    panel.add(Box.createRigidArea(new Dimension(0, 20)));
    panel.add(label);
    panel.add(animalSelector);
    panel.add(Box.createRigidArea(new Dimension(0, 20)));
    panel.add(startButton);

    frame.add(panel);
    frame.setVisible(true);
  }

  private void startGame() {
    String playerName = playerNameField.getText().trim();
    String petName = petNameField.getText().trim();

    if (playerName.isEmpty() || petName.isEmpty()) {
      JOptionPane.showMessageDialog(frame, "Please give me your name and name your pet!", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    String selectedAnimal = (String) animalSelector.getSelectedItem();
    Animal chosenAnimal;

    switch (selectedAnimal) {
      case "Dog":
        chosenAnimal = new Dog(petName);
        break;
      case "Cat":
        chosenAnimal = new Cat(petName);
        break;
      case "Bird":
        chosenAnimal = new Bird(petName);
        break;
      case "Rabbit":
        chosenAnimal = new Rabbit(petName);
        break;
      default:
        chosenAnimal = new Dog(petName);
    }

    frame.dispose();
    new GameWindow(chosenAnimal);
  }
}