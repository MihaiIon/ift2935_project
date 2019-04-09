package sample.controllers;

import javafx.fxml.FXML;
import sample.controllers.seller.SellerIndexController;
import sample.managers.DataManager;

public class SellerController {

    private static DataManager dataManager;

    @FXML private SellerIndexController sellerIndexController;

    @FXML private void initialize(){

        sellerIndexController.injectMainController(this);

    }

    public static void setDataManager(DataManager dataM) {
        dataManager = dataM;
    }
    public DataManager getDataManager(){
        return dataManager;
    }

}
