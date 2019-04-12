package sample.controllers.seller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.models.ProductModel;

import java.io.IOException;

/**
 *  Controller for the addProduct.fxml view
 */
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
    @FXML
    Button submit;

    @FXML
    private void initialize(){
        submit.setDisable(true);
        price.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(title.getText().compareTo("")!=0) {
                    submit.setDisable(false);
                }
            }
        });
        title.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(price.getText().compareTo("")!=0){
                    submit.setDisable(false);
                }
            }
        });

    }

    public void injectIndexController(SellerIndexController sellerIndexController){
        this.sellerIndexController = sellerIndexController;
    }

    // Methods
    // =======================================================

    /**
     * Sends a new product to the sellerIndexController waiting to be estimated
     */
    @FXML
    public void onSubmit() {
        if(String.valueOf(title.getCharacters()).compareTo("") !=0 && String.valueOf(price.getCharacters()).compareTo("") !=0){
            float p = Float.valueOf(price.getText());
            if(p <= 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Carefull !");
                alert.setContentText("Product price should be a positive non-zero number");
                alert.showAndWait();
                price.setText("");
            }else{

                ProductModel newProduct = new ProductModel(
                        sellerIndexController.getCurrentSeller().getId(),
                        title.getText(),
                        (description.getText().compareTo("")!=0)?description.getText():"non décrit",
                        p,
                        0);

                sellerIndexController.addProductToSeller(newProduct);
            }
        }
    }
}