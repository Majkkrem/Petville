import javax.swing.*;

public class SnakeGameFrame extends JFrame {
  private ScreenFactory screenFactory;

  public SnakeGameFrame(ScreenFactory screenFactory) {
    this.screenFactory = screenFactory;
    initialize();
  }

  private void initialize() {
    this.setTitle("Snake Game - Earn Coins!");
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.setResizable(false);
    this.add(new SnakeGamePanel(this));
    this.pack();
    this.setLocationRelativeTo(null);
    this.setVisible(true);
  }

  public ScreenFactory getScreenFactory() {
    return screenFactory;
  }
}