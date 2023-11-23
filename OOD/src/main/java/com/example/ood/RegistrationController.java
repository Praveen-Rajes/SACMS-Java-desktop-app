package com.example.ood;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class RegistrationController {

    @FXML
    private Button submitButton;
    @FXML
    private Button submitButton2;
    @FXML
    private TextField studentIdField;
    @FXML
    private TextField studentFNameField;
    @FXML
    private TextField advisorIdField;
    @FXML
    private Button selectimagebutton;
    @FXML
    private Button selectimagebutton2;
    @FXML
    private TextField advisorFNameField;

    private Image studentImage;
    private Image advisorImage;
    @FXML
    private ImageView studentImageField;
    @FXML
    private ImageView advisorImageField;
    private String userImage = "";
    private String userImage2 = "";
    @FXML
    protected void onSubmitButtonClick(){
        StudentRegistration s1 = new StudentRegistration();
        int studentId = Integer.parseInt(studentIdField.getText());
        String studentFirstName = studentFNameField.getText();
        s1.setStudentId(studentId);
        s1.setFirstName(studentFirstName);
        System.out.println(s1.getStudentId());
        System.out.println(s1.getFirstName());
    }
    @FXML
    protected void onSubmitButton2Click(){
        AdvisorRegistration c1 = new AdvisorRegistration();
        int advisorId = Integer.parseInt(advisorIdField.getText());
        String advisorFirstName = advisorFNameField.getText();
        c1.setAdvisorId(advisorId);
        c1.setFirstName(advisorFirstName);
        System.out.println(c1.getAdvisorId());
        System.out.println(c1.getFirstName());}
    @FXML
    protected void onStudentIdFill(){
//        StudentRegistration s1 = new StudentRegistration();
//        int studentId = Integer.parseInt(studentIdField.getText());
//        s1.setStudentId(studentId);
//        System.out.println(s1.getStudentId());
    }

    @FXML
    protected void onSelectImageButtonClick(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        Stage stage = (Stage) selectimagebutton.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null && file.exists()) {
            studentImage = new Image(file.toURI().toString());
            studentImageField.setImage(studentImage);
            String studentId = studentIdField.getText();

            // Corrected the destinationFilePath creation
            Path destinationDirectory = Paths.get("OOD", "src", "main", "resources", "studentImages");
            Path destinationFilePath = destinationDirectory.resolve(studentId + ".jpg");

            try {
                // Create the directory if it doesn't exist
                if (!Files.exists(destinationDirectory)) {
                    Files.createDirectories(destinationDirectory);
                }

                // Copy the file to the destination
                Files.copy(file.toPath(), destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File copied to: " + destinationFilePath);
                removeText();
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception appropriately, e.g., show an error message to the user
            }

        } else {
            System.err.println("Selected file is null or does not exist.");
        }
    }
    @FXML
    protected void onSelectImageButton2Click(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        Stage stage = (Stage) selectimagebutton2.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null && file.exists()) {
            advisorImage = new Image(file.toURI().toString());
            advisorImageField.setImage(advisorImage);
            String advisorId = advisorIdField.getText();

            // Corrected the destinationFilePath creation
            Path destinationDirectory = Paths.get("OOD", "src", "main", "resources", "advisorImages");
            Path destinationFilePath = destinationDirectory.resolve(advisorId + ".jpg");

            try {
                // Create the directory if it doesn't exist
                if (!Files.exists(destinationDirectory)) {
                    Files.createDirectories(destinationDirectory);
                }

                // Copy the file to the destination
                Files.copy(file.toPath(), destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File copied to: " + destinationFilePath);
                removeText();
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception appropriately, e.g., show an error message to the user
            }

        } else {
            System.err.println("Selected file is null or does not exist.");
        }

    }
    private void removeText() {
        Pane imagePane = (Pane) studentImageField.getParent();
        imagePane.getChildren().removeIf(node -> node instanceof FontIcon || node instanceof Text);
    }
    @FXML
    protected void onLogoutButtonClick(ActionEvent e2)throws IOException {
        Stage previousStage = (Stage) ((Node) e2.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("student_login.fxml"));
        previousStage.setScene(new Scene(root, 1200, 750));
    }


    @FXML
    protected void onClubButttonClick(ActionEvent actionEvent) throws IOException{
        Stage previousStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("JoinClub.fxml"));
        previousStage.setScene(new Scene(root, 1200, 750));
    }
}
