import Animals.Animal;
import Database.GameClient;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;
import java.awt.CardLayout;

public class GameWindow {
  private JFrame frame;
  private Animal animal;
  private Timer gameTimer;
  private ScreenFactory screenFactory;

  public GameWindow(Animal animal) {
    this.animal = animal;
    if (animal.getEnergy() <= 0) {
      SwingUtilities.invokeLater(() -> showEnergyAlert());
    }
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

    cardPanel.add(screenFactory.createPlayGroundScreen(), "Playground");
    cardPanel.add(screenFactory.createBedRoomScreen(), "Bedroom");
    cardPanel.add(screenFactory.createKitchenScreen(), "Kitchen");

    frame.add(cardPanel, BorderLayout.CENTER);
    frame.add(createNavigationPanel(cardPanel, cardLayout), BorderLayout.SOUTH);
    frame.setJMenuBar(createMenuBar());

    setupAutoSave();
    frame.setVisible(true);
  }

  private void showEnergyAlert() {
    BorderLayout layout = (BorderLayout) frame.getContentPane().getLayout();
    JPanel cardPanel = (JPanel) layout.getLayoutComponent(BorderLayout.CENTER);
    CardLayout cardLayout = (CardLayout) cardPanel.getLayout();

    int response = JOptionPane.showConfirmDialog(
        frame,
        "Your pet is completely exhausted! Would you like to go to the bedroom to rest?",
        "Energy Depleted",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.WARNING_MESSAGE
    );

    if (response == JOptionPane.YES_OPTION) {
      cardLayout.show(cardPanel, "Bedroom");
      if (screenFactory != null && screenFactory.getSleepButton() != null) {
        screenFactory.getSleepButton().doClick();
      }
    }
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
    JMenuItem mainMenuItem = new JMenuItem("Return to Main Menu");
    JMenuItem exitItem = new JMenuItem("Exit");

    saveItem.addActionListener(e -> saveGame());
    mainMenuItem.addActionListener(e -> returnToMainMenu());
    exitItem.addActionListener(e -> exitGame());

    gameMenu.add(saveItem);
    gameMenu.addSeparator();
    gameMenu.add(mainMenuItem);
    gameMenu.addSeparator();
    gameMenu.add(exitItem);
    menuBar.add(gameMenu);

    return menuBar;
  }

  private void saveGame() {
    boolean saved = GameClient.saveGameData(animal);
    // Uncomment if you want to show save confirmation
    // String message = saved ? "Game saved successfully!" : "Failed to save game!";
    // JOptionPane.showMessageDialog(frame, message);
  }

  private void exitGame() {
    frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
  }

  private void returnToMainMenu() {
    // Save game before returning to main menu
    saveGame();

    // Close current window
    frame.dispose();
    stopGameTimer();
    GameClient.logout();

    // Open main menu
    SwingUtilities.invokeLater(() -> new MainMenu());
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

    // Previous button
    JButton prevBtn = new JButton() {
      @Override
      protected void paintComponent(Graphics g) {
        if (getIcon() != null) {
          ImageIcon icon = (ImageIcon) getIcon();
          g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
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

    // Next button
    JButton nextBtn = new JButton("") {
      @Override
      protected void paintComponent(Graphics g) {
        if (getIcon() != null) {
          ImageIcon icon = (ImageIcon) getIcon();
          g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
        } else {
          super.paintComponent(g);
        }
      }
    };

    try {
      ImageIcon prevIcon = new ImageIcon(getClass().getResource("/icons/Button_left.png"));
      Image prevImg = prevIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
      prevBtn.setIcon(new ImageIcon(prevImg));

      ImageIcon nextIcon = new ImageIcon(getClass().getResource("/icons/Button_right.png"));
      Image nextImg = nextIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
      nextBtn.setIcon(new ImageIcon(nextImg));
    } catch (Exception e) {
      System.err.println("Navigation button images not found, using text arrows only");
    }

    for (JButton btn : new JButton[]{prevBtn, nextBtn}) {
      btn.setBorderPainted(false);
      btn.setFocusPainted(false);
      btn.setContentAreaFilled(false);
      btn.setPreferredSize(new Dimension(60, 60));
    }

    prevBtn.addActionListener(e -> {
      if (screenFactory.isSleeping()) {
        JOptionPane.showMessageDialog(frame,
            "Can't change screens while pet is sleeping!",
            "Warning",
            JOptionPane.WARNING_MESSAGE);
      } else {
        cardLayout.previous(cardPanel);
      }
    });

    nextBtn.addActionListener(e -> {
      if (screenFactory.isSleeping()) {
        JOptionPane.showMessageDialog(frame,
            "Can't change screens while pet is sleeping!",
            "Warning",
            JOptionPane.WARNING_MESSAGE);
      } else {
        cardLayout.next(cardPanel);
      }
    });

    panel.add(prevBtn, BorderLayout.WEST);
    panel.add(nextBtn, BorderLayout.EAST);

    JLabel userLabel = new JLabel("User: " + GameClient.getCurrentUser(), SwingConstants.CENTER);
    panel.add(userLabel, BorderLayout.CENTER);

    return panel;
  }
}