package patrik.ga.util.image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ImageUtils {
    public static ArrayList<Color> getColorsList(BufferedImage bi, int w, int h) {
        ArrayList<Color> colors = new ArrayList<>();
        for(int i = 0; i < h; i++) {
            for(int j = 0; j < w; j++) {
                Color myColor = new Color(bi.getRGB(j, i)); // bi.getRGB returns an integer like -14350844, representing the specific color. use Color class to get the individual colors with: myColor.getBlue()...
                colors.add(myColor);
            }
        }
        return colors;
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        java.awt.Image tmp = img.getScaledInstance(newW, newH, java.awt.Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }
}
