package sample.controllers;

import javafx.fxml.FXML;
import sample.controllers.seller.RequestsController;
import sample.controllers.seller.SellerIndexController;
import sample.managers.DataManager;

/**
 * This class acts as a super-controller that provides access to shared
 * data among the sellers's controllers.
 */
public class SellerController {

    private static DataManager dataManager;

    @FXML private SellerIndexController sellerIndexController;
    @FXML private RequestsController requestsController;

    @FXML private void initialize(){
        sellerIndexController.injectMainController(this);
        requestsController.injectIndexController(this);
    }

    public int getSellerId(){
        return sellerIndexController.getCurrentSeller().getId();
    }

    public void setSellerId(int id){
        sellerIndexController.setSeller(id);
        requestsController.setSellerId(id);
    }

    public void setDataManager(DataManager dataM) {
        dataManager = dataM;
    }

    public DataManager getDataManager(){
        return dataManager;
    }
}
