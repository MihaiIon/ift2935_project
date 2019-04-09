package sample.controllers.seller;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.controllers.SellerController;
import sample.controllers.expert.EstimationPromptController;
import sample.models.*;

import java.util.ArrayList;

public class SellerIndexController {

    private SellerController sellerController;
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
        }
        sellerController.getDataManager().addProduct(product);
        currentSeller.addProduct(product);
        productsController.fill(currentSeller.getProducts());
    }



    public void activateAddProduct(){
        addProductController.submit.setDisable(false);
    }

    public void setSeller(int id){
        currentSeller = sellerController.getDataManager().getSellers().get(id);
        label.setText(currentSeller.getName());
        productsController.fill(currentSeller.getProducts());
        productsController.setOffersOfSeller(sellerController.getDataManager().getOffersWithSellerId(currentSeller.getId()));
    }

    public void injectMainController(SellerController sellerController) {
        this.sellerController = sellerController;
    }

    public ArrayList<OfferModel> getProductOffers(ProductModel product){
        return null;
    }

    public SellerModel getCurrentSeller(){
        return this.currentSeller;
    }

    public Float setExpertPrice(Float expertPrice){
        return this.expertPrice = expertPrice;
    }

    public void sellerAcceptOffer(ProductModel product, OfferModel offer){

        sellerController.getDataManager().acceptingOffer(product,offer,false);
     /*  mainController.getDataManager().updateProduct(product);
       ClientModel client = mainController.getDataManager().getClientFromId(offer.getClientId());
       mainController.getDataManager().addTransaction(new TransactionModel(product.getSellerId(), client, offer, false));
       //mainController.getDataManager().removeOffersWithProductId(product.getId());
*/

    }

}
