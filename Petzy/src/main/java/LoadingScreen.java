import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import java.io.IOException;

public class LoadingScreen {
  private JFrame frame;
  private JLabel loadingLabel;
  private JProgressBar progressBar;
  private int dotCount = 0;
  private String fontName;
  private Image backgroundImage;

  public LoadingScreen() {
    CustomFont cf = new CustomFont();
    fontName = cf.getFont().getName();
    loadBackgroundImage();
    createAndShowGUI();
    startAnimationTimer();
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
    frame = new JFrame("Loading...");
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

    // Create a semi-transparent panel for content (matches Welcome/MainMenu style)
    JPanel contentPanel = new JPanel(new GridBagLayout()) {
      @Override
      protected void paintComponent(Graphics g) {
        // Draw semi-transparent background with rounded corners
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(new Color(40, 40, 40, 180)); // Dark with 70% opacity
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
        g2d.dispose();
        super.paintComponent(g);
      }
    };
    contentPanel.setOpaque(false);
    contentPanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(15, 15, 15, 15);
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.anchor = GridBagConstraints.CENTER;

    // Loading label with same styling as other screens
    loadingLabel = new JLabel("Loading", SwingConstants.CENTER);
    loadingLabel.setFont(new Font(fontName, Font.BOLD, 36));
    loadingLabel.setForeground(Color.WHITE);
    contentPanel.add(loadingLabel, gbc);

    // Progress bar with styling that matches the theme
    gbc.gridy = 1;
    progressBar = new JProgressBar();
    progressBar.setIndeterminate(true);
    progressBar.setPreferredSize(new Dimension(300, 20));
    progressBar.setBorderPainted(false);
    progressBar.setForeground(new Color(70, 130, 180)); // Same blue as buttons
    progressBar.setBackground(new Color(200, 200, 200, 150)); // Semi-transparent

    // Wrap progress bar in its own semi-transparent panel
    JPanel progressPanel = new JPanel(new BorderLayout()) {
      @Override
      protected void paintComponent(Graphics g) {
        g.setColor(new Color(60, 60, 60, 120));
        g.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        super.paintComponent(g);
      }
    };
    progressPanel.setOpaque(false);
    progressPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    progressPanel.add(progressBar, BorderLayout.CENTER);

    contentPanel.add(progressPanel, gbc);

    mainPanel.add(contentPanel, gbc);
    frame.setContentPane(mainPanel);
    frame.setVisible(true);

    // Transition to WelcomeScreen after delay
    new Timer().schedule(new TimerTask() {
      @Override
      public void run() {
        frame.dispose();
        new WelcomeScreen();
      }
    }, 3000);
  }

  private void startAnimationTimer() {
    new Timer().scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        if (loadingLabel != null) {
          dotCount = (dotCount + 1) % 4;
          String dots = ".".repeat(dotCount);
          SwingUtilities.invokeLater(() -> loadingLabel.setText("Loading" + dots));
        }
      }
    }, 0, 500);
  }
}