import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.IOException;

public class WelcomeScreen {
  private JFrame frame;
  private String fontName;
  private Image backgroundImage;

  public WelcomeScreen() {
    CustomFont cf = new CustomFont();
    fontName = cf.getFont().getName();
    loadBackgroundImage();
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

  private void createAndShowGUI() {
    frame = new JFrame("Welcome to Petville");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    frame.setResizable(false);

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

    // Create a semi-transparent panel for the title
    JPanel titlePanel = new JPanel(new BorderLayout()) {
      @Override
      protected void paintComponent(Graphics g) {
        // Draw semi-transparent background
        g.setColor(new Color(30, 30, 30, 180)); // Dark with 70% opacity
        g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
        super.paintComponent(g);
      }
    };
    titlePanel.setOpaque(false);
    titlePanel.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));

    JLabel titleLabel = new JLabel("Welcome to Petville", SwingConstants.CENTER);
    titleLabel.setFont(new Font(fontName, Font.BOLD, 56));
    titleLabel.setForeground(Color.WHITE);
    titlePanel.add(titleLabel, BorderLayout.CENTER);

    // Create a semi-transparent panel for the button
    JPanel buttonPanel = new JPanel(new BorderLayout()) {
      @Override
      protected void paintComponent(Graphics g) {
        // Draw semi-transparent background
        g.setColor(new Color(40, 40, 40, 150)); // Dark with 60% opacity
        g.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
        super.paintComponent(g);
      }
    };
    buttonPanel.setOpaque(false);
    buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

    JButton startButton = createStyledButton("Start Your Journey");
    startButton.addActionListener(e -> {
      frame.dispose();
      new MainMenu();
    });
    buttonPanel.add(startButton, BorderLayout.CENTER);

    // Layout components
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weighty = 0.5;
    gbc.anchor = GridBagConstraints.CENTER;
    mainPanel.add(titlePanel, gbc);

    gbc.gridy = 1;
    gbc.weighty = 0.5;
    mainPanel.add(buttonPanel, gbc);

    frame.setContentPane(mainPanel);
    frame.setVisible(true);
  }

  private JButton createStyledButton(String text) {
    JButton button = new JButton(text);
    button.setFont(new Font(fontName, Font.BOLD, 28));
    button.setContentAreaFilled(false);
    button.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(255, 255, 255, 200), 3),
        BorderFactory.createEmptyBorder(15, 40, 15, 40)
    ));
    button.setForeground(Color.WHITE);
    button.setFocusPainted(false);

    // Hover effects
    button.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(java.awt.event.MouseEvent evt) {
        button.setForeground(new Color(220, 240, 255));
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 240, 255), 3),
            BorderFactory.createEmptyBorder(15, 40, 15, 40)
        ));
      }

      public void mouseExited(java.awt.event.MouseEvent evt) {
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 200), 3),
            BorderFactory.createEmptyBorder(15, 40, 15, 40)
        ));
      }
    });

    return button;
  }
}