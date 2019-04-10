package sample.controllers;

import javafx.fxml.FXML;
import sample.controllers.client.ClientIndexController;
import sample.controllers.client.RequestsController;
import sample.managers.DataManager;

public class ClientController {

    private static DataManager dataManager;
    private int clientId;

    @FXML private ClientIndexController clientIndexController;
    @FXML private RequestsController requestsController;

    @FXML private void initialize(){
        clientIndexController.injectIndexController(this);
        requestsController.injectIndexController(this);

    }

    public void setClientId(int id){
        this.clientId = id;
        clientIndexController.setCurrentClient(id);
    }
    public int getClientId(){
        return clientId;
    }

    public void setDataManager(DataManager dataM) {
        dataManager = dataM;
    }

    public DataManager getDataManager(){
        return dataManager;
    }

}
