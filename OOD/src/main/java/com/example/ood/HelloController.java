package com.example.ood;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class HelloController {
    private Stage stage;
    private Scene scene;
    private Parent root;


    public void  switchStudent (ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 750);

        stage.setScene(scene);
        stage.show();

    }
    public void  switchAdvisor (ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("advisor_login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }
}