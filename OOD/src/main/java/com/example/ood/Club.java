package com.example.ood;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;

public class Club {
    private String name;
    private String category;
    private String description;
    private String clubID;
    private Image logoImage;

    public Club(String name, String clubID, String category, String description, Image logoImage) {
        this.name = name;
        this.clubID = clubID;
        this.category = category;
        this.description = description;
        this.logoImage = logoImage;
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