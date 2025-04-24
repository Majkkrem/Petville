<<<<<<< HEAD
<<<<<<< HEAD
=======
// ScreenFactory.java

>>>>>>> main
=======
// ScreenFactory.java
>>>>>>> parent of 76167e6 (Stoping the time while playing minigame)
import Animals.Animal;

import javax.swing.*;
import java.awt.*;
<<<<<<< HEAD
<<<<<<< HEAD
import java.awt.geom.Rectangle2D;
=======
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
>>>>>>> main
=======
import java.awt.event.ActionEvent;
>>>>>>> parent of 76167e6 (Stoping the time while playing minigame)
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class ScreenFactory {
  private final JFrame frame;
  private final Animal animal;
  private int coins = 100;
  private final Map<String, Integer> inventory = new HashMap<>();
  private Timer sleepTimer;
  private final JPanel cardPanel;
  private final CardLayout cardLayout;
  private final Map<String, Map<Lables, JLabel>> overlayLabels;

  public ScreenFactory(JFrame frame, Animal animal, JPanel cardPanel, CardLayout cardLayout) {
    this.frame = frame;
    this.animal = animal;
    this.cardPanel = cardPanel;
    this.cardLayout = cardLayout;
    this.overlayLabels = new HashMap<>();
    initializeInventory();
  }

  private void initializeInventory() {
    inventory.put("Toy", 1);
    inventory.put("Health Potion", 0);
    inventory.put("Water", 2);
  }

  private JButton createStyledButton(String text) {
<<<<<<< HEAD
<<<<<<< HEAD
    JButton button = new JButton(text) {  // Set text in constructor
=======
    JButton button = new JButton() {
>>>>>>> parent of 76167e6 (Stoping the time while playing minigame)
      @Override
      protected void paintComponent(Graphics g) {
        // Paint the button image if it exists
        if (getIcon() != null) {
          ImageIcon icon = (ImageIcon) getIcon();
          g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);

          // Paint the text manually
          g.setColor(Color.WHITE); // Set text color
          g.setFont(getFont());

          // Calculate text position for perfect centering
          FontMetrics fm = g.getFontMetrics();
          int textWidth = fm.stringWidth(getText());
          int textHeight = fm.getHeight();

          int x = (getWidth() - textWidth) / 2;
          int y = ((getHeight() - textHeight) / 2) + fm.getAscent();

          g.drawString(getText(), x, y);
        } else {
          // Fallback if no image
          super.paintComponent(g);
        }
      }
    };

    button.setText(text); // Set the button text

    try {
      // Load and scale the button image
      ImageIcon icon = new ImageIcon(getClass().getResource("/icons/Button.png"));
      Image img = icon.getImage().getScaledInstance(140, 70, Image.SCALE_SMOOTH);
      button.setIcon(new ImageIcon(img));

      // Style the button
      button.setFont(new Font("Monospace", Font.BOLD, 14));
      button.setBorderPainted(false);
      button.setFocusPainted(false);
      button.setContentAreaFilled(false);
      button.setPreferredSize(new Dimension(140, 70));

    } catch (Exception e) {
      System.err.println("Error loading button image: " + e.getMessage());
      // Fallback styling
      button.setBackground(new Color(70, 130, 180));
      button.setForeground(Color.WHITE);
      button.setFont(new Font("Monospace", Font.BOLD, 14));
    }
<<<<<<< HEAD

    // Ensure proper text alignment
    button.setHorizontalTextPosition(SwingConstants.CENTER);
    button.setVerticalTextPosition(SwingConstants.CENTER);
=======
    JButton button = new JButton(text) {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (getIcon() != null) {
          ImageIcon icon = (ImageIcon) getIcon();
          g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
        }

        g.setColor(Color.WHITE);
        g.setFont(getFont());
        FontMetrics fm = g.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(getText())) / 2;
        int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
        g.drawString(getText(), x, y);
      }
    };

    // Dynamically adjust button size based on text length
    button.setPreferredSize(new Dimension(150, 80));  // Slightly larger buttons
    button.setMinimumSize(new Dimension(150, 80));
    button.setMaximumSize(new Dimension(150, 80));

    button.setMargin(new Insets(0, 0, 0, 0));

    try {
      ImageIcon icon = new ImageIcon(getClass().getResource("/icons/Button.png"));
      Image img = icon.getImage().getScaledInstance(150, 80, Image.SCALE_SMOOTH);
      button.setIcon(new ImageIcon(img));
    } catch (Exception e) {
      button.setBackground(new Color(70, 130, 180));
      button.setOpaque(true);
    }

    button.setFont(new Font("Monospace", Font.BOLD, 16));  // Adjust font size if needed
    button.setForeground(Color.WHITE);
    button.setBorderPainted(false);
    button.setFocusPainted(false);
    button.setContentAreaFilled(false);
>>>>>>> main

    return button;
  }

<<<<<<< HEAD
  public JPanel createPlayGroundScreen() {
=======

  private JPanel createButtonPanel() {
    JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 5, 5));
    buttonPanel.setOpaque(false);

    String[] buttons = {"Snake Game", "Jump Game"};
    for (String text : buttons) {
      JButton button = createStyledButton(text);
      button.addActionListener(this::handleButtonAction);
      buttonPanel.add(button);
    }

    return buttonPanel;
  }

  public JPanel createMainScreen() {
>>>>>>> main
=======
    return button;
  }

  public JPanel createMainScreen() {
>>>>>>> parent of 76167e6 (Stoping the time while playing minigame)
    JPanel panel = new JPanel(new BorderLayout());
    panel.add(createBackgroundPanel(), BorderLayout.CENTER);
    panel.add(createOverlayPanel("Main"), BorderLayout.CENTER);
    return panel;
  }

  public JPanel createKitchenScreen() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.add(createBackgroundPanel(), BorderLayout.CENTER);

    JPanel kitchenPanel = new JPanel(new BorderLayout());
    kitchenPanel.setOpaque(false);

    JPanel iconPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    iconPanel.setOpaque(false);

    JButton fridgeButton = createStyledButton("Fridge");
    fridgeButton.addActionListener(e -> openShop());

    JButton inventoryButton = createStyledButton("Inventory");
    inventoryButton.addActionListener(e -> openInventory());

    iconPanel.add(fridgeButton);
    iconPanel.add(inventoryButton);

    kitchenPanel.add(iconPanel, BorderLayout.NORTH);
    kitchenPanel.add(createStatsPanel("Kitchen"), BorderLayout.SOUTH);

    panel.add(kitchenPanel, BorderLayout.CENTER);
    return panel;
  }

  public JPanel createBedRoomScreen() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.add(createBackgroundPanel(), BorderLayout.CENTER);

    JPanel bedroomPanel = new JPanel(new BorderLayout());
    bedroomPanel.setOpaque(false);

    bedroomPanel.add(createStatsPanel("Bedroom"), BorderLayout.NORTH);
    bedroomPanel.add(createButtonPanel(), BorderLayout.SOUTH);

    panel.add(bedroomPanel, BorderLayout.CENTER);
    return panel;
  }

  private JPanel createBackgroundPanel() {
    JLabel backgroundLabel = new JLabel("Placeholder Background", SwingConstants.CENTER);
    backgroundLabel.setOpaque(true);
    backgroundLabel.setBackground(Color.GRAY);

    JPanel panel = new JPanel(new BorderLayout());
    panel.add(backgroundLabel, BorderLayout.CENTER);
    return panel;
  }

  private JPanel createOverlayPanel(String screenName) {
    JPanel overlay = new JPanel(new BorderLayout());
    overlay.setOpaque(false);

    overlay.add(createStatsPanel(screenName), BorderLayout.NORTH);
    overlay.add(createButtonPanel(), BorderLayout.SOUTH);

    return overlay;
  }

  private JPanel createStatsPanel(String screenName) {
    JPanel statsPanel = new JPanel(new GridLayout(1, 4, 10, 10));
    statsPanel.setOpaque(false);
    statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    Map<Lables, JLabel> labels = new HashMap<>();

    for (Lables labelType : Lables.values()) {
      JLabel label = new JLabel(getLabelText(labelType), SwingConstants.CENTER);
      label.setForeground(Color.WHITE);
      label.setFont(new Font("Arial", Font.BOLD, 14));
      statsPanel.add(label);
      labels.put(labelType, label);
    }

    overlayLabels.put(screenName, labels);

    return statsPanel;
  }

  private String getLabelText(Lables labelType) {
    switch (labelType) {
      case ENERGY:
        return "Energy: " + animal.getEnergy();
      case HUNGER:
        return "Hunger: " + animal.getHunger();
      case HEALTH:
        return "Health: " + animal.getHealth();
      case MOOD:
        return "Mood: " + animal.getMood();
      default:
        return "";
    }
  }

<<<<<<< HEAD
<<<<<<< HEAD
=======
  private boolean isGameOpened = false;  // Keep track if the game has been opened


  private void handleButtonAction(ActionEvent e) {
    String command = ((JButton) e.getSource()).getText();

    // Prevent opening the game if it's already opened
    if (isGameOpened) {
      JOptionPane.showMessageDialog(frame, "You can only play one game at a time!");
      return;
    }

    switch (command) {
      case "Snake Game":
        isGameOpened = true;  // Mark the game as opened
        SnakeGameFrame snakeFrame = new SnakeGameFrame(this);
        snakeFrame.addWindowListener(new WindowAdapter() {
          @Override
          public void windowClosed(WindowEvent e) {
            isGameOpened = false;  // Mark the game as closed
          }
        });
        break;
      case "Jump Game":
        isGameOpened = true;
        JumpGameFrame jumpFrame = new JumpGameFrame(this);
        jumpFrame.addWindowListener(new WindowAdapter() {
          @Override
          public void windowClosed(WindowEvent e) {
            isGameOpened = false;
          }
        });
        break;
    }
    updateAllScreens();
  }

  public void setGameOpened(boolean isOpened) {
    this.isGameOpened = isOpened;
  }

  public JFrame getFrame() {
    return frame;
  }

>>>>>>> main
  private void toggleSleep() {
    isSleeping = !isSleeping;
    animal.setSleeping(isSleeping);
=======
  private JPanel createButtonPanel() {
    JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 5, 5));
    buttonPanel.setOpaque(false);
>>>>>>> parent of 76167e6 (Stoping the time while playing minigame)

    String[] buttons = {"Feed", "Play", "Sleep", "Heal", "Snake Game"};
    for (String text : buttons) {
      JButton button = createStyledButton(text);
      button.addActionListener(this::handleButtonAction);
      buttonPanel.add(button);
    }

    return buttonPanel;
  }

  private void handleButtonAction(ActionEvent e) {
    String command = ((JButton) e.getSource()).getText();
    switch (command) {
      case "Feed":
        animal.feed();
        break;
      case "Play":
        animal.play();
        break;
      case "Sleep":
        toggleSleep();
        break;
      case "Heal":
        animal.heal();
        break;
      case "Snake Game":
        new SnakeGameFrame(this);
        break;
    }
    updateAllScreens();
  }

  private void toggleSleep() {
    if (sleepTimer == null) {
      animal.setSleeping(true);
      sleepTimer = new Timer();
      sleepTimer.scheduleAtFixedRate(new TimerTask() {
        @Override
        public void run() {
          animal.setEnergy(Math.min(100, animal.getEnergy() + 5));
          SwingUtilities.invokeLater(() -> updateAllScreens());
        }
      }, 0, 1000);
    } else {
      animal.setSleeping(false);
      sleepTimer.cancel();
      sleepTimer = null;
    }
  }

  public void updateAllScreens() {
    for (Map.Entry<String, Map<Lables, JLabel>> screenEntry : overlayLabels.entrySet()) {
      for (Map.Entry<Lables, JLabel> labelEntry : screenEntry.getValue().entrySet()) {
        labelEntry.getValue().setText(getLabelText(labelEntry.getKey()));
      }
    }
    cardPanel.revalidate();
    cardPanel.repaint();
  }

  public void addGold(int amount) {
    coins += amount;
    JOptionPane.showMessageDialog(frame, "You earned " + amount + " coins!");
    updateAllScreens();
  }

  private void openShop() {
    JDialog shopDialog = new JDialog(frame, "Shop", true);
    shopDialog.setSize(400, 300);
    shopDialog.setLayout(new BorderLayout());

    JLabel coinLabel = new JLabel("Coins: " + coins, SwingConstants.CENTER);
    shopDialog.add(coinLabel, BorderLayout.NORTH);

    JPanel itemsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
    String[] items = {"Food", "Toy", "Health Potion", "Water"};
    int[] prices = {10, 20, 30, 5};

    for (int i = 0; i < items.length; i++) {
      JPanel itemPanel = new JPanel(new BorderLayout());
      JButton buyButton = createStyledButton("Buy (" + prices[i] + ")");
      int finalI = i;

      buyButton.addActionListener(e -> {
        if (coins >= prices[finalI]) {
          coins -= prices[finalI];
          inventory.put(items[finalI], inventory.getOrDefault(items[finalI], 0) + 1);
          coinLabel.setText("Coins: " + coins);
          JOptionPane.showMessageDialog(shopDialog, "Purchased " + items[finalI] + "!");
        } else {
          JOptionPane.showMessageDialog(shopDialog, "Not enough coins!", "Error", JOptionPane.ERROR_MESSAGE);
        }
      });

      itemPanel.add(new JLabel(items[i], SwingConstants.CENTER), BorderLayout.CENTER);
      itemPanel.add(buyButton, BorderLayout.SOUTH);
      itemsPanel.add(itemPanel);
    }

    shopDialog.add(itemsPanel, BorderLayout.CENTER);
    shopDialog.setLocationRelativeTo(frame);
    shopDialog.setVisible(true);
  }

  private void openInventory() {
    JDialog inventoryDialog = new JDialog(frame, "Inventory", true);
    inventoryDialog.setSize(400, 300);
    inventoryDialog.setLayout(new BorderLayout());

    JPanel itemsPanel = new JPanel(new GridLayout(2, 2, 10, 10));

    for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
      if (entry.getValue() > 0) {
        JPanel itemPanel = new JPanel(new BorderLayout());
        JButton useButton = createStyledButton("Use (" + entry.getValue() + ")");

        useButton.addActionListener(e -> {
          inventory.put(entry.getKey(), entry.getValue() - 1);
          useItem(entry.getKey());
          inventoryDialog.dispose();
          openInventory(); // Refresh
        });

        itemPanel.add(new JLabel(entry.getKey(), SwingConstants.CENTER), BorderLayout.CENTER);
        itemPanel.add(useButton, BorderLayout.SOUTH);
        itemsPanel.add(itemPanel);
      }
    }

    inventoryDialog.add(itemsPanel, BorderLayout.CENTER);
    inventoryDialog.setLocationRelativeTo(frame);
    inventoryDialog.setVisible(true);
  }

  private void useItem(String item) {
    switch (item) {
      case "Food":
        animal.feed();
        break;
      case "Toy":
        animal.play();
        break;
      case "Health Potion":
        animal.heal();
        break;
    }
    updateAllScreens();
    JOptionPane.showMessageDialog(frame, "Used " + item + "!");
  }
}