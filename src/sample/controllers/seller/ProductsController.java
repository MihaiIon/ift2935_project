package sample.controllers.seller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import sample.models.OfferModel;
import sample.models.ProductModel;

import java.util.ArrayList;


public class ProductsController {

    @FXML
    public Button button;
    @FXML
    private ListView myProducts;
    @FXML
    private ListView prices;

    private SellerIndexController sellerIndexController;

    private ArrayList<OfferModel> offersOfSeller;

    private OfferModel selectedOffer;

    private ProductModel selectedProduct;

    @FXML


    public void injectIndexController(SellerIndexController sellerIndexController) {
        this.sellerIndexController = sellerIndexController;
    }

    public void fill(ArrayList<ProductModel> prods) {
        prices.getItems().clear();
        myProducts.getItems().clear();
        for (ProductModel p : prods) {

                myProducts.getItems().add(p);

        }
    }

    @FXML
    void initialize() {
        myProducts.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ProductModel>() {
            @Override
            public void changed(ObservableValue observable, ProductModel oldValue, ProductModel newValue) {
                sellerIndexController.getSummaryList().getItems().clear();
                prices.getItems().clear();
                if (newValue != null) {
                    selectedProduct = newValue;
                    sellerIndexController.getSellerSummaryController().addProduct(newValue);
                    prices.setDisable(false);
                    showOffers(newValue);

                }

            }
        });
        myProducts.setCellFactory(param -> new ListCell<ProductModel>() {
            @Override
            protected void updateItem(ProductModel product, boolean empty) {
                super.updateItem(product, empty);
                if (empty || product == null || product.getName() == null) {
                    setText(null);
                } else {

                    if(product.isAvailable()){
                        setText(product.getName());
                    }else if(product.isUnavailable()){
                        setText(String.format("%s\t\t\t\t%s", product.getName(), "SOLD!"));
                        setTextFill(Color.LIGHTGREY);
                        setDisable(true);
                    }else if(product.isSubmitted()){
                        setText(String.format("%s\t\t\t\t%s", product.getName(), "AWAITING ESTIMATION"));
                        setTextFill(Color.LIGHTGREY);

                    }
                }
            }
        });

        prices.setCellFactory(param -> new ListCell<OfferModel>() {
            @Override
            protected void updateItem(OfferModel offer, boolean empty) {
                super.updateItem(offer, empty);
                if (empty || offer == null) {
                    setText(null);
                } else {
                    setText(String.format("Offer : %.2f ,  Date ; %s", offer.getAmount(), offer.getOfferDate().toString()));
                }
            }
        });

        prices.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<OfferModel>() {
            @Override
            public void changed(ObservableValue observable, OfferModel oldValue, OfferModel newValue) {
                selectedOffer = newValue;
                button.setDisable(false);
            }
        });
    }

    public void setOffersOfSeller(ArrayList<OfferModel> offers) {
        offersOfSeller = offers;
    }

    private void showOffers(ProductModel product) {
        for (OfferModel om : offersOfSeller) {
            if (om.getProductId() == product.getId()) {
                prices.getItems().add(om);
            }
        }
    }

    @FXML
    void acceptOffer(){
        selectedProduct.sell();
        sellerIndexController.sellerAcceptOffer(selectedProduct,selectedOffer);
    }

}