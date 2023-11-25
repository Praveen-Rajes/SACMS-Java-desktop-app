package com.example.ood;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class JoinClubController {
    @FXML
    protected void onBackButtonClick(ActionEvent actionEvent)throws IOException {
        Stage previousStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("studentProfile.fxml"));
        previousStage.setScene(new Scene(root, 1200, 750));
    }


}
