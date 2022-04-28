package patrik.ga.util.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ImageBuilder {
    public File file;
    public ImageBuilder(File file){
        this.file = file;
    }
    public Image BuildImageFromFile(int newW, int newH){
        Image image = null;
        try(InputStream is = new FileInputStream(this.file)) {
            BufferedImage bi = ImageIO.read(is); // Use ImageIO to create a BufferedImage
            bi = resize(bi, newW, newH);
            int w = bi.getWidth();
            int h = bi.getHeight();
            image = new Image(w,h);

            ArrayList<Color> colors = new ArrayList<>();
            for(int i = 0; i < h; i++) {
                for(int j = 0; j < w; j++) {
                    Color myColor = new Color(bi.getRGB(j, i)); // bi.getRGB returns an integer like -14350844, representing the specific color. use Color class to get the individual colors with: myColor.getBlue()...
                    colors.add(myColor);
                }
            }
            image.setColors(colors);
            return image;
        }catch(IOException e) {
            System.out.println(e);
        }
        return image;
    }
    private BufferedImage resize(BufferedImage img, int newW, int newH) {
        java.awt.Image tmp = img.getScaledInstance(newW, newH, java.awt.Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

}
