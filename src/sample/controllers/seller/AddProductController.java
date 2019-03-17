package sample.controllers.seller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AddProductController {

    // Components
    // =======================================================

    @FXML
    TextField title;
    @FXML
    TextField price;
    @FXML
    TextArea description;

    // Methods
    // =======================================================

    public void onSubmit() {
        System.out.println(title.getText());
    }
}