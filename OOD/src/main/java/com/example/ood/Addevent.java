package com.example.ood;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Addevent {
    @FXML
    private Spinner<Integer> startHourSpinner;

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

        String[] items = {"ROTRACT CLUB", "IEEE", "LEO CLUB"};

        // Create an ObservableList from the array
        ObservableList<String> observableList = FXCollections.observableArrayList(items);

        // Set items to the ChoiceBox
        clubChoiceBox.setItems(observableList);

        // Table View
        // Set cell value for the table columns
        eventid.setCellValueFactory(data -> data.getValue().eventIDProperty());
        eventname.setCellValueFactory(data -> data.getValue().eventNameProperty());
        clubid.setCellValueFactory(data -> data.getValue().clubProperty());

        loadDataFromDatabase();
        viewTable.setItems(eventDetails);
        viewTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

        });

    }



    public void viewDate(ActionEvent actionEvent) {
        String selectedClubName = (String) clubChoiceBox.getValue();
        LocalDate selectedDate = datePicker.getValue();
        System.out.println(selectedClubName);
        System.out.println(selectedDate);

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
        event.setSelectedClubName((String) clubChoiceBox.getValue());


    }
    private void loadDataFromDatabase() {
        DBQuery dbQuery = new DBQuery();
        ArrayList<Event> eventList = dbQuery.getEventList();
        if (eventList != null) {
            eventDetails.addAll(eventList);
        }
    }
}
