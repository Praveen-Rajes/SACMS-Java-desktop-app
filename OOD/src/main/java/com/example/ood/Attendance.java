package com.example.ood;

import javafx.scene.control.CheckBox;

public class Attendance {

    private CheckBox attendance;
    private StudentRegistration student;


    public Attendance(CheckBox attendance) {

        this.attendance =new CheckBox();
    }

    public StudentRegistration getStudent() {
        return student;
    }

    public void setStudent(StudentRegistration student) {
        this.student = student;
    }

    public CheckBox getAttendance(){
        return attendance;
    }

    public void setAttendance(CheckBox attendance) {
        this.attendance = attendance;
    }

    public StudentRegistration createStudent(int studentId, String studentFirstName) {
        student = new StudentRegistration(studentId,studentFirstName);
        return student;
    }
}




