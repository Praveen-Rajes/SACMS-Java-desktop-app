package com.example.ood;
import java.sql.*;


public class Main {
    public static void main(String[] args){
        remove(1002);
        select();
    }
    public static void remove(int studentID) {
        try {
            Connection conn = getConnection();
            PreparedStatement delete = conn.prepareStatement("DELETE FROM student WHERE studentID = ?");

            // Set the parameter (student_id) for the WHERE clause
            delete.setInt(1, studentID);

            // Execute the DELETE statement
            int rowsAffected = delete.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Data removed successfully!");
            } else {
                System.out.println("Invalid Student ID!");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void select() {
        try {
            Connection conn = getConnection();
            PreparedStatement select = conn.prepareStatement("SELECT * FROM student");
            ResultSet result = select.executeQuery();

            // Get the number of columns in the result set
            ResultSetMetaData metaData = result.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Loop through each row in the result set
            while (result.next()) {
                // Loop through each column in the row
                for (int i = 1; i <= columnCount; i++) {
                    // Print the data from each column
                    System.out.print(result.getString(i) + " ");
                }
                System.out.println(); // Move to the next line for the next row
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void insert(){
        try{
            Connection conn = getConnection();
            PreparedStatement insert = conn.prepareStatement("INSERT INTO student(studentID,studentFName,studentLName,dob,gender,address,gradeClass)"+ "VALUES(1002,'Yasini','De Silve','15-01-2003','Female','35/A,Jawatta RD','10-B')");
            insert.executeUpdate();

        }catch (Exception e){
            System.out.println(e);
        }
    }
    public static Connection getConnection() {
        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            String databaseurl = "jdbc:mysql://localhost:3306/clubmaster";
            String username = "root";
            String password = "Praveen@2003";
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(databaseurl, username, password);
            System.out.println("Database Connected");
            return conn;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;

    }
}
