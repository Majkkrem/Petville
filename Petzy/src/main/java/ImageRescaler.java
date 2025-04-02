import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ImageRescaler {
  private Image image;
  private JLabel backgroundLabel;
  private JFrame frame;

  public ImageRescaler(JFrame frame) {
    this.frame = frame;
  }

  public void setImage(Image image) {
    this.image = image;
    rescaleImage(frame.getWidth(), frame.getHeight());
  }

  public void setBackgroundLabel(JLabel backgroundLabel) {
    this.backgroundLabel = backgroundLabel;
  }

  public void setupRescaleListener() {
    frame.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        rescaleImage(frame.getWidth(), frame.getHeight());
      }
    });
  }

  private void rescaleImage(int width, int height) {
    if (image != null && backgroundLabel != null) {
      double widthRatio = (double) width / image.getWidth(null);
      double heightRatio = (double) height / image.getHeight(null);
      double scale = Math.min(widthRatio, heightRatio);

      int scaledWidth = (int) (image.getWidth(null) * scale);
      int scaledHeight = (int) (image.getHeight(null) * scale);
      Image scaledImage = image.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
      backgroundLabel.setIcon(new ImageIcon(scaledImage));
      backgroundLabel.setHorizontalAlignment(JLabel.CENTER);
      backgroundLabel.setVerticalAlignment(JLabel.CENTER);
    }
  }
}