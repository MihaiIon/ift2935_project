package sample.controllers.expert;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.controllers.seller.SellerIndexController;
import sample.models.ProductModel;

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



    private SellerIndexController sellerIndexController;

    public void injectIndexController(SellerIndexController sellerIndexController, ProductModel product){
        this.sellerIndexController = sellerIndexController;
        productModel = product;
        itemName.setText(product.getName());
        itemInfo.setItems(FXCollections.observableArrayList(
                String.format("Description: %s", product.getDescription()),
                String.format("Seller Price: %.2f", product.getSellerPrice())));
    }

    @FXML
    void initialize(){

    }

    @FXML
    void outSourcePrice(ActionEvent event){
        Float sellerP = productModel.getSellerPrice();
        double estimate = (0.95*sellerP)*(Math.random()*(0.1 * sellerP));
        estimation.setText(String.valueOf(Math.round(estimate * 100.0) / 100.0));
    }

    @FXML
    void setExpertPrice(ActionEvent event){
        sellerIndexController.setExpertPrice(Float.valueOf(estimation.getText()));
        closeStage(event);
    }

    private void closeStage(ActionEvent event) {
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }


}
