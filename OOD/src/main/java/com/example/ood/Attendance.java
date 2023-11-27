package com.example.ood;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;

public class Attendance {
    private CheckBox attendance;
    private StudentRegistration student;
    private Club club;
    private Addevent addEvent;
    private String ClubName;



    public Attendance(CheckBox attendance, StudentRegistration student, Club club, Addevent addEvent, String ClubName) {
        this.attendance = attendance;
        this.student = student;
        this.club = club;
        this.addEvent = addEvent;
        this.ClubName = ClubName;
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

    public StudentRegistration getStudent() {
        return student;
    }

    public void setStudent(StudentRegistration student) {
        this.student = student;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Addevent getAddEvent() {
        return addEvent;
    }

    public void setAddEvent(Addevent addEvent) {
        this.addEvent = addEvent;
    }

    public ObservableValue<String> clubNameProperty() {
        return new SimpleStringProperty(ClubName);
    }
}
