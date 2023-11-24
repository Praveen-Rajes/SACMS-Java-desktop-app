package com.example.ood;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.Image;

public class Club {
    private AdvisorRegistration advisor;
    private String name;
    private String category;
    private String description;
    private ColorPicker theme;
    private String clubID;
    private Image logoImage;

    public Club(AdvisorRegistration advisor, String name, String category, String description, ColorPicker theme, String clubID, Image logoImage) {
        this.advisor = advisor;
        this.name = name;
        this.category = category;
        this.description = description;
        this.theme = theme;
        this.clubID = clubID;
        this.logoImage = logoImage;
    }

    public Club(String name, String clubID, String category, String description, ColorPicker theme, Image logoImage) {
        this.name = name;
        this.clubID = clubID;
        this.category = category;
        this.description = description;
        this.theme = theme;
        this.logoImage = logoImage;
    }

    public int getAdvisorId() {
        return advisor.getAdvisorId();
    }



    public void setAdvisor(AdvisorRegistration advisor) {
        this.advisor = advisor;
    }

    public Club(String name, String clubID, String category) {
        this.name = name;
        this.clubID = clubID;
        this.category = category;

    }
    public Club(String clubID,String name) {
        this.clubID = clubID;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClubID() {
        return clubID;
    }

    public void setClubID(String clubID) {
        this.clubID = clubID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ColorPicker getTheme() {
        return theme;
    }

    public void setTheme(ColorPicker theme) {
        this.theme = theme;
    }

    public Image getLogoImage() {
        return logoImage;
    }

    public void setLogoImage(Image logoImage) {
        this.logoImage = logoImage;
    }

    public ObservableValue<String> clubIDProperty() {
        return new SimpleStringProperty(clubID);
    }

    public ObservableValue<String> clubNameProperty() {
        return new SimpleStringProperty(name);
    }

    public ObservableValue<String> categoryProperty() {
        return new SimpleStringProperty(category);
    }
}