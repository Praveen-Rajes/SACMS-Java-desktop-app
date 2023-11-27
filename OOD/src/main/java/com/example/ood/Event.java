package com.example.ood;

import java.time.LocalDate;

public class Event {
    private String selectedClubName;
    private LocalDate selectedDate ;
    private String EventID ;
    private String EventName ;
    private String EventLocation ;
    private String EstartTime ;
    private String EendTime ;
    private String EventDescription;

    public Event(String selectedClubName, LocalDate selectedDate, String eventID, String eventName, String eventLocation, String estartTime, String eendTime, String eventDescription) {
        this.selectedClubName = selectedClubName;
        this.selectedDate = selectedDate;
        EventID = eventID;
        EventName = eventName;
        EventLocation = eventLocation;
        EstartTime = estartTime;
        EendTime = eendTime;
        EventDescription = eventDescription;
    }

    public String getSelectedClubName() {
        return selectedClubName;
    }

    public void setSelectedClubName(String selectedClubName) {
        this.selectedClubName = selectedClubName;
    }

    public LocalDate getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(LocalDate selectedDate) {
        this.selectedDate = selectedDate;
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
}
