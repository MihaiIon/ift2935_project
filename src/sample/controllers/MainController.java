package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.util.Pair;
import sample.managers.DataManager;
import javafx.stage.Stage;
import sample.models.ClientModel;
import sample.models.SellerModel;

import java.util.Optional;

/**
 *
 */
public class MainController {

    private ClientController clientController;
    private SellerController sellerController;

    private static Stage primaryStage;
    private static Scene mainScene;
    private static DataManager dataManager;
    private int height = 500;
    private int width = 900;

    // Data Manager
    // ==========================================================
    
    public static void setDataManager(DataManager dataM) {
        dataManager = dataM;
    }

    public static DataManager getDataManager(){
        return dataManager;
    }

    // Main Scene
    // ==========================================================

    public static void setMainScene() {
      primaryStage.setScene(mainScene);
      primaryStage.setHeight(350);
      primaryStage.setWidth(180);
      primaryStage.setMinHeight(350);
      primaryStage.setMinWidth(180);
      centerStage(180,350);
    }

    public static void centerStage(double width, double height){
      Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
      primaryStage.setX((screenBounds.getWidth() - width) / 2);
      primaryStage.setY((screenBounds.getHeight() - height) / 2);
    }

    public void injectPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
        this.mainScene = primaryStage.getScene();
    }


    public void changeSizeWindow(Stage primaryStage){
        primaryStage.setMinHeight(height);
        primaryStage.setMinWidth(width);
    }

    // Client Window
    // ==========================================================

    @FXML
    public void client_windows(){
        try {
            int id = selectUserPopUp("Client");
            if(id != 0) {
                FXMLLoader fxmlLoader_client = new FXMLLoader(getClass().getResource("/sample/views/main_client.fxml"));
                Parent root_client = fxmlLoader_client.load();
                clientController = fxmlLoader_client.<ClientController>getController();
                clientController.setDataManager(dataManager);
                clientController.setClientId(id);
                primaryStage.setScene(new Scene(root_client, width, height));
                centerStage(width,height);
                changeSizeWindow(primaryStage);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * This helps us to switch between clients and test differents situations.
     */
    public int selectUserPopUp(String type){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(String.format("%s Select",type));
        dialog.setHeaderText(String.format("%s Selection", type));
        dialog.setContentText(String.format("Please enter your %s id:", type.toLowerCase()));

        Optional<String> result = dialog.showAndWait();
        if(result.isPresent()) return Integer.valueOf(result.get());
        return 0;
    }

    /**
     * Creates a custom user: client or seller.
     */
    @FXML
    void createUser(){
        Dialog<Pair<String,String>> dialog = new Dialog<>();
        dialog.setTitle("New user");
        dialog.setHeaderText("Creation of a new User");

        ButtonType oktype = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
        ButtonType canceltype = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(oktype, canceltype);

        // Simple form
        TextField name = new TextField();
        name.setPromptText("Name");
        ObservableList<String> options =
        FXCollections.observableArrayList(
            "Seller",
            "Client"
            );
        ComboBox comboBox = new ComboBox(options);

        // Create and set grid
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        grid.add(name, 0,0);
        grid.add(comboBox,0,1);
        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {
            if(dialogButton == oktype){
                return new Pair<>(name.getText(),(String)comboBox.getValue());
            }
            return null;
        });

        // Get result and create user...
        Optional<Pair<String,String>> result = dialog.showAndWait();
        if(result.get().getKey().compareTo("") !=0){
            if(result.get().getValue().compareTo("") !=0){
                if(result.get().getValue().compareTo("Seller") == 0){
                    dataManager.addSeller(new SellerModel(result.get().getKey()));
                    Alert d = new Alert(Alert.AlertType.CONFIRMATION);
                    d.setTitle("Seller Created");
                    d.setHeaderText(String.format("Your id is: %d",dataManager.getSellers().size()));
                    d.showAndWait();
                }else {
                    dataManager.addClient(new ClientModel(result.get().getKey()));
                    Alert d = new Alert(Alert.AlertType.CONFIRMATION);
                    d.setTitle("Client Created");
                    d.setHeaderText(String.format("Your id is: %d",dataManager.getClients().size()));
                    d.showAndWait();
                }
            }
        }
    }




    @FXML
    public void seller_windows(){
        try {
            int id = selectUserPopUp("Seller");
            if (id != 0) {
                FXMLLoader fxmlLoader_seller = new FXMLLoader(getClass().getResource("/sample/views/main_seller.fxml"));
                Parent root_seller = fxmlLoader_seller.load();
                sellerController = fxmlLoader_seller.<SellerController>getController();
                sellerController.setDataManager(dataManager);
                sellerController.setSellerId(id);

                primaryStage.setScene(new Scene(root_seller, width, height));
                centerStage(width,height);
            }
        } catch(Exception e){
            e.printStackTrace();
            System.exit(0);
        }
        changeSizeWindow(primaryStage);
    }

    


}
