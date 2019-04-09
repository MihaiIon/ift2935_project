package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Screen;
import sample.managers.DataManager;
import javafx.stage.Stage;

import java.util.Optional;


public class MainController {



    private ClientController clientController;
    private SellerController sellerController;

    private static Stage primaryStage;
    private static Scene mainScene;
    private static DataManager dataManager;
    private int height = 500;
    private int width = 900;

  public static void setMainScene(){
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

    public static void setDataManager(DataManager dataM) {
        dataManager = dataM;
    }
    public static DataManager getDataManager(){
        return dataManager;
    }

    public void changeSizeWindow(Stage primaryStage){
        primaryStage.setMinHeight(height);
        primaryStage.setMinWidth(width);
    }

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

    public int selectUserPopUp(String type){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(String.format("%s Select",type));
        dialog.setHeaderText(String.format("%s Selection", type));
        dialog.setContentText(String.format("Please enter your %s id:", type.toLowerCase()));


        Optional<String> result = dialog.showAndWait();
        if(result.isPresent()){
            return Integer.valueOf(result.get());
        }else{
            return 0;
        }
    }

}
