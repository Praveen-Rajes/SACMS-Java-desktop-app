package com.example.ood;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;

public class Attendance {
    private CheckBox attendance;
    private Student student;
    private Club club;
    private String ClubName;
    private Event event;
    private String EventName;
    private String EstartTime;
    private String EendTime;

    public Attendance(CheckBox attendance, Student student, Club club, String ClubName, Event event, String EventName, String EstartTime, String EendTime) {
        this.attendance = attendance;
        this.student = student;
        this.club = club;
        this.ClubName = ClubName;
        this.event = event;
        this.EventName = EventName;
        this.EstartTime = EstartTime;
        this.EendTime = EendTime;
    }

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
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

    public Attendance(Event event) {
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Attendance(String clubName) {
        ClubName = clubName;
    }

    public String getClubName() {
        return ClubName;
    }

    public void setClubName(String clubName) {
        ClubName = clubName;
    }

    public CheckBox getAttendance() {
        return attendance;
    }

    public void setAttendance(CheckBox attendance) {
        this.attendance = attendance;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public ObservableValue<String> clubNameProperty() {
        return new SimpleStringProperty(ClubName);
    }

    public String getEventStartTime() {
        return event.getEstartTime();
    }

    public String getEventEndTime() {
        return event.getEendTime();
    }
}