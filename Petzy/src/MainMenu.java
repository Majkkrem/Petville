import Animals.Animal;
import Animals.Bird;
import Animals.Cat;
import Animals.Dog;
import Animals.Rabbit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class MainMenu {
  public MainMenu() {
    createAndShowGUI();
  }

  private void createAndShowGUI() {
    JFrame frame = new JFrame("Main Menu");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(300, 200);

    JPanel panel = new JPanel();
    JButton startButton = new JButton("Start Game");

    startButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        new GameWindow(new Animal(100, 100, 100, 100, 100));
        frame.dispose();
      }
    });

    panel.add(startButton);
    frame.add(panel);
    frame.setVisible(true);
  }

  private Scanner scanner = new Scanner(System.in);

  public Animal startMenu() {
    System.out.println("Üdv a Tamagotchi játékban!");
    System.out.print("Add meg a neved: ");
    String playerName = scanner.nextLine();

    System.out.println("Válassz állatot: 1) Kutya 2) Macska 3) Madár 4) Nyúl");
    int choice = scanner.nextInt();
    scanner.nextLine();

    System.out.print("Nevezd el az állatod: ");
    String petName = scanner.nextLine();

    switch (choice) {
      case 1: return new Dog(petName);
      case 2: return new Cat(petName);
      case 3: return new Bird(petName);
      case 4: return new Rabbit(petName);
      default:
        System.out.println("Érvénytelen választás. Alapértelmezettként kutyát kapsz.");
        return new Dog(petName);
    }
  }
}
