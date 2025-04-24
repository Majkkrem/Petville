import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class JumpGameFrame extends JFrame {
  private final ScreenFactory screenFactory;
  private JumpGamePlus jumpGame;

  public JumpGameFrame(ScreenFactory screenFactory) {
    this.screenFactory = screenFactory;
    this.screenFactory.setGameActive(true); // Jelzés, hogy játék aktív
    initialize();
  }

  private void initialize() {
    setTitle("Jump Game - Earn Coins!");
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setResizable(false);

    jumpGame = new JumpGamePlus(screenFactory);
    add(jumpGame);

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
    setLocationRelativeTo(screenFactory.getFrame());
    setVisible(true);
  }

  private void closeGame() {
    jumpGame.stopGameTimer();
    screenFactory.setGameActive(false); // Jelzés, hogy játék véget ért
  }
}