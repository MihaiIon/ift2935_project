package sample.controllers.client;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import sample.controllers.ClientController;
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
    private Label label;

    private ClientController clientController;

    private ClientModel currentClient;

    @FXML
    private void initialize(){
        selectClientController.injectIndexController(this);
        createOfferController.injectIndexController(this);
        clientOffersController.injectIndexController(this);

    }

    @FXML
    public void injectIndexController(ClientController clientController){
        this.clientController = clientController;
    }

    public void setCurrentClient(int id){
        this.currentClient = clientController.getDataManager().getClientFromId(id);
        label.setText(currentClient.getName());
        clientOffersController.fill(clientController.getDataManager().getOffersWithClientId(id));
        createOfferController.fill(clientController.getDataManager().getAvailableProducts());
    }

    public ClientModel getCurrentClient(){
        return this.currentClient;
    }

    public ListView getSummary(){
        return this.clientSummaryController.getClientSummary();
    }

    public ClientSummaryController getClientSummaryController(){
        return this.clientSummaryController;
    }

    public DataManager getDataManager(){
        return clientController.getDataManager();
    }
}
