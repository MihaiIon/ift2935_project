package sample.controllers.client;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import sample.models.OfferModel;
import sample.models.ProductModel;

public class ClientSummaryController {

    @FXML
    private ListView clientSummary;


    public ListView getClientSummary(){
        return this.clientSummary;
    }

    public void addProduct(ProductModel product){
        clientSummary.setItems(FXCollections.observableArrayList(
                String.format("Name: %s", product.getName()),
                String.format("Description: %s", product.getDescription()),
                String.format("Seller Price: %.2f", product.getSellerPrice()),
                String.format("Expert Price: %s", (product.getExpertPrice() != null && product.getExpertPrice() != 0.0f)?String.valueOf(product.getExpertPrice()) : "undefined"),
                String.format("Creation Date: %s", product.getCreationDate().toString()),
                String.format("Publish Date: %s", (product.getPublishDate()== null)?"":product.getPublishDate().toString())));

    }

    public void addOffer(OfferModel offer){
        ProductModel product = offer.getProduct();
        clientSummary.setItems(FXCollections.observableArrayList(
                String.format("Name: %s", product.getName()),
                String.format("ID: %s", offer.getSellerId()),
                String.format("Description: %s", product.getDescription()),
                String.format("Seller Price: %.2f", product.getSellerPrice()),
                String.format("Expert Price: %s", (product.getExpertPrice() != null && product.getExpertPrice() != 0.0f)?String.valueOf(product.getExpertPrice()) : "undefined"),
                String.format("Offered Price: %.2f", offer.getAmount()),
                String.format("Offer Date: %s", offer.getOfferDate().toString())
                ));

    }

}
