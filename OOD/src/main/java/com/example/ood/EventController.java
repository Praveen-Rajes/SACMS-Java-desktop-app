package com.example.ood;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EventController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
