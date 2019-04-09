package sample.controllers.client;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import sample.models.OfferModel;
import sample.models.ProductModel;

/**
 *  Controller For the summary in the Client View clientSummary.fxml
 */
public class ClientSummaryController {

    @FXML
    private ListView clientSummary;


    /**
     * @return the client Summary ListView
     */
    public ListView getClientSummary(){
        return this.clientSummary;
    }

    /**
     * @param product
     */
    public void addProduct(ProductModel product){
        clientSummary.setItems(FXCollections.observableArrayList(
                String.format("Name: %s", product.getName()),
                String.format("Description: %s", product.getDescription()),
                String.format("Seller Price: %.2f", product.getSellerPrice()),
                String.format("Expert Price: %s", (product.getExpertPrice() != null && product.getExpertPrice() != 0.0f)?String.valueOf(product.getExpertPrice()) : "undefined"),
                String.format("Creation Date: %s", product.getCreationDate().toString()),
                String.format("Publish Date: %s", (product.getPublishDate()== null)?"":product.getPublishDate().toString())));

    }

    /**
     * @param offer
     */
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
