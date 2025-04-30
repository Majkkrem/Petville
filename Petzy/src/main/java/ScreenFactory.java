import Animals.Animal;
import Database.GameClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import java.io.IOException;

public class ScreenFactory {
  private final JFrame frame;
  private final Animal animal;
  private int coins = 100;
  private final Map<String, Integer> inventory = new HashMap<>();
  private Timer sleepTimer;
  private final JPanel cardPanel;
  private final CardLayout cardLayout;
  private final Map<String, Map<Lables, JProgressBar>> overlayBars;
  private boolean isSleeping = false;
  private JButton sleepButton;
  private boolean gameActive = false;
  private Timer statsTimer;
  private JLabel animalImageLabel;
  public Map<String, Integer> getInventory() {
    return inventory;
  }

  public int getCoins() {
    return coins;
  }

  public enum Lables {
    ENERGY, HUNGER, HEALTH, MOOD
  }

  public Animal getAnimal() {
    return animal;
  }

  public void addMood(int amount) {
    animal.setMood(Math.min(100, animal.getMood() + amount));
    updateAllScreens();
  }

  public ScreenFactory(JFrame frame, Animal animal, JPanel cardPanel, CardLayout cardLayout) {
    this.frame = frame;
    this.animal = animal;
    this.cardPanel = cardPanel;
    this.cardLayout = cardLayout;
    this.overlayBars = new HashMap<>();

    this.coins = GameClient.loadCoins();
    Map<String, Integer> loadedInventory = GameClient.loadInventoryData();
    if (loadedInventory != null) {
      this.inventory.putAll(loadedInventory);
    } else {
      initializeInventory();
    }

    startStatsDecreaseTimer();
    updateAllScreens();
  }

  private void startStatsDecreaseTimer() {
    if (statsTimer != null) {
      statsTimer.cancel();
    }

    statsTimer = new Timer();
    statsTimer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        if (!gameActive && !isSleeping) {
          decreaseStats();
          SwingUtilities.invokeLater(() -> updateAllScreens());
        }
      }
    }, 0, 5000);
  }

  private void decreaseStats() {
    animal.setEnergy(Math.max(0, animal.getEnergy() - 2));
    animal.setHunger(Math.min(100, animal.getHunger() + 5));
    animal.setMood(Math.max(0, animal.getMood() - 3));

    if (animal.getHunger() == 100) {
      animal.setHealth(Math.max(0, animal.getHealth() - 5));
    }
    if (animal.getMood() == 0) {
      animal.setHealth(Math.max(0, animal.getHealth() - 5));
    }

    // Check for energy depletion
    if (animal.getEnergy() <= 0 && !isSleeping) {
      SwingUtilities.invokeLater(() -> {
        int response = JOptionPane.showConfirmDialog(
            frame,
            "Your pet is completely exhausted! Would you like to go to the bedroom to rest?",
            "Energy Depleted",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (response == JOptionPane.YES_OPTION) {
          cardLayout.show(cardPanel, "Bedroom");
          if (sleepButton != null) {
            sleepButton.doClick(); // Automatically put to sleep
          }
        }
      });
    }

    // Check for health depletion
    if (animal.getHealth() <= 0) {
      SwingUtilities.invokeLater(() -> {
        JOptionPane.showMessageDialog(
            frame,
            "Your pet has died! You'll be returned to the main menu.",
            "Pet Died",
            JOptionPane.ERROR_MESSAGE
        );
        // Return to main menu
        frame.dispose();
        new MainMenu();
      });
      // Stop the timer to prevent further execution
      if (statsTimer != null) {
        statsTimer.cancel();
        statsTimer = null;
      }
    }
  }

  
  public void setGameActive(boolean active) {
    this.gameActive = active;
    if (!active) {
      updateAllScreens();
    }
  }

  private void initializeInventory() {
    inventory.put("Health Potion", 0);
    inventory.put("Chocolate Doughnut", 0);
    inventory.put("Burrito", 0);
    inventory.put("Pink Doughnut", 0);
    inventory.put("Medium Energy", 0);
    inventory.put("Large Energy", 0);
  }

  private JButton createStyledButton(String text) {
    JButton button = new JButton(text);
    button.setFont(new Font("Arial", Font.BOLD, 18));
    button.setBackground(new Color(70, 130, 180));
    button.setForeground(Color.WHITE);
    button.setFocusPainted(false);
    button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
    return button;
  }

  public JPanel createPlayGroundScreen() {
    return createScreenWithBackground("icons/Garden.png", "Playground", true);
  }

  public JFrame getFrame() {
    return frame;
  }

  public JPanel createBedRoomScreen() {
    return createScreenWithBackground("icons/Bedroom.png", "Bedroom", false);
  }

  public boolean isSleeping() {
    return isSleeping;
  }

  public JButton getSleepButton() {
    return sleepButton;
  }

  public JPanel createKitchenScreen() {
    return createScreenWithBackground("icons/Kitchen.png", "Kitchen", false);
  }

  private JPanel createScreenWithBackground(String imagePath, String screenName, boolean isPlayground) {
    JPanel panel = new JPanel(new BorderLayout()) {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
          // Try multiple path variations
          InputStream is = getClass().getResourceAsStream(imagePath.startsWith("/") ? imagePath : "/" + imagePath);
          if (is == null) {
            is = getClass().getResourceAsStream(imagePath.startsWith("/") ? imagePath.substring(1) : imagePath);
          }

          if (is != null) {
            Image bgImage = ImageIO.read(is);
            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
          } else {
            throw new IOException("Image not found");
          }
        } catch (IOException e) {
          // Fallback background color based on screen
          Color bgColor = screenName.equals("Bedroom") ? new Color(50, 50, 80) :
              screenName.equals("Kitchen") ? new Color(80, 50, 50) :
                  new Color(50, 80, 50);
          g.setColor(bgColor);
          g.fillRect(0, 0, getWidth(), getHeight());
        }
      }
    };

    // Add stats panel to the top
    panel.add(createStatsPanel(screenName), BorderLayout.NORTH);

    // Create animal panel
    JPanel animalPanel = new JPanel(new BorderLayout());
    animalPanel.setOpaque(false);

    // Add animal image
    animalImageLabel = new JLabel();
    try {
      ImageIcon animalIcon = new ImageIcon(getClass().getResource("icons/" + animal.getClass().getSimpleName() + ".png"));
      Image scaledAnimal = animalIcon.getImage().getScaledInstance(350, 350, Image.SCALE_SMOOTH);
      animalImageLabel.setIcon(new ImageIcon(scaledAnimal));
    } catch (Exception e) {
      System.err.println("Error loading animal image: " + animal.getClass().getSimpleName() + ".png");
      // Fallback to default image
      try {
        ImageIcon defaultIcon = new ImageIcon(getClass().getResource("icons/Pet.png"));
        Image scaledDefault = defaultIcon.getImage().getScaledInstance(350, 350, Image.SCALE_SMOOTH);
        animalImageLabel.setIcon(new ImageIcon(scaledDefault));
      } catch (Exception ex) {
        System.err.println("Error loading default pet image");
      }
    }
    animalImageLabel.setHorizontalAlignment(JLabel.CENTER);

    // Position animal based on screen
    if (screenName.equals("Bedroom")) {
      // For bedroom - position lower than center
      JPanel wrapperPanel = new JPanel(new BorderLayout());
      wrapperPanel.setOpaque(false);

      // Add empty space at top to push animal down
      wrapperPanel.add(Box.createVerticalStrut(150), BorderLayout.NORTH); // Adjust this value to move animal up/down

      animalImageLabel = new JLabel();
      try {
        ImageIcon animalIcon = new ImageIcon(getClass().getResource("icons/" + animal.getClass().getSimpleName() + ".png"));
        Image scaledAnimal = animalIcon.getImage().getScaledInstance(350, 350, Image.SCALE_SMOOTH);
        animalImageLabel.setIcon(new ImageIcon(scaledAnimal));
      } catch (Exception e) {
        System.err.println("Error loading animal image: " + animal.getClass().getSimpleName() + ".png");
      }
      animalImageLabel.setHorizontalAlignment(JLabel.CENTER);

      wrapperPanel.add(animalImageLabel, BorderLayout.CENTER);
      animalPanel.add(wrapperPanel, BorderLayout.CENTER);
      animalPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    } else {
      // For garden and kitchen - bottom position
      animalPanel.add(animalImageLabel, BorderLayout.PAGE_END);
      animalPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
    }

    // Create buttons panel
    JPanel buttonPanel = new JPanel();
    buttonPanel.setOpaque(false);

    if (screenName.equals("Kitchen")) {
      // Kitchen - buttons on right side above the pet
      buttonPanel.setLayout(new BorderLayout());
      JPanel rightButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
      rightButtonPanel.setOpaque(false);
      rightButtonPanel.add(createImageButton("icons/Shop_icon.png", e -> openShop(), 100, 100));
      rightButtonPanel.add(createImageButton("icons/Inventory_icon.png", e -> openInventory(), 100, 100));
      buttonPanel.add(rightButtonPanel, BorderLayout.EAST);
      buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 20)); // Some right margin
    } else if (screenName.equals("Bedroom")) {
      sleepButton = createStyledButton("Sleep");
      sleepButton.addActionListener(e -> toggleSleep());
      buttonPanel.add(sleepButton);
    } else if (isPlayground) {
      JButton snakeGameButton = createStyledButton("Snake Game");
      snakeGameButton.addActionListener(e -> {
        setGameActive(true);
        new SnakeGameFrame(this);
      });
      JButton hopperGameButton = createStyledButton("Hopper Game");
      hopperGameButton.addActionListener(e -> {
        setGameActive(true);
        new HopperFrame(this);
      });
      buttonPanel.add(snakeGameButton);
      buttonPanel.add(hopperGameButton);
    }

    // Combine everything
    JPanel southPanel = new JPanel(new BorderLayout());
    southPanel.setOpaque(false);

    if (screenName.equals("Kitchen")) {
      // For kitchen, add buttons first then animal below
      southPanel.add(buttonPanel, BorderLayout.CENTER);
      southPanel.add(animalPanel, BorderLayout.SOUTH);
    } else {
      // For other screens, standard layout
      southPanel.add(animalPanel, BorderLayout.CENTER);
      southPanel.add(buttonPanel, BorderLayout.PAGE_END);
    }

    panel.add(southPanel, BorderLayout.CENTER);

    return panel;
  }

  private JButton createImageButton(String imagePath, ActionListener action, int width, int height) {
    try {
      // Try loading with exact path first
      InputStream is = getClass().getResourceAsStream(imagePath.startsWith("/") ? imagePath : "/" + imagePath);
      if (is == null) {
        // Try without leading slash if first attempt fails
        is = getClass().getResourceAsStream(imagePath.startsWith("/") ? imagePath.substring(1) : imagePath);
      }

      if (is != null) {
        ImageIcon originalIcon = new ImageIcon(ImageIO.read(is));
        Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        JButton button = new JButton(new ImageIcon(scaledImage));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.addActionListener(action);
        button.setPreferredSize(new Dimension(width, height));
        return button;
      }
    } catch (Exception e) {
      System.err.println("Error loading button image: " + imagePath);
    }

    // Fallback to text button
    JButton button = new JButton("Button");
    button.addActionListener(action);
    return button;
  }


  private JPanel createStatsPanel(String screenName) {
    JPanel statsPanel = new JPanel(new GridLayout(1, 4, 10, 10));
    statsPanel.setOpaque(false);
    statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    Map<Lables, JProgressBar> progressBars = new HashMap<>();

    for (Lables labelType : Lables.values()) {
      JPanel statPanel = new JPanel(new BorderLayout(5, 5));
      statPanel.setOpaque(false);
      statPanel.setPreferredSize(new Dimension(150, 60));

      // Load icon using the new method
      ImageIcon icon = loadIconResource(labelType.toString(), 100, 100);
      JLabel iconLabel = new JLabel(icon);
      iconLabel.setPreferredSize(new Dimension(100, 100));
      statPanel.add(iconLabel, BorderLayout.WEST);

      // Progress bar setup (unchanged)
      JProgressBar progressBar = new JProgressBar(0, 100);
      progressBar.setValue(getStatValue(labelType));
      progressBar.setStringPainted(true);
      progressBar.setBorderPainted(false);
      progressBar.setForeground(getStatColor(labelType));
      progressBar.setBackground(new Color(200, 200, 200, 150));
      progressBar.setString(String.valueOf(getStatValue(labelType)));
      progressBar.setPreferredSize(new Dimension(100, 20));

      JPanel progressContainer = new JPanel(new BorderLayout());
      progressContainer.setOpaque(false);
      progressContainer.add(progressBar, BorderLayout.CENTER);

      statPanel.add(progressContainer, BorderLayout.CENTER);
      statsPanel.add(statPanel);
      progressBars.put(labelType, progressBar);
    }

    overlayBars.put(screenName, progressBars);
    return statsPanel;
  }

  private ImageIcon loadIconResource(String iconName, int width, int height) {
    // Try multiple possible paths and cases
    String[] possiblePaths = {
        "/icons/" + iconName.toLowerCase() + ".png",
        "/icons/" + iconName.toUpperCase() + ".png",
        "/icons/" + iconName + ".png",
        "icons/" + iconName.toLowerCase() + ".png",
        "icons/" + iconName.toUpperCase() + ".png",
        "icons/" + iconName + ".png"
    };

    for (String path : possiblePaths) {
      try (InputStream is = getClass().getResourceAsStream(path)) {
        if (is != null) {
          ImageIcon icon = new ImageIcon(ImageIO.read(is));
          return new ImageIcon(icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
        }
      } catch (IOException e) {
        System.err.println("Error loading icon from path: " + path);
      }
    }

    // Fallback: create a colored circle icon
    return createFallbackIcon(iconName, width, height);
  }

  private ImageIcon createFallbackIcon(String iconName, int width, int height) {
    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = img.createGraphics();

    // Set color based on icon type
    if (iconName.equalsIgnoreCase("energy")) {
      g2d.setColor(new Color(100, 200, 100)); // Green
    } else if (iconName.equalsIgnoreCase("hunger")) {
      g2d.setColor(new Color(200, 150, 50)); // Orange
    } else if (iconName.equalsIgnoreCase("health")) {
      g2d.setColor(new Color(200, 50, 50)); // Red
    } else if (iconName.equalsIgnoreCase("mood")) {
      g2d.setColor(new Color(100, 100, 200)); // Blue
    } else {
      g2d.setColor(Color.GRAY); // Default
    }

    g2d.fillOval(2, 2, width-4, height-4);
    g2d.dispose();
    return new ImageIcon(img);
  }


  private int getStatValue(Lables labelType) {
    switch (labelType) {
      case ENERGY: return animal.getEnergy();
      case HUNGER: return animal.getHunger();
      case HEALTH: return animal.getHealth();
      case MOOD: return animal.getMood();
      default: return 0;
    }
  }

  private Color getStatColor(Lables labelType) {
    switch (labelType) {
      case ENERGY: return new Color(100, 200, 100);
      case HUNGER: return new Color(200, 150, 50);
      case HEALTH: return new Color(200, 50, 50);
      case MOOD: return new Color(100, 100, 200);
      default: return Color.BLUE;
    }
  }

  private void useItem(String item) {
    boolean canUse = true;
    String message = "Used " + item + " successfully!";

    switch (item) {
      case "Health Potion":
        if (animal.getHealth() >= 100) {
          canUse = false;
          message = "Health is already full!";
        } else {
          animal.setHealth(Math.min(100, animal.getHealth() + 50));
        }
        break;
      case "Chocolate Doughnut":
      case "Pink Doughnut":
      case "Burrito":
        if (animal.getHunger() <= 0) {
          canUse = false;
          message = "Pet is not hungry!";
        } else {
          animal.setHunger(Math.max(0, animal.getHunger() - 30));
        }
        break;
      case "Medium Energy":
        if (animal.getEnergy() >= 100) {
          canUse = false;
          message = "Energy is already full!";
        } else {
          animal.setEnergy(Math.min(100, animal.getEnergy() + 30));
        }
        break;
      case "Large Energy":
        if (animal.getEnergy() >= 100) {
          canUse = false;
          message = "Energy is already full!";
        } else {
          animal.setEnergy(100);
        }
        break;
    }

    if (canUse) {
      inventory.put(item, inventory.get(item) - 1);
      updateAllScreens();

      // Check if health reached 0 after using item
      if (animal.getHealth() <= 0) {
        SwingUtilities.invokeLater(() -> {
          JOptionPane.showMessageDialog(
              frame,
              "Your pet has died! You'll be returned to the main menu.",
              "Pet Died",
              JOptionPane.ERROR_MESSAGE
          );
          frame.dispose();
          new MainMenu();
        });
        return;
      }
    }
    JOptionPane.showMessageDialog(frame, message);
  }

  private void toggleSleep() {
    isSleeping = !isSleeping;
    animal.setSleeping(isSleeping);

    if (isSleeping) {
      sleepTimer = new Timer();
      sleepTimer.scheduleAtFixedRate(new TimerTask() {
        @Override
        public void run() {
          animal.setEnergy(Math.min(100, animal.getEnergy() + 5));
          SwingUtilities.invokeLater(() -> updateAllScreens());
        }
      }, 0, 1000);
      sleepButton.setText("Wake Up");
    } else {
      if (sleepTimer != null) {
        sleepTimer.cancel();
        sleepTimer = null;
      }
      sleepButton.setText("Sleep");
    }
  }

  public void updateAllScreens() {
    for (Map.Entry<String, Map<Lables, JProgressBar>> screenEntry : overlayBars.entrySet()) {
      for (Map.Entry<Lables, JProgressBar> barEntry : screenEntry.getValue().entrySet()) {
        int value = getStatValue(barEntry.getKey());
        barEntry.getValue().setValue(value);
        barEntry.getValue().setString(String.valueOf(value));
      }
    }
    if (sleepButton != null) {
      sleepButton.setText(isSleeping ? "Wake Up" : "Sleep");
    }
    cardPanel.revalidate();
    cardPanel.repaint();
  }

  public void addGold(int amount) {
    coins += amount;
    updateAllScreens();
  }

  private void openShop() {
    JDialog shopDialog = new JDialog(frame, "Shop", true);
    shopDialog.setSize(600, 500);
    shopDialog.setResizable(false);

    JPanel shopPanel = new JPanel(new BorderLayout()) {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
          Image bgImage = ImageIO.read(getClass().getResource("icons/Shop.png"));
          g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
        } catch (IOException e) {
          g.setColor(new Color(240, 240, 240, 200));
          g.fillRect(0, 0, getWidth(), getHeight());
        }
      }
    };
    shopPanel.setOpaque(false);
    shopPanel.setLayout(new BorderLayout());

    // Coin display panel
    JPanel coinPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    coinPanel.setOpaque(false);
    coinPanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20));

    JLabel coinAmountLabel = new JLabel(String.valueOf(coins));
    coinAmountLabel.setFont(new Font("Monospace", Font.BOLD, 18));
    coinAmountLabel.setForeground(Color.WHITE);

    try {
      ImageIcon coinIcon = new ImageIcon(getClass().getResource("icons/Coin.png"));
      Image scaledCoin = coinIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
      JLabel coinIconLabel = new JLabel(new ImageIcon(scaledCoin));
      coinPanel.add(coinIconLabel);
      coinPanel.add(coinAmountLabel);
    } catch (Exception e) {
      System.err.println("Error loading coin icon");
    }
    shopPanel.add(coinPanel, BorderLayout.NORTH);

    // Items panel with padding to push items down
    JPanel itemsContainer = new JPanel(new BorderLayout());
    itemsContainer.setOpaque(false);
    itemsContainer.setBorder(BorderFactory.createEmptyBorder(110, 85, 70, 85));

    JPanel itemsPanel = new JPanel(new GridLayout(2, 3, 40, 40));
    itemsPanel.setOpaque(false);

    String[] items = {"Health Potion", "Chocolate Doughnut", "Burrito", "Pink Doughnut", "Medium Energy", "Large Energy"};
    String[] imagePaths = {"Poti.png", "Chocolate-doughnut.png", "Burrito.png", "Pink-doughnut.png", "EnergyBar_big.png", "EnergyBar_small.png"};
    int[] prices = {50, 10, 20, 12, 25, 75};

    for (int i = 0; i < items.length; i++) {
      JPanel itemPanel = new JPanel(new BorderLayout());
      itemPanel.setOpaque(false);

      try {
        ImageIcon itemIcon = new ImageIcon(getClass().getResource("icons/" + imagePaths[i]));
        Image scaledItem = itemIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JButton itemButton = new JButton(new ImageIcon(scaledItem));
        itemButton.setBorderPainted(false);
        itemButton.setContentAreaFilled(false);
        itemButton.setFocusPainted(false);

        int finalI = i;
        itemButton.addActionListener(e -> {
          if (coins >= prices[finalI]) {
            coins -= prices[finalI];
            inventory.put(items[finalI], inventory.getOrDefault(items[finalI], 0) + 1);
            coinAmountLabel.setText(String.valueOf(coins));
            JOptionPane.showMessageDialog(shopDialog, "Purchase successful!");
          } else {
            JOptionPane.showMessageDialog(shopDialog, "Not enough coins!", "Error", JOptionPane.ERROR_MESSAGE);
          }
        });

        JLabel priceLabel = new JLabel(prices[i] + " coins", SwingConstants.CENTER);
        priceLabel.setFont(new Font("Arial", Font.BOLD, 14));
        priceLabel.setForeground(Color.WHITE);

        itemPanel.add(itemButton, BorderLayout.CENTER);
        itemPanel.add(priceLabel, BorderLayout.SOUTH);
        itemsPanel.add(itemPanel);
      } catch (Exception e) {
        System.err.println("Error loading shop item: " + items[i]);
      }
    }

    itemsContainer.add(itemsPanel, BorderLayout.CENTER);
    shopPanel.add(itemsContainer, BorderLayout.CENTER);
    shopDialog.setContentPane(shopPanel);
    shopDialog.setLocationRelativeTo(frame);
    shopDialog.setVisible(true);
  }

  private void openInventory() {
    JDialog inventoryDialog = new JDialog(frame, "Inventory", true);
    inventoryDialog.setSize(600, 500);
    inventoryDialog.setResizable(false);

    JPanel inventoryPanel = new JPanel(new BorderLayout()) {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
          Image bgImage = ImageIO.read(getClass().getResource("icons/Inventory.png"));
          g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
        } catch (IOException e) {
          g.setColor(new Color(240, 240, 240, 200));
          g.fillRect(0, 0, getWidth(), getHeight());
        }
      }
    };
    inventoryPanel.setOpaque(false);
    inventoryPanel.setLayout(new BorderLayout());

    // Items container with padding to push items down
    JPanel itemsContainer = new JPanel(new BorderLayout());
    itemsContainer.setOpaque(false);
    itemsContainer.setBorder(BorderFactory.createEmptyBorder(110, 85, 70, 85));

    JPanel itemsPanel = new JPanel(new GridLayout(2, 3, 40, 40));
    itemsPanel.setOpaque(false);

    String[] items = {"Health Potion", "Chocolate Doughnut", "Burrito", "Pink Doughnut", "Medium Energy", "Large Energy"};
    String[] imagePaths = {"Poti.png", "Chocolate-doughnut.png", "Burrito.png", "Pink-doughnut.png", "Medium-energy.png", "Large-energy.png"};

    for (int i = 0; i < items.length; i++) {
      if (inventory.get(items[i]) > 0) {
        JPanel itemPanel = new JPanel(new BorderLayout());
        itemPanel.setOpaque(false);

        try {
          ImageIcon itemIcon = new ImageIcon(getClass().getResource("icons/" + imagePaths[i]));
          Image scaledItem = itemIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
          JButton itemButton = new JButton(new ImageIcon(scaledItem));
          itemButton.setBorderPainted(false);
          itemButton.setContentAreaFilled(false);
          itemButton.setFocusPainted(false);

          String finalItem = items[i];
          itemButton.addActionListener(e -> {
            useItem(finalItem);
            inventoryDialog.dispose();
            openInventory();
          });

          JLabel quantityLabel = new JLabel("x" + inventory.get(items[i]), SwingConstants.CENTER);
          quantityLabel.setFont(new Font("Arial", Font.BOLD, 14));
          quantityLabel.setForeground(Color.WHITE);

          itemPanel.add(itemButton, BorderLayout.CENTER);
          itemPanel.add(quantityLabel, BorderLayout.SOUTH);
          itemsPanel.add(itemPanel);
        } catch (Exception e) {
          System.err.println("Error loading inventory item: " + items[i]);
        }
      } else {
        // Empty slot
        JPanel emptyPanel = new JPanel();
        emptyPanel.setOpaque(false);
        itemsPanel.add(emptyPanel);
      }
    }

    itemsContainer.add(itemsPanel, BorderLayout.CENTER);
    inventoryPanel.add(itemsContainer, BorderLayout.CENTER);
    inventoryDialog.setContentPane(inventoryPanel);
    inventoryDialog.setLocationRelativeTo(frame);
    inventoryDialog.setVisible(true);
  }
}