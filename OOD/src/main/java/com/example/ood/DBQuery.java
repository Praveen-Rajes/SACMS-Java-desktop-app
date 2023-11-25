package com.example.ood;
import java.sql.*;
import java.util.ArrayList;


public class DBQuery {
    public void addClub(Club club){
        String query1 = "INSERT INTO club(clubID, clubName, clubCategory, clubDescription, clubTheme, clubLogo,advisorID) VALUES(?,?,?,?,?,?,?)";
        String query2 = "INSERT INTO advisor_club (advisorID, clubID) VALUES(?,?)";

        Connection connection = null;
        try{
            connection = getConnection();
            PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
            PreparedStatement preparedStatement2 = connection.prepareStatement(query2);

            preparedStatement1.setString(1, club.getClubID());
            preparedStatement1.setString(2, club.getName());
            preparedStatement1.setString(3, club.getCategory());
            preparedStatement1.setString(4, club.getDescription());
            preparedStatement1.setString(5, String.valueOf(club.getTheme()));
            preparedStatement1.setString(6, String.valueOf(club.getLogoImage()));
            preparedStatement1.setInt(7, club.getAdvisorId());

            preparedStatement2.setInt(1,club.getAdvisorId());
            preparedStatement2.setString(2,club.getClubID());

            preparedStatement1.executeUpdate();
            preparedStatement2.executeUpdate();
        }catch (SQLException e){
            System.out.println("Error!");
        }finally {
            try {
                if(connection != null && !connection.isClosed()){
                    connection.close();
                }
            }catch (SQLException e){
                System.out.println("Error closing connection"+e.getMessage());
            }
        }
    }
    public ArrayList<Club> getClubList() {
        String query = "SELECT c.clubID, c.clubName, c.clubCategory, c.clubDescription, c.clubTheme, c.clubLogo, ac.advisorID" +
                "FROM club c" +
                "JOIN advisor_club ac ON c.clubID = ac.clubID";
        ArrayList<Club> clubList = new ArrayList<>();


        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Club club = new Club(resultSet.getString("clubID"));
                resultSet.getString("clubName");
                resultSet.getString("clubDescription");
                resultSet.getString("clubCategory");
                resultSet.getString("clubLogo");
                resultSet.getString("clubTheme");
                resultSet.getInt("advisorID");
                clubList.add(club);
            }
            return clubList;

        } catch (SQLException e) {
            System.out.println("Error!");
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing connection" + e.getMessage());
            }
        }
        return null;
    }
    public Club getClub() {
        String query = "SELECT c.clubID, c.clubName, c.clubCategory, c.clubDescription, c.clubTheme, c.clubLogo, ac.advisorID" +
                "FROM club c" +
                "JOIN advisor_club ac ON c.clubID = ac.clubID" +
                "WHERE c.clubID = ?";


        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Club club = new Club(resultSet.getString("clubID"));
                resultSet.getString("clubName");
                resultSet.getString("clubDescription");
                resultSet.getString("clubCategory");
                resultSet.getString("clubLogo");
                resultSet.getString("clubTheme");
                resultSet.getInt("advisorID");
                return club;
            }

        } catch (SQLException e) {
            System.out.println("Error!");
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing connection" + e.getMessage());
            }
        }
        return null;
    }

    public void addStudent(StudentRegistration student){
        String query1 = "INSERT INTO student(studentID, studentFName, studentLName, dob, gender, address, gradeClass) VALUES(?,?,?,?,?,?,?)";
        String query2 = "INSERT INTO guardian (guardianName, phoneNo, email, studentID) VALUES(?,?,?,?)";

        Connection connection = null;
        try{
            connection = getConnection();
            PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
            PreparedStatement preparedStatement2 = connection.prepareStatement(query2);

            preparedStatement1.setInt(1, student.getStudentId());
            preparedStatement1.setString(2, student.getFirstName());
            preparedStatement1.setString(3, student.getLastName());
            preparedStatement1.setString(4, student.getDateOfBirth());
            preparedStatement1.setString(5, student.getGender());
            preparedStatement1.setString(6, student.getAddress());
            preparedStatement1.setString(7, student.getStudentGradeClass());

            // Get guardian details from the composition relationship
            GuardianDetails guardianDetails = student.getGuardianDetails();

            // Set parameters for guardian table
            preparedStatement2.setString(1, guardianDetails.getGuardianName());
            preparedStatement2.setInt(2, guardianDetails.getGuardianPhone());
            preparedStatement2.setString(3, guardianDetails.getGuardianEmail());
            preparedStatement2.setInt(4, student.getStudentId());

            preparedStatement1.executeUpdate();
            preparedStatement2.executeUpdate();
        }catch (SQLException e){
            System.out.println("Error!");
        }finally {
            try {
                if(connection != null && !connection.isClosed()){
                    connection.close();
                }
            }catch (SQLException e){
                System.out.println("Error closing connection"+e.getMessage());
            }
        }
    }
    public void addAdvisor(AdvisorRegistration advisor){
        String query1 = "INSERT INTO clubadvisor(advisorID, advisorFirstName, advisorLastName, advisorDOB, advisorGender, advisorAddress, advisorPhone) VALUES(?,?,?,?,?,?,?)";


        Connection connection = null;
        try{
            connection = getConnection();
            PreparedStatement preparedStatement1 = connection.prepareStatement(query1);


            preparedStatement1.setInt(1, advisor.getAdvisorId());
            preparedStatement1.setString(2, advisor.getFirstName());
            preparedStatement1.setString(3, advisor.getLastName());
            preparedStatement1.setString(4, advisor.getDateOfBirth());
            preparedStatement1.setString(5, advisor.getGender());
            preparedStatement1.setString(6, advisor.getAddress());
            preparedStatement1.setInt(7, advisor.getAdvisorPhone());


            preparedStatement1.executeUpdate();

        }catch (SQLException e){
            System.out.println("Error!");
        }finally {
            try {
                if(connection != null && !connection.isClosed()){
                    connection.close();
                }
            }catch (SQLException e){
                System.out.println("Error closing connection"+e.getMessage());
            }
        }
    }
    public static void addStudentLogin(StudentRegistration student) {
        String query1 = "INSERT INTO studentlogin(studentId, Password) VALUES(?, ?)";

        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement1 = connection.prepareStatement(query1);

            preparedStatement1.setInt(1, student.getStudentId());

            // Assuming you have a method getPassword() in StudentRegistration class
            preparedStatement1.setString(2, student.getPassword());

            preparedStatement1.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error!");
            System.out.println(e.getMessage());
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing connection" + e.getMessage());
            }
        }
    }
    public static void addAdvisorLogin(AdvisorRegistration advisor) {
        String query1 = "INSERT INTO advisorlogin(advisorId, Password) VALUES(?, ?)";

        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement1 = connection.prepareStatement(query1);

            preparedStatement1.setInt(1, advisor.getAdvisorId());

            // Assuming you have a method getPassword() in StudentRegistration class
            preparedStatement1.setString(2, advisor.getPassword());

            preparedStatement1.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error!");
            System.out.println(e.getMessage());
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing connection" + e.getMessage());
            }
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
