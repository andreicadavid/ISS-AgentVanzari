package com.example.program_agentvanzari.Repository;

import com.example.program_agentvanzari.Domain.Entity;
import com.example.program_agentvanzari.Domain.User;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDatabaseRepo implements IDatabaseRepository {

    private final String url = "jdbc:sqlite:users.sqlite";
    private Connection connection;

    public UserDatabaseRepo() {
        createTablesIfNotExists();
    }
//    super(userId);
//        this.username = username;
//        this.password = password;
    public void createTablesIfNotExists() {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS Users (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Username TEXT," +
                "Password TEXT" +
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
        if (entity instanceof User) {
            User user = (User) entity;
            String updateQuery = "UPDATE Orders SET CustomerName=?, CustomerSurname=?, Adress=?, Postal=?, Email=?, Phone=?, Amount=?, ProductID=? WHERE ID=?;";
            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
                pstmt.setString(1, user.getUsername());
                pstmt.setString(2, user.getPassword());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException("Entity must be User");
        }
    }

    @Override
    public void delete(Entity entity) {
        if (entity instanceof User) {
            User user = (User) entity;
            String deleteQuery = "DELETE FROM Users WHERE ID=?;";
            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement pstmt = conn.prepareStatement(deleteQuery)) {
                pstmt.setInt(1, user.getId());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException("Entity must be User");
        }
    }

    @Override
    public void add(Entity entity) {
        if (entity instanceof User) {
            User user = (User) entity;
            String insertQuery = "INSERT INTO Users (Username, Password) VALUES (?, ?);";
            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                pstmt.setString(1, user.getUsername());
                pstmt.setString(2, user.getPassword());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException("Entity must be User");
        }
    }

    @Override
    public List<User> getData() {
        List<User> data = new ArrayList<>();
        String selectQuery = "SELECT * FROM Users;";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectQuery)) {

            while (rs.next()) {
//                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
//                "Username TEXT," +
//                "Password TEXT"
                int id = rs.getInt("ID");
                String username = rs.getString("Username");
                String password = rs.getString("Password");

                User user = new User(id, username, password);
                data.add(user);
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
