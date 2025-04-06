import Animals.Animal;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class ScreenFactory {
  private JFrame frame;
  private Animal animal;
  private int coins = 100;
  private Map<String, Integer> inventory = new HashMap<>();
  private Timer sleepTimer;
  private JPanel mainScreen;
  private JPanel kitchenScreen;
  private JPanel bedroomScreen;
  private Map<String, Map<Lables, JLabel>> overlayScreens;

  public ScreenFactory(JFrame frame, Animal animal) {
    this.frame = frame;
    this.animal = animal;
    this.overlayScreens = new HashMap<>();
    //createCommonStatsLabels();
  }

  private JPanel createStatsPanel(String screenName) {
    JPanel statsPanel = new JPanel(new GridLayout(1, 4));
    statsPanel.setOpaque(false);
    statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JLabel energyLabel = new JLabel("Energy: " + animal.getEnergy(), SwingConstants.CENTER);
    JLabel hungerLabel = new JLabel("Hunger: " + animal.getHunger(), SwingConstants.CENTER);
    JLabel healthLabel = new JLabel("Health: " + animal.getHealth(), SwingConstants.CENTER);
    JLabel moodLabel = new JLabel("Mood: " + animal.getMood(), SwingConstants.CENTER);

    Map<Lables, JLabel> overlayLabels = new HashMap<>();
    overlayLabels.put(Lables.ENERGY, energyLabel);
    overlayLabels.put(Lables.HUNGER, hungerLabel);
    overlayLabels.put(Lables.HEALTH, healthLabel);
    overlayLabels.put(Lables.MOOD, moodLabel);
    overlayScreens.put(screenName, overlayLabels);

    Font labelFont = new Font("Arial", Font.BOLD, 14);
    energyLabel.setFont(labelFont);
    hungerLabel.setFont(labelFont);
    healthLabel.setFont(labelFont);
    moodLabel.setFont(labelFont);

    energyLabel.setForeground(Color.BLACK);
    hungerLabel.setForeground(Color.BLACK);
    healthLabel.setForeground(Color.BLACK);
    moodLabel.setForeground(Color.BLACK);

    statsPanel.add(energyLabel);
    statsPanel.add(hungerLabel);
    statsPanel.add(healthLabel);
    statsPanel.add(moodLabel);

    return statsPanel;
  }

  public JPanel createMainScreen() {
    if (mainScreen == null) {
      mainScreen = new JPanel(new BorderLayout());
      ImageRescaler rescaler = new ImageRescaler(frame);

      ImageIcon backgroundIcon = new ImageIcon("images/Kitchen.png");
      rescaler.setImage(backgroundIcon.getImage());
      JLabel backgroundLabel = new JLabel();
      backgroundLabel.setLayout(new BorderLayout());
      rescaler.setBackgroundLabel(backgroundLabel);
      rescaler.setupRescaleListener();

      JPanel overlayPanel = new JPanel(new BorderLayout());
      overlayPanel.setOpaque(false);
      overlayPanel.add(createStatsPanel("MainScreen"), BorderLayout.NORTH);
      overlayPanel.add(createButtonPanel(), BorderLayout.SOUTH);
      backgroundLabel.add(overlayPanel);
      mainScreen.add(backgroundLabel, BorderLayout.CENTER);
    }
    return mainScreen;
  }

  public JPanel createKitchenScreen() {
    if (kitchenScreen == null) {
      kitchenScreen = new JPanel(new BorderLayout());
      ImageRescaler rescaler = new ImageRescaler(frame);

      ImageIcon backgroundIcon = new ImageIcon("images/Kitchen.png");
      rescaler.setImage(backgroundIcon.getImage());
      JLabel backgroundLabel = new JLabel();
      backgroundLabel.setLayout(new BorderLayout());
      backgroundLabel.setHorizontalAlignment(JLabel.CENTER);
      backgroundLabel.setVerticalAlignment(JLabel.CENTER);
      rescaler.setBackgroundLabel(backgroundLabel);
      rescaler.setupRescaleListener();

      JPanel overlayPanel = new JPanel(new BorderLayout());
      overlayPanel.setOpaque(false);

      JLabel kitchenLabel = new JLabel("Kitchen", SwingConstants.CENTER);
      kitchenLabel.setForeground(Color.WHITE);

      JPanel iconPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
      ImageIcon fridgeIcon = new ImageIcon("path/to/fridge_icon.png");
      ImageIcon inventoryIcon = new ImageIcon("path/to/inventory_icon.png");

      JButton fridgeButton = new JButton(fridgeIcon);
      JButton inventoryButton = new JButton(inventoryIcon);

      fridgeButton.addActionListener(e -> openShop());
      inventoryButton.addActionListener(e -> openInventory());

      iconPanel.add(fridgeButton);
      iconPanel.add(inventoryButton);

      overlayPanel.add(createStatsPanel("KitchenScreen"), BorderLayout.NORTH);
      overlayPanel.add(iconPanel, BorderLayout.CENTER);
      backgroundLabel.add(overlayPanel);
      kitchenScreen.add(backgroundLabel, BorderLayout.CENTER);
    }
    return kitchenScreen;
  }

  public JPanel createBedRoomScreen() {
    if (bedroomScreen == null) {
      bedroomScreen = new JPanel(new BorderLayout());
      ImageRescaler rescaler = new ImageRescaler(frame);

      ImageIcon backgroundIcon = new ImageIcon("images/bedroom_bed_style_39274_1920x1080.jpg");
      rescaler.setImage(backgroundIcon.getImage());
      JLabel backgroundLabel = new JLabel();
      backgroundLabel.setLayout(new BorderLayout());
      backgroundLabel.setHorizontalAlignment(JLabel.CENTER);
      backgroundLabel.setVerticalAlignment(JLabel.CENTER);
      rescaler.setBackgroundLabel(backgroundLabel);
      rescaler.setupRescaleListener();

      JPanel overlayPanel = new JPanel(new BorderLayout());
      overlayPanel.setOpaque(false);
      overlayPanel.add(createStatsPanel("BedroomScreen"), BorderLayout.NORTH);
      overlayPanel.add(createButtonPanel(), BorderLayout.SOUTH);
      backgroundLabel.add(overlayPanel);

      JLabel bedroomLabel = new JLabel("Bedroom", SwingConstants.CENTER);
      bedroomLabel.setForeground(Color.WHITE);

      bedroomScreen.add(backgroundLabel, BorderLayout.CENTER);
    }
    return bedroomScreen;
  }

  private JPanel createButtonPanel() {
    JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
    buttonPanel.setOpaque(false);

    ImageIcon feedIcon = new ImageIcon("path/to/feed_button.png");
    ImageIcon playIcon = new ImageIcon("path/to/play_button.png");
    ImageIcon sleepIcon = new ImageIcon("path/to/sleep_button.png");
    ImageIcon healIcon = new ImageIcon("path/to/heal_button.png");

    JButton feedButton = new JButton(feedIcon);
    JButton playButton = new JButton(playIcon);
    JToggleButton sleepButton = new JToggleButton(sleepIcon);
    JButton healButton = new JButton(healIcon);

    feedButton.addActionListener(e -> {
      animal.feed();
      updateStats();
    });

    playButton.addActionListener(e -> {
      animal.play();
      updateStats();
    });

    sleepButton.addActionListener(e -> {
      if (sleepButton.isSelected()) {
        animal.setSleeping(true);
        sleepTimer = new Timer();
        sleepTimer.scheduleAtFixedRate(new TimerTask() {
          @Override
          public void run() {
            animal.setEnergy(animal.getEnergy() + 5);
            updateStats();
          }
        }, 0, 1000);
      } else {
        animal.setSleeping(false);
        if (sleepTimer != null) {
          sleepTimer.cancel();
          sleepTimer = null;
        }
      }
    });

    healButton.addActionListener(e -> {
      animal.heal();
      updateStats();
    });

    buttonPanel.add(feedButton);
    buttonPanel.add(playButton);
    buttonPanel.add(sleepButton);
    buttonPanel.add(healButton);

    return buttonPanel;
  }

  public void updateStats() {
    SwingUtilities.invokeLater(() -> {
      overlayScreens.forEach((key, value) -> {
            value.forEach((k, v) -> {
              switch (k) {
                case ENERGY:
                  v.setText("Energy: " + animal.getEnergy());
                  break;
                case HUNGER:
                  v.setText("Hunger: " + animal.getHunger());
                  break;
                case HEALTH:
                  v.setText("Health: " + animal.getHealth());
                  break;
                case MOOD:
                  v.setText("Mood: " + animal.getMood());
                  break;
              }
            });
          }
      );

      if (mainScreen != null) mainScreen.repaint();
      if (kitchenScreen != null) kitchenScreen.repaint();
      if (bedroomScreen != null) bedroomScreen.repaint();
    });
  }

  private void openShop() {
    JDialog shopDialog = new JDialog(frame, "Shop", true);
    shopDialog.setSize(400, 300);
    shopDialog.setLocationRelativeTo(frame);

    JPanel shopPanel = new JPanel(new BorderLayout());

    JLabel goldLabel = new JLabel("Gold: " + coins, SwingConstants.CENTER);
    shopPanel.add(goldLabel, BorderLayout.NORTH);

    JPanel itemsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
    String[] items = {"Food", "Toy", "Health Potion", "Water"};
    int[] prices = {10, 20, 30, 5};

    for (int i = 0; i < items.length; i++) {
      JPanel itemPanel = new JPanel(new BorderLayout());
      JLabel itemLabel = new JLabel(items[i], SwingConstants.CENTER);
      JButton buyButton = new JButton("Buy (" + prices[i] + " gold)");

      int finalI = i;
      buyButton.addActionListener(e -> {
        if (coins >= prices[finalI]) {
          coins -= prices[finalI];
          goldLabel.setText("Gold: " + coins);
          inventory.put(items[finalI], inventory.getOrDefault(items[finalI], 0) + 1);
          JOptionPane.showMessageDialog(shopDialog, "You bought " + items[finalI] + "!");
        } else {
          JOptionPane.showMessageDialog(shopDialog, "Not enough gold!", "Error", JOptionPane.ERROR_MESSAGE);
        }
      });

      itemPanel.add(itemLabel, BorderLayout.CENTER);
      itemPanel.add(buyButton, BorderLayout.SOUTH);
      itemsPanel.add(itemPanel);
    }

    shopPanel.add(itemsPanel, BorderLayout.CENTER);

    JButton closeButton = new JButton("X");
    closeButton.addActionListener(e -> shopDialog.dispose());
    shopPanel.add(closeButton, BorderLayout.SOUTH);

    shopDialog.add(shopPanel);
    shopDialog.setVisible(true);
  }

  private void openInventory() {
    JDialog inventoryDialog = new JDialog(frame, "Inventory", true);
    inventoryDialog.setSize(400, 300);
    inventoryDialog.setLocationRelativeTo(frame);

    JPanel inventoryPanel = new JPanel(new BorderLayout());

    JPanel itemsPanel = new JPanel(new GridLayout(2, 2, 10, 10));

    for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
      JPanel itemPanel = new JPanel(new BorderLayout());
      JLabel itemLabel = new JLabel(entry.getKey() + " (" + entry.getValue() + ")", SwingConstants.CENTER);
      JButton useButton = new JButton("Use");

      useButton.addActionListener(e -> {
        if (entry.getValue() > 0) {
          inventory.put(entry.getKey(), entry.getValue() - 1);
          itemLabel.setText(entry.getKey() + " (" + (entry.getValue() - 1) + ")");
          JOptionPane.showMessageDialog(inventoryDialog, "You used " + entry.getKey() + "!");
        } else {
          JOptionPane.showMessageDialog(inventoryDialog, "No " + entry.getKey() + " left!", "Error", JOptionPane.ERROR_MESSAGE);
        }
      });

      itemPanel.add(itemLabel, BorderLayout.CENTER);
      itemPanel.add(useButton, BorderLayout.SOUTH);
      itemsPanel.add(itemPanel);
    }

    inventoryPanel.add(itemsPanel, BorderLayout.CENTER);

    JButton closeButton = new JButton("X");
    closeButton.addActionListener(e -> inventoryDialog.dispose());
    inventoryPanel.add(closeButton, BorderLayout.SOUTH);

    inventoryDialog.add(inventoryPanel);
    inventoryDialog.setVisible(true);
  }
}