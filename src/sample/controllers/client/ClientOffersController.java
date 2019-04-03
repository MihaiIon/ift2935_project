package sample.controllers.client;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import sample.models.OfferModel;
import sample.models.ProductModel;

import java.util.ArrayList;

public class ClientOffersController {

    @FXML
    private ListView clientOffers;

    private ClientIndexController clientIndexController;

    public void injectIndexController(ClientIndexController clientIndexController){
        this.clientIndexController = clientIndexController;
    }

    @FXML
    void initialize(){
        clientOffers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                clientIndexController.getSummary().getItems().clear();
                if(newValue != null){
                    if(newValue instanceof ProductModel){
                        clientIndexController.getClientSummaryController().addProduct((ProductModel)newValue);
                    }else{
                        clientIndexController.getClientSummaryController().addOffer((OfferModel)newValue);
                    }
                }
            }
        });

        clientOffers.setCellFactory(param -> new ListCell<OfferModel>(){
            @Override
            protected void updateItem(OfferModel offer, boolean empty){
                super.updateItem(offer,empty);
                if(empty || offer == null || offer.getProduct().getName() == null){
                    setText(null);
                }else{
                    setText(offer.getProduct().getName());
                }
            }
        });
    }

    public void fill(ArrayList<OfferModel> offers){
        clientOffers.getItems().clear();
        for(OfferModel o : offers){
            clientOffers.getItems().add(o);
        }
    }
}
