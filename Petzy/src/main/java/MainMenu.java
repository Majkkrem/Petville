import Animals.Animal;
import Animals.Bird;
import Animals.Cat;
import Animals.Dog;
import Animals.Rabbit;
import Database.GameClient;

import javax.swing.*;
import java.awt.*;

public class MainMenu { 
  private JFrame frame;
  private JTextField playerNameField;
  private JPasswordField passwordField;
  private JTextField petNameField;
  private JComboBox<String> animalSelector;
  private String fontName;

  public MainMenu() {
    CustomFont cf = new CustomFont();
    fontName = cf.getFont().getName();
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
    panel.setOpaque(false);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.anchor = GridBagConstraints.CENTER;

    JLabel playerNameLabel = new JLabel("Enter your name:", SwingConstants.CENTER);
    playerNameLabel.setFont(new Font(fontName, Font.BOLD, 18));
    playerNameLabel.setForeground(Color.BLACK);
    gbc.gridx = 0;
    gbc.gridy = 0;
    panel.add(playerNameLabel, gbc);

    playerNameField = new JTextField(15);
    playerNameField.setFont(new Font(fontName, Font.PLAIN, 16));
    gbc.gridy = 1;
    panel.add(playerNameField, gbc);

    JLabel passwordLabel = new JLabel("Enter your password:", SwingConstants.CENTER);
    passwordLabel.setFont(new Font(fontName, Font.BOLD, 18));
    passwordLabel.setForeground(Color.BLACK);
    gbc.gridy = 2;
    panel.add(passwordLabel, gbc);

    passwordField = new JPasswordField(15);
    passwordField.setFont(new Font(fontName, Font.PLAIN, 16));
    gbc.gridy = 3;
    panel.add(passwordField, gbc);

    JLabel petNameLabel = new JLabel("Name your pet:", SwingConstants.CENTER);
    petNameLabel.setFont(new Font(fontName, Font.BOLD, 18));
    petNameLabel.setForeground(Color.BLACK);
    gbc.gridy = 4;
    panel.add(petNameLabel, gbc);

    petNameField = new JTextField(15);
    petNameField.setFont(new Font(fontName, Font.PLAIN, 16));
    gbc.gridy = 5;
    panel.add(petNameField, gbc);

    JLabel label = new JLabel("Choose your pet:", SwingConstants.CENTER);
    label.setFont(new Font(fontName, Font.BOLD, 18));
    label.setForeground(Color.BLACK);
    gbc.gridy = 6;
    panel.add(label, gbc);

    String[] animals = {"Dog", "Cat", "Bird", "Rabbit"};
    animalSelector = new JComboBox<>(animals);
    animalSelector.setFont(new Font(fontName, Font.PLAIN, 16));
    gbc.gridy = 7;
    panel.add(animalSelector, gbc);

    JButton startButton = new JButton("Start Game");
    startButton.setFont(new Font(fontName, Font.BOLD, 18));
    gbc.gridy = 8;
    panel.add(startButton, gbc);

    startButton.addActionListener(e ->
    {
      if (GameClient.login(playerNameField.getText(), new String(passwordField.getPassword()))) {
        startGame();
      } else {
        JOptionPane.showMessageDialog(frame, "Login failed! Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
      }
    });

    JLabel backgroundLabel = new JLabel(new ImageIcon("path/to/main_menu_background.jpg"));
    backgroundLabel.setLayout(new GridBagLayout());
    backgroundLabel.add(panel, gbc);

    frame.add(backgroundLabel);
    frame.setVisible(true);
  }

  private void startGame() {
    String playerName = playerNameField.getText().trim();
    String petName = petNameField.getText().trim();

    if (playerName.isEmpty() || petName.isEmpty()) {
      JOptionPane.showMessageDialog(frame, "Please enter your name and name your pet!", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    String selectedAnimal = (String) animalSelector.getSelectedItem();
    Animal chosenAnimal;

    switch (selectedAnimal) {
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