package com.example.ood;

public class Student {
    private final String studentID;
    private final String studentName;
    private String attendance;

    public Student(String studentID, String studentName) {
        this.studentID = studentID;
        this.studentName = studentName;
        this.attendance = attendance;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }
}




