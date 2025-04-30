import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class CustomFont {
  private Font font;
  private static final String FONT_PATH = "/fonts/PressStart2P-Regular.ttf";

  public CustomFont() {
    try (InputStream is = getClass().getResourceAsStream(FONT_PATH)) {
      if (is != null) {
        font = Font.createFont(Font.TRUETYPE_FONT, is);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(font);
      } else {
        System.err.println("Font file not found at: " + FONT_PATH);
        font = new Font("Monospaced", Font.PLAIN, 12); // Fallback font
      }
    } catch (FontFormatException | IOException e) {
      System.err.println("Error loading font: " + e.getMessage());
      font = new Font("Monospaced", Font.PLAIN, 12); // Fallback font
    }
  }

  public Font getFont() {
    return font;
  }

  public Font getDerivedFont(float size) {
    return font.deriveFont(size);
  }

  public Font getDerivedFont(int style, float size) {
    return font.deriveFont(style, size);
  }
}