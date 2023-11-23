package com.example.ood;

public class AdvisorRegistration extends com.example.ood.Person {
    private int advisorId;
    private int advisorPhone;

    public AdvisorRegistration(int advisorId,String firstName,String lastName,String dateOfBirth,String gender,String address,int advisorPhone){
        super(firstName,lastName,dateOfBirth,gender,address);
        this.advisorId=advisorId;
        this.advisorPhone=advisorPhone;
    }

    public AdvisorRegistration() {}

    public int getAdvisorId() {
        return advisorId;
    }

    public void setAdvisorId(int advisorId) {
        this.advisorId = advisorId;
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


}
