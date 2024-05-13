package com.example.program_agentvanzari.Repository;

import com.example.program_agentvanzari.Domain.Entity;
import com.example.program_agentvanzari.Domain.Product;
import org.sqlite.SQLiteDataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDatabaseRepo implements IDatabaseRepository {

    private final String url = "jdbc:sqlite:product.sqlite";
    private Connection connection;

    public ProductDatabaseRepo() {
        createTablesIfNotExists();
    }

    public void createTablesIfNotExists() {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS Products (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Name TEXT," +
                "Price DOUBLE," +
                "Quantity INTEGER" +
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
        if (entity instanceof Product) {
            Product product = (Product) entity;
            String updateQuery = "UPDATE Products SET Name=?, Price=?, Quantity=? WHERE ID=?;";
            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
                pstmt.setString(1, product.getName());
                pstmt.setDouble(2, product.getPrice());
                pstmt.setInt(3, product.getNr());
                pstmt.setInt(4, product.getId());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException("Entity must be Product");
        }
    }

    @Override
    public void delete(Entity entity) {
        if (entity instanceof Product) {
            Product product = (Product) entity;
            String deleteQuery = "DELETE FROM Products WHERE ID=?;";
            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement pstmt = conn.prepareStatement(deleteQuery)) {
                pstmt.setInt(1, product.getId());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException("Entity must be Product");
        }
    }

    @Override
    public void add(Entity entity) {
        if (entity instanceof Product) {
            Product product = (Product) entity;
            String insertQuery = "INSERT INTO Products (Name, Price, Quantity) VALUES (?, ?, ?);";
            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                pstmt.setString(1, product.getName());
                pstmt.setDouble(2, product.getPrice());
                pstmt.setInt(3, product.getNr());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException("Entity must be Product");
        }
    }

    @Override
    public List<Product> getData() {
        List<Product> data = new ArrayList<>();
        String selectQuery = "SELECT * FROM Products;";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectQuery)) {

            while (rs.next()) {
//                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
//                "Name TEXT," +
//                "Price INTEGER," +
//                "Quantity INTEGER" +
                int id = rs.getInt("ID");
                String Name = rs.getString("Name");
                double price = rs.getDouble("Price");
                int quantity = rs.getInt("Quantity");

                Product product = new Product(id, Name, price, quantity);
                data.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public Product getById(int id) {
        String selectQuery = "SELECT * FROM Products WHERE ID = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(selectQuery)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("Name");
                double price = rs.getDouble("Price");
                int quantity = rs.getInt("Quantity");
                return new Product(id, name, price, quantity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Returnează null dacă nu se găsește niciun produs cu id-ul dat
    }

}
