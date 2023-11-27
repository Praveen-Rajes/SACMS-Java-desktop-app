package com.example.ood;

public class GuardianDetails {
    private int studentID;
    private String guardianName;
    private int guardianPhone;
    private String guardianEmail;

//    public GuardianDetails(String guardianName, int guardianPhone, String guardianEmail) {
//        this.guardianName = guardianName;
//        this.guardianPhone = guardianPhone;
//        this.guardianEmail = guardianEmail;
//    }

    public GuardianDetails(int studentID,String guardianName,int guardianPhone,String guardianEmail){
        this.studentID = studentID;
        this.guardianName=guardianName;
        this.guardianPhone=guardianPhone;
        this.guardianEmail=guardianEmail;
    }
    public GuardianDetails() {}

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getGuardianName() {
        return guardianName;
    }

    public void setGuardianName(String guardianName) {
        this.guardianName = guardianName;
    }

    public int getGuardianPhone() {
        return guardianPhone;
    }

    public void setGuardianPhone(int guardianPhone) {
        this.guardianPhone = guardianPhone;
    }

    public String getGuardianEmail() {
        return guardianEmail;
    }

    public void setGuardianEmail(String guardianEmail) {
        this.guardianEmail = guardianEmail;
    }
}
