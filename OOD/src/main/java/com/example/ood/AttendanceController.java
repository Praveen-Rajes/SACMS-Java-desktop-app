package com.example.ood;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import java.util.ArrayList;

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
    private TableColumn<Attendance, String> attendanceColumn;
    @FXML
    private TableView<Attendance> studentTableView;
    @FXML
    private TableColumn<Attendance, String> studentIDColumn;
    @FXML
    private TableColumn<Attendance, String> studentNameColumn;
    @FXML
    private Button submitButton;
    private String selectedClub;
    private final ObservableList<Attendance> clubDetails = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        eventNameColumn.setCellValueFactory(data -> data.getValue().eventNameProperty());
        startTimeColumn.setCellValueFactory(data -> data.getValue().startTimeProperty());
        endTimeColumn.setCellValueFactory(data -> data.getValue().endTimeProperty());

        attendanceColumn.setCellValueFactory(data -> data.getValue().attendanceStatusProperty());
        attendanceColumn.setCellFactory(col -> {
            ChoiceBox<String> choiceBox = new ChoiceBox<>(FXCollections.observableArrayList("Absent", "Present"));
            TableCell<Attendance, String> cell = new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        choiceBox.setValue(item);
                        choiceBox.setOnAction(event -> {
                            String selectedStatus = choiceBox.getValue();
                            Attendance attendance = getTableView().getItems().get(getIndex());
                            attendance.setAttendanceStatus(selectedStatus);
                            System.out.println("Selected Status: " + selectedStatus);
                        });
                        setGraphic(choiceBox);
                    }
                }
            };
            return cell;
        });

        DBQuery dbQuery = new DBQuery();
        ArrayList<Attendance> attendanceList = dbQuery.getClubListForAttendance();

        if (attendanceList != null) {
            clubDetails.clear();
            clubDetails.addAll(attendanceList);

            clubChoiceBox.setItems(clubDetails);

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

            if (!clubDetails.isEmpty()) {
                clubChoiceBox.setValue(clubDetails.get(0));
            }

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
            DBQuery dbQuery = new DBQuery();
            ArrayList<Attendance> eventList = dbQuery.getEventListForAttendance(selectedClub);

            eventTableView.getItems().clear();

            if (eventList != null && !eventList.isEmpty()) {
                eventTableView.getItems().addAll(eventList);

                // Check if there are students before loading them
                boolean hasStudents = checkIfStudentsExist(selectedClub);

                if (hasStudents) {
                    loadStudentName();
                } else {
                    // Disable studentTableView if no students
                    studentTableView.setDisable(true);
                }
            } else {
                System.out.println("No events found for the selected club.");
            }
        } else {
            System.out.println("No club selected.");
        }
    }

    private boolean checkIfStudentsExist(String club) {
        DBQuery dbQuery = new DBQuery();
        ArrayList<Attendance> studentList = dbQuery.getStudentListForAttendance(club);

        return studentList != null && !studentList.isEmpty();
    }


    public void loadStudentName() {
        studentIDColumn.setCellValueFactory(data -> data.getValue().studentIDProperty());
        studentNameColumn.setCellValueFactory(data -> data.getValue().studentFNameProperty());

        DBQuery dbQuery = new DBQuery();
        ArrayList<Attendance> studentList = dbQuery.getStudentListForAttendance(selectedClub);

        studentTableView.getItems().clear();

        if (studentList != null) {
            studentTableView.getItems().addAll(studentList);
        } else {
            System.out.println("No students found for the selected club.");
        }
    }
}
