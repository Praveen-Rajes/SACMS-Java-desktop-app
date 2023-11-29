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
    @FXML
    private Button savebutton;
    private String selectedClub;
    private String selectedEventName;
    private String selectedAttendanceStatus;
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
                            selectedAttendanceStatus = choiceBox.getValue();
                            Attendance attendance = getTableView().getItems().get(getIndex());
                            attendance.setAttendanceStatus(selectedAttendanceStatus);
                            System.out.println("Selected Status: " + selectedAttendanceStatus);
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

            // Add a placeholder item
            Attendance placeholderClub = new Attendance("Select a club");
            clubDetails.add(placeholderClub);

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
            eventTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    String eventName = newValue.getEventName();
                    selectedEventName = eventName;
                    System.out.println("Selected Event: " + selectedEventName);

                    // Now you can use selectedEventName in your database operations or pass it to other methods
                }
            });

        }
    }

    @FXML
    public void OnActionSubmitClick() {
        Attendance selectedAttendance = clubChoiceBox.getValue();

        if (selectedAttendance != null && !selectedAttendance.getClubName().equals("Select a club")) {
            // Proceed with the selected club
            selectedClub = selectedAttendance.getClubName();
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
                showAlert("No Events Found", "No events found for the selected club.");
                System.out.println("No events found for the selected club.");
            }
        } else {
            showAlert("No Club Selected", "Please select a club.");
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
    @FXML
    public void OnActionSaveClick() {
        saveAttendance();
    }
    public void saveAttendance() {
        Attendance selectedAttendance = clubChoiceBox.getValue();
        ObservableList<Attendance> students = studentTableView.getItems();

        if (selectedAttendance != null && !selectedAttendance.getClubName().equals("Select a club")) {
            if (students != null && !students.isEmpty()) {
                DBQuery dbQuery = new DBQuery();
                String attendance = selectedAttendanceStatus; // Use the selected attendance status

                for (Attendance student : students) {
                    // Create a new Attendance object with student details and attendance status
                    Attendance studentAttendance = new Attendance(
                            student.getStudentId(),
                            student.getStudentName(),
                            selectedClub,
                            selectedEventName,
                            attendance
                    );

                    // Save the attendance for each student
                    dbQuery.addAttendance(studentAttendance);
                }
            } else {
                System.out.println("No students found for the selected club.");
            }
        } else {
            showAlert("No Club Selected", "Please select a club.");
            System.out.println("No club selected.");
        }
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        // Add event handler for OK button
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(okButton);

        // Show the alert and wait for user response
        alert.showAndWait().ifPresent(result -> {
            if (result == okButton) {
                eventTableView.getItems().clear();
                studentTableView.getItems().clear();
            }
        });
    }

}