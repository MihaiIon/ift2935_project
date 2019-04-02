package sample.controllers.seller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AddProductController {

    // Components
    // =======================================================

    private SellerIndexController sellerIndexController;

    @FXML
    TextField title;
    @FXML
    TextField price;
    @FXML
    TextArea description;

    public AddProductController(){

    }

    public void injectIndexController(SellerIndexController sellerIndexController){
        this.sellerIndexController = sellerIndexController;
    }

    // Methods
    // =======================================================

    public void onSubmit() {
        sellerIndexController.getSummaryList().getItems().add("TOTO");

    }
}