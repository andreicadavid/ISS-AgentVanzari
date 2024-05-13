package com.example.program_agentvanzari;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private TextField userbar;

    @FXML
    private PasswordField passbar;

    public boolean login(String username, String password) {
        String query = "SELECT * FROM Users WHERE username = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:users.sqlite");
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // Returnează true dacă a fost găsit un utilizator cu acest username și parolă
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Returnează false în caz de eroare sau dacă autentificarea a eșuat
        }
    }

    public void switchToSecondView(ActionEvent event) throws IOException {
        String username = userbar.getText();
        String password = passbar.getText();
        // Verificați dacă username-ul se termină în ".empl"
        try {
            if (username.endsWith(".empl") && login(username, password)) {
                // Dacă username-ul este valid, deschideți următoarea scenă
                FXMLLoader loader = new FXMLLoader(getClass().getResource("menu-view.fxml"));
                Parent secondView = loader.load();
                Scene secondScene = new Scene(secondView);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(secondScene);
            } else {
                // Dacă username-ul nu este valid, puteți afișa un mesaj de eroare
                throw new NumberFormatException("Username-ul sau parola nu este valid! parola introdusa este: " + password);
                //System.out.println("Username-ul sau parola nu este valid! parola introdusa este: " + password);
            }
        }catch (NumberFormatException e){
            Alert hello = new Alert(Alert.AlertType.ERROR, e.getMessage());
            hello.show();
        }
    }
}