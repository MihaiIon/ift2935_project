package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.controllers.MainController;
import sample.controllers.ClientController;
import sample.controllers.SellerController;
import javafx.scene.control.Button;
import sample.managers.DataManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.sql.DriverManager;


public class Main extends Application {

    private static int width_select = 180;
    private static int height_select = 350;

    @FXML
    private MainController mainController;
    private Stage primaryStage;

    @FXML
    private Button acheteur_button;

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.primaryStage = primaryStage;

        // Initialize Manager
        DataManager dm = DataManager.getInstance();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/views/main.fxml"));

        // Create Window
        Parent root = fxmlLoader.load();
        mainController = fxmlLoader.<MainController>getController();

        MainController.setDataManager(dm);

        primaryStage.setTitle("IFT2935 - Projet");
        primaryStage.setScene(new Scene(root, width_select, height_select));
        primaryStage.setMinHeight(height_select);
        primaryStage.setMinWidth(width_select);
        primaryStage.show();
        mainController.injectPrimaryStage(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
