package sample.controllers;

import javafx.fxml.FXML;
import sample.controllers.client.ClientIndexController;
import sample.managers.DataManager;

public class ClientController {

    private static DataManager dataManager;

    @FXML private ClientIndexController clientIndexController;

    @FXML private void initialize(){

        clientIndexController.injectIndexController(this);

    }

    public static void setDataManager(DataManager dataM) {
        dataManager = dataM;
    }

    public DataManager getDataManager(){
        return dataManager;
    }

}
