package com.example.ood;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import java.io.*;


public class HelloController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField advisorLoginIdField;

    @FXML
    private PasswordField advisorPasswordField;
    public int loggedInAdvisorId;
    public String loggedInAdvisorFName;
    public String loggedInAdvisorLName;


    @FXML
    public void  switchStudent (ActionEvent e1) throws IOException {
        Stage previousStage = (Stage) ((Node) e1.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("StudentLogin.fxml"));
        previousStage.setScene(new Scene(root, 1200, 750));

    }

    @FXML
    public void onSignupButton2Click(ActionEvent e4) throws IOException{
        Stage previousStage = (Stage) ((Node) e4.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("AdvisorRegistrationForm.fxml"));
        previousStage.setScene(new Scene(root, 1200, 750));
    }
    @FXML
    public void onLoginButtonClick(ActionEvent event) throws IOException {
        int enteredAdvisorId = Integer.parseInt(advisorLoginIdField.getText());
        String enteredPassword = advisorPasswordField.getText();

        DBQuery dbQuery = new DBQuery();
        dbQuery.setLoggedInAdvisorId(loggedInAdvisorId);
        ClubAdvisor loggedInAdvisor = dbQuery.getAdvisorByLogin(enteredAdvisorId, enteredPassword);

        if (loggedInAdvisor != null) {
            // Set the logged-in advisor ID
            loggedInAdvisorId = loggedInAdvisor.getAdvisorId();
            loggedInAdvisorFName =loggedInAdvisor.getFirstName();
            loggedInAdvisorLName =loggedInAdvisor.getLastName();

            dbQuery.setLoggedInAdvisorId(loggedInAdvisorId);


            // Load the Club Dashboard FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ClubDashboard.fxml"));
            Parent root = loader.load();

            // Get the ClubDashboardController instance
            ClubDashboardController dashboardController = loader.getController();
            dashboardController.setHelloController(this);

            // Set the logged-in advisor's name in the label
            dashboardController.advisorNameLabel.setText(loggedInAdvisor.getFirstName() + " " + loggedInAdvisor.getLastName());

            // Load and display advisor image in the Circle
            String imagePath = "OOD/src/main/resources/advisorImages/" + loggedInAdvisorId + ".jpg";
            // Load the image and set it in the Circle
            Image image = new Image(new FileInputStream(imagePath));
            dashboardController.advisorImageView.setFill(new ImagePattern(image));

            ClubdetailsController.setLoggedInAdvisorId(loggedInAdvisorId);


            // Show the Club Dashboard
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1200, 750));

            // Call setLoggedInAdvisorId after the FXML is loaded and controller is initialized
            dashboardController.setLoggedInAdvisorId(loggedInAdvisorId);
            ReportController.setLoggedInAdvisorName(loggedInAdvisorFName,loggedInAdvisorLName);



        } else {
            // Handle invalid login (display an error message, etc.)
            System.out.println("Login failed");
        }
    }


}