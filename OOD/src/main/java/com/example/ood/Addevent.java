package com.example.ood;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

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

//    @FXML
//    private TableColumn<Event, String> eventid;
//
//    @FXML
//    private TableColumn<Event, String> eventname;
//    @FXML
//    private TableColumn<Event, String> clubid;

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

    @FXML
    private ChoiceBox ClubID;

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
        ArrayList<String> EventclubID = dbQuery.getClubIDListForAttendance();



        // Create an ObservableList from the array
        ObservableList<Attendance> observableList = FXCollections.observableArrayList(items);
        ObservableList<String> observableList1 = FXCollections.observableArrayList(EventclubID);

        // Set items to the ChoiceBox
        clubChoiceBox.setItems(observableList);
        ClubID.setItems(observableList1);
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
//        ClubID.setConverter(new StringConverter<Attendance>() {
//            @Override
//            public String toString(Attendance attendance) {
//                return attendance.getClubName();
//            }
//
//            @Override
//            public Attendance fromString(String string) {
//                return null;
//            }
//        });
//        if (!observableList1.isEmpty()) {
//            ClubID.setValue(observableList1.get(0));
//        }
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
        String hi = String.valueOf(( (ClubID.getValue())));
        System.out.println(hi);
        LocalDate selectedDate = datePicker.getValue();
        //System.out.println(selectedClubName);
        System.out.println(selectedDate);
        loadDataFromDatabase();

        DBQuery dbQuery = new DBQuery();
        ArrayList<Attendance> items = dbQuery.getClubListForAttendance();
        ArrayList<String> EventclubID = dbQuery.getClubIDListForAttendance();

       for(int i = 0;i<items.size();i++){
            if((ClubID.getValue()) == (items.get(i))){
                System.out.println(i);
            }
        }



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



//        String eventid =eventID.getText();
//        String clubid = String.valueOf(( (ClubID.getValue())));
//        String eventname = eventName.getText();
//        String eventDate = String.valueOf((datePicker.getValue()));
//        String eventStartTime = (String.format("%02d:%02d", startHourSpinner.getValue(), startMinuteSpinner.getValue()));
//        String eventEndTime = (String.format("%02d:%02d", endHourSpinner.getValue(), endMinuteSpinner.getValue()));
//        String eventlocation = eventLoc.getText();
//        String eventdesc = eventDesc.getText();
//
//        Event event = new Event(eventid, clubid,eventname,eventDate,eventStartTime,eventEndTime,eventlocation,eventdesc);
        Event event = new Event();
        String clubid = String.valueOf(( (ClubID.getValue())));
        event.setEventID(eventID.getText());
        event.setClubId(clubid);
        event.setEventName(eventName.getText());
        event.setEventLocation(eventLoc.getText());
        event.setEstartTime(String.format("%02d:%02d", startHourSpinner.getValue(), startMinuteSpinner.getValue()));
        event.setEendTime(String.format("%02d:%02d", endHourSpinner.getValue(), endMinuteSpinner.getValue()));
        event.setEventDescription(eventDesc.getText());
        event.setSelectedDate(datePicker.getValue());
        event.setSelectedClubName(String.valueOf(clubChoiceBox.getValue()));




        DBQuery dbQuery = new DBQuery();

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
