package sample.controllers.seller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import sample.models.ProductModel;
import sample.models.SellerModel;

import java.util.ArrayList;


public class ProductsController {

    @FXML
    public Button button;
    @FXML
    private ListView myProducts;
    @FXML
    private ListView prices;

    private SellerIndexController sellerIndexController;

    @FXML


    public void injectIndexController (SellerIndexController sellerIndexController){
        this.sellerIndexController = sellerIndexController;
    }

    public void fill(ArrayList<ProductModel> prods){

        myProducts.getItems().clear();
        for(ProductModel p : prods){
            myProducts.getItems().add(p);
        }

    }

    @FXML void initialize(){
        myProducts.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ProductModel>() {
            @Override
            public void changed(ObservableValue observable, ProductModel oldValue, ProductModel newValue) {
                sellerIndexController.getSummaryList().getItems().clear();
                if(newValue != null) {
                    sellerIndexController.getSellerSummaryController().addProduct(newValue);
                }

            }
        });
        myProducts.setCellFactory(param -> new ListCell<ProductModel>(){
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
    }

}