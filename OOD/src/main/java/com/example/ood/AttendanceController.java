package com.example.ood;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AttendanceController {

    @FXML
    private ChoiceBox<Attendance> clubChoiceBox;
    @FXML
    private TableView<Attendance> eventTableView;
    @FXML
    private TableColumn<Attendance, String> eventNameColumn;
    @FXML
    private TableColumn<Attendance, String> startTimeColumn;
    @FXML
    private TableColumn<Attendance, String> endTimeColumn;
    @FXML
    private Button submitButton;

    private String selectedClub;
    private final ObservableList<Attendance> clubDetails = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        eventNameColumn.setCellValueFactory(data -> data.getValue().eventNameProperty());
        startTimeColumn.setCellValueFactory(data -> data.getValue().startTimeProperty());
        endTimeColumn.setCellValueFactory(data -> data.getValue().endTimeProperty());

        DBQuery dbQuery = new DBQuery();
        ArrayList<Attendance> attendanceList = dbQuery.getClubListForAttendance();

        if (attendanceList != null) {
            // Clear existing items in the observable list (if necessary)
            clubDetails.clear();

            // Extract club names from the Attendance objects
            List<String> clubNames = attendanceList.stream()
                    .map(Attendance::getClubName)
                    .collect(Collectors.toList());

            // Set items to the ChoiceBox
            clubDetails.addAll(attendanceList);
            clubChoiceBox.setItems(clubDetails);

            // Set a cell factory to display club names in the ChoiceBox
            clubChoiceBox.setConverter(new StringConverter<Attendance>() {
                @Override
                public String toString(Attendance attendance) {
                    return attendance.getClubName();
                }

                @Override
                public Attendance fromString(String string) {
                    return null;
                }
            });

            // Optionally, set a default value for the ChoiceBox
            if (!clubDetails.isEmpty()) {
                clubChoiceBox.setValue(clubDetails.get(0));
            }

            // Add a change listener to the clubChoiceBox
            clubChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    selectedClub = newValue.getClubName();
                    System.out.println("Selected Club: " + selectedClub);
                } else {
                    System.out.println("Null");
                }
            });
        }
    }


    @FXML
    public void OnActionSubmitClick() {
        if (selectedClub != null) {
            // Fetch event details for the selected club
            DBQuery dbQuery = new DBQuery();
            ArrayList<Attendance> eventList = dbQuery.getEventListForAttendance(selectedClub);

            // Clear existing items in the TableView
            eventTableView.getItems().clear();

            // Populate the TableView with event details
            if (eventList != null) {
                eventTableView.getItems().addAll(eventList);
            }
        } else {
            System.out.println("No club selected.");
        }
    }
}