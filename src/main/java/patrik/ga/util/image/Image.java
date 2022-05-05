package patrik.ga.util.image;

import patrik.ga.Parameters;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Image {
    private double width;
    private double height;
    private ArrayList<Color> colors;
    private BufferedImage Img;
    private ArrayList<Shape> shapes;

    public Image(double width, double height, ArrayList<Color> colors) {
        this.width = width;
        this.height = height;
        this.colors = colors;
    }
    public Image(double width, double height) {
        this.width = width;
        this.height = height;
        generateShapes();
        this.Img = draw();
        this.colors = ImageUtils.getColorsList(Img, Parameters.imageSize, Parameters.imageSize);

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
    public BufferedImage draw(){

        BufferedImage bufferedImage = new BufferedImage(Parameters.imageSize, Parameters.imageSize, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) bufferedImage.getGraphics();
        g.setColor(Color.WHITE);
        g.setBackground(Color.WHITE);
        g.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
        for (var shape : shapes) {
            //drawing the circle
            g.setColor(shape.getColor());
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.fillRect(shape.getX(), shape.getY(), shape.getWidth(), shape.getWidth());
        }
        return bufferedImage;

    }

    public BufferedImage getImg() {
        return Img;
    }

    public void setImg(BufferedImage img) {
        Img = img;
    }

    public ArrayList<Shape> getShapes() {
        return shapes;
    }

    public void setShapes(ArrayList<Shape> shapes) {
        this.shapes = shapes;
    }

    public void generateShapes(){
        shapes = new ArrayList<Shape>();
        for (int i = 0; i < Parameters.numberOfCircles; i++) {
            Random rand = new Random();
            int r = rand.nextInt(255);
            int g = rand.nextInt(255);
            int b = rand.nextInt(255);
            int a = rand.nextInt(50,100);
            var color = new Color(r,g,b,a);

            shapes.add(new Shape(new Random().nextInt(0, Parameters.imageSize),
                    new Random().nextInt(0,Parameters.imageSize),
                    new Random().nextInt(0, Parameters.maxCircleSize),
                    new Random().nextInt(0,Parameters.maxCircleSize),
                    new Random().nextInt(0,Parameters.maxCircleSize),
                    new Random( ).nextInt(0, Parameters.imageSize/10),
                    color));
        }
    }
}
