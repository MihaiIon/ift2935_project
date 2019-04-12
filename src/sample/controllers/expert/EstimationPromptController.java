package sample.controllers.expert;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.controllers.seller.SellerIndexController;
import sample.models.ExpertModel;
import sample.models.ProductModel;

/**
 * Controller for the estimationPrompt.fxml view
 */
public class EstimationPromptController {

    @FXML
    private Label itemName;
    @FXML
    private ListView itemInfo;
    @FXML
    private TextField estimation;
    @FXML
    private Button outsource;
    @FXML
    private Button sendEstimation;

    private ProductModel productModel;
    private ExpertModel expertModel;
    private SellerIndexController sellerIndexController;

    /**
     * @param sellerIndexController
     * @param product
     */
    public void injectIndexController(SellerIndexController sellerIndexController, ProductModel product){
        this.sellerIndexController = sellerIndexController;
        productModel = product;
        itemName.setText(product.getName());
        itemInfo.setItems(FXCollections.observableArrayList(
                String.format("Description: %s", product.getDescription()),
                String.format("Seller Price: %.2f", product.getSellerPrice())));

        expertModel = sellerIndexController.getRandomExpert();
    }

    /**
     * Simulates the price estimation of an expert
     * @param event
     */
    @FXML
    void outSourcePrice(ActionEvent event){
        Float sellerP = productModel.getSellerPrice();
        // sellerPrice +- 5%
        double estimate = (Math.random()*(1.05*sellerP - 0.95*sellerP)) + 0.95*sellerP;
        float rounded = (float)Math.round(estimate*100)/100;
        estimation.setText(String.valueOf(rounded));
    }

    /**
     * Sends Expert Price to the Seller Index Controller
     * @param event
     */
    @FXML
    void setExpertPrice(ActionEvent event){
        if(Float.valueOf((estimation.getText())) == null || Float.valueOf((estimation.getText())) == 0.0f){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Carefull !");
            alert.setContentText("Estimation should be a positive non-zero number");
            alert.showAndWait();
            estimation.setText("");
        }else{
        sellerIndexController.setExpertPrice(Float.valueOf(estimation.getText()));
        productModel.setExpertId(expertModel.getId());
        closeStage(event);
    }
    }

    /**
     * Close export stage and return to main stage.
     */
    private void closeStage(ActionEvent event) {
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
