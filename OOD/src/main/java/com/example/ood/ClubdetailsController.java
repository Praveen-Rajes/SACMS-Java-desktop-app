// ClubdetailsController.java
package com.example.ood;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Random;

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
    private ColorPicker ThemeField;
    @FXML
    private ImageView logoImageView;
    @FXML
    private Button uploadButton;
    @FXML
    private Button continueButton;
    @FXML
    private Button backButton;
    @FXML
    private Label alert;
    private Image selectedImage;
    private static int loggedInAdvisorId;
    private ClubHomeController homeController;



    public void setHomeController(ClubHomeController homeController) {
        this.homeController = homeController;
    }

    public static void setLoggedInAdvisorId(int advisorId) {
        ClubdetailsController.loggedInAdvisorId = advisorId;

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
        if(validateInput()) {
            String clubName = clubNameTextField.getText().trim();
            String category = categoryChoiceBox.getValue().trim();
            String description = descriptionTextArea.getText().trim();
            Color theme = ThemeField.getValue();
            String clubID = clubIDField.getText().trim();


            Club club = new Club(clubName, clubID, category, description, theme, selectedImage, loggedInAdvisorId);

            if (selectedImage != null) {
                club.setLogoImage(selectedImage);
            }

            DBQuery dbQuery = new DBQuery();
            dbQuery.addClub(club);
            homeController.addClubDetail(club);

            Stage stage = (Stage) continueButton.getScene().getWindow();

            // Close the stage
            stage.close();
        }
    }
    private boolean isClubIdExists(String clubID) {
        // Implement your logic to check if the clubId exists in the database
        // You may need to query the database or use some other method based on your DB implementation
        // For demonstration purposes, assuming a method `checkClubIdExists` in DBQuery class
        DBQuery dbQuery = new DBQuery();
        return dbQuery.checkClubIdExists(clubID);
    }
    private boolean validateInput() {
        String clubName = clubNameTextField.getText().trim();
        String category = categoryChoiceBox.getValue().trim();
        String description = descriptionTextArea.getText().trim();
        String clubID = clubIDField.getText().trim();

        // Add your validation logic here
        if (clubName == null || clubName.trim().isEmpty()) {
            alert.setText("Please enter a valid club name.");
            return false;
        }

        if (category == null || category.trim().isEmpty()) {
            alert.setText("Please select a valid category.");
            return false;
        }
        if(emptyFields(clubName,category,description,clubID)){
            alert.setText("Please fill required fields");
            return false;
        }

        if (description.length() > 1000) {
            alert.setText("Description should be less than 1000 characters.");
            return false;
        }
        // Check if the clubId already exists in the database
        if (isClubIdExists(clubID)) {
            Random random = new Random();
            System.out.println("Club ID already exists in the database.");
            alert.setText("Club ID changed! Press Continue.");
            String currentclubid = clubIDField.getText();
            int randomInRange = random.nextInt(100); // Example: generates a random number between 0 and 99
            clubIDField.setText(randomInRange + "_" + currentclubid);
            // You may want to show a message to the user or take other actions
            return false;
        }

        // Add more validation as needed

        return true;
    }
    public static boolean emptyFields(String clubName, String category, String description, String  clubID){
        return (clubID.isEmpty() ||
                category.isEmpty() ||
                description.isEmpty() ||
                clubName.isEmpty());
    }

    public void onUploadButtonClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        Stage stage = (Stage) uploadButton.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null && file.exists()) {
            selectedImage = new Image(file.toURI().toString());
            logoImageView.setImage(selectedImage);
            String clubID = clubIDField.getText();

            // Corrected the destinationFilePath creation
            Path destinationDirectory = Paths.get("OOD", "src", "main", "resources", "LogoImages");
            Path destinationFilePath = destinationDirectory.resolve(clubID + ".jpg");

            try {
                // Create the directory if it doesn't exist
                if (!Files.exists(destinationDirectory)) {
                    Files.createDirectories(destinationDirectory);
                }

                // Copy the file to the destination
                Files.copy(file.toPath(), destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File copied to: " + destinationFilePath);
                Club.setImagePath(destinationFilePath.toString());
                removeText();
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception appropriately, e.g., show an error message to the user
            }

        } else {
            System.err.println("Selected file is null or does not exist.");
        }
    }


    private void onImageDragDropped(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        boolean success = false;

        if (dragboard.hasFiles()) {
            File file = dragboard.getFiles().get(0);

            selectedImage = new Image(file.toURI().toString());
            logoImageView.setImage(selectedImage);
            success = true;
            String clubID = clubIDField.getText();

            // Corrected the destinationFilePath creation
            Path destinationDirectory = Paths.get("OOD", "src", "main", "resources", "LogoImages");
            Path destinationFilePath = destinationDirectory.resolve(clubID + ".jpg");
            Club.setImagePath(destinationFilePath.toString());

            try {
                // Create the directory if it doesn't exist
                if (!Files.exists(destinationDirectory)) {
                    Files.createDirectories(destinationDirectory);
                }

                // Copy the file to the destination
                Files.copy(file.toPath(), destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File copied to: " + destinationFilePath);
                Club.setImagePath(destinationFilePath.toString());
                removeText();
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception appropriately, e.g., show an error message to the user
            }
            event.setDropCompleted(success);
            event.consume();

        } else {
            System.err.println("Selected file is null or does not exist.");
        }
    }

    private void onImageDragOver(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY);
        }
        event.consume();
    }



    private void removeText() {
        Pane imagePane = (Pane) logoImageView.getParent();
        imagePane.getChildren().removeIf(node -> node instanceof FontIcon || node instanceof Text);
    }

    @FXML
    private void onBackButtonClick() {
        Stage stage = (Stage) backButton.getScene().getWindow();

        // Close the stage
        stage.close();
    }
}