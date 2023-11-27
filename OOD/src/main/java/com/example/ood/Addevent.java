package com.example.ood;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

public class Addevent {
    @FXML
    private Spinner<Integer> startHourSpinner;
    @FXML
    private TextField ClubIDField;

    @FXML
    private Spinner<Integer> startMinuteSpinner;

    @FXML
    private Spinner<Integer> endHourSpinner;

    @FXML
    private Spinner<Integer> endMinuteSpinner;

    @FXML
    TableView viewTable;

    @FXML
    private TableColumn<Event, String> eventid;

    @FXML
    private TableColumn<Event, String> eventname;
    @FXML
    private TableColumn<Event, String> clubid;

    @FXML
    private ChoiceBox clubChoiceBox;

    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField eventID;
    @FXML
    private TextArea eventDesc;

    @FXML
    private TextField eventLoc;

    @FXML
    private TextField eventName;
    @FXML
    private Button backToMenu;

    private ObservableList<Event> eventDetails = FXCollections.observableArrayList();
    @FXML
    private void initialize() {
        // Set spinner values dynamically
        int minHValue = 0;
        int maxHValue = 23;
        int minMValue = 0;
        int maxMValue = 59;

        SpinnerValueFactory<Integer> valueFactorySH =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(minHValue, maxHValue);
        SpinnerValueFactory<Integer> valueFactorySM =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(minMValue, maxMValue);
        SpinnerValueFactory<Integer> valueFactoryEH =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(minHValue, maxHValue);
        SpinnerValueFactory<Integer> valueFactoryEM =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(minMValue, maxMValue);

        startHourSpinner.setValueFactory(valueFactorySH);
        startMinuteSpinner.setValueFactory(valueFactorySM);
        endHourSpinner.setValueFactory(valueFactoryEH);
        endMinuteSpinner.setValueFactory(valueFactoryEM);

        DBQuery dbQuery = new DBQuery();
        ArrayList<Attendance> items = dbQuery.getClubListForAttendance();



        // Create an ObservableList from the array
        ObservableList<Attendance> observableList = FXCollections.observableArrayList(items);

        // Set items to the ChoiceBox
        clubChoiceBox.setItems(observableList);
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
        if (!observableList.isEmpty()) {
            clubChoiceBox.setValue(observableList.get(0));
        }

        System.out.println(observableList);

        // Table View
        // Set cell value for the table columns
        //eventid.setCellValueFactory(data -> data.getValue().eventIDProperty());
       // eventname.setCellValueFactory(data -> data.getValue().eventNameProperty());
       // clubid.setCellValueFactory(data -> data.getValue().clubProperty());

       // loadDataFromDatabase();
       // viewTable.setItems(eventDetails);
       // viewTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

      //  });

    }



    public void viewDate(ActionEvent actionEvent) {
        String selectedClubName = String.valueOf(clubChoiceBox.getValue());
        LocalDate selectedDate = datePicker.getValue();
        System.out.println(selectedClubName);
        System.out.println(selectedDate);
        loadDataFromDatabase();

    }

    public void clearData(ActionEvent actionEvent) {
        eventID.setText(null);
        eventName.setText(null);
        eventLoc.setText(null);
        eventDesc.setText(null);
        startHourSpinner.getValueFactory().setValue(0);
        startMinuteSpinner.getValueFactory().setValue(0);
        endHourSpinner.getValueFactory().setValue(0);
        endMinuteSpinner.getValueFactory().setValue(0);

    }

    public void backToAddMenu(ActionEvent actionEvent) {
        Stage currentstage = (Stage) backToMenu.getScene().getWindow();

        // Close the stage
        currentstage.close();


    }

    public void addEventSave(ActionEvent actionEvent) {
        viewTable.setItems(eventDetails);
        getEventDetails();

    }

    public void getEventDetails(){
        Event event = new Event();
        event.setEventID(eventID.getText());
        event.setEventName(eventName.getText());
        event.setEventLocation(eventLoc.getText());
        event.setEstartTime(String.format("%02d:%02d", startHourSpinner.getValue(), startMinuteSpinner.getValue()));
        event.setEendTime(String.format("%02d:%02d", endHourSpinner.getValue(), endMinuteSpinner.getValue()));
        event.setEventDescription(eventDesc.getText());
        event.setSelectedDate(datePicker.getValue());
        event.setSelectedClubName(String.valueOf(clubChoiceBox.getValue()));



        DBQuery dbQuery = new DBQuery();
        ArrayList<Attendance> items = dbQuery.getClubListForAttendance();
        ArrayList<Attendance> EventclubID = dbQuery.getClubListForAttendance();

        String searchString = (String.valueOf(clubChoiceBox.getValue()));

        // Search for the index of the matching element
        int index = -1;  // Default value if not found

        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).equals(searchString)) {
                index = i;
                break; // Exit the loop once a match is found
            }
        }
        String ClubID = (String.valueOf(EventclubID.get(index)));
        System.out.println(ClubID);
        event.setClubID(ClubID);
        dbQuery.EventInsert(event);


    }
    private void loadDataFromDatabase() {
        // Clear existing data
        eventDetails.clear();

        DBQuery dbQuery = new DBQuery();
        ArrayList<Event> eventList = dbQuery.getEventList(String.valueOf(datePicker.getValue()), String.valueOf(clubChoiceBox.getValue()));

        // Add new data to eventDetails
        if (eventList != null) {
            eventDetails.addAll(eventList);
        }


        // Refresh the table
        viewTable.refresh();

    }


}
