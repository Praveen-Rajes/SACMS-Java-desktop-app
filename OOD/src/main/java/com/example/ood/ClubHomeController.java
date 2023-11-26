package com.example.ood;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;


public class ClubHomeController {
    @FXML
    AnchorPane homePane;

    @FXML
    private Button homeButton;
    @FXML
    private Button removeButton;
    @FXML
    private TableView<Club> tableView;
    @FXML
    private TableColumn<Club, String> clubIDColumn;
    @FXML
    private TableColumn<Club, String> clubNameColumn;
    @FXML
    private TableColumn<Club, String> categoryColumn;
    @FXML
    private TableColumn<Club, String> clubAdvisorColumn;



    private ObservableList<Club> clubDetails = FXCollections.observableArrayList();

    public void initialize() {
        // Set cell value for the table columns
        clubIDColumn.setCellValueFactory(data -> data.getValue().clubIDProperty());
        clubNameColumn.setCellValueFactory(data -> data.getValue().clubNameProperty());
        categoryColumn.setCellValueFactory(data -> data.getValue().categoryProperty());

        // Bind the table view to the observable list
        loadDataFromDatabase();
        tableView.setItems(clubDetails);
    }

    public void addClubDetail(Club club) {
        clubDetails.add(club);
    }

    @FXML
    private void onHomeButtonClick(ActionEvent e1) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("clubdetails.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1200, 750);
            Stage stage = new Stage();
            stage.setTitle("Club Details");
            stage.setScene(scene);

            ClubdetailsController clubdetailsController = fxmlLoader.getController();
            clubdetailsController.setHomeController(this); // Set a reference to HomeController

            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadDataFromDatabase() {
        DBQuery dbQuery = new DBQuery();
        ArrayList<Club> clubList = dbQuery.getClubList();
        if (clubList != null) {
            clubDetails.addAll(clubList);
        }
    }









}