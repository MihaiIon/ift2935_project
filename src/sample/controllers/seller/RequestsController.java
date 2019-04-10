package sample.controllers.seller;



import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.util.Callback;

import sample.controllers.SellerController;

import java.util.ArrayList;

public class RequestsController {
    private SellerController sellerController;

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
    Text req;

    @FXML
    TableView requestResult;

    private int sellerId;

    public void injectIndexController(SellerController sellerController) {
        this.sellerController = sellerController;
    }


    public void fillTable(ArrayList<String> results) {
        requestResult.getColumns().clear();
        requestResult.getItems().clear();
        String[] header = results.get(0).split("\t");
        for (int i = 0; i < header.length; i++) {
            requestResult.getColumns().add(createColumn(i, header[i]));
        }
        for (int i = 1; i < results.size(); i++) {
            String[] values = results.get(i).split("\t");
            ObservableList<StringProperty> data = FXCollections.observableArrayList();
            for (String value : values) {
                data.add(new SimpleStringProperty(value));
            }
            requestResult.getItems().add(data);
        }
    }

    public TableColumn<ObservableList<StringProperty>, String> createColumn(int columnIndex, String columnTitle) {
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
                        } else {
                            return cellDataFeatures.getValue().get(columnIndex);
                        }
                    }
                });
        return column;

    }

    @FXML
    void request1() {
        ArrayList<String> res = sellerController.getDataManager().request(String.format("with r1 as (select seller_id, product.name as product_name, product.id as product_id from davidbam.product where seller_id = %d), \n" +
                "     r2 as (select id as offer_id, amount, product_id, product_name, client_id as id from davidbam.offer natural join r1), \n" +
                "     r3 as (select offer_id, name,product_id, product_name, amount, id as client_id from davidbam.client natural join r2) \n" +
                "select product_id, product_name, client_id, name, amount, transaction_date from davidbam.transaction natural join r3;",sellerId));

        fillTable(res);

    }

    @FXML
    void request2() {
        ArrayList<String> res = sellerController.getDataManager().request(String.format("with r1 as (select product.id as product_id, expert_price, expert_id from davidbam.product where seller_id = %d), \n" +
                "     r2 as (select product_id, offer_id, is_automatic, expert_price, expert_id as id from davidbam.transaction natural join r1), \n" +
                "     r3 as (select product_id, offer_id as id, is_automatic, expert_price, id as expert_id, name as expert_name from davidbam.expert natural join r2),\n" +
                "     r4 as (select product_id as product, id as offer_id, is_automatic, expert_id, expert_name, (amount - expert_price)/expert_price * 100 as profit from davidbam.offer natural join r3), \n" +
                "     r5 as (select expert_id, expert_name, is_automatic, count(product) as nb_product, sum(profit) as profit from r4 group by expert_id, expert_name, is_automatic) \n" +
                "select expert_id, expert_name, is_automatic, nb_product, profit/nb_product as profit from r5 group by expert_id, expert_name, is_automatic, nb_product, profit order by expert_id;",sellerId));

        fillTable(res);
    }

    @FXML
    void request3() {
        ArrayList<String> res = sellerController.getDataManager().request(String.format("with r1 as (select product.id as product_id, product.name as product_name, expert_id as id, expert_price, seller_price from davidbam.product where seller_id = %d and state = 'AVAILABLE'), \n" +
                "     r2 as (select product_id, product_name, expert_price, seller_price, id as expert_id, name as expert_name from davidbam.expert natural join r1),\n" +
                "     r3 as (select product_id, product_name, expert_price, seller_price, expert_id, expert_name, client_id as id, offer_date, amount from davidbam.offer natural join r2) \n" +
                "select product_id, product_name, expert_price, expert_id, expert_name, seller_price, amount, offer_date, id as client_id, name as client_name from davidbam.client natural join r3;",sellerId));

        fillTable(res);
    }

    @FXML
    void request4() {
        ArrayList<String> res = sellerController.getDataManager().request(String.format("with r1 as (select expert_id, id as product_id from davidbam.product where seller_id = %d and state = 'AVAILABLE'),\n" +
                "     r2 as (select expert_id as id, product_id, id as offer_id from davidbam.offer natural join r1),\n" +
                "     r3 as (select id as expert_id, name as expert_name, product_id as id, offer_id, reputation from davidbam.expert natural join r2)\n" +
                "select expert_id, expert_name, reputation, count(id) as nb_product, count(offer_id) as nb_offer from r3 group by expert_id, expert_name, reputation;",sellerId));

        fillTable(res);
    }

    @FXML
    void request5() {
        ArrayList<String> res = sellerController.getDataManager().request(String.format("with r1 as (select offer_id as id from davidbam.transaction where seller_id = %d) \n" +
                "select max(amount) as max_transaction from davidbam.offer natural join r1;",sellerId));

        fillTable(res);
    }

    public void setSellerId(int id){
        sellerId = id;
    }
}
