package sample.controllers;

import javafx.fxml.FXML;
import sample.controllers.client.ClientIndexController;
import sample.controllers.seller.SellerIndexController;
import sample.managers.DataManager;

public class MainController {

    @FXML private SellerIndexController sellerIndexController;
    @FXML private ClientIndexController clientIndexController;

    private static DataManager dataManager;

    @FXML private void initialize(){

        sellerIndexController.injectMainController(this);
        clientIndexController.injectIndexController(this);
    }

    public static void setDataManager(DataManager dataM) {
        dataManager = dataM;
    }
    public DataManager getDataManager(){
        return dataManager;
    }
}
