package com.example.program_agentvanzari;

import com.example.program_agentvanzari.Domain.Product;
import com.example.program_agentvanzari.Repository.OrderDatabaseRepo;
import com.example.program_agentvanzari.Repository.ProductDatabaseRepo;
import com.example.program_agentvanzari.Repository.UserDatabaseRepo;
import com.example.program_agentvanzari.Service.Service;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdministrationController implements Initializable {

    @FXML
    private TableView<Product> tabel;
    //add
    @FXML
    private TextField namefield;
    @FXML
    private TextField pricefield;
    @FXML
    private TextField quantityfield;
    //update
    @FXML
    private TextField upnamefield;
    @FXML
    private TextField uppricefield;
    @FXML
    private TextField upquantityfield;
    @FXML
    private TextField upidfield;
    //delete
    @FXML
    private TextField delid;
    //filter
    @FXML
    private TextField srcnamefield;
    @FXML
    private TextField srcpricefield;
    @FXML
    private TextField srcquantityfield;
    @FXML
    private TextField srcidfield;

    private ProductDatabaseRepo productRepo;
    private OrderDatabaseRepo orderRepo;
    private UserDatabaseRepo userRepo;
    private Service service;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productRepo = new ProductDatabaseRepo();
        orderRepo = new OrderDatabaseRepo();
        userRepo = new UserDatabaseRepo();
        service = new Service(productRepo, orderRepo, userRepo);
        showAllRecords();
    }


    @FXML
    protected void showAllRecords() {
        List<Product> products = service.getAllProducts();

        TableColumn<Product, String> columnId = new TableColumn<>("ID");
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Product, String> columnNume = new TableColumn<>("Name");
        columnNume.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Product, String> columnPret = new TableColumn<>("Price");
        columnPret.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Product, String> columnCantitate = new TableColumn<>("Quantity");
        columnCantitate.setCellValueFactory(cellData -> {
            int quantity = cellData.getValue().getNr();
            return new SimpleStringProperty(quantity == 0 ? "n/a" : String.valueOf(quantity));
        });

        tabel.getColumns().clear();
        tabel.getColumns().addAll(columnId, columnNume, columnPret, columnCantitate);

        ObservableList<Product> data = FXCollections.observableArrayList(products);
        tabel.setItems(data);
    }

    public void onUpdateButtonClick(ActionEvent actionEvent) {
        try {
            var nameText = upnamefield.getText();
            var priceText = uppricefield.getText();
            var quantityText = upquantityfield.getText();
            var idText = upidfield.getText();

            if (nameText.isEmpty() || priceText.isEmpty() || quantityText.isEmpty() || idText.isEmpty()) {
                throw new NumberFormatException("All fields must be filled");
            }

            double price = Double.parseDouble(priceText);
            int quantity = Integer.parseInt(quantityText);
            int id = Integer.parseInt(idText);

            if (price < 0 || quantity < 0) {
                throw new NumberFormatException("Price and Quantity must be greater than or equal to 0");
            }
            service.update(id, nameText, price, quantity);
            showAllRecords();
            upnamefield.clear();
            uppricefield.clear();
            upquantityfield.clear();
            upidfield.clear();

        }catch (NumberFormatException e){
            Alert hello = new Alert(Alert.AlertType.ERROR, e.getMessage());
            hello.show();
        }
    }

    public void onAddButtonClick(ActionEvent actionEvent) {
        try {
            var nameText = namefield.getText();
            var priceText = pricefield.getText();
            var quantityText = quantityfield.getText();

            if (nameText.isEmpty() || priceText.isEmpty() || quantityText.isEmpty()) {
                throw new NumberFormatException("All fields must be filled");
            }

            double price = Double.parseDouble(priceText);
            int quantity = Integer.parseInt(quantityText);

            if (price < 0 || quantity < 0) {
                throw new NumberFormatException("Price and Quantity must be greater than or equal to 0");
            }
            service.add(nameText, price, quantity);
            showAllRecords();
            namefield.clear();
            pricefield.clear();
            quantityfield.clear();

        } catch (NumberFormatException e) {
            Alert hello = new Alert(Alert.AlertType.ERROR, e.getMessage());
            hello.show();
        }
    }

    public void onDeleteButtonClick(ActionEvent actionEvent) {
        try {
            var iddel = delid.getText();

            if (iddel.isEmpty()) {
                throw new NumberFormatException("All fields must be filled");
            }

            int id = Integer.parseInt(iddel);

            if (id < 0) {
                throw new NumberFormatException("Price and Quantity must be greater than or equal to 0");
            }
            service.delete(id);
            showAllRecords();
            delid.clear();

        } catch (NumberFormatException e) {
            Alert hello = new Alert(Alert.AlertType.ERROR, e.getMessage());
            hello.show();
        }
    }

    public void onFilterButtonClick(ActionEvent actionEvent) {
        try {
            String idsearchBar = srcidfield.getText();
            String namesearchBar = srcnamefield.getText();
            String pricesearchBar = srcpricefield.getText();
            String nrsearchBar = srcquantityfield.getText();

            //List<Product> products =  service.filter(Integer.parseInt(idsearchBar), namesearchBar, Double.parseDouble(pricesearchBar), Integer.parseInt(nrsearchBar));

            List<Product> products = service.filter(
                    !idsearchBar.isEmpty() ? Integer.parseInt(idsearchBar) : null,
                    namesearchBar.isEmpty() ? null : namesearchBar,
                    !pricesearchBar.isEmpty() ? Double.parseDouble(pricesearchBar) : null,
                    !nrsearchBar.isEmpty() ? Integer.parseInt(nrsearchBar) : null
            );

            if (products.isEmpty()) {
                showAllRecords();
                throw new RuntimeException("Nu a fost gasit nimic");
            }

            TableColumn<Product, String> columnId = new TableColumn<>("ID");
            columnId.setCellValueFactory(new PropertyValueFactory<>("id"));

            TableColumn<Product, String> columnNume = new TableColumn<>("Name");
            columnNume.setCellValueFactory(new PropertyValueFactory<>("name"));

            TableColumn<Product, String> columnPret = new TableColumn<>("Price");
            columnPret.setCellValueFactory(new PropertyValueFactory<>("price"));

            TableColumn<Product, String> columnCantitate = new TableColumn<>("Quantity");
            columnCantitate.setCellValueFactory(cellData -> {
                int quantity = cellData.getValue().getNr();
                return new SimpleStringProperty(quantity == 0 ? "n/a" : String.valueOf(quantity));
            });

            tabel.getColumns().clear();
            tabel.getColumns().addAll(columnId, columnNume, columnPret, columnCantitate);

            ObservableList<Product> data = FXCollections.observableArrayList(products);
            tabel.setItems(data);

        } catch (NumberFormatException e) {
            Alert hello = new Alert(Alert.AlertType.ERROR, e.getMessage());
            hello.show();
        } catch (RuntimeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
        }


    }
}
