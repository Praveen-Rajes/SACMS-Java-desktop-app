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
    public boolean checkClubIdExists(String clubId) {
        String query = "SELECT COUNT(*) FROM club WHERE clubID = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, clubId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately, e.g., show an error message to the user
        }

        return false; // Default to false in case of an error
    }


    public void updateClub(Club club) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Establish a database connection (you need to implement getConnection method)
            connection = getConnection();

            // Define the SQL query to update club details
            String updateQuery = "UPDATE club SET clubName=?, clubCategory=?, clubDescription=? WHERE clubID=?";

            // Create a prepared statement
            preparedStatement = connection.prepareStatement(updateQuery);

            // Set the parameters
            preparedStatement.setString(1, club.getName());
            preparedStatement.setString(2, club.getCategory());
            preparedStatement.setString(3, club.getDescription());
            preparedStatement.setString(4, club.getClubID());

            // Execute the update
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        } finally {
            // Close the resources
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle exceptions appropriately
            }
        }
    }
    public static void addStudent(Student student){
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
    public static void addAdvisor(ClubAdvisor advisor){
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
    public static void addStudentLogin(Student student) {
        String query1 = "INSERT INTO studentlogin(studentId, loginPassword) VALUES(?, ?)";

        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement1 = connection.prepareStatement(query1);

            preparedStatement1.setInt(1, student.getStudentId());

            // Assuming you have a method getPassword() in Student class
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
    public static void addAdvisorLogin(ClubAdvisor advisor) {
        String query1 = "INSERT INTO advisorlogin(advisorId, loginPassword) VALUES(?, ?)";

        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement1 = connection.prepareStatement(query1);

            preparedStatement1.setInt(1, advisor.getAdvisorId());

            // Assuming you have a method getPassword() in Student class
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
    public ClubAdvisor getAdvisorById(int advisorId) {
        String query = "SELECT al.advisorID, a.advisorFirstName, a.advisorLastName " +
                "FROM clubadvisor a " +
                "JOIN advisorlogin al ON al.advisorID = a.advisorID " +
                "WHERE al.advisorID = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, advisorId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                ClubAdvisor advisor = new ClubAdvisor(resultSet.getInt("advisorID"));
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

    public Student getStudentById(int studentId) {
        String query = "SELECT sl.studentID, s.studentFName, s.studentLName, s.dob, s.gender " +
                "FROM student s " +
                "JOIN studentlogin sl ON sl.studentID = s.studentID " +
                "WHERE sl.studentID = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, studentId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Student student = new Student();
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

    public ClubAdvisor getAdvisorByLogin(int advisorId, String password) {
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

    public Student getStudentByLogin(int studentId, String password) {
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
    public ArrayList<Attendance> getEventListForAttendance(String clubName) {
        String query = "SELECT e.eventName, e.eventStartTime, e.eventEndTime FROM events AS e JOIN club AS c ON e.clubID = c.clubID WHERE clubName=?;";
        ArrayList<Attendance> eventDataList = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, clubName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    // Create an Attendance object and set its properties
                    Attendance attendance = new Attendance(
                            clubName,
                            resultSet.getString("eventName"),
                            resultSet.getString("eventStartTime"),
                            resultSet.getString("eventEndTime"),
                            null, // You may need to adjust this depending on your use case
                            null  // You may need to adjust this depending on your use case
                    );
                    // Add the Attendance object to the list
                    eventDataList.add(attendance);
                }
                return eventDataList;

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
    public ArrayList<Attendance> getStudentListForAttendance(String clubName) {
        String query = "SELECT s.studentID, s.studentFName FROM student s LEFT JOIN student_club sc ON s.studentID = sc.studentID RIGHT JOIN club c ON sc.clubID = c.clubID WHERE c.clubName = ?;";
        ArrayList<Attendance> studentDataList = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, clubName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    // Create an Attendance object and set its properties
                    Attendance attendance = new Attendance(
                            clubName,
                            resultSet.getString("studentID"),
                            resultSet.getString("studentFName"));
                    // Add the Attendance object to the list
                    studentDataList.add(attendance);
                }
                return studentDataList;

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
    public ArrayList<Event> getEventNameList(String clubID) {
        String query = "SELECT eventName FROM events WHERE clubName=?";


        ArrayList<Event> eventNameList = new ArrayList<>();


        try (Connection connection = getConnection();

             PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, clubID);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    Event event1 = new Event(resultSet.getString("eventName"));

                    // set other attributes as needed
                    eventNameList.add(event1);
                }
                return eventNameList;}

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error retrieving club list from the database.");
        }
        return null;
    }
    public static void addAttendance(Attendance attendance){
        String query1 = "INSERT INTO attendance(studentID,studentName, clubName, eventName, attendance) VALUES(?,?,?,?,?)";


        Connection connection = null;
        try{
            connection = getConnection();
            PreparedStatement preparedStatement1 = connection.prepareStatement(query1);


            preparedStatement1.setInt(1, Integer.valueOf(attendance.getStudentId()));
            preparedStatement1.setString(2, attendance.getStudentName());
            preparedStatement1.setString(3, attendance.getClubName());
            preparedStatement1.setString(4, attendance.getEventName());
            preparedStatement1.setString(5, attendance.getAttendance());


            preparedStatement1.executeUpdate();
            System.out.println("Data Inserted to Attendance table");

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


    public static Connection getConnection() {
        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            String databaseurl = "jdbc:mysql://localhost:3306/clubmaster";
            String username = "root";
            String password = "";
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(databaseurl, username, password);
            System.out.println("Database Connected");
            return conn;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;

    }

    public String[][] retrieveAllEventData() {
        String query = "SELECT * FROM events";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            // Get the metadata to determine the number of columns
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Get the number of rows in the result set
            int rowCount = 0;
            while (resultSet.next()) {
                rowCount++;
            }

            // Move the cursor back to the beginning
            resultSet.beforeFirst();

            // Create a two-dimensional array to store the data
            String[][] eventDataArray = new String[rowCount][columnCount];

            int index = 0;
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    eventDataArray[index][i - 1] = resultSet.getString(i);
                }
                index++;
            }

            // Now eventDataArray contains the retrieved data
            return eventDataArray;


        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error retrieving event data from the database.");
        }
        return null;
    }

    public String getAttendanceDataForEvent(String eventName) {
        return eventName;
    }

    public ArrayList<String> getEventList() {
        return null;
    }
}