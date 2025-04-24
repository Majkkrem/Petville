import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SnakeGameFrame extends JFrame {
  private final ScreenFactory screenFactory;
  private SnakeGamePanel snakeGamePanel;

  public SnakeGameFrame(ScreenFactory screenFactory) {
    this.screenFactory = screenFactory;
    this.screenFactory.setGameActive(true); // Jelzés, hogy játék aktív
    initialize();
  }

  private void initialize() {
    setTitle("Snake Game - Earn Coins!");
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setResizable(false);

    snakeGamePanel = new SnakeGamePanel(this);
    add(snakeGamePanel);

    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        closeGame();
      }

      @Override
      public void windowClosed(WindowEvent e) {
        closeGame();
      }
    });

    pack();
    setLocationRelativeTo(null);
    setVisible(true);
  }

  private void closeGame() {
    snakeGamePanel.stopGame();
    screenFactory.setGameActive(false); // Jelzés, hogy játék véget ért
  }

  public ScreenFactory getScreenFactory() {
    return screenFactory;
  }
}