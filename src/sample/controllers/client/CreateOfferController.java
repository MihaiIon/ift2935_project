package sample.controllers.client;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class CreateOfferController {

    public CreateOfferController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sample/views/client/createOffer.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

    }

}