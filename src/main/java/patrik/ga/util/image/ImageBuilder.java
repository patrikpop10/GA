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
    private  File file;
    private BufferedImage bi;
    public ImageBuilder(File file){
        this.file = file;
    }
    public Image BuildImageFromFile(int newW, int newH){
        Image image = null;
        try(InputStream is = new FileInputStream(this.file)) {

            setBi(ImageIO.read(is));
            setBi(resize(getBi(), newW, newH));
            int w = getBi().getWidth();
            int h = getBi().getHeight();
            image = new Image(w,h);

            ArrayList<Color> colors = new ArrayList<>();
            for(int i = 0; i < h; i++) {
                for(int j = 0; j < w; j++) {
                    Color myColor = new Color(getBi().getRGB(j, i));
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

    public BufferedImage getBi() {
        return bi;
    }

    public void setBi(BufferedImage bi) {
        this.bi = bi;
    }
}
