package com.example.ood;

import java.util.ArrayList;

public class StudentRegistration extends com.example.ood.Person {
    private int studentId;
    private String studentPassword = "abc123";
    private String studentGradeClass;
    private GuardianDetails guardian;
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
        this.guardian = new GuardianDetails();
    }

    public StudentRegistration(int studentId,String guardianName,int guardianPhone,String guardianEmail){
        this.studentId=studentId;
        guardian = new GuardianDetails(studentId,guardianName,guardianPhone,guardianEmail);
    }

    public GuardianDetails getGuardian() {
        return guardian;
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

    public void setGuardian(GuardianDetails guardian) {
        this.guardian = guardian;
    }
//    public void setGuardianDetails(String guardianName, int guardianPhone, String guardianEmail) {
//        this.guardian = new GuardianDetails(guardianName, guardianPhone, guardianEmail);
//    }

    //unique getId methd for each class
    @Override
    public int getId() {
        return getStudentId();
    }

    @Override
    public String getPassword(){return getStudentPassword();}

}
