package com.example.ood;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import static com.example.ood.DBQuery.getConnection;

public class RegistrationController {
    @FXML
    private Label errormsg;

    @FXML
    private TextField studentIdField;
    @FXML
    private TextField studentFNameField;
    @FXML
    private TextField studentLNameField;
    @FXML
    private DatePicker studentDOBField;
    @FXML
    private TextField studentGenderField;
    @FXML
    private TextField studentAddressField;
    @FXML
    private TextField studentGradeClassField;

    @FXML
    private TextField studentGuardianNameField;
    @FXML
    private TextField studentGuardianPhoneField;
    @FXML
    private TextField studentGuardianEmailField;

    @FXML
    private TextField advisorIdField;
    @FXML
    private TextField advisorFNameField;
    @FXML
    private TextField advisorLNameField;
    @FXML
    private TextField advisorDOBField;
    @FXML
    private TextField advisorGenderField;
    @FXML
    private TextField advisorAddressField;
    @FXML
    private TextField advisorPhoneField;

    @FXML
    private Button selectimagebutton;
    @FXML
    private Button selectimagebutton2;
    private Image studentImage;
    private Image advisorImage;
    @FXML
    private ImageView studentImageField;
    @FXML
    private ImageView advisorImageField;
    @FXML
    private TextField studentLoginIdField;
    @FXML
    private TextField studentPasswordField;
    public static int loggedInStudentId;

    @FXML
    public Label studentIdLabel;
    @FXML
    public Label studentFNameLabel;
    @FXML
    public Label studentLNameLabel;
    @FXML
    public Label studentDOBLabel;
    @FXML
    public Label studentGenderLabel;
    @FXML
    public ImageView studentImageView;

    @FXML
    public  TableView<Club> allclubtableview;
    @FXML
    private TableColumn<Club, String> clubIDColumn;
    @FXML
    private TableColumn<Club, String> clubNameColumn;
    @FXML
    private TableColumn<Club, String> clubCategoryColumn;
    @FXML
    private TableColumn<Club, String> clubDescriptionColumn;
    private ObservableList<Club> clubList = FXCollections.observableArrayList();

    @FXML
    private TableView<Club> studentclubtableview;
    @FXML
    private TableColumn<Club, String> iDColumn;
    @FXML
    private TableColumn<Club, String> nameColumn;
    private ObservableList<Club> studentClubList = FXCollections.observableArrayList();

    @FXML
    private ChoiceBox<String> joinclubchoicebox;
    @FXML
    private ChoiceBox<String> leaveclubchoicebox;


    public void setHelloController (HelloController helloController){this.helloController = helloController;}
    private HelloController helloController;

    private void populateChoiceBox() {
        // Connect to your database (replace the placeholders with your database details)
        try (Connection connection = getConnection()) {
            // SQL query to fetch data from your database table
            String query = "SELECT c.clubID FROM club c";

            // Create a PreparedStatement
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                // Create an ObservableList to hold the items for the ChoiceBox
                ObservableList<String> items = FXCollections.observableArrayList();

                // Iterate through the result set and add each value to the ObservableList
                while (resultSet.next()) {
                    String value = resultSet.getString("clubID");
                    items.add(value);
                }

                // Set the items in the ChoiceBox
                joinclubchoicebox.setItems(items);
                leaveclubchoicebox.setItems(items);

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void setLoggedInStudentId(int studentId) {
        RegistrationController.loggedInStudentId = studentId;
    }

    public void getAllClubsList() {
        allclubtableview.refresh();
        allclubtableview.getColumns().clear();

        // Define columns
        TableColumn<Club, String> clubIDColumn = new TableColumn<>("Club ID");
        clubIDColumn.setCellValueFactory(data -> data.getValue().clubIDProperty());

        TableColumn<Club, String> clubNameColumn = new TableColumn<>("Club Name");
        clubNameColumn.setCellValueFactory(data -> data.getValue().clubNameProperty());

        TableColumn<Club, String> clubCategoryColumn = new TableColumn<>("Category");
        clubCategoryColumn.setCellValueFactory(data -> data.getValue().categoryProperty());

        TableColumn<Club, String> clubDescriptionColumn = new TableColumn<>("Description");
        clubDescriptionColumn.setCellValueFactory(data -> data.getValue().descriptionProperty());

        // Add columns to tableview
        allclubtableview.getColumns().addAll(clubIDColumn, clubNameColumn, clubCategoryColumn, clubDescriptionColumn);

        // Get club data and set it in the tableview
        List<Club> allClubs = getDataFromClubTable();
        ObservableList<Club> clubObservableList = FXCollections.observableArrayList(allClubs);
        allclubtableview.setItems(clubObservableList);

        // Set listener (if needed)
        allclubtableview.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Handle selection change if needed
        });
    }

    public void getStudentClubList(){
        studentClubList.clear();
        studentclubtableview.refresh();
        studentclubtableview.getColumns().clear();
        TableColumn<Club, String> iDColumn = new TableColumn<>("Club ID");
        iDColumn.setCellValueFactory(data -> data.getValue().clubIDProperty());
        TableColumn<Club, String> nameColumn = new TableColumn<>("Club Name");
        nameColumn.setCellValueFactory(data -> data.getValue().clubNameProperty());
        studentclubtableview.getColumns().addAll(iDColumn,nameColumn);
        getClubsFromDatabase();
        studentclubtableview.setItems(studentClubList);
        studentclubtableview.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{} );
    }
    public void getStudentDetails(){
        int parsedStudentId = Integer.parseInt(studentIdField.getText());

        GuardianDetails g1 = new GuardianDetails();
        g1.setStudentID(parsedStudentId);
        g1.setGuardianName(studentGuardianNameField.getText());
        g1.setGuardianPhone(Integer.parseInt(studentGuardianPhoneField.getText()));
        g1.setGuardianEmail(studentGuardianEmailField.getText());

        Student s1 = new Student();
        s1.setStudentId(parsedStudentId);
        s1.setFirstName(studentFNameField.getText());
        s1.setLastName(studentLNameField.getText());
        s1.setDateOfBirth(studentDOBField.getValue().toString());
        s1.setGender(studentGenderField.getText());
        s1.setAddress(studentAddressField.getText());
        s1.setStudentGradeClass(studentGradeClassField.getText());

        // Set the imge path
        String studentId = studentIdField.getText();
        Path destinationDirectory = Paths.get("OOD", "src", "main", "resources", "studentImages");
        Path destinationFilePath = destinationDirectory.resolve(studentId + ".jpg");
        s1.setImagePath(destinationFilePath.toString());

        s1.setGuardian(g1);
        DBQuery.addStudent(s1);
        DBQuery.addStudentLogin(s1);
    }
    public void getAdvisorDetails(){
        ClubAdvisor a1 = new ClubAdvisor();
        a1.setAdvisorId(Integer.parseInt(advisorIdField.getText()));
        a1.setFirstName(advisorFNameField.getText());
        a1.setLastName(advisorLNameField.getText());
        a1.setDateOfBirth(advisorDOBField.getText());
        a1.setGender(advisorGenderField.getText());
        a1.setAddress(advisorAddressField.getText());
        a1.setAdvisorPhone(Integer.parseInt(advisorPhoneField.getText()));

        // Set the image path
        String advisorId = advisorIdField.getText();
        Path destinationDirectory = Paths.get("OOD", "src", "main", "resources", "advisorImages");
        Path destinationFilePath = destinationDirectory.resolve(advisorId + ".jpg");
        a1.setImagePath(destinationFilePath.toString());
        DBQuery.addAdvisor(a1);
        DBQuery.addAdvisorLogin(a1);
    }

    @FXML
    private void validateLogin() {
        String sID = studentIdField.getText();
        String fName = studentFNameField.getText();
        String lName = studentLNameField.getText();
        String dob = studentDOBField.getValue().toString();
        String gName = studentGuardianNameField.getText();
        String gPhone = studentGuardianPhoneField.getText();
        String gEmail = studentGuardianEmailField.getText();

        try {
            // Check if student with the same ID already exists in the database
            PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * FROM student WHERE studentID = ?");
            preparedStatement.setString(1, sID);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Check if any rows were returned
            if (resultSet.next()) {
                studentIdField.setStyle("-fx-border-color: red;-fx-border-width: 2px");
                errormsg.setText("Error: Student with ID " + sID + " already exists!");
                return; // Exit the method if the student already exists
            } else {
                studentIdField.setStyle("-fx-border-color: white");
            }

            // Close the ResultSet and PreparedStatement
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            // Handle any potential SQLException
            ex.printStackTrace();
        }
        if(!isValidID(sID)){
            studentIdField.setStyle("-fx-border-color: red;-fx-border-width: 2px");
            errormsg.setText("Error: Please enter a valid Id");
        }else {
            studentIdField.setStyle("-fx-border-color: white");}
        if (!isValidName(fName)) {
            studentFNameField.setStyle("-fx-border-color: red;-fx-border-width: 2px");
            errormsg.setText("Error: First Name should contain only letters.");
            return;
        } else {
            studentFNameField.setStyle("-fx-border-color: white");
        }

        if (!isValidName(lName)) {
            studentLNameField.setStyle("-fx-border-color: red;-fx-border-width: 2px");
            errormsg.setText("Error: Last Name should contain only letters.");
            return;
        } else {
            studentLNameField.setStyle("-fx-border-color: white");
        }

        if (!isValidName(gName)) {
            studentGuardianNameField.setStyle("-fx-border-color: red;-fx-border-width: 2px");
            errormsg.setText("Error: Guardian Name should contain only letters.");
            return;
        } else {
            studentGuardianNameField.setStyle("-fx-border-color: white");
        }

        if (!isValidPhone(gPhone)) {
            studentGuardianPhoneField.setStyle("-fx-border-color: red;-fx-border-width: 2px");
            errormsg.setText("Error: Guardian phone should contain only 10 integers.");
            return;
        } else {
            studentGuardianPhoneField.setStyle("-fx-border-color: white");
        }

        if (!isValidEmail(gEmail)) {
            studentGuardianEmailField.setStyle("-fx-border-color: red;-fx-border-width: 2px");
            errormsg.setText("Error: Invalid email format.");
            return;
        } else {
            studentGuardianEmailField.setStyle("-fx-border-color: white");
        }
        if (isValidID(sID) && isValidName(fName) && isValidName(lName) &&
                isValidName(gName) && isValidPhone(gPhone) && isValidEmail(gEmail)) {
            errormsg.setText(""); // Clear the error message
            status = 1;
        }
    }
    @FXML
    private void validateLogin2() {
        String aID = advisorIdField.getText();
        String fName = advisorFNameField.getText();
        String lName = advisorLNameField.getText();
        String aPhone = advisorPhoneField.getText();

        try {
            // Check if student with the same ID already exists in the database
            PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * FROM advisor WHERE advisorID = ?");
            preparedStatement.setString(1, aID);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Check if any rows were returned
            if (resultSet.next()) {
                advisorIdField.setStyle("-fx-border-color: red;-fx-border-width: 2px");
                errormsg.setText("Error: Student with ID " + aID + " already exists!");
                return; // Exit the method if the student already exists
            } else {
                advisorIdField.setStyle("-fx-border-color: white");
            }

            // Close the ResultSet and PreparedStatement
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            // Handle any potential SQLException
            ex.printStackTrace();
        }
        if(!isValidID(aID)){
            advisorIdField.setStyle("-fx-border-color: red;-fx-border-width: 2px");
            errormsg.setText("Error: Please enter a valid Id");
        }else {
            advisorIdField.setStyle("-fx-border-color: white");}
        if (!isValidName(fName)) {
            advisorFNameField.setStyle("-fx-border-color: red;-fx-border-width: 2px");
            errormsg.setText("Error: First Name should contain only letters.");
            return;
        } else {
            advisorFNameField.setStyle("-fx-border-color: white");
        }

        if (!isValidName(lName)) {
            advisorLNameField.setStyle("-fx-border-color: red;-fx-border-width: 2px");
            errormsg.setText("Error: Last Name should contain only letters.");
            return;
        } else {
            advisorLNameField.setStyle("-fx-border-color: white");
        }

        if (!isValidPhone(aPhone)) {
            advisorPhoneField.setStyle("-fx-border-color: red;-fx-border-width: 2px");
            errormsg.setText("Error: Guardian phone should contain only 10 integers.");
            return;
        } else {
            advisorPhoneField.setStyle("-fx-border-color: white");
        }
        // If all validations pass, proceed to get advisor details
//        getAdvisorDetails();
        if (isValidID(aID) && isValidName(fName) && isValidName(lName) && isValidPhone(aPhone)) {
            errormsg.setText(""); // Clear the error message
            status = 1;
        }

    }

    private boolean isValidName(String name) {
        // Use a regular expression to check if the name contains only letters
        return name.matches("[a-zA-Z]+");
    }
    private boolean isValidPhone(String phone) {
        // Check if the phone contains only digits and has a length of 10
        return phone.matches("\\d{10}");
    }
    private boolean isValidID(String id) {
        // Check if the ID contains only digits and has a length of 4
        return id.matches("\\d{4}");
    }
    private boolean isValidEmail(String gEmail) {
        return gEmail.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
    }

    private int status = 0;
    @FXML
    public void onValidateButtonClick(){
        validateLogin();
    }
    @FXML
    public void onValidateButton2Click(){
        validateLogin2();
    }
    @FXML
    protected void onSubmitButtonClick(ActionEvent e2)throws IOException {
        if(status == 1){
            getStudentDetails();
            Stage previousStage = (Stage) ((Node) e2.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("StudentLogin.fxml"));
            previousStage.setScene(new Scene(root, 1200, 750));
        } else{errormsg.setText("Please validate input before clicking submit button");}
    }

    @FXML
    protected void onSubmitButton2Click(ActionEvent e)throws IOException {
        if(status == 1){
            getAdvisorDetails();
            Stage previousStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("AdvisorLogin.fxml"));
            previousStage.setScene(new Scene(root, 1200, 750));
        }else{errormsg.setText("Please validate input before clicking submit button");}
    }


    @FXML
    protected void onSelectImageButtonClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        Stage stage = (Stage) selectimagebutton.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null && file.exists()) {
            String studentId = studentIdField.getText();
            if (isValidID(studentId)) {
                errormsg.setText("");
                studentImage = new Image(file.toURI().toString());
                studentImageField.setImage(studentImage);

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
                errormsg.setText("Invalid studentId");
            }
        } else {
            System.err.println("Selected file is null or does not exist.");
        }
    }
    @FXML
    protected void onSelectImageButton2Click() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        Stage stage = (Stage) selectimagebutton2.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null && file.exists()) {
            String advisorId = advisorIdField.getText();
            if (isValidID(advisorId)) {
                errormsg.setText("");
                advisorImage = new Image(file.toURI().toString());
                advisorImageField.setImage(advisorImage);

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
                    removeText2();
                } catch (IOException e) {
                    e.printStackTrace();
                    // Handle the exception appropriately, e.g., show an error message to the user
                }
            } else {
                errormsg.setText("Invalid advisorId");
            }
        } else {
            System.err.println("Selected file is null or does not exist.");
        }
    }
    private void removeText() {
        Pane imagePane = (Pane) studentImageField.getParent();
        imagePane.getChildren().removeIf(node -> node instanceof FontIcon || node instanceof Text);
    }
    private void removeText2() {
        Pane imagePane = (Pane) advisorImageField.getParent();
        imagePane.getChildren().removeIf(node -> node instanceof FontIcon || node instanceof Text);
    }

    @FXML
    public void  switchAdvisor (ActionEvent e2) throws IOException {
        Stage previousStage = (Stage) ((Node) e2.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("AdvisorLogin.fxml"));
        previousStage.setScene(new Scene(root, 1200, 750));
    }
    @FXML
    public void onSignupButtonClick(ActionEvent e3) throws IOException{
        Stage previousStage = (Stage) ((Node) e3.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("StudentRegistrationForm.fxml"));
        previousStage.setScene(new Scene(root, 1200, 750));
    }
    public void onLoginButton2Click(ActionEvent event) throws IOException {
        int enteredStudentId = Integer.parseInt(studentLoginIdField.getText());
        String enteredPassword = studentPasswordField.getText();
        System.out.println("Entered Student ID: " + enteredStudentId);
        System.out.println("Entered Password: " + enteredPassword);

        DBQuery dbQuery = new DBQuery();
        DBQuery.setLoggedInStudentId(loggedInStudentId);
        Student loggedInStudent = dbQuery.getStudentByLogin(enteredStudentId, enteredPassword);

        if (loggedInStudent != null) {
            // Set the logged-in advisor ID
            loggedInStudentId = loggedInStudent.getStudentId();
            DBQuery.setLoggedInStudentId(loggedInStudentId);


            // Load the student Profile FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentProfile.fxml"));
            Parent root = loader.load();

            // Get the RegistrationController instance
            RegistrationController registrationController = loader.getController();

            // Set the logged-in advisor's details in the lbel
            registrationController.studentIdLabel.setText(String.valueOf(loggedInStudent.getId()));
            registrationController.studentFNameLabel.setText(loggedInStudent.getFirstName());
            registrationController.studentLNameLabel.setText(loggedInStudent.getLastName());
            registrationController.studentDOBLabel.setText(loggedInStudent.getDateOfBirth());
            registrationController.studentGenderLabel.setText(loggedInStudent.getGender());

            // Load and display advisor image in the Circle
            String imagePath = "OOD/src/main/resources/studentImages/" + loggedInStudentId + ".jpg";
            // Load the image and set it in img view
            Image image = new Image(new FileInputStream(imagePath));
            registrationController.studentImageView.setImage(image);

            RegistrationController.setLoggedInStudentId(loggedInStudentId);


            // Show the Club Dashboard
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1200, 750));

            // Call setLoggedInAdvisorId after the FXML is loaded and controller is initialized
            RegistrationController.setLoggedInStudentId(loggedInStudentId);
        } else {
            // Handle invalid login (display an error message, etc.)
            System.out.println("Login failed");
        }
    }

    public List<Club> getDataFromClubTable() {
        List<Club> clubList = new ArrayList<>();

        String query = "SELECT c.clubID, c.clubName, c.clubCategory, c.clubDescription FROM club c";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Club club = new Club(
                        resultSet.getString("clubID"),
                        resultSet.getString("clubName"),
                        resultSet.getString("clubCategory"),
                        resultSet.getString("clubDescription")
                );
                clubList.add(club);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clubList;
    }
    private void getClubsFromDatabase() {
        String query = "SELECT c.clubID, c.clubName FROM club c JOIN student_club sc ON c.clubID = sc.clubID WHERE sc.studentID = ?";
        try (Connection connection = getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, loggedInStudentId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Club club = new Club(resultSet.getString("clubID"), resultSet.getString("clubName"));
                        studentClubList.add(club);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void onShowClubsButtonClick(ActionEvent actionEvent) {
        populateChoiceBox();
        getAllClubsList();
    }
    public void onRefreshClubsButtonClick(ActionEvent event){
        studentclubtableview.getColumns().clear();
        getStudentClubList();
    }

    public void joinClub(){
        System.out.println(loggedInStudentId);
        String selectedClubID = joinclubchoicebox.getValue();  // Assuming you want to get the selected club name

// First, check if a record already exists
        String checkQuery = "SELECT * FROM student_club WHERE studentID = ? AND clubID = ?";

        try (Connection connection = getConnection();
             PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {

            checkStatement.setInt(1, loggedInStudentId);
            checkStatement.setString(2,selectedClubID );

            try (ResultSet resultSet = checkStatement.executeQuery()) {
                if (resultSet.next()) {
                    // The record already exists, you may choose to handle this case
                    System.out.println("Record already exists");
                } else {
                    // The record doesn't exist, proceed with the insertion
                    String insertQuery = "INSERT INTO student_club (studentID, clubID) VALUES (?,?)";

                    try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                        insertStatement.setInt(1, loggedInStudentId);
                        insertStatement.setString(2, selectedClubID);

                        int rowsAffected = insertStatement.executeUpdate();

                        if (rowsAffected > 0) {
                            System.out.println("Record inserted successfully");
                        } else {
                            System.out.println("Failed to insert record");
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void leaveClub() {
        System.out.println(loggedInStudentId);
        String selectedClubID = leaveclubchoicebox.getValue();  // Assuming you want to get the selected club name

        // First, check if a record exists
        String checkQuery = "SELECT * FROM student_club WHERE studentID = ? AND clubID = ?";

        try (Connection connection = getConnection();
             PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {

            checkStatement.setInt(1, loggedInStudentId);
            checkStatement.setString(2, selectedClubID);

            try (ResultSet resultSet = checkStatement.executeQuery()) {
                if (resultSet.next()) {
                    // The record exists, proceed with deletion
                    String deleteQuery = "DELETE FROM student_club WHERE studentID = ? AND clubID = ?";

                    try (PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
                        deleteStatement.setInt(1, loggedInStudentId);
                        deleteStatement.setString(2, selectedClubID);

                        int rowsAffected = deleteStatement.executeUpdate();

                        if (rowsAffected > 0) {
                            System.out.println("Record deleted successfully");
                        } else {
                            System.out.println("Failed to delete record");
                        }
                    }
                } else {
                    // The record doesn't exist, you may choose to handle this case
                    System.out.println("Record does not exist");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void onJoinClubButtonClick(ActionEvent actionEvent) {
        joinClub();
    }

    public void onLeaveClubButtonClick(ActionEvent actionEvent) {
        leaveClub();
    }

    @FXML
    protected void onLogoutButtonClick(ActionEvent e2)throws IOException {
        Stage previousStage = (Stage) ((Node) e2.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("StudentLogin.fxml"));
        previousStage.setScene(new Scene(root, 1200, 750));
    }
    @FXML
    public void onLogoutButton2Click(ActionEvent e2)throws IOException {
        Stage previousStage = (Stage) ((Node) e2.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("AdvisorLogin.fxml"));
        previousStage.setScene(new Scene(root, 1200, 750));
    }

    public void onEventButtonClick(ActionEvent actionEvent) throws IOException{
        Stage previousStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("student_Eventview.fxml"));
        previousStage.setScene(new Scene(root, 1200, 750));
    }
}
