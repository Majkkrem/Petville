import javax.swing.*;
import java.awt.*;

public class WelcomeScreen {
  private JFrame frame;
  private JProgressBar progressBar;
  private JLabel loadingLabel;
  private int progress = 0;
  private boolean isVisible = true;

  public WelcomeScreen() {
    createAndShowGUI();
    startLoading();
    startBlinking();
  }

  private void createAndShowGUI() {
    frame = new JFrame("Petville");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(400, 300);
    frame.setResizable(false);
    frame.setLocationRelativeTo(null);

    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());

    JLabel titleLabel = new JLabel("Petville", SwingConstants.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

    progressBar = new JProgressBar(0, 100);
    progressBar.setStringPainted(true);

    loadingLabel = new JLabel("Loading...", SwingConstants.CENTER);
    loadingLabel.setFont(new Font("Arial", Font.BOLD, 18));

    panel.add(titleLabel, BorderLayout.NORTH);
    panel.add(loadingLabel, BorderLayout.CENTER);
    panel.add(progressBar, BorderLayout.SOUTH);

    frame.add(panel);
    frame.setVisible(true);
  }

  private void startLoading() {
    Timer timer = new Timer(50, e -> {
      progress += 2;
      progressBar.setValue(progress);

      if (progress >= 100) {
        ((Timer) e.getSource()).stop();
        frame.dispose();
        new MainMenu();
      }
    });
    timer.start();
  }

  private void startBlinking() {
    Timer blinkTimer = new Timer(500, e -> {
      isVisible = !isVisible;
      loadingLabel.setVisible(isVisible);
    });
    blinkTimer.start();
  }
}