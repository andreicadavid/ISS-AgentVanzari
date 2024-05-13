package com.example.program_agentvanzari;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController {

        public void openThirdStage(ActionEvent event) throws IOException {
            // Încărcați fișierul FXML pentru al treilea Stage
            FXMLLoader loader = new FXMLLoader(getClass().getResource("administration-view.fxml"));
            Parent thirdView = loader.load();
            Scene thirdScene = new Scene(thirdView);

            // Creați un nou Stage
            Stage thirdStage = new Stage();
            thirdStage.setScene(thirdScene);
            thirdStage.setTitle("Manage the Stock");
            thirdStage.show();
        }

        public void openFourthStage(ActionEvent event) throws IOException {
            // Încărcați fișierul FXML pentru al patrulea Stage
            FXMLLoader loader = new FXMLLoader(getClass().getResource("order-view.fxml"));
            Parent fourthView = loader.load();
            Scene fourthScene = new Scene(fourthView);

            // Creați un nou Stage
            Stage fourthStage = new Stage();
            fourthStage.setScene(fourthScene);
            fourthStage.setTitle("Make a order");
            fourthStage.show();
        }
    }


