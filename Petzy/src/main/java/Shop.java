import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Shop {
  private JFrame frame;
  private int coins = 100;

  public Shop() {
    createAndShowGUI();
  }

  private void createAndShowGUI() {
    frame = new JFrame("Shop");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setSize(400, 300);

    JPanel panel = new JPanel(new GridLayout(3, 1));
    JLabel coinLabel = new JLabel("Coins: " + coins, SwingConstants.CENTER);
    JButton buyFoodButton = new JButton("Buy Food (10 coins)");
    JButton buyToyButton = new JButton("Buy Toy (20 coins)");

    buyFoodButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (coins >= 10) {
          coins -= 10;
          coinLabel.setText("Coins: " + coins);
          JOptionPane.showMessageDialog(frame, "You bought food!");
        } else {
          JOptionPane.showMessageDialog(frame, "Not enough coins!", "Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    });

    buyToyButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (coins >= 20) {
          coins -= 20;
          coinLabel.setText("Coins: " + coins);
          JOptionPane.showMessageDialog(frame, "You bought a toy!");
        } else {
          JOptionPane.showMessageDialog(frame, "Not enough coins!", "Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    });

    panel.add(coinLabel);
    panel.add(buyFoodButton);
    panel.add(buyToyButton);

    frame.add(panel);
    frame.setVisible(true);
  }
}