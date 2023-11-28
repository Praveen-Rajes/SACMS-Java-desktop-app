package com.example.ood;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ReportController {
    @FXML
    private Label reportClubID;

    @FXML
    private Label reportClubName;

    @FXML
    private Label reportAdvisor;
    private static String loggedInAdvisorFName;
    private static String loggedInAdvisorLName;
    private static String loggedInAdvisorName;




    public static String setLoggedInAdvisorName(String loggedInAdvisorFName, String loggedInAdvisorLName) {
        ReportController.loggedInAdvisorFName = loggedInAdvisorFName;
        ReportController.loggedInAdvisorLName = loggedInAdvisorLName;
        loggedInAdvisorName = loggedInAdvisorFName + loggedInAdvisorLName;
        return loggedInAdvisorName;
    }

    public void initialize() {
        // Initialization code, if needed
    }

    public void setReportDetails(String clubID, String clubName, String advisor) {
        reportClubID.setText(clubID);
        reportClubName.setText(clubName);
        reportAdvisor.setText(loggedInAdvisorName);
    }
}
