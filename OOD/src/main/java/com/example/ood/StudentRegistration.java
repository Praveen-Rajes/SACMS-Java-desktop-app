package com.example.ood;

public class StudentRegistration extends Person {
    private int studentId;
//    private String studentFirstName;
//    private String studentLastName;
//    private String studentDOB;
//    private String studentGender;
//    private String studentAddress;
    private String studentGradeClass;
    private GuardianDetails GuardianDetails;

    public StudentRegistration(int studentId,String guardianName,int guardianPhone,String guardianEmail){
        this.studentId = studentId;
        GuardianDetails = new GuardianDetails(guardianName,guardianPhone,guardianEmail);
    }
    public StudentRegistration(){}

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

//    public String getStudentFirstName() {
//        return studentFirstName;
//    }
//
//    public void setStudentFirstName(String studentFirstName) {
//        this.studentFirstName = studentFirstName;
//    }
//
//    public String getStudentLastName() {
//        return studentLastName;
//    }
//
//    public void setStudentLastName(String studentLastName) {
//        this.studentLastName = studentLastName;
//    }
//
//    public String getStudentDOB() {
//        return studentDOB;
//    }
//
//    public void setStudentDOB(String studentDOB) {
//        this.studentDOB = studentDOB;
//    }
//
//    public String getStudentGender() {
//        return studentGender;
//    }
//
//    public void setStudentGender(String studentGender) {
//        this.studentGender = studentGender;
//    }
//
//    public String getStudentAddress() {
//        return studentAddress;
//    }
//
//    public void setStudentAddress(String studentAddress) {
//        this.studentAddress = studentAddress;
//    }

    public String getStudentGradeClass() {
        return studentGradeClass;
    }

    public void setStudentGradeClass(String studentGradeClass) {
        this.studentGradeClass = studentGradeClass;
    }

    //unique getId methd for each class
    @Override
    public int getId() {
        return getStudentId();
    }


}
