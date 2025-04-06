import Animals.Animal;
import Database.GameClient;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;

public class GameWindow {
  private JFrame frame;
  private CardLayout cardLayout;
  private JPanel cardPanel;
  private Animal animal;
  private JMenuBar menuBar;
  private Timer gameTimer;
  private ScreenFactory screenFactory;

  public GameWindow(Animal animal) {
    this.animal = animal;
    this.frame = new JFrame("Petville - " + GameClient.getCurrentUser());
    this.screenFactory = new ScreenFactory(frame, animal);
    createAndShowGUI();
    setupAutoSave();
    createMenuBar();
    startGameTimer();
  }

  private void createAndShowGUI() {
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(800, 600);
    frame.setLocationRelativeTo(null);
    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    frame.setResizable(false);

    cardLayout = new CardLayout();
    cardPanel = new JPanel(cardLayout);

    cardPanel.add(screenFactory.createMainScreen(), "MainScreen");
    cardPanel.add(screenFactory.createBedRoomScreen(), "BedRoomScreen");
    cardPanel.add(screenFactory.createKitchenScreen(), "KitchenScreen");

    JPanel navigationPanel = createNavigationPanel();
    frame.add(cardPanel, BorderLayout.CENTER);
    frame.add(navigationPanel, BorderLayout.SOUTH);
    frame.setVisible(true);
  }

  private void startGameTimer() {
    if (gameTimer != null) {
      gameTimer.cancel();
    }

    gameTimer = new Timer();
    gameTimer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        animal.reduceStatsOverTime();
        screenFactory.updateStats();

        if (!animal.isAlive()) {
          stopGameTimer();
          SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(frame,
                "You neglected your pet! Take better care of it next time!",
                "Game Over",
                JOptionPane.ERROR_MESSAGE);
            frame.dispose();
          });
        }
      }
    }, 0, 5000);
  }

  private void stopGameTimer() {
    if (gameTimer != null) {
      gameTimer.cancel();
      gameTimer = null;
    }
  }

  private void setupAutoSave() {
    frame.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        boolean saved = GameClient.saveGameData(animal);
        if (saved) {
          System.out.println("Game data saved successfully");
        } else {
          System.out.println("Failed to save game data");
        }
        GameClient.logout();
        stopGameTimer();
      }
    });
  }

  private void createMenuBar() {
    menuBar = new JMenuBar();

    JMenu gameMenu = new JMenu("Game");
    JMenuItem saveItem = new JMenuItem("Save Game");
    JMenuItem exitItem = new JMenuItem("Exit");

    saveItem.addActionListener(e -> {
      boolean saved = GameClient.saveGameData(animal);
      if (saved) {
        JOptionPane.showMessageDialog(frame, "Game saved successfully!");
      } else {
        JOptionPane.showMessageDialog(frame, "Failed to save game!", "Error", JOptionPane.ERROR_MESSAGE);
      }
    });

    exitItem.addActionListener(e -> {
      frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    });

    gameMenu.add(saveItem);
    gameMenu.addSeparator();
    gameMenu.add(exitItem);
    menuBar.add(gameMenu);

    frame.setJMenuBar(menuBar);
  }

  private JPanel createNavigationPanel() {
    JPanel navigationPanel = new JPanel(new BorderLayout());
    JButton leftButton = new JButton("←");
    JButton rightButton = new JButton("→");

    leftButton.addActionListener(e -> cardLayout.previous(cardPanel));
    rightButton.addActionListener(e -> cardLayout.next(cardPanel));

    navigationPanel.add(leftButton, BorderLayout.WEST);
    navigationPanel.add(rightButton, BorderLayout.EAST);

    JLabel statusLabel = new JLabel("Logged in as: " + GameClient.getCurrentUser(), SwingConstants.CENTER);
    navigationPanel.add(statusLabel, BorderLayout.CENTER);

    return navigationPanel;
  }
}