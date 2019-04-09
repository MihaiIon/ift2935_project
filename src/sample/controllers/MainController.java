package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import sample.controllers.client.ClientIndexController;
import sample.controllers.seller.SellerIndexController;
import sample.managers.DataManager;
import javafx.stage.Stage;
import sample.models.OfferModel;

public class MainController {

//    private Stage primaryStage;
    private static DataManager dataManager;
    private int height = 500;
    private int width = 900;

    @FXML private void initialize(){

    }

//    public void injectPrimaryStage(Stage primaryStage){
//        this.primaryStage = primaryStage;
//    }

    public static void setDataManager(DataManager dataM) {
        dataManager = dataM;
    }
    public DataManager getDataManager(){
        return dataManager;
    }

//    public void changeSizeWindow(Stage primaryStage){
//        primaryStage.setMinHeight(height);
//        primaryStage.setMinWidth(width);
//    }
//
//    @FXML
//    public void client_windows(){
//
//        try {
//            FXMLLoader fxmlLoader_client = new FXMLLoader();
//            Parent root_client = fxmlLoader_client.load(getClass().getResource("views/main_client.fxml"));
//            primaryStage.setScene(new Scene(root_client, width, height));
//            changeSizeWindow(primaryStage);
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.exit(0);
//        }
//    }
//
//    @FXML
//    public void seller_windows(){
//
//        try {
//            FXMLLoader fxmlLoader_seller = new FXMLLoader();
//            Parent root_seller = fxmlLoader_seller.load(getClass().getResource("views/main_seller.fxml"));
//            primaryStage.setScene(new Scene(root_seller, width, height));
//            changeSizeWindow(primaryStage);
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.exit(0);
//        }
//    }

}
