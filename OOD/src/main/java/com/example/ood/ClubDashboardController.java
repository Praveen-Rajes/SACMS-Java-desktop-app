package com.example.ood;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClubDashboardController implements Initializable {

    @FXML
    private Button clubs;
    @FXML
    public Label advisorNameLabel;

    @FXML
    public Circle advisorImageView;

    @FXML
    private Button attendance;
    @FXML
    private Button event;
    @FXML
    private Button logout;
    @FXML
    private AnchorPane contentArea;


    private ClubHomeController homeController;
    private HelloController helloController;
    private int loggedInAdvisorId;

    public void setLoggedInAdvisorId(int loggedInAdvisorId) {
        this.loggedInAdvisorId = loggedInAdvisorId;
        // You can use loggedInAdvisorId as needed in this class
    }


    public void setHomeController(ClubHomeController homeController) {
        this.homeController = homeController;
    }

    public void setHelloController (HelloController helloController){this.helloController = helloController;}

    @FXML
    private void showClubPart() {
        loadFXML("ClubHome.fxml");
    }
    @FXML
    private void showEventPart() {
        loadFXML("EventSchedule.fxml");
    }


    @FXML
    private void showAttendance() {
        loadFXML("attendanceTracker.fxml");
    }
    @FXML
    private void advisorlogin() throws IOException {
        Stage stage = (Stage) logout.getScene().getWindow();
        // Close the stage
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("advisor_login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 750);
        Stage stage2 = new Stage();
        stage2.setTitle("Hello!");
        stage2.setScene(scene);
        stage2.show();
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
        logout.setOnMouseClicked(event -> {
            try {
                advisorlogin();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        event.setOnMouseClicked(event -> {
            showEventPart();
        });
    }


}
