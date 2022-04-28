package patrik.ga;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import patrik.ga.controllers.MainController;

import java.io.IOException;

public class HelloApplication extends Application {
    static BorderPane borderPane;
    static Scene scene;
    public static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        this.primaryStage = stage;
        MainController.control();
        MainController.runAlgorithm();
        MainController.chooseImage();
        borderPane = MainController.borderPane;
        scene  = new Scene(borderPane, 1000, 1000);
        MainController.menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}