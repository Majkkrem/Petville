import Animals.Animal;
import Database.GameClient;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameWindow {
  private JFrame frame;
  private CardLayout cardLayout;
  private JPanel cardPanel;
  private Animal animal;
  private JMenuBar menuBar;

  public GameWindow(Animal animal) {
    this.animal = animal;
    createAndShowGUI(animal);
    setupAutoSave();
    createMenuBar();
  }

  private void createAndShowGUI(Animal animal) {
    frame = new JFrame("Petville - " + GameClient.getCurrentUser());
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(800, 600);
    frame.setLocationRelativeTo(null);
    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    frame.setResizable(false);

    cardLayout = new CardLayout();
    cardPanel = new JPanel(cardLayout);

    ScreenFactory screenFactory = new ScreenFactory(frame, animal);
    cardPanel.add(screenFactory.createMainScreen(), "MainScreen");
    cardPanel.add(screenFactory.createBedRoomScreen(), "BedRoomScreen");
    cardPanel.add(screenFactory.createKitchenScreen(), "KitchenScreen");

    JPanel navigationPanel = createNavigationPanel();
    frame.add(cardPanel, BorderLayout.CENTER);
    frame.add(navigationPanel, BorderLayout.SOUTH);
    cardPanel.revalidate();
    cardPanel.repaint();
    frame.setVisible(true);
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

    // Add status label
    JLabel statusLabel = new JLabel("Logged in as: " + GameClient.getCurrentUser(), SwingConstants.CENTER);
    navigationPanel.add(statusLabel, BorderLayout.CENTER);

    return navigationPanel;
  }
}