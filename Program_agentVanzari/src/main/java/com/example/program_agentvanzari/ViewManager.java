package com.example.program_agentvanzari;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewManager{

    private Stage primaryStage;

    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        // Încărcați fxml-ul pentru prima scenă
        Parent firstView = FXMLLoader.load(getClass().getResource("login-view.fxml"));
        Scene firstScene = new Scene(firstView);

        // Încărcați fxml-ul pentru a doua scenă
        Parent secondView = FXMLLoader.load(getClass().getResource("menu-view.fxml"));
        Scene secondScene = new Scene(secondView);

        // Setează scena inițială
        primaryStage.setScene(firstScene);
        primaryStage.show();
    }
}

