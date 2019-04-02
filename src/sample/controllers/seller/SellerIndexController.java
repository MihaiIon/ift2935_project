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
    private SummaryController summaryController;

    public ListView getSummaryList() {
        return this.summaryController.getListView();
    }


    @FXML
    private void initialize() {
        addProductController.injectIndexController(this);
        productsController.injectIndexController(this);
        //summaryController.fill(mainController.getDataManager().getProductsWithSellerId(1));

    }

    public void injectMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public ArrayList<SellerModel> getProds() {
        label.setText(mainController.getDataManager().getSellers().get(1).getName());
        return mainController.getDataManager().getSellers();
    }

}
