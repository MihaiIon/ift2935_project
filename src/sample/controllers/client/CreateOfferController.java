package sample.controllers.client;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.models.OfferModel;
import sample.models.ProductModel;
import java.util.ArrayList;

/**
 * Controller for the createOffer.fxml view
 */
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
            /**
             * Formation of a ProductModel in the ListView
             * @param product
             * @param empty
             */
            @Override
            protected void updateItem(ProductModel product, boolean empty) {
                super.updateItem(product, empty);
                if (empty || product == null || product.getName() == null) {
                    setText(null);
                } else setText(product.getName());
            }
        });

        availableProducts
            .getSelectionModel()
            .selectedItemProperty()
            .addListener(
                new ChangeListener<ProductModel>() {
                    /**
                     * Handle ProductModel in the ListView
                     * @param observable
                     * @param oldValue
                     * @param newValue
                     */
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

    /**
     * @param products
     */
    public void fill(ArrayList<ProductModel> products) {
        for (ProductModel p : products) {
            availableProducts.getItems().add(p);
        }
    }

    /**
     * Sends new OfferModel to the Data Manager
     */
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