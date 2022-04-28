package patrik.ga.util.image;

import java.awt.*;
import java.util.ArrayList;

public class Image {
    private double width;
    private double height;
    private ArrayList<Color> colors;

    public Image(double width, double height, ArrayList<Color> colors) {
        this.width = width;
        this.height = height;
        this.colors = colors;
    }
    public Image(double width, double height) {
        this.width = width;
        this.height = height;
        this.colors = new ArrayList<Color>();
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public ArrayList<Color> getColors() {
        return colors;
    }

    public void setColors(ArrayList<Color> colors) {
        this.colors = colors;
    }
}
