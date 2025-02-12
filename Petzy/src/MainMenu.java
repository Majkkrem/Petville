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
    frame.setResizable(false);

    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBackground(Color.LIGHT_GRAY);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.anchor = GridBagConstraints.CENTER;

    JLabel playerNameLabel = new JLabel("Add your name:", SwingConstants.CENTER);
    playerNameLabel.setFont(new Font("Arial", Font.BOLD, 18));
    gbc.gridx = 0;
    gbc.gridy = 0;
    panel.add(playerNameLabel, gbc);

    playerNameField = new JTextField(15);
    playerNameField.setFont(new Font("Arial", Font.PLAIN, 16));
    gbc.gridy = 1;
    panel.add(playerNameField, gbc);

    JLabel petNameLabel = new JLabel("Name your pet:", SwingConstants.CENTER);
    petNameLabel.setFont(new Font("Arial", Font.BOLD, 18));
    gbc.gridy = 2;
    panel.add(petNameLabel, gbc);

    petNameField = new JTextField(15);
    petNameField.setFont(new Font("Arial", Font.PLAIN, 16));
    gbc.gridy = 3;
    panel.add(petNameField, gbc);

    JLabel label = new JLabel("Choose your pet:", SwingConstants.CENTER);
    label.setFont(new Font("Arial", Font.BOLD, 18));
    gbc.gridy = 4;
    panel.add(label, gbc);

    String[] animals = {"Dog", "Cat", "Bird", "Rabbit"}; //TODO átrakani adatbázisba
    animalSelector = new JComboBox<>(animals);
    animalSelector.setFont(new Font("Arial", Font.PLAIN, 16));
    gbc.gridy = 5;
    panel.add(animalSelector, gbc);

    JButton startButton = new JButton("Start Game");
    startButton.setFont(new Font("Arial", Font.BOLD, 18));
    gbc.gridy = 6;
    panel.add(startButton, gbc);

    startButton.addActionListener(e -> startGame());

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