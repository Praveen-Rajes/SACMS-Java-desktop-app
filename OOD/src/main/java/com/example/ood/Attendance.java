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
    private String studentFName;
    private Event event;
    private Student student;

    public Attendance(String clubName, String eventName, String startTime, String endTime, String studentId, String studentName, Event event, Student student) {
        this.clubName = clubName;
        this.eventName = eventName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.studentId = studentId;
        this.studentFName = studentName;
        this.event = event;
        this.student = student;
    }

    public Attendance(String clubName, String eventName, String startTime, String endTime, String studentId, String studentFName) {
        this.clubName = clubName;
        this.eventName = eventName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.studentId = studentId;
        this.studentFName = studentFName;
    }

    public Attendance(Event event) {
        this.event = event;
    }

    public Attendance(Student student) {
        this.student = student;
    }

    public Attendance(String clubName, String studentID, String studentFName) {
        this.clubName = clubName;
        this.studentId = studentID;
        this.studentFName = studentFName;
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
        return studentFName;
    }

    public void setStudentName(String studentName) {
        this.studentFName = studentName;
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
    public ObservableValue<String> studentIDProperty() {
        return new SimpleStringProperty(studentId);
    }

    public ObservableValue<String> studentFNameProperty() {
        return new SimpleStringProperty(studentFName);
    }

    public ObservableValue<String> attendanceStatusProperty() {
        return null;
    }

    public void setAttendanceStatus(String selectedStatus) {
    }
}