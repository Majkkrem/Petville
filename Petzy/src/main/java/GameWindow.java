import Animals.Animal;

import javax.swing.*;
import java.awt.*;

public class GameWindow {
  private JFrame frame;
  private CardLayout cardLayout;
  private JPanel cardPanel;

  public GameWindow(Animal animal) {
    createAndShowGUI(animal);
  }

  private void createAndShowGUI(Animal animal) {
    frame = new JFrame("Petville");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(800, 600);
    frame.setLocationRelativeTo(null);
    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    frame.setResizable(false);

    cardLayout = new CardLayout();
    cardPanel = new JPanel(cardLayout);

    ScreenFactory screenFactory = new ScreenFactory(frame, animal);
    cardPanel.add(screenFactory.createMainScreen(), "MainScreen");
    //cardPanel.add(screenFactory.createShopScreen(), "ShopScreen");
    cardPanel.add(screenFactory.createBedRoomScreen(), "BedRoomScreen");
    cardPanel.add(screenFactory.createKitchenScreen(), "KitchenScreen");

    JPanel navigationPanel = createNavigationPanel();
    frame.add(cardPanel, BorderLayout.CENTER);
    frame.add(navigationPanel, BorderLayout.SOUTH);
    cardPanel.revalidate();
    cardPanel.repaint();
    frame.setVisible(true);
  }

  private JPanel createNavigationPanel() {
    JPanel navigationPanel = new JPanel(new BorderLayout());
    JButton leftButton = new JButton("←");
    JButton rightButton = new JButton("→");

    leftButton.addActionListener(e -> cardLayout.previous(cardPanel));
    rightButton.addActionListener(e -> cardLayout.next(cardPanel));

    navigationPanel.add(leftButton, BorderLayout.WEST);
    navigationPanel.add(rightButton, BorderLayout.EAST);
    return navigationPanel;
  }
}