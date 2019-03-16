package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.managers.DataManager;

public class Main extends Application {

    private static int width = 900;
    private static int height = 680;

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Initialize Manager
        DataManager.getInstance();

        // Create Window
        Parent root = FXMLLoader.load(getClass().getResource("views/main.fxml"));
        primaryStage.setTitle("IFT2935 - Projet");
        primaryStage.setScene(new Scene(root, width, height));
        primaryStage.setMinHeight(height);
        primaryStage.setMinWidth(width);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
