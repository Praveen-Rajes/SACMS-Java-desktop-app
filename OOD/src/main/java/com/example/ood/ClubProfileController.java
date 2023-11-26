package com.example.ood;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;


public class ClubProfileController {
    @FXML
    private Button closeButton;
    @FXML
    private Text clubProfileTitle;
    @FXML
    private ImageView clubImageView;
    @FXML
    private TextField clubNameField;
    @FXML
    private TextField categoryField;

    @FXML
    private TextArea descriptionArea;
    @FXML
    private TextField clubIDField;
    @FXML
    private Button removeClubButton;


    private ClubHomeController homeController; // Reference to the HomeController


    private Club selectedClub;


    public void setClubDetails(Club club) throws FileNotFoundException {
        selectedClub = club;

        // Set club details to the fields
        clubNameField.setText(club.getName());
        categoryField.setText(club.getCategory());
        descriptionArea.setText(club.getDescription());
        clubIDField.setText(club.getClubID());
        String imagePath = "OOD/src/main/resources/logoImages/" + club.getClubID() + ".jpg";
        Image image = new Image(new FileInputStream(imagePath));
        clubImageView.setImage(image);
    }
    @FXML
    private void OnRemoveButtonClick() throws FileNotFoundException {
        // Display a confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Remove Club");
        alert.setContentText("Are you sure you want to remove this club?");

        // Wait for the user's response
        Optional<ButtonType> result = alert.showAndWait();

        // If the user presses OK, remove the club
        if (result.isPresent() && result.get() == ButtonType.OK) {

            // Remove from HomeController table
            homeController.removeClub(selectedClub);

            // Remove from the database (you need to implement this method in DBQuery)
            DBQuery dbQuery = new DBQuery();
            dbQuery.removeClub(selectedClub);


        }

    }

    public void setHomeController(ClubHomeController homeController) {
        this.homeController = homeController;
    }
}