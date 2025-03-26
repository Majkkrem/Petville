import java.awt.*;
import java.io.File;
import java.io.IOException;

public class CustomFont {
  private Font font;

  public CustomFont() {
    try {
      font = Font.createFont(Font.TRUETYPE_FONT, new File("PressStart2P-Regular.ttf"));
    } catch (FontFormatException | IOException e) {
      throw new RuntimeException(e);
    }
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    ge.registerFont(font);
  }

  public Font getFont() {
    return font;
  }
}


