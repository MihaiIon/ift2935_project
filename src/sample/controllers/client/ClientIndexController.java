/**
ClientIndexController

Controller for the clientIndex.fxml view

 */
package sample.controllers.client;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import sample.controllers.MainController;
import sample.managers.DataManager;
import sample.models.ClientModel;

public class ClientIndexController {

    @FXML
    private CreateOfferController createOfferController;

    @FXML
    private ClientSummaryController clientSummaryController;

    @FXML
    private SelectClientController selectClientController;

    @FXML
    private ClientOffersController clientOffersController;

    @FXML
    private Label label;        //Title of the view (name of the current client

    private MainController mainController;

    private ClientModel currentClient;

    /**
     *  Tell's it's children who their parent is.
     */
    @FXML
    private void initialize(){
        selectClientController.injectIndexController(this);
        createOfferController.injectIndexController(this);
        clientOffersController.injectIndexController(this);

    }

    /**
     * @param mainController
     */
    @FXML
    public void injectIndexController(MainController mainController){
        this.mainController = mainController;
    }

    /**
     *
     * @param id the client's id
     */
    public void setCurrentClient(int id){
        this.currentClient = mainController.getDataManager().getClientFromId(id);
        label.setText(currentClient.getName());
        clientOffersController.fill(mainController.getDataManager().getOffersWithClientId(id));
        createOfferController.fill(mainController.getDataManager().getAvailableProducts());
    }

    /**
     *
     * @return the current selected Client
     */
    public ClientModel getCurrentClient(){
        return this.currentClient;
    }

    /**
     *
     * @return the Client Summary from the Client Summary Controller
     */
    public ListView getSummary(){
        return this.clientSummaryController.getClientSummary();
    }

    /**
     *
     * @return the Client Summary Controller
     */
    public ClientSummaryController getClientSummaryController(){
        return this.clientSummaryController;
    }

    /**
     *
     * @return the Data Manager
     */
    public DataManager getDataManager(){
        return mainController.getDataManager();
    }
}
