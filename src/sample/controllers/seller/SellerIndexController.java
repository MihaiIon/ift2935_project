package sample.controllers.seller;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.controllers.MainController;
import sample.controllers.SellerController;
import sample.controllers.expert.EstimationPromptController;
import sample.models.*;

import java.util.ArrayList;
import java.util.Optional;

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
        sellerSummaryController.injectIndexController(this);
    }

    public void addProductToSeller(ProductModel product){
        try {
            expertPrompt(product);
            boolean accepted = acceptExpertPrice();
            if(!accepted){
                product.submit();
            }else{
                product.publish(expertPrice);
            }


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

    public void setExpertPrice(Float expertPrice){
        this.expertPrice = expertPrice;
    }

    public Float getExpertPrice(){
        return this.expertPrice;
    }

    public void sellerAcceptOffer(ProductModel product, OfferModel offer){

        MainController.getDataManager().acceptingOffer(product,offer,false);
    }


    public void expertPrompt(ProductModel product) throws Exception{
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
    }

    private boolean acceptExpertPrice(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation of the expert price");
        alert.setHeaderText("Are you OK with the following expert price?");
        alert.setContentText(String.format("%.2f $", expertPrice));

        ButtonType buttonTypeAccept = new ButtonType("Accept", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeDecline = new ButtonType("Decline", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeAccept, buttonTypeDecline);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeAccept) {
            return true;
        } else {
            return false;
        }
    }

    @FXML
    void backToMain(){
        MainController.setMainScene();
    }

}
