import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SnakeGameFrame extends JFrame {
  private ScreenFactory screenFactory;
  private SnakeGamePanel snakeGamePanel;  // Declare the panel variable


  public SnakeGameFrame(ScreenFactory screenFactory) {
    this.screenFactory = screenFactory;
    initialize();
  }

  private void initialize() {
    setTitle("Snake Game - Earn Coins!");
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setResizable(false);

    snakeGamePanel = new SnakeGamePanel(this);
    add(snakeGamePanel);

    // Add window listener to stop timer on close
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        snakeGamePanel.stopGame();
      }
    });

    pack();
    setLocationRelativeTo(null);
    setVisible(true);
  }

  public ScreenFactory getScreenFactory() {
    return screenFactory;
  }
}