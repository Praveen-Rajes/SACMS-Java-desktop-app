package com.example.ood;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AttendanceController {

    @FXML
    public DatePicker datePicker;
    @FXML
    public ChoiceBox<String> clubChoiceBox; // Updated to use String for the ChoiceBox
    @FXML
    public ChoiceBox<String> eventChoiceBox; // Updated to use String for the ChoiceBox
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



    private final List<String> clubIDs = new ArrayList<>();
    private final List<String> clubNames = new ArrayList<>();
    private String selectedClubID; // Store the selected club ID


    private final List<Club> clubs = new ArrayList<>();
    @FXML
    private void initialize() {
        // Populate club data
        clubs.add(new Club("English Literature", "EL_AC", "Academic", "sssssssssssssssssssssssss", null));
        clubs.add(new Club("Swimming", "SW_SP", "Sports", "sssssssssssssssssssss", null));
        clubs.add(new Club("Sinhala Literature Society", "SLS_AC", "Academic", "ssssssssssssssss", null));
        clubs.add(new Club("Badminton", "BA_SP", "Sports", "ddddddddddddddddd", null));
        clubs.add(new Club("Japanese Language Society", "JLS_CU", "Cultural", "dddddddddddddd", null));
        clubs.add(new Club("Cricket", "CR_SP", "Sports", "cccccccccccccccc", null));
        clubs.add(new Club("Information Communication Technology", "ICT_TE", "Technological", "2222222222222222222", null));


    }

    @FXML
    private void onDateOrClubSelection(ActionEvent event) {
    }

    @FXML
    private void onEventSelection(ActionEvent event) {
    }
    @FXML
    private void onSearchButtonClicked(ActionEvent event) {

    }








}