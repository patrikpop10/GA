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
            bi = ImageUtils.resize(bi, newW, newH);
            int w = bi.getWidth();
            int h = bi.getHeight();
            image = new Image(w,h);

            ArrayList<Color> colors = ImageUtils.getColorsList(bi, w, h);
            image.setColors(colors);
            return image;
        }catch(IOException e) {
            System.out.println(e);
        }
        return image;
    }



}
