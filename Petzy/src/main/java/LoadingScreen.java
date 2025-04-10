import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class LoadingScreen {
  private JFrame frame;
  private JLabel loadingLabel;
  private JProgressBar progressBar;
  private int dotCount = 0;
  private String fontName;

  public LoadingScreen() {
    CustomFont cf = new CustomFont();
    fontName = cf.getFont().getName();
    createAndShowGUI();
  }

  private void createAndShowGUI() {
    frame = new JFrame("Loading...");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(800, 600);
    frame.setLocationRelativeTo(null);
    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    frame.setResizable(false);

    JPanel panel = new JPanel(new BorderLayout());
    loadingLabel = new JLabel("Loading", SwingConstants.CENTER);
    loadingLabel.setFont(new Font(fontName, Font.BOLD, 36));
    loadingLabel.setForeground(Color.BLACK);
    panel.add(loadingLabel, BorderLayout.CENTER);

    progressBar = new JProgressBar();
    progressBar.setIndeterminate(true);
    panel.add(progressBar, BorderLayout.SOUTH);

    frame.add(panel);
    frame.setVisible(true);

    new Timer().scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        dotCount = (dotCount + 1) % 4;
        String dots = ".".repeat(dotCount);
        loadingLabel.setText("Loading" + dots);
      }
    }, 0, 500);

    new Timer().schedule(new TimerTask() {
      @Override
      public void run() {
        frame.dispose();
        new WelcomeScreen();
      }
    }, 3000);
  }
}