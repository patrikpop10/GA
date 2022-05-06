package patrik.ga.controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import patrik.ga.HelloApplication;
import patrik.ga.Parameters;
import patrik.ga.algorithms.geneticalg.GA;
import patrik.ga.util.GAService;
import patrik.ga.util.image.ImageBuilder;

import java.awt.*;
import java.io.*;


public class MainController {

    static Button button;
    static Button openFileChooser;
    static Text text;
    public static HBox bottomPane;
    public static BorderPane borderPane;
    public static MenuBar menuBar;
    static FileChooser fileChooser;
    static HBox pane;
    static MenuItem testParameters;
    static Menu menu;
    static MenuItem properties;
    static MenuItem statisticalSimulation;
    public static ImageView imageView;
    public static WritableImage wi;
    public static Canvas canvas;
    public static GraphicsContext context;
    static ObservableList<String> options = FXCollections.observableArrayList(
            "Simulated Annealing", "Genetic Algorithm"
    );



    public static void control(){
        try {
            initializeLayout();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        setMenuBar();
        headerStyle();

        pane.setMargin(text, new Insets(50, 0, 0, 0));
        setMainPane();
    }




    private static void initializeLayout() throws FileNotFoundException {


        pane = new HBox();
        pane.setAlignment(Pos.TOP_CENTER);

        button = new Button("Run");
        openFileChooser = new Button("Select Image");
        fileChooser = new FileChooser();
        pane.getChildren().addAll(button, openFileChooser);
        pane.setPadding(new Insets(100,100,100,100));
        bottomPane = new HBox();
        canvas = new Canvas(300,300);
        context = canvas.getGraphicsContext2D();
        bottomPane.getChildren().addAll(canvas);
        bottomPane.setPadding(new Insets(100,50,50,50));
        bottomPane.setAlignment(Pos.CENTER);

    }


    private static void headerStyle(){
        text = new Text("Simulated Annealing or Genetic Algorithm");

        //Style the text
        text.setStyle("-fx-font: 44 arial; -fx-text-alignment: center;");


    }

    private static void setMainPane(){
        borderPane = new BorderPane();
        borderPane.setTop(pane);
        borderPane.setCenter(bottomPane);
    }

    private static void setMenuBar(){
        menuBar = new MenuBar();
        menu = new Menu("");
        Label label = new Label("Menu");
        label.setStyle("-fx-text-fill: #404040 ");
        menu.setGraphic(label);
        properties = new MenuItem("Properties");
        statisticalSimulation = new MenuItem("Simulate");
        testParameters = new MenuItem("Test Parameters");
        menu.getItems().addAll(properties, statisticalSimulation, testParameters);
        menuBar.getMenus().add(menu);



    }


    public static void chooseImage(){
        openFileChooser.setOnAction(actionEvent -> {
            File file = fileChooser.showOpenDialog(HelloApplication.primaryStage);
            if(file != null){
                file.setReadable(true);

                wi = new WritableImage(Parameters.imageSize, Parameters.imageSize);
                try {
                    wi = new WritableImage(new Image(new FileInputStream(file)).getPixelReader(),200,150);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                bottomPane.getChildren().remove(imageView);

               ImageBuilder reader = new ImageBuilder(file);

               patrik.ga.util.image.Image image = reader.BuildImageFromFile(Parameters.imageSize, Parameters.imageSize);
               Parameters.BaseImage = image;
               imageView = new ImageView(wi);
               imageView.setFitHeight(Parameters.imageSize + 10);
               imageView.setFitWidth(Parameters.imageSize + 10);
               bottomPane.getChildren().add(0,imageView);
            }
        });
    }


    //runs the selected algorithm from the combo box
    public static void runAlgorithm(){

        button.setOnAction(actionEvent -> {
            new GAService(new GA()).start();
        });
    }


}
