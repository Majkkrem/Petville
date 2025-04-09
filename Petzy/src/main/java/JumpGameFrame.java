import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class JumpGameFrame extends JFrame {
  private final JumpGamePlus jumpGame;

  public JumpGameFrame(ScreenFactory screenFactory) {
    setTitle("Jump Game");
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setResizable(false);

    jumpGame = new JumpGamePlus(screenFactory);
    add(jumpGame);
    pack();
    setLocationRelativeTo(screenFactory.getFrame());

    // Add window listener to stop timer on close
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        jumpGame.stopGameTimer();
        screenFactory.setGameOpened(false); // Add this method to ScreenFactory
      }
    });
    setVisible(true);
  }
}