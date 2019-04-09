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

    public void setSellerId(int id){
        sellerIndexController.setSeller(id);
    }

    public void setDataManager(DataManager dataM) {
        dataManager = dataM;
    }
    public DataManager getDataManager(){
        return dataManager;
    }

}
