package com.example.ood;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static com.example.ood.DBQuery.getConnection;

public class ReportController {

    @FXML
    private TextArea attendance;

    @FXML
    private TextArea events;


    @FXML
    private TextArea studentList;

    @FXML
    void initialize() {
        // Call a method to retrieve and set event names
        retrieveAndSetEventNames();
        fetchAndDisplayStudentCounts();
        updateAttendanceTextArea();
    }

    private void retrieveAndSetEventNames() {
        // JDBC URL, username, and password of your MySQL server
        String url = "jdbc:mysql://localhost:3306/clubmaster";
        String user = "root";
        String password = "";

        // SQL query to retrieve event names
        String query = "SELECT eventName FROM events";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            // Clear existing content in the TextArea
            events.clear();

            // Iterate through the result set and append event names to the TextArea
            while (resultSet.next()) {
                String eventName = resultSet.getString("eventName");
                events.appendText(eventName + "\n");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error retrieving event names from the database.");
        }
    }

    private void fetchAndDisplayStudentCounts() {
        // Fetch student count for each club from events table
        String query = "SELECT c.clubName, COUNT(DISTINCT s.studentID) AS studentCount " +
                "FROM club c " +
                "LEFT JOIN student_club sc ON c.clubID = sc.clubID " +
                "LEFT JOIN student s ON sc.studentID = s.studentID " +
                "GROUP BY c.clubName";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            // Iterate through the result set and append club names and student counts to the studentList TextArea
            while (resultSet.next()) {
                String clubName = resultSet.getString("clubName");
                int studentCount = resultSet.getInt("studentCount");

                String line = "Club : " + clubName + "=> Students " + studentCount + "\n";
                studentList.appendText(line);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error retrieving student counts from the database.");
        }
    }
    private void updateAttendanceTextArea() {
        // Assuming you have a method in DBQuery to retrieve attendance data
        DBQuery dbQuery = new DBQuery();

        // Retrieve the list of events
        ArrayList<String> eventList = dbQuery.getEventList();

        // Clear existing content in the TextArea
        attendance.clear();

        if (eventList != null) {
            // Iterate over each event and retrieve attendance data
            for (String eventName : eventList) {
                String attendanceData = dbQuery.getAttendanceDataForEvent(eventName);

                // Calculate counts (You need to implement this based on your data)
                int presentCount = calculatePresentCount(attendanceData);
                int absentCount = calculateAbsentCount(attendanceData);

                // Append the information to the TextArea
                attendance.appendText("Event: " + eventName + "\n");
                attendance.appendText("Present Count: " + presentCount + "\n");
                attendance.appendText("Absent Count: " + absentCount + "\n\n");
            }
        } else {
            attendance.setText("No events found.");

        }
    }

    // You need to implement these methods based on your data
    private int calculatePresentCount(String attendanceData) {
        // Implement the logic to calculate the present count
        return 0;
    }

    private int calculateAbsentCount(String attendanceData) {
        // Implement the logic to calculate the absent count
        return 0;
    }



    public static void setLoggedInAdvisorName(String loggedInAdvisorFName, String loggedInAdvisorLName) {
    }

    @FXML
    void DownloadClick(ActionEvent event) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String formattedDateTime = now.format(formatter);

        // Create a file with the current date and time in the file name
        File file = new File("report_" + formattedDateTime + ".txt");

        // Write the text to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("Event Names:\n");
            writer.write(events.getText() + "\n");

            writer.write("\nStudent Counts:\n");
            writer.write(studentList.getText() + "\n");

            writer.write("\nAttendance Data:\n");
            writer.write(attendance.getText() + "\n");

            System.out.println("Report saved to: " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error writing report to the file.");
        }

    }

    public void setReportDetails(String clubID, String name, String s) {
    }

}
