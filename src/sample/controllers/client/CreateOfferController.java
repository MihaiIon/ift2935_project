package sample.controllers.client;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import sample.models.ProductModel;

import java.io.IOException;
import java.util.ArrayList;

public class CreateOfferController {

    @FXML
    private ListView availableProducts;

    private ClientIndexController clientIndexController;

    public void injectIndexController(ClientIndexController clientIndexController){
        this.clientIndexController = clientIndexController;
    }

    @FXML
    void initialize(){
        availableProducts.setCellFactory(param -> new ListCell<ProductModel>(){
            @Override
            protected void updateItem(ProductModel product, boolean empty){
                super.updateItem(product,empty);
                if(empty || product == null || product.getName() == null){
                    setText(null);
                }else{
                    setText(product.getName());
                }
            }
        });

        availableProducts.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ProductModel>() {
            @Override
            public void changed(ObservableValue observable, ProductModel oldValue, ProductModel newValue) {
                clientIndexController.getSummary().getItems().clear();
                if(newValue != null){
                    clientIndexController.getClientSummaryController().addProduct(newValue);
                }
            }
        });

    }

    public void fill(ArrayList<ProductModel> products){
        for(ProductModel p : products){
            availableProducts.getItems().add(p);
        }
    }



}