package com.example.ood;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AttendanceController {
    @FXML
    public DatePicker datePicker;
    @FXML
    public ChoiceBox<Attendance> clubChoiceBox;
    @FXML
    public ChoiceBox<String> eventChoiceBox;
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

    private final ObservableList<Attendance> clubDetails = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
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

            System.out.println(clubDetails);
        }
    }

    @FXML
    private void onDateOrClubSelection(ActionEvent event) {
    }

    @FXML
    private void onEventSelection(ActionEvent event) {
        // Handle event selection
    }

    @FXML
    private void onSearchButtonClicked(ActionEvent event) {

    }
}
