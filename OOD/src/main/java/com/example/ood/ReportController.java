package com.example.ood;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class ReportController {
    @FXML
    private Label reportClubID;
    @FXML
    private Label reportevent;

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
    public void setEventDetails() {
        DBQuery dbQuery = new DBQuery();
        ArrayList<Event> eventList = dbQuery.getEventNameList(reportClubID.getText());

        StringBuilder eventsText = new StringBuilder();
        for (Event event : eventList) {
            eventsText.append(event.getEventName()).append("\n");
        }

        reportevent.setText(eventsText.toString());
    }

}
