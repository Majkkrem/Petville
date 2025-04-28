import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class HopperGame extends JPanel implements ActionListener, KeyListener {
  final int WIDTH = 800;
  final int HEIGHT = 400;


  private ScreenFactory screenFactory;  // Add this field

  // Modify constructor to accept ScreenFactory
  public HopperGame(ScreenFactory screenFactory) {
    this.screenFactory = screenFactory;
    timer.setInitialDelay(0);
    requestFocusInWindow();
    setPreferredSize(new Dimension(WIDTH, HEIGHT));
    addKeyListener(this);
    setFocusable(true);

    // Remove the JFrame creation from here since HopperFrame will handle it
    setPreferredSize(new Dimension(WIDTH, HEIGHT));
    addKeyListener(this);
    setFocusable(true);
  }

  // Game states
  enum GameState { START, PLAYING, GAME_OVER }
  GameState gameState = GameState.START;

  // Player
  int playerX = 100;
  int playerY = 300;
  int playerSize = 40;
  boolean jumping = false;
  double velocityY = 0;
  double gravity = 0.4;
  double jumpStrength = -10;
  int jumpCount = 0;
  int maxJumps = 2;

  // Player animation
  int squashFrame = 0;
  final int SQUASH_DURATION = 8;

  // Enemies
  class Enemy {
    int x, y, width, height, speed;
    Color color;
    boolean scored = false; // Track if this enemy has been scored

    public Enemy(int x, int width, int height, int speed, Color color) {
      this.x = x;
      this.y = 350 - height;
      this.width = width;
      this.height = height;
      this.speed = speed;
      this.color = color;
    }

    void move() {
      x -= speed;
    }

    void draw(Graphics g) {
      g.setColor(color);
      g.fillRect(x, y, width, height);
    }
  }

  ArrayList<Enemy> enemies = new ArrayList<>();
  Random rand = new Random();

  // Game timing
  int enemySpawnTimer = 0;
  int enemySpawnDelay = 150;
  int score = 0;
  int highScore = 0;
  int gameTime = 0;

  Timer timer = new Timer(16, this); // ~60 FPS


  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    // Background
    drawBackground(g);

    // Ground
    g.setColor(new Color(60, 179, 113));
    g.fillRect(0, 350, WIDTH, 50);

    switch (gameState) {
      case START:
        drawStartScreen(g);
        break;
      case PLAYING:
        drawGameElements(g);
        break;
      case GAME_OVER:
        drawGameElements(g);
        drawGameOverScreen(g);
        break;
    }
  }

  private void drawBackground(Graphics g) {
    Graphics2D g2d = (Graphics2D)g;
    GradientPaint gradient = new GradientPaint(0, 0, new Color(135, 206, 235), 0, HEIGHT, new Color(100, 149, 237));
    g2d.setPaint(gradient);
    g2d.fillRect(0, 0, WIDTH, HEIGHT);

    // Clouds
    g.setColor(new Color(255, 255, 255, 150));
    for (int i = 0; i < 5; i++) {
      int cloudX = (i * 200 + gameTime / 2) % (WIDTH + 200) - 100;
      g.fillOval(cloudX, 50, 60, 30);
      g.fillOval(cloudX + 20, 40, 50, 40);
      g.fillOval(cloudX + 50, 50, 60, 30);
    }
  }

  private void drawStartScreen(Graphics g) {
    g.setColor(new Color(0, 0, 0, 150));
    g.fillRect(WIDTH / 2 - 150, HEIGHT / 2 - 100, 300, 200);

    g.setColor(Color.WHITE);
    g.setFont(new Font("Monospace", Font.BOLD, 36));
    g.drawString(" Hopper", WIDTH / 2 - 140, HEIGHT / 2 - 50);

    g.setFont(new Font("Monospace", Font.PLAIN, 18));
    g.drawString("Press SPACE to start", WIDTH / 2 - 90, HEIGHT / 2);
    g.drawString("SPACE to jump", WIDTH / 2 - 60, HEIGHT / 2 + 30);
    g.drawString("Double tap for double jump", WIDTH / 2 - 110, HEIGHT / 2 + 60);
  }

  private void drawGameElements(Graphics g) {
    // Shadow
    g.setColor(new Color(0, 0, 0, 50));
    g.fillOval(playerX + 5, 360, playerSize - 10, 10);

    // Player with squash effect
    g.setColor(new Color(30, 144, 255));
    int drawY = playerY;
    int drawHeight = playerSize;

    if (squashFrame > 0) {
      drawHeight = playerSize + (int)(4 * Math.sin((double)squashFrame / SQUASH_DURATION * Math.PI));
      drawY = playerY - (drawHeight - playerSize);
      squashFrame--;
    }

    g.fillRoundRect(playerX, drawY, playerSize, drawHeight, 10, 10);

    // Eyes
    g.setColor(Color.WHITE);
    g.fillOval(playerX + 10, drawY + 10, 10, 10);
    g.fillOval(playerX + 25, drawY + 10, 10, 10);
    g.setColor(Color.BLACK);
    g.fillOval(playerX + 12, drawY + 12, 5, 5);
    g.fillOval(playerX + 27, drawY + 12, 5, 5);

    // Enemies
    for (Enemy enemy : enemies) {
      enemy.draw(g);
    }

    // Score display
    g.setColor(Color.BLACK);
    g.setFont(new Font("Monospace", Font.BOLD, 20));
    g.drawString("Score: " + score, 20, 30);
    g.drawString("High Score: " + highScore, 20, 60);
    g.drawString("Level: " + (gameTime / 1000 + 1), 20, 90);
  }

  private void drawGameOverScreen(Graphics g) {
    g.setColor(new Color(0, 0, 0, 150));
    g.fillRect(WIDTH / 2 - 150, HEIGHT / 2 - 100, 300, 200);

    g.setColor(Color.WHITE);
    g.setFont(new Font("Monospace", Font.BOLD, 36));
    g.drawString("Game Over", WIDTH / 2 - 100, HEIGHT / 2 - 50);

    g.setFont(new Font("Monospace", Font.PLAIN, 20));
    g.drawString("Score: " + score, WIDTH / 2 - 40, HEIGHT / 2);
    g.drawString("High Score: " + highScore, WIDTH / 2 - 50, HEIGHT / 2 + 30);
    g.drawString("Mood +" + (score * 2), WIDTH / 2 - 40, HEIGHT / 2 + 60);
    g.drawString("Press R to restart", WIDTH / 2 - 80, HEIGHT / 2 + 90);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (gameState != GameState.PLAYING) return;

    gameTime++;

    // Player physics
    if (jumping) {
      playerY += velocityY;
      velocityY += gravity;

      if (playerY >= 300) {
        playerY = 300;
        jumping = false;
        velocityY = 0;
        jumpCount = 0;
        squashFrame = SQUASH_DURATION;
      }
    }

    // Spawn enemies with progressive difficulty
    enemySpawnTimer++;
    if (enemySpawnTimer >= enemySpawnDelay) {
      int difficultyLevel = gameTime / 800;

      int width = 30 + rand.nextInt(30);
      int height = 30 + rand.nextInt(20);
      int baseSpeed = 3 + difficultyLevel / 2;
      int speed = Math.min(baseSpeed + rand.nextInt(2), 8);
      enemySpawnDelay = Math.max(50, 150 - difficultyLevel * 10);

      enemies.add(new Enemy(WIDTH, width, height, speed, new Color(200, 50, 50)));
      enemySpawnTimer = 0;
    }

    // Move enemies and check scoring
    Iterator<Enemy> it = enemies.iterator();
    while (it.hasNext()) {
      Enemy enemy = it.next();
      enemy.move();

      // Remove off-screen enemies
      if (enemy.x + enemy.width < 0) {
        it.remove();
        continue;
      }

      // Score when player jumps over an enemy (only +1 point)
      if (!enemy.scored && enemy.x + enemy.width < playerX && playerY < 300) {
        enemy.scored = true;
        score += 1; // Changed from 5 to 1
        highScore = Math.max(score, highScore);
      }

      // Collision detection
      if (enemy.x < playerX + playerSize &&
          enemy.x + enemy.width > playerX &&
          playerY < enemy.y + enemy.height &&
          playerY + playerSize > enemy.y)
      {
        gameState = GameState.GAME_OVER;
        stopGameTimer();
        int coinsEarned = score;
        int moodBoost = score * 2;
        SwingUtilities.invokeLater(() -> {
          if (screenFactory != null) {
            // Add coins and mood boost
            screenFactory.addGold(coinsEarned);
            screenFactory.addMood(moodBoost);

            JOptionPane.showMessageDialog(this,
                "Game Over! You earned " + coinsEarned + " coins and your pet's mood increased by " + moodBoost + "!",
                "Game Over",
                JOptionPane.INFORMATION_MESSAGE);
          } else {
            JOptionPane.showMessageDialog(this,
                "Game Over! You earned " + coinsEarned + " coins!",
                "Game Over",
                JOptionPane.INFORMATION_MESSAGE);
          }
        });
      }

      repaint();
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {
    switch (gameState) {
      case START:
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
          gameState = GameState.PLAYING;
          timer.start();
        }
        break;
      case PLAYING:
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
          if (jumpCount < maxJumps) {
            jumping = true;
            velocityY = jumpStrength;
            jumpCount++;
          }
        }
        break;
      case GAME_OVER:
        if (e.getKeyCode() == KeyEvent.VK_R) {
          resetGame();
        }
        break;
    }
  }



  private void resetGame() {
    enemies.clear();
    playerY = 300;
    score = 0;
    gameTime = 0;
    gameState = GameState.PLAYING;
    enemySpawnTimer = 0;
    enemySpawnDelay = 150;
    velocityY = 0;
    jumpCount = 0;
    timer.start();
  }

  void stopGameTimer() {
    timer.stop();
  }

  @Override public void keyReleased(KeyEvent e) {}
  @Override public void keyTyped(KeyEvent e) {}
}