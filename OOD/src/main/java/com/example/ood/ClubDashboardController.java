package com.example.ood;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClubDashboardController implements Initializable {
    @FXML
    private Label menu;

    @FXML
    private Label menuBack;

    @FXML
    private Button clubs;

    @FXML
    private Button attendance;
    @FXML
    private AnchorPane contentArea;
    @FXML
    private AnchorPane slider;

    private ClubHomeController homeController;

    public void setHomeController(ClubHomeController homeController) {
        this.homeController = homeController;
    }

    @FXML
    private void showClubPart() {
        loadFXML("Home.fxml");
    }


    @FXML
    private void showAttendance() {
        loadFXML("attendanceTracker.fxml");
    }




    private void loadFXML(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            contentArea.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showClubPart();

        clubs.setOnMouseClicked(event -> {
            showClubPart();
        });

        attendance.setOnMouseClicked(event -> {
            showAttendance();
        });
    }
}
