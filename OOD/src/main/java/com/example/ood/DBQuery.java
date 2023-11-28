package com.example.ood;
import java.sql.*;
import java.util.ArrayList;


public class DBQuery {
    private static int loggedInAdvisorId;
    public static void setLoggedInAdvisorId(int loggedInAdvisorId) {
        DBQuery.loggedInAdvisorId = loggedInAdvisorId;
    }
    private static int loggedInStudentId;
    public static void setLoggedInStudentId(int loggedInStudentId) {
        DBQuery.loggedInStudentId = loggedInStudentId;
    }
    public void addClub(Club club){
        String query1 = "INSERT INTO club(clubID, clubName, clubCategory, clubDescription, clubTheme, clubLogo, advisorID) VALUES(?,?,?,?,?,?,?)";
        String query2 = "INSERT INTO advisor_club (advisorID, clubID) VALUES(?,?)";

        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
            PreparedStatement preparedStatement2 = connection.prepareStatement(query2);

            preparedStatement1.setString(1, club.getClubID());
            preparedStatement1.setString(2, club.getName());
            preparedStatement1.setString(3, club.getCategory());
            preparedStatement1.setString(4, club.getDescription());
            preparedStatement1.setString(5, club.getThemeHex());
            preparedStatement1.setString(6, club.getImagePath());  // Verify if this is the correct way to store an image
            preparedStatement1.setInt(7, club.getAdvisorId());

            preparedStatement2.setInt(1, club.getAdvisorId());
            preparedStatement2.setString(2, club.getClubID());

            // Execute both queries
            preparedStatement1.executeUpdate();
            preparedStatement2.executeUpdate();

            System.out.println("Club added successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error adding club to the database.");
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }
    public ArrayList<Club> getClubList() {
        setLoggedInAdvisorId(loggedInAdvisorId);
        System.out.println(loggedInAdvisorId);
        setLoggedInAdvisorId(loggedInAdvisorId);
        String query = "SELECT * FROM club WHERE advisorID = "+loggedInAdvisorId+";";
        ArrayList<Club> clubList = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Club club = new Club(resultSet.getString("clubName"));
                club.setClubID(resultSet.getString("clubID"));
                club.setCategory(resultSet.getString("clubCategory"));
                club.setDescription(resultSet.getString("clubDescription"));
                // set other attributes as needed
                clubList.add(club);
            }
            return clubList;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error retrieving club list from the database.");
        }
        return null;
    }
    public void removeClub(Club club) {
        String deleteAdvisorClubQuery = "DELETE FROM advisor_club WHERE clubID = ?";
        String deleteClubQuery = "DELETE FROM club WHERE clubID = ?";

        try (Connection connection = getConnection()) {
            // Delete from advisor_club table
            try (PreparedStatement deleteAdvisorClubStatement = connection.prepareStatement(deleteAdvisorClubQuery)) {
                deleteAdvisorClubStatement.setString(1, club.getClubID());
                int rowsAffectedAdvisorClub = deleteAdvisorClubStatement.executeUpdate();

                if (rowsAffectedAdvisorClub > 0) {
                    System.out.println("Records removed from advisor_club table.");
                } else {
                    System.out.println("No records found with the given clubID in advisor_club table.");
                }

            }

            // Delete from club table
            try (PreparedStatement deleteClubStatement = connection.prepareStatement(deleteClubQuery)) {
                deleteClubStatement.setString(1, club.getClubID());
                int rowsAffectedClub = deleteClubStatement.executeUpdate();

                if (rowsAffectedClub > 0) {
                    System.out.println("Club removed from the club table.");
                } else {
                    System.out.println("No club found with the given ID in the club table.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately, e.g., show an error message to the user
        }
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

    public static void addStudent(StudentRegistration student){
        String query1 = "INSERT INTO student(studentID, studentFName, studentLName, dob, gender, address, gradeClass) VALUES(?,?,?,?,?,?,?)";
        String query2 = "INSERT INTO guardian (gaurdianName, phoneNo, email, studentID) VALUES(?,?,?,?)";

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
            GuardianDetails guardianDetails = student.getGuardian();

            // Set parameters for guardian table
            preparedStatement2.setString(1, guardianDetails.getGuardianName());
            preparedStatement2.setInt(2, guardianDetails.getGuardianPhone());
            preparedStatement2.setString(3, guardianDetails.getGuardianEmail());
            preparedStatement2.setInt(4, guardianDetails.getStudentID());

            preparedStatement1.executeUpdate();
            preparedStatement2.executeUpdate();
        }catch (SQLException e){
            System.out.println("Error!"+e.getMessage());
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
    public static void addAdvisor(AdvisorRegistration advisor){
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
            System.out.println("Error!" + e.getMessage());
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
        String query1 = "INSERT INTO studentlogin(studentId, loginPassword) VALUES(?, ?)";

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
        String query1 = "INSERT INTO advisorlogin(advisorId, loginPassword) VALUES(?, ?)";

        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement1 = connection.prepareStatement(query1);

            preparedStatement1.setInt(1, advisor.getAdvisorId());

            // Assuming you have a method getPassword() in StudentRegistration class
            preparedStatement1.setString(2, advisor.getPassword());

            preparedStatement1.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error!" +e.getMessage());
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
    public AdvisorRegistration getAdvisorById(int advisorId) {
        String query = "SELECT al.advisorID, a.advisorFirstName, a.advisorLastName " +
                "FROM clubadvisor a " +
                "JOIN advisorlogin al ON al.advisorID = a.advisorID " +
                "WHERE al.advisorID = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, advisorId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                AdvisorRegistration advisor = new AdvisorRegistration(resultSet.getInt("advisorID"));
                advisor.setFirstName(resultSet.getString("advisorFirstName"));
                advisor.setLastName(resultSet.getString("advisorLastName"));
                return advisor;
            }
            System.out.println("Data Retrieval Successful");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public StudentRegistration getStudentById(int studentId) {
        String query = "SELECT sl.studentID, s.studentFName, s.studentLName, s.dob, s.gender " +
                "FROM student s " +
                "JOIN studentlogin sl ON sl.studentID = s.studentID " +
                "WHERE sl.studentID = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, studentId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                StudentRegistration student = new StudentRegistration();
                student.setStudentId(resultSet.getInt("studentID"));
                student.setFirstName(resultSet.getString("studentFName"));
                student.setLastName(resultSet.getString("studentLName"));
                student.setDateOfBirth(resultSet.getString("dob"));
                student.setGender(resultSet.getString("gender"));
                return student;
            }
            System.out.println("Data Retrieval Successful");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public AdvisorRegistration getAdvisorByLogin(int advisorId, String password) {
        String query = "SELECT * FROM advisorlogin WHERE advisorID = ? AND loginPassword = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, advisorId);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return getAdvisorById(advisorId);
            }
            System.out.println("Data Retrieval Successful");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public StudentRegistration getStudentByLogin(int studentId, String password) {
        String query = "SELECT * FROM studentlogin WHERE studentID = ? AND loginPassword = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, studentId);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return getStudentById(studentId);
            }
            System.out.println("Data Retrieval Successful");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Attendance> getClubListForAttendance() {
        setLoggedInAdvisorId(loggedInAdvisorId);
        System.out.println(loggedInAdvisorId);
        setLoggedInAdvisorId(loggedInAdvisorId);
        String query = "SELECT clubName FROM club WHERE advisorID = "+loggedInAdvisorId+";";
        ArrayList<Attendance> clubList = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Attendance attendance = new Attendance(resultSet.getString("clubName"));
                // set other attributes as needed
                clubList.add(attendance);
            }
            return clubList;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error retrieving club list from the database.");
        }
        return null;
    }
    public ArrayList<String> getClubIDListForAttendance() {
        setLoggedInAdvisorId(loggedInAdvisorId);
        System.out.println(loggedInAdvisorId);
        setLoggedInAdvisorId(loggedInAdvisorId);
        String query = "SELECT clubID FROM club WHERE advisorID = "+loggedInAdvisorId+";";
        ArrayList<String> itemsList = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                // Assuming the result is a String, modify accordingly based on your database schema
                String clubID = resultSet.getString("clubID");
                itemsList.add(clubID);
            }
            return itemsList;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error retrieving club list from the database.");
        }
        return null;
    }


    public ArrayList<Attendance> getEventListForAttendance(String selectedDate, String selectedClub) {
        String query = "SELECT e.eventName, e.eventStartTime, e.eventEndTime FROM events e JOIN club c ON e.clubID = c.clubID WHERE e.eventDate=? AND c.clubName = ?";
        ArrayList<Attendance> eventList = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, selectedDate);
            preparedStatement.setString(2, selectedClub);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Attendance attendance = new Attendance(resultSet.getString("eventName"));
                    resultSet.getString("eventStartTime");
                    resultSet.getString("eventEndTime");
                    // Set other attributes as needed
                    eventList.add(attendance);
                }
                return eventList;

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error retrieving event list from the database.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error retrieving club list from the database.");
        }
        return null;
    }


    public void EventInsert(Event event){

        String query1 = "INSERT INTO events (eventID,clubID,eventName,eventLocation,eventDate,eventStartTime,eventEndTime,eventDescription) VALUES(?,?,?,?,?,?,?,?);";

        Connection connection = null;
        try {
            connection = getConnection();

            PreparedStatement preparedStatement1 = connection.prepareStatement(query1);




            // Execute both queries

            preparedStatement1.setString(1, event.getEventID());
            preparedStatement1.setString(2, event.getClubId());
            preparedStatement1.setString(3, event.getEventName());
            preparedStatement1.setString(4, event.getEventLocation());
            preparedStatement1.setString(5, String.valueOf(event.getSelectedDate()));
            preparedStatement1.setString(6, event.getEstartTime());  // Verify if this is the correct way to store an image
            preparedStatement1.setString(7, event.getEendTime());
            preparedStatement1.setString(8, event.getEventDescription());
            preparedStatement1.executeUpdate();


            System.out.println("Event added successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error adding event to the database."+e.getMessage());
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }
    public ArrayList<Event> getEventList(String date,String clubname) {
        String query = "SELECT e.clubID, e.eventName, e.eventID FROM events e JOIN club c ON e.clubID = c.clubID WHERE e.eventDate=? AND c.clubName = ?";


        ArrayList<Event> eventList = new ArrayList<>();


        try (Connection connection = getConnection();

             PreparedStatement preparedStatement = connection.prepareStatement(query)){
             preparedStatement.setString(1, date);
             preparedStatement.setString(2, clubname);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Event event1 = new Event(resultSet.getString("eventName"));
                resultSet.getString("eventID");
                resultSet.getString("clubID");
                // set other attributes as needed
                eventList.add(event1);
            }
            return eventList;}

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error retrieving club list from the database.");
        }
        return null;
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
