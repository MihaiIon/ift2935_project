package sample.controllers.seller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import sample.models.ProductModel;
import sample.models.SellerModel;

import java.io.IOException;
import java.util.ArrayList;


public class ProductsController {

    @FXML
    public Button button;

    private SellerIndexController sellerIndexController;

    public void injectIndexController (SellerIndexController sellerIndexController){
        this.sellerIndexController = sellerIndexController;
    }


    @FXML
    public void testData(){
        ArrayList<SellerModel> prods = sellerIndexController.getProds();
        for(SellerModel p : prods){
            sellerIndexController.getSummaryList().getItems().add(p.getName());
        }
    }

}