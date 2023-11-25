package com.example.ood;

public class AdvisorRegistration extends com.example.ood.Person {
    private int advisorId;
    private String advisorPassword = "abc123";
    private int advisorPhone;

    public AdvisorRegistration(int advisorId,String firstName,String lastName,String dateOfBirth,String gender,String address,int advisorPhone,String advisorPassword,String imagePath){
        super(firstName,lastName,dateOfBirth,gender,address,imagePath);
        this.advisorId=advisorId;
        this.advisorPassword = advisorPassword;
        this.advisorPhone=advisorPhone;
    }

    public AdvisorRegistration() {}

    public int getAdvisorId() {
        return advisorId;
    }

    public void setAdvisorId(int advisorId) {
        this.advisorId = advisorId;
    }

    public String getAdvisorPassword() {
        return advisorPassword;
    }

    public void setAdvisorPassword(String advisorPassword) {
        this.advisorPassword = advisorPassword;
    }

    public int getAdvisorPhone() {
        return advisorPhone;
    }

    public void setAdvisorPhone(int advisorPhone) {
        this.advisorPhone = advisorPhone;
    }

    //unique getId method for each class
    @Override
    public int getId() {
        return getAdvisorId();
    }
    @Override
    public String getPassword(){return getAdvisorPassword();}


}
