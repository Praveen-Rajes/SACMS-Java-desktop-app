package com.example.ood;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.*;


public class ClubHomeController {
    @FXML
    AnchorPane homePane;

    @FXML
    private Button homeButton;
    @FXML
    private Button removeButton;
    @FXML
    private TableView<Club> tableView;
    @FXML
    private TableColumn<Club, String> clubIDColumn;
    @FXML
    private TableColumn<Club, String> clubNameColumn;
    @FXML
    private TableColumn<Club, String> categoryColumn;
    @FXML
    private TableColumn<Club, String> clubAdvisorColumn;
    private static final String DATA_FILE_NAME = "club_data.txt";

    private ObservableList<Club> clubDetails = FXCollections.observableArrayList();

    public void initialize() {
        // Check if the data file exists
        File dataFile = new File(DATA_FILE_NAME);
        if (dataFile.exists()) {
            // If the data file exists, load data from it
            loadDataFromFile();
        }

        // Set cell value for the table columns
        clubIDColumn.setCellValueFactory(data -> data.getValue().clubIDProperty());
        clubNameColumn.setCellValueFactory(data -> data.getValue().clubNameProperty());
        categoryColumn.setCellValueFactory(data -> data.getValue().categoryProperty());

        // Bind the table view to the observable list
        tableView.setItems(clubDetails);
    }

    public void addClubDetail(Club club) {
        clubDetails.add(club);
    }

    @FXML
    private void onHomeButtonClick(ActionEvent e1) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("clubdetails.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1200, 750);
            Stage stage = new Stage();
            stage.setTitle("Club Details");
            stage.setScene(scene);

            ClubdetailsController clubdetailsController = fxmlLoader.getController();
            clubdetailsController.setHomeController(this); // Set a reference to HomeController

            stage.showAndWait();

            // Save data to the text file when the program closes
            saveDataToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void saveDataToFile() {
        try (FileWriter fileWriter = new FileWriter(DATA_FILE_NAME);
             BufferedWriter writer = new BufferedWriter(fileWriter)) {
            for (Club club : clubDetails) {
                writer.write(club.getClubID() + "," + club.getName() + "," + club.getCategory() + "," + club.getDescription() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void loadDataFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 4) {
                    Club club = new Club(data[1], data[0], data[2]);
                    club.setDescription(data[3]);
                    clubDetails.add(club);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void onRemoveButtonClick() {
        Club selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            clubDetails.remove(selectedItem);
            saveDataToFile(); // Save the updated data to the text file
        } else {
            //
        }
    }


}