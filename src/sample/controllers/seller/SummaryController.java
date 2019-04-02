package sample.controllers.seller;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import sample.models.ProductModel;

import java.util.ArrayList;


public class SummaryController {
    @FXML private Label label;

    @FXML private ListView listView;


    public ListView getListView(){
        return this.listView;
    }

    @FXML
    public void addText(){
        listView.getItems().add("toto");
    }

    public void fill(ArrayList<ProductModel> prods){
        for(ProductModel m : prods){
            listView.getItems().add(m.getName());
        }
    }


}
