package com.example.program_agentvanzari.Repository;

import com.example.program_agentvanzari.Domain.Entity;
import com.example.program_agentvanzari.Domain.Order;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDatabaseRepo implements IDatabaseRepository {

    private final String url = "jdbc:sqlite:orders.sqlite";
    private Connection connection;

    public OrderDatabaseRepo() {
        createTablesIfNotExists();
    }
    public void createTablesIfNotExists() {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS Orders (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "CustomerName TEXT," +
                "CustomerSurname TEXT," +
                "Adress TEXT," +
                "Postal DOUBLE," +
                "Email TEXT," +
                "Phone TEXT," +
                "Amount DOUBLE," +
                "ProductID INTEGER" +
                ");";

        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl(url);
        try {
            if (connection == null || connection.isClosed()) {
                connection = ds.getConnection();
                Statement stmt = connection.createStatement();
                stmt.execute(createTableQuery);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Entity entity) {
        if (entity instanceof Order) {
            Order order = (Order) entity;
            String updateQuery = "UPDATE Orders SET CustomerName=?, CustomerSurname=?, Adress=?, Postal=?, Email=?, Phone=?, Amount=?, ProductID=? WHERE ID=?;";
            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
                pstmt.setString(1, order.getCustomerName());
                pstmt.setString(2, order.getCustomerSurname());
                pstmt.setString(3, order.getCustomerAddress());
                pstmt.setDouble(4, order.getPostalCode());
                pstmt.setString(5, order.getCustomerEmail());
                pstmt.setString(6, order.getCustomerPhone());
                pstmt.setDouble(7, order.getOrderAmount());
                pstmt.setInt(8, order.getProductId());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException("Entity must be Order");
        }
    }

    @Override
    public void delete(Entity entity) {
        if (entity instanceof Order) {
            Order order = (Order) entity;
            String deleteQuery = "DELETE FROM Orders WHERE ID=?;";
            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement pstmt = conn.prepareStatement(deleteQuery)) {
                pstmt.setInt(1, order.getId());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException("Entity must be Order");
        }
    }

    @Override
    public void add(Entity entity) {
        if (entity instanceof Order) {
            Order order = (Order) entity;
            String insertQuery = "INSERT INTO Orders (CustomerName, CustomerSurname, Adress, Postal, Email, Phone, Amount, ProductID) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                pstmt.setString(1, order.getCustomerName());
                pstmt.setString(2, order.getCustomerSurname());
                pstmt.setString(3, order.getCustomerAddress());
                pstmt.setDouble(4, order.getPostalCode());
                pstmt.setString(5, order.getCustomerEmail());
                pstmt.setString(6, order.getCustomerPhone());
                pstmt.setDouble(7, order.getOrderAmount());
                pstmt.setInt(8, order.getProductId());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException("Entity must be Order");
        }
    }

    @Override
    public List<Order> getData() {
        List<Order> data = new ArrayList<>();
        String selectQuery = "SELECT * FROM Orders;";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectQuery)) {

            while (rs.next()) {
//                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
//                        "CustomerName TEXT," +
//                        "CustomerSurname TEXT," +
//                        "Adress TEXT," +
//                        "Postal DOUBLE," +
//                        "Email TEXT," +
//                        "Phone TEXT," +
//                        "Amount DOUBLE," +
//                        "ProductID INTEGER"
                int id = rs.getInt("ID");
                String customerName = rs.getString("CustomerName");
                String customerSurname = rs.getString("CustomerSurname");
                String customerAdress = rs.getString("Adress");
                double postal = rs.getDouble("Postal");
                String email = rs.getString("Email");
                String phone = rs.getString("Phone");
                double amount = rs.getDouble("Amount");
                int productID = rs.getInt("ProductID");

                Order order = new Order(id, customerName, customerSurname, customerAdress, postal, email, phone, amount, productID);
                data.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public Entity getById(int id) {
//        String selectQuery = "SELECT * FROM Products WHERE ID = ?";
//        try (Connection conn = DriverManager.getConnection(url);
//             PreparedStatement pstmt = conn.prepareStatement(selectQuery)) {
//            pstmt.setInt(1, id);
//            ResultSet rs = pstmt.executeQuery();
//            if (rs.next()) {
//                String name = rs.getString("Name");
//                double price = rs.getDouble("Price");
//                int quantity = rs.getInt("Quantity");
//                return new Product(id, name, price, quantity);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        return null; // Returnează null dacă nu se găsește niciun produs cu id-ul dat
    }

}

