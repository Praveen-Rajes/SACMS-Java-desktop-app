package com.example.ood;

import java.util.ArrayList;

public class StudentRegistration extends com.example.ood.Person {
    private int studentId;
    private String studentPassword = "abc123";
    private String studentGradeClass;
    private GuardianDetails guardianDetails;
    private ArrayList<Club> clublist = new ArrayList<>();

    public StudentRegistration(int studentId,String firstName,String lastName,String dateOfBirth,String gender,String address,String studentGradeClass,String studentPassword,String imagePath){
        super(firstName,lastName,dateOfBirth,gender,address,imagePath);
        this.studentId=studentId;
        this.studentPassword=studentPassword;
        this.studentGradeClass=studentGradeClass;
    }
    public StudentRegistration(int studentId,ArrayList<Club> clublist){
        this.studentId =studentId;
        this.clublist = clublist;
    }
    public ArrayList<Club> getClublist(){
        return clublist;
    }
    public void setClublist(ArrayList<Club> clublist){
        this.clublist=clublist;
    }
    public void joinClub(Club c){
        clublist.add(c);
    }
    public void leaveClub(Club c){
        clublist.remove(c);
    }

    public StudentRegistration() {
        super();
    }

    public StudentRegistration(int studentId,String guardianName,int guardianPhone,String guardianEmail){
        this.studentId=studentId;
        guardianDetails = new GuardianDetails(guardianName,guardianPhone,guardianEmail);
    }

    public GuardianDetails getGuardianDetails() {
        return guardianDetails;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentPassword() {
        return studentPassword;
    }

    public void setStudentPassword(String studentPassword) {
        this.studentPassword = studentPassword;
    }

    public String getStudentGradeClass(){
        return studentGradeClass;
    }
    public void setStudentGradeClass(String studentGradeClass){
        this.studentGradeClass=studentGradeClass;
    }

    public void setGuardianDetails(GuardianDetails guardianDetails) {
        this.guardianDetails = guardianDetails;
    }

    //unique getId methd for each class
    @Override
    public int getId() {
        return getStudentId();
    }

    @Override
    public String getPassword(){return getStudentPassword();}

}
