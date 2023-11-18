package com.example.ood;
import java.sql.Connection;
import java.sql.DriverManager;


public class Main {
    public static void main(String[] args){
        getConnection();
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
