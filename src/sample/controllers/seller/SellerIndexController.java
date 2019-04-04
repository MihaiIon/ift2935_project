package sample.controllers.seller;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.controllers.MainController;
import sample.controllers.expert.EstimationPromptController;
import sample.models.ProductModel;
import sample.models.SellerModel;

import java.util.ArrayList;

public class SellerIndexController {

    private MainController mainController;
    private Float expertPrice;

    @FXML
    private Label label;
    @FXML
    private AddProductController addProductController;
    @FXML
    private ProductsController productsController;
    @FXML
    private SellerSummaryController sellerSummaryController;
    @FXML
    private SelectSellerController selectSellerController;

    private SellerModel currentSeller;


    public ListView getSummaryList() {
        return this.sellerSummaryController.getListView();
    }

    public SellerSummaryController getSellerSummaryController(){
        return this.sellerSummaryController;
    }


    @FXML
    private void initialize() {
        addProductController.injectIndexController(this);
        productsController.injectIndexController(this);
        selectSellerController.injectIndexController(this);
        sellerSummaryController.injectIndexController(this);
    }

    public void addProductToSeller(ProductModel product){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/views/expert/estimationPrompt.fxml"));
            Parent parent = fxmlLoader.load();
            EstimationPromptController epc = fxmlLoader.<EstimationPromptController>getController();
            epc.injectIndexController(this, product);
            Scene scene = new Scene(parent, 500, 300);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setTitle("Product price estimation");
            stage.showAndWait();
        }catch (Exception e){
            System.out.println("TOT");
        }/*
        mainController.getDataManager().addProduct(product);
        currentSeller.addProduct(product);
        productsController.fill(currentSeller.getProducts());*/
    }



    public void activateAddProduct(){
        addProductController.submit.setDisable(false);
    }

    public void setSeller(int id){
        currentSeller = mainController.getDataManager().getSellers().get(id);
        label.setText(currentSeller.getName());
        productsController.fill(currentSeller.getProducts());
    }

    public void injectMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public ArrayList<SellerModel> getProds() {
        label.setText(mainController.getDataManager().getSellers().get(1).getName());
        return mainController.getDataManager().getSellers();
    }

    public SellerModel getCurrentSeller(){
        return this.currentSeller;
    }

    public Float setExpertPrice(Float expertPrice){
        return this.expertPrice = expertPrice;
    }

}
