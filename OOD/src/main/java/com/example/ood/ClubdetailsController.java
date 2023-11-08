// ClubdetailsController.java
package com.example.ood;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;

public class ClubdetailsController {

    @FXML
    private TextField clubNameTextField;
    @FXML
    private ChoiceBox<String> categoryChoiceBox;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private TextField clubIDField;
    @FXML
    private ImageView logoImageView;
    @FXML
    private Button uploadButton;
    @FXML
    private Button continueButton;
    @FXML
    private Button closeButton;
    private Image selectedImage;
    private ClubHomeController homeController;

    public void setHomeController(ClubHomeController homeController) {
        this.homeController = homeController;
    }

    public void initialize() {
        categoryChoiceBox.getItems().addAll("Sports", "Political", "Cultural", "Academic", "Technological");

        continueButton.setOnAction(event -> onOkButtonClick());
        logoImageView.setOnDragOver(this::onImageDragOver);
        logoImageView.setOnDragDropped(this::onImageDragDropped);

        // Add listeners to clubNameTextField and categoryChoiceBox
        clubNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateClubID();
        });

        categoryChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            updateClubID();
        });

        // Call updateClubID
        updateClubID();
    }

    private void updateClubID() {
        String clubName = clubNameTextField.getText();
        String category = categoryChoiceBox.getValue();

        if (clubName != null && !clubName.trim().isEmpty() && category != null && !category.trim().isEmpty()) {
            // Generate the club ID and display it in the clubIDField
            String generatedClubID = generateClubID(clubName, category);
            clubIDField.setText(generatedClubID);
        } else {

            clubIDField.setText("Enter Club Name and Category to generate a club ID");
        }
    }


    private String generateClubID(String clubName, String category) {
        if (clubName != null && !clubName.isEmpty() && category != null && !category.isEmpty()) {
            String[] words = clubName.split(" ");
            String clubNameAbbreviation;

            if (words.length == 1) {
                // Get the first two letters of the word
                clubNameAbbreviation = clubName.substring(0, Math.min(2, clubName.length())).toUpperCase();
            } else if (words.length == 2) {
                // Get the first letter of each word
                String firstWordAbbreviation = words[0].substring(0, 1).toUpperCase();
                String secondWordAbbreviation = words[1].substring(0, 1).toUpperCase();
                clubNameAbbreviation = firstWordAbbreviation + secondWordAbbreviation;
            } else {
                //word length is more than 2
                StringBuilder clubID = new StringBuilder();

                for (String word : words) {
                    if (!word.isEmpty()) {
                        clubID.append(word.substring(0, 1));
                    }
                }
                return clubID.toString().toUpperCase() + "_" + category.substring(0, 2).toUpperCase();

            }

            String categoryAbbreviation = category.substring(0, Math.min(2, category.length())).toUpperCase();
            return clubNameAbbreviation + "_" + categoryAbbreviation;
        } else {
            return "Invalid ID";
        }
    }


    public void onOkButtonClick() {
        String clubName = clubNameTextField.getText();
        String category = categoryChoiceBox.getValue();
        String description = descriptionTextArea.getText();

        String clubID = clubIDField.getText();

        Club club = new Club(clubName, clubID, category, description, selectedImage);

        if (selectedImage != null) {
            club.setLogoImage(selectedImage);
        }

        homeController.addClubDetail(club);

        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void onUploadButtonClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        Stage stage = (Stage) uploadButton.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            int requiredWidth = 261;
            int requiredHeight = 250;

            if (image.getWidth() == requiredWidth && image.getHeight() == requiredHeight) {
                selectedImage = new Image(file.toURI().toString(), 200, 200, true, true);
                logoImageView.setFitHeight(200);
                logoImageView.setFitWidth(200);
                logoImageView.setImage(selectedImage);
                removeText();
            } else {
                System.out.println("Image does not meet the required dimensions.");
            }
        }
    }

    private void onImageDragOver(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY);
        }
        event.consume();
    }

    private void onImageDragDropped(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        boolean success = false;

        if (dragboard.hasFiles()) {
            File file = dragboard.getFiles().get(0);
            Image image = new Image(file.toURI().toString());
            int requiredWidth = 261;
            int requiredHeight = 250;

            if (image.getWidth() == requiredWidth && image.getHeight() == requiredHeight) {
                selectedImage = new Image(file.toURI().toString(), 200, 200, true, true);
                logoImageView.setFitHeight(200);
                logoImageView.setFitWidth(200);
                logoImageView.setImage(selectedImage);
                success = true;
                removeText();
            } else {
                System.out.println("Image does not meet the required dimensions.");
            }
        }

        event.setDropCompleted(success);
        event.consume();
    }

    private void removeText() {
        Pane imagePane = (Pane) logoImageView.getParent();
        imagePane.getChildren().removeIf(node -> node instanceof FontIcon || node instanceof Text);
    }
    @FXML
    private void onCloseButtonClick() {
        Stage stage = (Stage) closeButton.getScene().getWindow();

        // Close the stage
        stage.close();
    }

}