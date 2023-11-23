package com.example.ood;

public class StudentRegistration extends com.example.ood.Person {
    private int studentId;
    private String studentGradeClass;
    private GuardianDetails GuardianDetails;

    public StudentRegistration(int studentId,String firstName,String lastName,String dateOfBirth,String gender,String address,String studentGradeClass){
        super(firstName,lastName,dateOfBirth,gender,address);
        this.studentId=studentId;
        this.studentGradeClass=studentGradeClass;
    }

    public StudentRegistration() {
        super();
    }


    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
    public String getStudentGradeClass(){
        return studentGradeClass;
    }
    public void setStudentGradeClass(String studentGradeClass){
        this.studentGradeClass=studentGradeClass;
    }

    //unique getId methd for each class
    @Override
    public int getId() {
        return getStudentId();
    }


}
