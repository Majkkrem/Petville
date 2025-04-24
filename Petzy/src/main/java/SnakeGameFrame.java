import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SnakeGameFrame extends JFrame {
<<<<<<< HEAD
  private final ScreenFactory screenFactory;
  private SnakeGamePanel snakeGamePanel;
=======
  private ScreenFactory screenFactory;
  private SnakeGamePanel snakeGamePanel;  // Declare the panel variable

>>>>>>> main

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

<<<<<<< HEAD
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        closeGame();
      }

      @Override
      public void windowClosed(WindowEvent e) {
        closeGame();
=======
    // Add window listener to stop timer on close
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        snakeGamePanel.stopGame();
>>>>>>> main
      }
    });

    pack();
    setLocationRelativeTo(null);
    setVisible(true);
<<<<<<< HEAD
  }

  private void closeGame() {
    snakeGamePanel.stopGame();
    screenFactory.setGameActive(false); // Jelzés, hogy játék véget ért
=======
>>>>>>> main
  }

  public ScreenFactory getScreenFactory() {
    return screenFactory;
  }
}