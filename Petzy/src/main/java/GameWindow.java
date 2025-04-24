// GameWindow.java
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
  private Animal animal;
  private Timer gameTimer;
  private ScreenFactory screenFactory;

  public GameWindow(Animal animal) {
    this.animal = animal;
    initialize();
  }

  private void initialize() {
    frame = new JFrame("Petville - " + GameClient.getCurrentUser());
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    frame.setResizable(false);

    CardLayout cardLayout = new CardLayout();
    JPanel cardPanel = new JPanel(cardLayout);

    screenFactory = new ScreenFactory(frame, animal, cardPanel, cardLayout);

    cardPanel.add(screenFactory.createMainScreen(), "Main");
    cardPanel.add(screenFactory.createKitchenScreen(), "Kitchen");
    cardPanel.add(screenFactory.createBedRoomScreen(), "Bedroom");

    frame.add(cardPanel, BorderLayout.CENTER);
    frame.add(createNavigationPanel(cardPanel, cardLayout), BorderLayout.SOUTH);
    frame.setJMenuBar(createMenuBar());

    setupAutoSave();
    startGameTimer();
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
        screenFactory.updateAllScreens();

        if (!animal.isAlive()) {
          stopGameTimer();
          SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(frame,
                "You neglected your pet! Take better care of it next time!",
                "Game Over",
                JOptionPane.ERROR_MESSAGE);
            frame.dispose();
            System.exit(0);
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

  private JMenuBar createMenuBar() {
    JMenuBar menuBar = new JMenuBar();

    JMenu gameMenu = new JMenu("Game");
    JMenuItem saveItem = new JMenuItem("Save");
    JMenuItem exitItem = new JMenuItem("Exit");

    saveItem.addActionListener(e -> saveGame());
    exitItem.addActionListener(e -> exitGame());

    gameMenu.add(saveItem);
    gameMenu.addSeparator();
    gameMenu.add(exitItem);
    menuBar.add(gameMenu);

    return menuBar;
  }

  private void saveGame() {
    boolean saved = GameClient.saveGameData(animal);
//    boolean savedInventory = GameClient.saveInventoryData(animal);
//    String message = saved ? "Game saved successfully!" : "Failed to save game!";
//    JOptionPane.showMessageDialog(frame, message);
  }

  private void exitGame() {
    frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
  }

  private void setupAutoSave() {
    frame.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        saveGame();
        GameClient.logout();
        stopGameTimer();
      }
    });
  }

  private JPanel createNavigationPanel(JPanel cardPanel, CardLayout cardLayout) {
    JPanel panel = new JPanel(new BorderLayout());

    // Previous button with arrow drawing
    JButton prevBtn = new JButton("<") { // Using < as arrow
      @Override
      protected void paintComponent(Graphics g) {
        if (getIcon() != null) {
          // Draw background image
          ImageIcon icon = (ImageIcon) getIcon();
          g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);

          // Draw arrow text
          g.setColor(Color.WHITE);
          g.setFont(new Font("Arial", Font.BOLD, 24));

          FontMetrics fm = g.getFontMetrics();
          int x = (getWidth() - fm.stringWidth(getText())) / 2;
          int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();

          g.drawString(getText(), x, y);
        } else {
          super.paintComponent(g);
        }
      }
    };

    // Next button with arrow drawing
    JButton nextBtn = new JButton("") {
      @Override
      protected void paintComponent(Graphics g) {
        if (getIcon() != null) {
          // Draw background image
          ImageIcon icon = (ImageIcon) getIcon();
          g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
        } else {
          super.paintComponent(g);
        }
      }
    };

    try {
      // Load button images (optional - can use just text arrows)
      ImageIcon prevIcon = new ImageIcon(getClass().getResource("/icons/Button_left.png"));
      Image prevImg = prevIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
      prevBtn.setIcon(new ImageIcon(prevImg));

      ImageIcon nextIcon = new ImageIcon(getClass().getResource("/icons/Button_right.png"));
      Image nextImg = nextIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
      nextBtn.setIcon(new ImageIcon(nextImg));

    } catch (Exception e) {
      System.err.println("Navigation button images not found, using text arrows only");
    }

    // Style both buttons
    for (JButton btn : new JButton[]{prevBtn, nextBtn}) {
      btn.setBorderPainted(false);
      btn.setFocusPainted(false);
      btn.setContentAreaFilled(false);
      btn.setPreferredSize(new Dimension(60, 60));
    }

    prevBtn.addActionListener(e -> cardLayout.previous(cardPanel));
    nextBtn.addActionListener(e -> cardLayout.next(cardPanel));

    panel.add(prevBtn, BorderLayout.WEST);
    panel.add(nextBtn, BorderLayout.EAST);

    JLabel userLabel = new JLabel("User: " + GameClient.getCurrentUser(), SwingConstants.CENTER);
    panel.add(userLabel, BorderLayout.CENTER);

    return panel;
  }
  }