package sample.controllers.client;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import sample.models.OfferModel;
import sample.models.ProductModel;

import java.io.IOException;
import java.util.ArrayList;

public class CreateOfferController {

    @FXML
    private ListView availableProducts;

    @FXML
    private TextField offer;

    @FXML
    private Button submitOffer;

    private ProductModel selectedProduct;

    private ClientIndexController clientIndexController;

    public void injectIndexController(ClientIndexController clientIndexController) {
        this.clientIndexController = clientIndexController;
    }

    @FXML
    void initialize() {
        availableProducts.setCellFactory(param -> new ListCell<ProductModel>() {
            @Override
            protected void updateItem(ProductModel product, boolean empty) {
                super.updateItem(product, empty);
                if (empty || product == null || product.getName() == null) {
                    setText(null);
                } else {
                    setText(product.getName());
                }
            }
        });

        availableProducts.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ProductModel>() {
            @Override
            public void changed(ObservableValue observable, ProductModel oldValue, ProductModel newValue) {
                clientIndexController.getSummary().getItems().clear();
                offer.setDisable(false);
                offer.setText("");
                submitOffer.setDisable(false);
                selectedProduct = newValue;
                if (newValue != null) {
                    clientIndexController.getClientSummaryController().addProduct(newValue);
                }
            }
        });

    }

    public void fill(ArrayList<ProductModel> products) {
        for (ProductModel p : products) {
            availableProducts.getItems().add(p);
        }
    }

    @FXML
    void submitOffer() {
        float offerValue = Float.valueOf(offer.getText());
        if (offerValue <= 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Carefull !");
            alert.setContentText("Offer price should be a positive non-zero number");
            alert.showAndWait();
            offer.setText("");
        } else {
            OfferModel offerModel = clientIndexController.getCurrentClient().createOffer(selectedProduct, offerValue);
            clientIndexController.getDataManager().addOffer(offerModel);
            if (offerValue >= selectedProduct.getExpertPrice()) {
                selectedProduct.sell();
                clientIndexController.getDataManager().acceptingOffer(selectedProduct, offerModel, true);
            }
        }
    }


}