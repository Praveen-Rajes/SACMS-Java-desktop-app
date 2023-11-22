package com.example.ood;

public class GuardianDetails {

    private String studentGuardianName;
    private int studentGuardianPhone;
    private String studentGuardianEmail;

    public GuardianDetails(String studentGuardianName, int studentGuardianPhone, String studentGuardianEmail) {
        this.studentGuardianName = studentGuardianName;
        this.studentGuardianPhone = studentGuardianPhone;
        this.studentGuardianEmail = studentGuardianEmail;
    }

    public String getStudentGuardianName() {
        return studentGuardianName;
    }

    public void setStudentGuardianName(String studentGuardianName) {
        this.studentGuardianName = studentGuardianName;
    }

    public int getStudentGuardianPhone() {
        return studentGuardianPhone;
    }

    public void setStudentGuardianPhone(int studentGuardianPhone) {
        this.studentGuardianPhone = studentGuardianPhone;
    }

    public String getStudentGuardianEmail() {
        return studentGuardianEmail;
    }

    public void setStudentGuardianEmail(String studentGuardianEmail) {
        this.studentGuardianEmail = studentGuardianEmail;
    }
}
