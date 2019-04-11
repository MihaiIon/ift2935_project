package sample.controllers.client;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import sample.models.OfferModel;
import sample.models.ProductModel;
import sample.models.TransactionModel;

import java.util.ArrayList;

/**
 *  Controller for the Client's offers view clientOffers.fxml
 */
public class ClientOffersController {

    @FXML
    private ListView clientOffers;

    private ClientIndexController clientIndexController;

    /**
     * @param clientIndexController
     */
    public void injectIndexController(ClientIndexController clientIndexController){
        this.clientIndexController = clientIndexController;
    }

    /**
     *
     */
    @FXML
    void initialize(){
        clientOffers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            /**
             * Shows either Product Or Offer in the summary ListView
             * @param observable
             * @param oldValue
             * @param newValue
             */
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
            /**
             * Handles OfferModel arguments in the ListView
             * @param offer
             * @param empty
             */
            @Override
            protected void updateItem(OfferModel offer, boolean empty){
                super.updateItem(offer,empty);
                if(empty || offer == null || offer.getProduct().getName() == null){
                    setText(null);
                }else{
                    TransactionModel transactionModel = clientIndexController.getDataManager().getTransactionWithProductId(offer.getProductId());
                    //If no transaction were done for the product, the client's offer still stands
                    if(transactionModel == null){
                        setText(offer.getProduct().getName());
                        setDisable(false);
                        setTextFill(Color.BLACK);
                    }else if(offer.getProduct().isUnavailable()){
                        //If the transaction concerns the client, then his offer was accepted
                        if(offer.getClientId() == transactionModel.getClient().getId()){
                            setText(String.format("%s\t\t\t\t%s",offer.getProduct().getName(), "ACCEPTED"));
                        }else{

                            setText(String.format("%s\t\t\t\t%s",offer.getProduct().getName(), "DECLINED"));
                        }
                            setDisable(true);
                            setTextFill(Color.LIGHTGREY);
                    }

                }
            }
        });
    }

    /**
     * @param offers to add to the clientOffers ListView
     */
    public void fill(ArrayList<OfferModel> offers){
        clientOffers.getItems().clear();
        for(OfferModel o : offers){
                clientOffers.getItems().add(o);
        }
    }
}
