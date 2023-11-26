package com.example.ood;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Club {
    private AdvisorRegistration advisor;
    private String name;
    private String category;
    private String description;
    private Color theme;
    private String clubID;
    private Image logoImage;
    private static String imagePath;
    private int advisorId;



    public AdvisorRegistration getAdvisor() {
        return advisor;
    }


    public Club(String name, String clubID, String category, String description, Color theme, Image logoImage, int advisorId) {
        this.name = name;
        this.clubID = clubID;
        this.category = category;
        this.description = description;
        this.theme = theme;
        this.logoImage = logoImage;
        this.advisor = new AdvisorRegistration(advisorId);
    }

    public Club(String name, String clubID, String category, String description, Color theme, Image logoImage) {
        this.name = name;
        this.clubID = clubID;
        this.category = category;
        this.description = description;
        this.theme = theme;
        this.logoImage = logoImage;
    }

    public Club(String name, String clubID, String category) {
        this.name = name;
        this.clubID = clubID;
        this.category = category;
    }

    public Club(String clubID, String name) {
        this.clubID = clubID;
        this.name = name;
    }

    public Club(String name) {
        this.name = name;
    }

    public int getAdvisorId() {
        if (advisor != null) {
            return advisor.getAdvisorId();
        } else {
            // Handle the case where advisor is null
            return 0; // Or throw an exception, depending on your requirements
        }
    }
    public void setAdvisorId(int advisorId) {
        this.advisorId = advisorId;
    }


    public void setAdvisor(AdvisorRegistration advisor) {
        this.advisor = advisor;
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

    public Color getTheme() {
        return theme;
    }

    public void setTheme(Color theme) {
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


    public String getImagePath() {
        return imagePath;
    }

    public static void setImagePath(String imagePath) {
        Club.imagePath = imagePath;
    }

    public String getThemeHex() {
        return String.format("#%02X%02X%02X",
                (int) (theme.getRed() * 255),
                (int) (theme.getGreen() * 255),
                (int) (theme.getBlue() * 255));
    }
    @Override
    public String toString() {
        return "Club{" +
                "clubID='" + clubID + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                '}';
    }



}
