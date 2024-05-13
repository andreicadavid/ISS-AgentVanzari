package com.example.program_agentvanzari;

import com.example.program_agentvanzari.Domain.Order;
import com.example.program_agentvanzari.Domain.Product;
import com.example.program_agentvanzari.Repository.OrderDatabaseRepo;
import com.example.program_agentvanzari.Repository.ProductDatabaseRepo;
import com.example.program_agentvanzari.Repository.UserDatabaseRepo;
import com.example.program_agentvanzari.Service.Service;
import com.example.program_agentvanzari.Repository.ProductDatabaseRepo;
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

public class OrderController implements Initializable {
    @FXML
    private TableView<Order> tabelord;
    @FXML
    private TextField name;
    @FXML
    private TextField prenume;
    @FXML
    private TextField adresa;
    @FXML
    private TextField codpostal;
    @FXML
    private TextField email;
    @FXML
    private TextField telefon;
    @FXML
    private TextField cantitate;
    @FXML
    private TextField idprodus;

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
        showAllOrders();
    }


    @FXML
    protected void showAllOrders() {
        List<Order> orders = service.getAllOrders();

        TableColumn<Order, Integer> columnId = new TableColumn<>("ID");
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Order, String> columnName = new TableColumn<>("Customer Name");
        columnName.setCellValueFactory(new PropertyValueFactory<>("customerName"));

        TableColumn<Order, String> columnSurname = new TableColumn<>("Customer Surname");
        columnSurname.setCellValueFactory(new PropertyValueFactory<>("customerSurname"));

        TableColumn<Order, String> columnAddress = new TableColumn<>("Address");
        columnAddress.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));

        TableColumn<Order, Double> columnPostal = new TableColumn<>("Postal Code");
        columnPostal.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        TableColumn<Order, String> columnEmail = new TableColumn<>("Email");
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("customerEmail"));

        TableColumn<Order, String> columnPhone = new TableColumn<>("Phone");
        columnPhone.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));

        TableColumn<Order, Double> columnAmount = new TableColumn<>("Order Amount");
        columnAmount.setCellValueFactory(new PropertyValueFactory<>("orderAmount"));

        TableColumn<Order, Integer> columnProductId = new TableColumn<>("Product ID");
        columnProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));

        tabelord.getColumns().clear();
        tabelord.getColumns().addAll(columnId,columnName,columnSurname,columnAddress,columnPostal,columnEmail,columnPhone,columnAmount,columnProductId);

        ObservableList<Order> data = FXCollections.observableArrayList(orders);
        tabelord.setItems(data);
    }

    @FXML
    public void onAddOrderButtonClick(ActionEvent actionEvent) {


//        try {
//            var nameText = namefield.getText();
//            var priceText = pricefield.getText();
//            var quantityText = quantityfield.getText();
//
//            if (nameText.isEmpty() || priceText.isEmpty() || quantityText.isEmpty()) {
//                throw new NumberFormatException("All fields must be filled");
//            }
//
//            double price = Double.parseDouble(priceText);
//            int quantity = Integer.parseInt(quantityText);
//
//            if (price < 0 || quantity < 0) {
//                throw new NumberFormatException("Price and Quantity must be greater than or equal to 0");
//            }
//            service.add(nameText, price, quantity);
//            showAllRecords();
//            namefield.clear();
//            pricefield.clear();
//            quantityfield.clear();
//
//        } catch (NumberFormatException e) {
//            Alert hello = new Alert(Alert.AlertType.ERROR, e.getMessage());
//            hello.show();
//        }
        try {
            var customerName = name.getText();
            var customerSurname = prenume.getText();
            var customerAddress = adresa.getText();
            var postalCode = Double.parseDouble(codpostal.getText());
            var customerEmail = email.getText();
            var customerPhone = telefon.getText();
            var orderAmount = Double.parseDouble(cantitate.getText());
            var idprodus1 = Integer.parseInt(idprodus.getText());

            if (customerName.isEmpty() || customerSurname.isEmpty() || customerAddress.isEmpty() || customerEmail.isEmpty() || customerPhone.isEmpty()) {
                throw new RuntimeException("All fields must be filled");
            }

            if (postalCode < 0 || orderAmount < 0) {
                throw new RuntimeException("Postal code and Order amount must be greater than or equal to 0");
            }
            service.update(idprodus1,productRepo.getById(idprodus1).getName(),productRepo.getById(idprodus1).getPrice(),productRepo.getById(idprodus1).getNr() - (int) orderAmount);
            service.addOrder(customerName, customerSurname, customerAddress, postalCode, customerEmail, customerPhone, orderAmount, idprodus1);
            showAllOrders();
            // Clear fields after adding
            name.clear();
            prenume.clear();
            adresa.clear();
            codpostal.clear();
            email.clear();
            telefon.clear();
            cantitate.clear();
            idprodus.clear();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Postal code, Order amount, and Product ID must be numbers");
            alert.show();
        } catch (RuntimeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
        }
    }

}
