import Animals.Animal;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
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
  private boolean isSleeping = false;
  private JButton sleepButton;
  private boolean gameActive = false;  // Új flag a játék állapotának követésére
  private Timer statsTimer;

  public ScreenFactory(JFrame frame, Animal animal, JPanel cardPanel, CardLayout cardLayout) {
    this.frame = frame;
    this.animal = animal;
    this.cardPanel = cardPanel;
    this.cardLayout = cardLayout;
    this.overlayLabels = new HashMap<>();
    initializeInventory();
    startStatsDecreaseTimer();
  }

  private void startStatsDecreaseTimer() {
    if (statsTimer != null) {
      statsTimer.cancel();
    }

    statsTimer = new Timer();
    statsTimer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        if (!gameActive && !isSleeping) {  // Csak akkor csökkenjenek a statok, ha nincs aktív játék és nem alszik
          decreaseStats();
          SwingUtilities.invokeLater(() -> updateAllScreens());
        }
      }
    }, 0, 5000);  // 5 másodpercenként frissít
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
  }

  public void setGameActive(boolean active) {
    this.gameActive = active;
    if (!active) {
      updateAllScreens();  // Frissítjük a statokat, ha a játék bezárul
    }
  }

  private void initializeInventory() {
    inventory.put("Toy", 1);
    inventory.put("Health Potion", 0);
    inventory.put("Food", 2);
  }

  private JButton createStyledButton(String text) {
    JButton button = new JButton(text) {  // Set text in constructor
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (getIcon() != null) {
          ImageIcon icon = (ImageIcon) getIcon();
          g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
        } else {
          g.setColor(new Color(70, 130, 180));
          g.fillRect(0, 0, getWidth(), getHeight());
        }
        g.setColor(Color.WHITE);
        Font font = getFont().deriveFont(Font.BOLD, 14f);
        g.setFont(font);

        FontMetrics fm = g.getFontMetrics();
        Rectangle2D textBounds = fm.getStringBounds(getText(), g);

        // Calculate text position (centered)
        int textX = (getWidth() - (int)textBounds.getWidth()) / 2;
        int textY = (getHeight() - (int)textBounds.getHeight()) / 2 + fm.getAscent();

        g.drawString(getText(), textX, textY);
      }
    };

    button.setPreferredSize(new Dimension(150, 80));  // Slightly larger buttons
    button.setMinimumSize(new Dimension(150, 80));
    button.setMaximumSize(new Dimension(150, 80));

    try {
      ImageIcon icon = new ImageIcon(getClass().getResource("/icons/Button.png"));
      Image img = icon.getImage().getScaledInstance(160, 80, Image.SCALE_SMOOTH);
      button.setIcon(new ImageIcon(img));
      button.setFont(new Font("Arial", Font.BOLD, 18));
      button.setBorderPainted(false);
      button.setFocusPainted(false);
      button.setContentAreaFilled(false);
      button.setPreferredSize(new Dimension(160, 80)); // Slightly larger

    } catch (Exception e) {
      System.err.println("Error loading button image: " + e.getMessage());
      button.setBackground(new Color(70, 130, 180));
      button.setForeground(Color.WHITE);
      button.setFont(new Font("Arial", Font.BOLD, 14));
      button.setPreferredSize(new Dimension(160, 80));
    }

    // Ensure proper text alignment
    button.setHorizontalTextPosition(SwingConstants.CENTER);
    button.setVerticalTextPosition(SwingConstants.CENTER);

    return button;
  }

  public JPanel createPlayGroundScreen() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.add(createBackgroundPanel(), BorderLayout.CENTER);
    JPanel playgroundPanel = new JPanel(new BorderLayout());
    playgroundPanel.setOpaque(false);
    playgroundPanel.add(createStatsPanel("Playground"), BorderLayout.NORTH);
    JPanel buttonPanel = new JPanel();
    buttonPanel.setOpaque(false);
    JButton snakeGameButton = createStyledButton("Snake Game");
    snakeGameButton.addActionListener(e -> {
      setGameActive(true);  // Játék aktív
      new SnakeGameFrame(this);
    });
    JButton jumpGameButton = createStyledButton("Jump Game");
    jumpGameButton.addActionListener(e -> {
      setGameActive(true);  // Játék aktív
      new JumpGameFrame(this);
    });
    buttonPanel.add(snakeGameButton);
    buttonPanel.add(jumpGameButton);
    playgroundPanel.add(buttonPanel, BorderLayout.SOUTH);
    panel.add(playgroundPanel, BorderLayout.CENTER);
    return panel;
  }

  public JFrame getFrame() {
    return frame;
  }

  public JPanel createBedRoomScreen() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.add(createBackgroundPanel(), BorderLayout.CENTER);

    JPanel bedroomPanel = new JPanel(new BorderLayout());
    bedroomPanel.setOpaque(false);

    bedroomPanel.add(createStatsPanel("Bedroom"), BorderLayout.NORTH);

    // Only add Sleep button
    JPanel buttonPanel = new JPanel();
    buttonPanel.setOpaque(false);
    sleepButton = createStyledButton("Sleep");
    sleepButton.addActionListener(e -> toggleSleep());
    buttonPanel.add(sleepButton);

    bedroomPanel.add(buttonPanel, BorderLayout.SOUTH);

    panel.add(bedroomPanel, BorderLayout.CENTER);
    return panel;
  }

  public boolean isSleeping() {
    return isSleeping;
  }

  public JButton getSleepButton() {
    return sleepButton;
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

  private JPanel createBackgroundPanel() {
    JLabel backgroundLabel = new JLabel("Placeholder Background", SwingConstants.CENTER);
    backgroundLabel.setOpaque(true);
    backgroundLabel.setBackground(Color.GRAY);

    JPanel panel = new JPanel(new BorderLayout());
    panel.add(backgroundLabel, BorderLayout.CENTER);
    return panel;
  }

  private void useItem(String item) {
    switch (item) {
      case "Food":
        animal.setHunger(Math.min(100, animal.getHunger() - 30));
        break;
      case "Health Potion":
        animal.setHealth(Math.min(100, animal.getHealth() + 50));
        break;
      case "Toy":
        animal.setMood(Math.min(100, animal.getMood() + 40));
        break;
    }
    updateAllScreens();
    JOptionPane.showMessageDialog(frame, "Used " + item + "!");
  }

  private JPanel createStatsPanel(String screenName) {
    JPanel statsPanel = new JPanel(new GridLayout(1, 4, 10, 10));
    statsPanel.setOpaque(false);
    statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    Map<Lables, JLabel> labels = new HashMap<>();

    for (Lables labelType : Lables.values()) {
      JLabel label = new JLabel(getLabelText(labelType), SwingConstants.CENTER);
      label.setForeground(Color.BLACK);
      label.setFont(new Font("Arial", Font.BOLD, 18));
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
    for (Map.Entry<String, Map<Lables, JLabel>> screenEntry : overlayLabels.entrySet()) {
      for (Map.Entry<Lables, JLabel> labelEntry : screenEntry.getValue().entrySet()) {
        labelEntry.getValue().setText(getLabelText(labelEntry.getKey()));
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
    JOptionPane.showMessageDialog(frame, "You earned " + amount + " coins!");
    updateAllScreens();
  }

  private void openShop() {
    JDialog shopDialog = new JDialog(frame, "Shop", true);
    shopDialog.setSize(400, 300);
    shopDialog.setLayout(new BorderLayout());

    JLabel coinLabel = new JLabel("Coins: " + coins, SwingConstants.CENTER);
    shopDialog.add(coinLabel, BorderLayout.NORTH);

    JPanel itemsPanel = new JPanel(new GridLayout(2, 1, 10, 10));

    // Only show Food and Health Potion in shop
    String[] items = {"Food", "Health Potion", "Toy"};
    int[] prices = {20, 30, 10};

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
          openInventory();
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
}