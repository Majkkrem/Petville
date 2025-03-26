import javax.swing.*;
import java.awt.*;

public class WelcomeScreen {
  private JFrame frame;
  private String fontName;

  public WelcomeScreen() {
    CustomFont cf = new CustomFont();
    fontName = cf.getFont().getName();
    createAndShowGUI();
  }

  private void createAndShowGUI() {
    frame = new JFrame("Welcome to Petville");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(800, 600);
    frame.setLocationRelativeTo(null);
    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    frame.setResizable(false);

    JPanel panel = new JPanel(new BorderLayout());
    JLabel backgroundLabel = new JLabel(new ImageIcon("path/to/welcome_background.jpg"));
    backgroundLabel.setLayout(new GridBagLayout());

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.anchor = GridBagConstraints.CENTER;

    JLabel titleLabel = new JLabel("Welcome to Petville", SwingConstants.CENTER);
    titleLabel.setFont(new Font(fontName, Font.BOLD, 48));
    titleLabel.setForeground(Color.BLACK);
    backgroundLabel.add(titleLabel, gbc);

    gbc.gridy = 1;
    JButton startButton = new JButton("Start");
    startButton.setFont(new Font(fontName, Font.BOLD, 24));
    startButton.setContentAreaFilled(false);
    startButton.setBorderPainted(false);
    startButton.setFocusPainted(false);
    startButton.setForeground(Color.BLACK);
    startButton.addActionListener(e -> {
      frame.dispose();
      new MainMenu();
    });
    backgroundLabel.add(startButton, gbc);

    panel.add(backgroundLabel, BorderLayout.CENTER);
    frame.add(panel);
    frame.setVisible(true);
  }
}