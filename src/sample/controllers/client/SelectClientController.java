package sample.controllers.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

public class SelectClientController {

    @FXML
    private TextField clientIdInput;

    @FXML
    private Button enterClientId;

    private ClientIndexController clientIndexController;

    public void injectIndexController(ClientIndexController clientIndexController){
        this.clientIndexController = clientIndexController;
    }

    @FXML
    void clientSelect(ActionEvent e){
        clientIndexController.setCurrentClient(Integer.valueOf(clientIdInput.getText()));
    }
}
