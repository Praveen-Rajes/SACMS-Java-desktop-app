package com.example.ood;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.*;


public class HelloController {
    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    public void  switchStudent (ActionEvent e1) throws IOException {
        Stage previousStage = (Stage) ((Node) e1.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        previousStage.setScene(new Scene(root, 1200, 750));

    }
    @FXML
    public void  switchAdvisor (ActionEvent e2) throws IOException {
        Stage previousStage = (Stage) ((Node) e2.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("advisor_login.fxml"));
        previousStage.setScene(new Scene(root, 1200, 750));
    }
    @FXML
    public void onSignupButtonClick(ActionEvent e3) throws IOException{
        Stage previousStage = (Stage) ((Node) e3.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("student_registration.fxml"));
        previousStage.setScene(new Scene(root, 1200, 750));
    }
    @FXML
    public void onSignupButton2Click(ActionEvent e4) throws IOException{
        Stage previousStage = (Stage) ((Node) e4.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("advisor_registration.fxml"));
        previousStage.setScene(new Scene(root, 1200, 750));
    }
    @FXML
    public void onLoginButtonClick(ActionEvent e3) throws IOException{
        Stage previousStage = (Stage) ((Node) e3.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("clubdashboard.fxml"));
        previousStage.setScene(new Scene(root, 1200, 750));
    }
}