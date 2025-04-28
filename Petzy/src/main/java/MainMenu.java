import Animals.*;
import Database.GameClient;
import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.border.Border;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainMenu {
  private JFrame frame;
  private JTextField playerNameField;
  private JPasswordField passwordField;
  private JTextField petNameField;
  private JComboBox<String> animalSelector;
  private String fontName;
  private Image backgroundImage;

  public MainMenu() {
    CustomFont cf = new CustomFont();
    fontName = cf.getFont().getName();
    loadBackgroundImage();
    initializeFrame();
    createAndShowGUI();
  }

  private void loadBackgroundImage() {
    try {
      backgroundImage = ImageIO.read(getClass().getResource("/icons/All_pet_image.png"));
    } catch (IOException e) {
      System.err.println("Error loading background image: " + e.getMessage());
      backgroundImage = null;
    }
  }

  private void initializeFrame() {
    frame = new JFrame("Main Menu");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    frame.setResizable(false);
  }

  private void createAndShowGUI() {
    // Main panel with background
    JPanel mainPanel = new JPanel(new GridBagLayout()) {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
          g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
      }
    };
    mainPanel.setOpaque(false);

    // Main content panel with semi-transparent background
    JPanel contentPanel = new JPanel(new GridBagLayout()) {
      @Override
      protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(new Color(40, 40, 40, 180));
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
        g2d.dispose();
        super.paintComponent(g);
      }
    };
    contentPanel.setOpaque(false);
    contentPanel.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80)); // Increased side padding

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(15, 15, 15, 15);
    gbc.anchor = GridBagConstraints.CENTER; // Center all components

    // Title label
    JLabel titleLabel = new JLabel("Petville", SwingConstants.CENTER);
    titleLabel.setFont(new Font(fontName, Font.BOLD, 48));
    titleLabel.setForeground(Color.WHITE);
    titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
    gbc.gridwidth = 2;
    gbc.gridx = 0;
    gbc.gridy = 0;
    contentPanel.add(titleLabel, gbc);

    // Form fields - now properly centered
    addFormField(contentPanel, "Player Name:", 1, gbc);
    playerNameField = createFormTextField();
    gbc.gridy = 2;
    contentPanel.add(createCenteredFieldPanel(playerNameField), gbc);

    addFormField(contentPanel, "Password:", 3, gbc);
    passwordField = new JPasswordField(15);
    passwordField.setFont(new Font(fontName, Font.PLAIN, 16));
    passwordField.setBorder(createFormFieldBorder());
    gbc.gridy = 4;
    contentPanel.add(createCenteredFieldPanel(passwordField), gbc);

    addFormField(contentPanel, "Pet Name:", 5, gbc);
    petNameField = createFormTextField();
    gbc.gridy = 6;
    contentPanel.add(createCenteredFieldPanel(petNameField), gbc);

    addFormField(contentPanel, "Choose Pet:", 7, gbc);
    String[] animals = {"Dog", "Cat", "Frog", "Bee"};
    animalSelector = new JComboBox<>(animals);
    animalSelector.setFont(new Font(fontName, Font.PLAIN, 16));
    animalSelector.setBackground(Color.WHITE);
    animalSelector.setBorder(createFormFieldBorder());
    gbc.gridy = 8;
    contentPanel.add(createCenteredFieldPanel(animalSelector), gbc);

    // Start button
    JButton startButton = createStyledButton("Start Game");
    startButton.addActionListener(e -> handleStartGame());
    gbc.gridy = 9;
    gbc.gridwidth = 2;
    contentPanel.add(startButton, gbc);

    mainPanel.add(contentPanel, gbc);
    frame.setContentPane(mainPanel);
    frame.setVisible(true);
  }

  private JPanel createCenteredFieldPanel(JComponent field) {
    JPanel panel = new JPanel(new GridBagLayout()) {
      @Override
      protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(new Color(60, 60, 60, 120));
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        g2d.dispose();
        super.paintComponent(g);
      }
    };
    panel.setOpaque(false);
    panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.CENTER;
    panel.add(field, gbc);

    return panel;
  }

  private void addFormField(JPanel panel, String text, int yPos, GridBagConstraints gbc) {
    JLabel label = new JLabel(text, SwingConstants.CENTER);
    label.setFont(new Font(fontName, Font.BOLD, 18));
    label.setForeground(Color.WHITE);
    gbc.gridwidth = 2;
    gbc.gridy = yPos;
    panel.add(label, gbc);
  }

  private JTextField createFormTextField() {
    JTextField field = new JTextField(15);
    field.setFont(new Font(fontName, Font.PLAIN, 16));
    field.setBorder(createFormFieldBorder());
    field.setBackground(new Color(255, 255, 255, 200));
    return field;
  }

  private Border createFormFieldBorder() {
    return BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(200, 200, 200)),
        BorderFactory.createEmptyBorder(8, 8, 8, 8)
    );
  }

  private JButton createStyledButton(String text) {
    JButton button = new JButton(text);
    button.setFont(new Font(fontName, Font.BOLD, 20));
    button.setContentAreaFilled(false);
    button.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(255, 255, 255, 200), 2),
        BorderFactory.createEmptyBorder(12, 40, 12, 40)
    ));
    button.setForeground(Color.WHITE);
    button.setFocusPainted(false);

    button.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(java.awt.event.MouseEvent evt) {
        button.setForeground(new Color(220, 240, 255));
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 240, 255), 2),
            BorderFactory.createEmptyBorder(12, 40, 12, 40)
        ));
      }

      public void mouseExited(java.awt.event.MouseEvent evt) {
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 200), 2),
            BorderFactory.createEmptyBorder(12, 40, 12, 40)
        ));
      }
    });

    return button;
  }

  private void handleStartGame() {
    if (GameClient.login(playerNameField.getText(), new String(passwordField.getPassword()))) {
      startGame();
    } else {
      JOptionPane.showMessageDialog(frame,
          "Login failed! Please try again.",
          "Error",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  private void startGame() {
    String playerName = playerNameField.getText().trim();
    String petName = petNameField.getText().trim();
    String selectedAnimal = (String) animalSelector.getSelectedItem();

    if (playerName.isEmpty() || petName.isEmpty()) {
      JOptionPane.showMessageDialog(frame,
          "Please enter your name and name your pet!",
          "Error",
          JOptionPane.ERROR_MESSAGE);
      return;
    }

    // Check for existing save
    Path savePath = Paths.get(GameClient.SAVE_DIR, playerName + "_" + petName + "_" + selectedAnimal + ".json");
    if (Files.exists(savePath)) {
      int response = JOptionPane.showConfirmDialog(
          frame,
          String.format("Found save files for \"%s\" (%s) for user \"%s\". Do you wish to continue?",
              petName, selectedAnimal, playerName),
          "Existing Save Found",
          JOptionPane.YES_NO_OPTION,
          JOptionPane.QUESTION_MESSAGE);

      if (response != JOptionPane.YES_OPTION) {
        // User wants to start new, delete existing save
        try {
          Files.deleteIfExists(savePath);
          Files.deleteIfExists(Paths.get(GameClient.SAVE_DIR, playerName + "_" + petName + "_" + selectedAnimal + "_inventory.json"));
        } catch (IOException e) {
          System.err.println("Error deleting save files: " + e.getMessage());
        }
      }
    }

    if (GameClient.login(playerName, new String(passwordField.getPassword()))) {
      Animal chosenAnimal;
      switch (selectedAnimal) {
        case "Cat":
          chosenAnimal = new Cat(petName);
          break;
        case "Frog":
          chosenAnimal = new Frog(petName);
          break;
        case "Bee":
          chosenAnimal = new Bee(petName);
          break;
        default: // Dog
          chosenAnimal = new Dog(petName);
          break;
      }

      frame.dispose();
      new GameWindow(chosenAnimal, playerName, petName, selectedAnimal);
    }
  }
}