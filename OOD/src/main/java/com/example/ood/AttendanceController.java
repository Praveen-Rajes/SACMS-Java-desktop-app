package com.example.ood;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TableView;


public class AttendanceController {

    @FXML
    public DatePicker datePicker;
    @FXML
    public ChoiceBox clubChoiceBox;
    @FXML
    public ChoiceBox eventChoiceBox;
    @FXML
    public TextField startTime;
    @FXML
    public TextField endTime;
    @FXML
    public Button search;
    @FXML
    public Button update;
    @FXML
    public TableView studentTableView;
    @FXML
    public TableColumn studentIDColumn;
    @FXML
    public TableColumn studentNameColumn;
    @FXML
    public TableColumn attendanceColumn;


    public void onDateOrClubSelection(ActionEvent event) {
    }

    public void onEventSelection(ActionEvent event) {
    }

    public void onSearchButtonClicked(ActionEvent event) {
    }



}

