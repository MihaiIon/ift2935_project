package sample.controllers.seller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SelectSellerController {

    @FXML
    private TextField sellerIdInput;
    @FXML
    private Button enterSellerId;

    private SellerIndexController sellerIndexController;

    public void injectIndexController(SellerIndexController sellerIndexController){
        this.sellerIndexController = sellerIndexController;
    }


    @FXML
    void sellerSelect(ActionEvent e){
        sellerIndexController.setSeller(Integer.valueOf(String.valueOf(sellerIdInput.getCharacters())));
        sellerIndexController.activateAddProduct();
    }
}
