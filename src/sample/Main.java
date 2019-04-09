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

        ClientController clientController = new ClientController();
        SellerController sellerController = new SellerController();

        // Initialize Manager
        DataManager dm = DataManager.getInstance();

        FXMLLoader fxmlLoader = new FXMLLoader();

        // Create Window
        Parent root = fxmlLoader.load(getClass().getResource("views/main.fxml"));

        MainController.setDataManager(dm);

        primaryStage.setTitle("IFT2935 - Projet");
        primaryStage.setScene(new Scene(root, width_select, height_select));
        primaryStage.setMinHeight(height_select);
        primaryStage.setMinWidth(width_select);
        primaryStage.show();

        FXMLLoader fxmlLoader_client = new FXMLLoader();
        Parent root_client = fxmlLoader_client.load(getClass().getResource("views/main_client.fxml"));
        Scene scene_client = new Scene(root_client, 900, 500);

        acheteur_button.setOnAction(e->primaryStage.setScene(scene_client));

        //mainController.injectPrimaryStage(primaryStage);
    }

        public void changeSizeWindow(Stage primaryStage){
        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(900);
        }

        public void client_windows(){

        try {
            FXMLLoader fxmlLoader_client = new FXMLLoader();
            Parent root_client = fxmlLoader_client.load(getClass().getResource("views/main_client.fxml"));
            primaryStage.setScene(new Scene(root_client, 900, 500));
            changeSizeWindow(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
