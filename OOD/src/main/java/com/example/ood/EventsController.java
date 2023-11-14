package com.example.ood;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EventsController {
    @FXML
    private DatePicker datePicker;

    @FXML
    private ChoiceBox<String> clubChoiceBox;

    @FXML
    private ChoiceBox<String> eventChoiceBox;

    @FXML
    private TextField startTime;

    @FXML
    private TextField endTime;

    @FXML
    private TableView<Student> studentTableView;

    @FXML
    private TableColumn<Student, String> studentIDColumn;

    @FXML
    private TableColumn<Student, String> studentNameColumn;

    @FXML
    private TableColumn<Student, String> attendanceColumn;

    @FXML
    private ChoiceBox<String> attendanceChoiceBox;

    private final List<String> clubIDs = new ArrayList<>();
    private final List<String> clubNames = new ArrayList<>();
    private String selectedClubID; // Store the selected club ID

    @FXML
    private void initialize() {
        readClubDataFromFile();
        clubChoiceBox.getItems().addAll(clubNames);

        // Associate the studentIDColumn and studentNameColumn with Student properties
        studentIDColumn.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));


    }

    @FXML
    private void onDateOrClubSelection(ActionEvent event) {
        selectedClubID = getSelectedClubID();
        LocalDate selectedDate = datePicker.getValue();

        if (selectedClubID != null && selectedDate != null) {
            List<String> filteredEvents = readEventsForClubAndDate(selectedClubID, selectedDate);

            eventChoiceBox.getItems().clear();
            eventChoiceBox.getItems().addAll(filteredEvents);

            startTime.clear();
            endTime.clear();
        }
    }

    @FXML
    private void onEventSelection(ActionEvent event) {
        String selectedEventName = eventChoiceBox.getValue();

        if (selectedClubID != null && selectedEventName != null) {
            String[] eventDetails = readEventDetailsForClubAndEvent(selectedClubID, selectedEventName);

            if (eventDetails != null) {
                startTime.setText(eventDetails[2]); // Assuming start time is at index 2
                endTime.setText(eventDetails[3]); // Assuming end time is at index 3
            }
        }
    }

    @FXML
    private void onSearchButtonClicked(ActionEvent event) {
        String selectedClub = clubChoiceBox.getValue();

        if (selectedClub != null) {
            List<Student> students = readStudentsForClub(selectedClubID);

            // Clear existing data in the table
            studentTableView.getItems().clear();

            // Create an ObservableList of students and set it to the table
            ObservableList<Student> studentData = FXCollections.observableArrayList(students);
            studentTableView.setItems(studentData);
        }
    }


    private void readClubDataFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("clubData.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    clubIDs.add(parts[0]);
                    clubNames.add(parts[1]); // Assuming club name is at index 1 in clubData.txt
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getSelectedClubID() {
        int selectedIndex = clubChoiceBox.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < clubIDs.size()) {
            return clubIDs.get(selectedIndex);
        }
        return null;
    }

    private List<String> readEventsForClubAndDate(String selectedClub, LocalDate selectedDate) {
        List<String> filteredEvents = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("events.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String eventClubID = parts[0];
                    LocalDate eventDate = LocalDate.parse(parts[4], DateTimeFormatter.ofPattern("M/d/yyyy"));
                    if (eventClubID.equals(selectedClub) && eventDate.equals(selectedDate)) {
                        filteredEvents.add(parts[1]);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filteredEvents;
    }

    private String[] readEventDetailsForClubAndEvent(String selectedClub, String selectedEventName) {
        try (BufferedReader br = new BufferedReader(new FileReader("events.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String eventClubID = parts[0];
                    if (eventClubID.equals(selectedClub) && parts[1].equals(selectedEventName)) {
                        return parts;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Student> readStudentsForClub(String selectedClubID) {
        List<Student> students = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("students.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String clubID = parts[0]; // Change variable name to clubID for clarity
                    if (clubID.equals(selectedClubID)) {
                        String studentID = parts[1];
                        String studentName = parts[2];
                        students.add(new Student(studentID, studentName)); // Default attendance status
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return students;
    }
}
