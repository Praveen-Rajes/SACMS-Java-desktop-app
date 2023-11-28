package com.example.ood;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;

public class Attendance{
    private String clubName;
    private String eventName;
    private String startTime;
    private String endTime;
    private String studentId;
    private String studentName;
    private Event event;
    private Student student;

    public Attendance(String clubName, String eventName, String startTime, String endTime, String studentId, String studentName, Event event, Student student) {
        this.clubName = clubName;
        this.eventName = eventName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.studentId = studentId;
        this.studentName = studentName;
        this.event = event;
        this.student = student;
    }

    public Attendance(String clubName, String eventName, String startTime, String endTime, String studentId, String studentName) {
        this.clubName = clubName;
        this.eventName = eventName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.studentId = studentId;
        this.studentName = studentName;
    }

    public Attendance(Event event) {
        this.event = event;
    }

    public Attendance(Student student) {
        this.student = student;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Attendance(String clubName) {
        this.clubName = clubName;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    public ObservableValue<String> eventNameProperty() {
        return new SimpleStringProperty(eventName);
    }

    public ObservableValue<String> startTimeProperty() {
        return new SimpleStringProperty(startTime);
    }

    public ObservableValue<String> endTimeProperty() {
        return new SimpleStringProperty(endTime);
    }
}