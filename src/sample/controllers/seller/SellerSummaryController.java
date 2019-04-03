package sample.controllers.seller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import sample.models.ProductModel;

public class SellerSummaryController {
    @FXML private Label label;

    @FXML private ListView listView;

    private SellerIndexController sellerIndexController;


    public ListView getListView(){
        return this.listView;
    }

    public void injectIndexController(SellerIndexController sellerIndexController){
        this.sellerIndexController = sellerIndexController;
    }



    public void addProduct(ProductModel product){
        listView.setItems(FXCollections.observableArrayList(
                String.format("Name: %s", product.getName()),
                String.format("Description: %s", product.getDescription()),
                String.format("Seller Price: %.2f", product.getSellerPrice()),
                String.format("Expert Price: %s", (product.getExpertPrice() != null && product.getExpertPrice() != 0.0f)?String.valueOf(product.getExpertPrice()) : "Undefined"),
                String.format("Creation Date: %s", product.getCreationDate().toString()),
                String.format("Publish Date: %s", (product.getPublishDate()== null)?"":product.getPublishDate().toString())));
    }



}
