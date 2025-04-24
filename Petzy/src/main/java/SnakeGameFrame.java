import javax.swing.*;

public class SnakeGameFrame extends JFrame {
<<<<<<< HEAD
<<<<<<< HEAD
  private final ScreenFactory screenFactory;
  private SnakeGamePanel snakeGamePanel;
=======
  private ScreenFactory screenFactory;
  private SnakeGamePanel snakeGamePanel;  // Declare the panel variable

>>>>>>> main
=======
  private ScreenFactory screenFactory;
>>>>>>> parent of 76167e6 (Stoping the time while playing minigame)

  public SnakeGameFrame(ScreenFactory screenFactory) {
    this.screenFactory = screenFactory;
    initialize();
  }

  private void initialize() {
<<<<<<< HEAD
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
=======
    this.setTitle("Snake Game - Earn Coins!");
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.setResizable(false);
    this.add(new SnakeGamePanel(this));
    this.pack();
    this.setLocationRelativeTo(null);
    this.setVisible(true);
>>>>>>> parent of 76167e6 (Stoping the time while playing minigame)
  }

  public ScreenFactory getScreenFactory() {
    return screenFactory;
  }
}