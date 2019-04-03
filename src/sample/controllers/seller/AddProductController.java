package sample.controllers.seller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.models.ProductModel;

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
    @FXML
    Button submit;

    @FXML
    private void initialize(){
        submit.setDisable(true);
    }


    public void injectIndexController(SellerIndexController sellerIndexController){
        this.sellerIndexController = sellerIndexController;
    }

    // Methods
    // =======================================================

    @FXML
    public void onSubmit() {
        if(String.valueOf(title.getCharacters()).compareTo("") !=0 && String.valueOf(price.getCharacters()).compareTo("") !=0){
            float p = Float.valueOf(String.valueOf(price.getCharacters()));
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
                        p);
                System.out.println(title.getText());
                sellerIndexController.addProductToSeller(newProduct);
            }
        }
    }
}