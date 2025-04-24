import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class HopperFrame extends JFrame {
  private final HopperGame hopperGame;

  public HopperFrame(ScreenFactory screenFactory) {
    setTitle("Jump Game");
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setResizable(false);

    hopperGame = new HopperGame(screenFactory);
    add(hopperGame);
    pack();
    setLocationRelativeTo(screenFactory.getFrame());

    // Add window listener to stop timer on close
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        hopperGame.stopGameTimer();
        screenFactory.setGameOpened(false); // Add this method to ScreenFactory
      }
    });
    setVisible(true);
  }
}