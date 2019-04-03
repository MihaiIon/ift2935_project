package sample.controllers.seller;

import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import sample.controllers.MainController;
import sample.models.ProductModel;
import sample.models.SellerModel;

import java.util.ArrayList;

public class SellerIndexController {

    private MainController mainController;

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
        mainController.getDataManager().addProduct(product);
        currentSeller.addProduct(product);
        productsController.fill(currentSeller.getProducts());
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

}
