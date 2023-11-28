package com.example.ood;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

import java.time.LocalDate;

public class Event {

    private String selectedClubName;
    private String selectedDate ;
    private String EventID ;
    private String EventName ;
    private String EventLocation ;
    private String EstartTime ;
    private String EendTime ;
    private String EventDescription;
    private String clubId;

    private String selectedClub;
    private String clubID;


    public Event(String eventName) {
        this.EventName=eventName;
    }





    public Event(String selectedClubName, LocalDate selectedDate, String eventID, String eventName, String eventLocation, String estartTime, String eendTime, String eventDescription) {
        this.selectedClubName = selectedClubName;
        this.selectedDate = String.valueOf(selectedDate);
        this.EventID = eventID;
        this.EventName = eventName;
        this.EventLocation = eventLocation;
        this.EstartTime = estartTime;
        this.EendTime = eendTime;
        this.EventDescription = eventDescription;
    }

    public Event( String eventID, String eventName, String eventLocation,String selectedDate, String estartTime, String eendTime, String eventDescription, String clubID) {

        EventID = eventID;
        EventName = eventName;
        EventLocation = eventLocation;
        this.selectedDate = String.valueOf(selectedDate);
        EstartTime = estartTime;
        EendTime = eendTime;
        EventDescription = eventDescription;
        this.clubID = clubID;
    }

    public Event() {
    }

    public String getSelectedClubName() {
        return selectedClubName;
    }

    public void setSelectedClubName(String selectedClubName) {
        this.selectedClubName = selectedClubName;
    }


    public String getClubId() {

        return clubId;
    }

    public void setClubID(String clubId) {
        this.clubID = clubID;
    }

    public String getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(LocalDate selectedDate) {
        this.selectedDate = String.valueOf(selectedDate);
    }

    public String getEventID() {
        return EventID;
    }

    public void setEventID(String eventID) {
        EventID = eventID;
    }

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }

    public String getEventLocation() {
        return EventLocation;
    }

    public void setEventLocation(String eventLocation) {
        EventLocation = eventLocation;
    }

    public String getEstartTime() {
        return EstartTime;
    }

    public void setEstartTime(String estartTime) {
        EstartTime = estartTime;
    }

    public String getEendTime() {
        return EendTime;
    }

    public void setEendTime(String eendTime) {
        EendTime = eendTime;
    }

    public String getEventDescription() {
        return EventDescription;
    }

    public void setEventDescription(String eventDescription) {
        EventDescription = eventDescription;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    public String getClubID() {
        return clubID;
    }

    public ObservableValue<String> eventIDProperty() {
        return new SimpleStringProperty(EventID);
    }

    public ObservableValue<String> eventNameProperty() {
        return new SimpleStringProperty(EventName);
    }

    public ObservableValue<String> clubProperty() {
        return new SimpleStringProperty();
    }
}

