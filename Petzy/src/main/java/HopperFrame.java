import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class HopperFrame extends JFrame {
  private final ScreenFactory screenFactory;
  private HopperGame hopperGame;

  public HopperFrame(ScreenFactory screenFactory) {
    this.screenFactory = screenFactory;
    this.screenFactory.setGameActive(true);
    initialize();
  }

  private void initialize() {
    setTitle("Hopper - Earn Coins!");

      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      setResizable(false);

      hopperGame = new HopperGame(screenFactory);
      add(hopperGame);


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
      hopperGame.stopGameTimer();
      screenFactory.setGameActive(false);
    }
}