package com.example.ood;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
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
        s1.setStudentFirstName(studentFirstName);
        System.out.println(s1.getStudentId());
    }
    @FXML
    protected void onSubmitButton2Click(){ AdvisorRegistration c1 = new AdvisorRegistration();
        int advisorId = Integer.parseInt(advisorIdField.getText());
        c1.setAdvisorId(advisorId);
        System.out.println(c1.getAdvisorId());}
    @FXML
    protected void onStudentIdFill(){
//        StudentRegistration s1 = new StudentRegistration();
//        int studentId = Integer.parseInt(studentIdField.getText());
//        s1.setStudentId(studentId);
//        System.out.println(s1.getStudentId());
    }

    @FXML
    protected void onSelectImageButtonClick(ActionEvent e){
        // Open a file chooser to let the user select an image file
        FileChooser choosingImage = new FileChooser();
        choosingImage.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        File selectedFile = choosingImage.showOpenDialog(stage);

        if (selectedFile != null) {
            userImage = selectedFile.toURI().toString();
            // Create an Image object with the selected file
            Image image = new Image(userImage);

            // Set the image to the ImageView
            studentImageField.setImage(image);
            System.out.println(userImage);
        }

    }
    @FXML
    protected void onSelectImageButton2Click(ActionEvent e1){
        // Open a file chooser to let the user select an image file
        FileChooser choosingImage2 = new FileChooser();
        choosingImage2.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );

        Stage stage = (Stage) ((Node) e1.getSource()).getScene().getWindow();
        File selectedFile = choosingImage2.showOpenDialog(stage);

        if (selectedFile != null) {
            userImage2 = selectedFile.toURI().toString();
            // Create an Image object with the selected file
            Image image2 = new Image(userImage2);

            // Set the image to the ImageView
            advisorImageField.setImage(image2);
            System.out.println(userImage2);
        }

    }



}
