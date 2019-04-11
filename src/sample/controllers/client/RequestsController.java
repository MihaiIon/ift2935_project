package sample.controllers.client;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import sample.controllers.ClientController;
import java.util.ArrayList;

public class RequestsController {

    @FXML
    Button r1;
    @FXML
    Button r2;
    @FXML
    Button r3;
    @FXML
    Button r4;
    @FXML
    Button r5;
    @FXML
    TableView requestResult;

    private int clientId;
    private ClientController clientController;

    public void injectIndexController(ClientController clientController){
        this.clientController = clientController;
        clientId = clientController.getClientId();
    }

    /**
     * Display results in table.
     */
    public void fillTable(ArrayList<String> results){
        requestResult.getColumns().clear();
        requestResult.getItems().clear();
        String[] header = results.get(0).split("\t");
        for(int i = 0; i<header.length;i++){
            requestResult.getColumns().add(createColumn(i, header[i]));
        }
        for(int i = 1;i<results.size();i++){
            String[] values = results.get(i).split("\t");
            ObservableList<StringProperty> data = FXCollections.observableArrayList();
            for(String value : values){
                data.add(new SimpleStringProperty(value));
            }
            requestResult.getItems().add(data);
        }
    }

    public TableColumn<ObservableList<StringProperty>,String> createColumn(int columnIndex, String columnTitle){
        TableColumn<ObservableList<StringProperty>, String> column = new TableColumn<>();
        String title;
        if (columnTitle == null || columnTitle.trim().length() == 0) {
            title = "Column " + (columnIndex + 1);
        } else {
            title = columnTitle;
        }
        column.setText(title);
        column
            .setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList<StringProperty>, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(
                        TableColumn.CellDataFeatures<ObservableList<StringProperty>, String> cellDataFeatures) {
                    ObservableList<StringProperty> values = cellDataFeatures.getValue();
                    if (columnIndex >= values.size()) {
                        return new SimpleStringProperty("");
                    }
                    return cellDataFeatures.getValue().get(columnIndex);
                }
            });
        return column;

    }

    // Simple Request functions.
    // ---
    // Each function is linked to one of the buttons above.
    // ======================================================================

    @FXML
    void request1(){
        fillTable(clientController
            .getDataManager()
            .request(
                String.format(
                    "with r1 as (select product_id as id from davidbam.offer where client_id = %d), \n"
                        + "     r2 as (select seller_id from davidbam.product natural join r1),\n" 
                        + "     r3 as (select seller_id, id as product_id, name as product_name, seller_price, expert_id as id from davidbam.product natural join r2 where state = 'AVAILABLE'),\n" 
                        + "     r4 as (select product_id, product_name, seller_price, seller_id as id, id as expert_id, name as expert_name, reputation from davidbam.expert natural join r3)\n" 
                        + "select id as seller_id, name as seller_name, product_id, product_name, seller_price, expert_id, expert_name, reputation from davidbam.seller natural join r4;",
                    clientId
                )
            )
        );
    }
    
    @FXML
    void request2(){
        fillTable(clientController
            .getDataManager()
            .request(
                String.format(
                    "with r1 as (select product_id as id, amount, id as offer_id, offer_date from davidbam.offer where client_id = %d), \n"
                        + "     r2 as (select id as product_id, name as product_name, seller_price, seller_id, amount, offer_id, offer_date, expert_id as id from davidbam.product natural join r1 where state = 'AVAILABLE'),\n"
                        + "     r3 as (select product_id, product_name, seller_price, seller_id as id, offer_id, amount, offer_date, id as expert_id, reputation from davidbam.expert natural join r2)\n"
                        + "select product_id, product_name, id as seller_id, name as seller_name, seller_price, offer_id, amount, offer_date, expert_id, reputation  from davidbam.seller natural join r3;"
                    , clientId
                )
            )
        );
    }

    @FXML
    void request3(){
        fillTable(clientController
            .getDataManager()
            .request(
                String.format(
                    "with r1 as (select product_id from davidbam.offer where client_id =%d), \n"
                        + "     r2 as (select product_id as id, client_id, id as offer_id, amount from davidbam.offer natural join r1 where client_id != %d)\n"
                        + "select id as product_id, name as product_name, client_id, offer_id, amount from davidbam.product natural join r2 where state = 'AVAILABLE';",
                    clientId,
                    clientId
                )
            )
        );
    }

    @FXML
    void request4(){
        fillTable(clientController
            .getDataManager()
            .request(
                String.format(
                    "select id as product_id, name as product_name, seller_price, description \n"
                        + "from davidbam.product \n"
                        + "where id not in (select product_id from davidbam.offer where client_id = %d) and seller_price < 70 and state = 'AVAILABLE';",
                    clientId
                )
            )
        );
    }

    @FXML
    void request5(){
        fillTable(clientController
            .getDataManager()
            .request(
                String.format(
                    "with r1 as (select client_id, offer_id as id from davidbam.transaction where client_id = %d),\n"
                        + "     r2 as (select client_id, count(id) as nb_accepted, sum(amount) as total_paid from davidbam.offer natural join r1 group by client_id),\n"
                        + "     r3 as (select client_id, count(id) as nb_offer from davidbam.offer where client_id = 2 group by client_id)\n"
                        + "select nb_offer, nb_accepted, total_paid from r3 natural join r2;",
                    clientId
                )
            )

        );
    }
}
