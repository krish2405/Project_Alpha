import java.sql.*;

public class Databaseconnection {
     // JDBC URL, username, and password of MySQL server
     private static final String URL = "jdbc:mysql://localhost:3306/kishlaydb";
     private static final String USER = "root";
     private static final String PASSWORD = "Kishlay123@";
 
     // JDBC variables for opening, closing, and managing connection
     public static Connection connection;
     public static Statement statement;
 
     // Method to establish a connection to the database
     public static void connect() {
         try {
             connection = DriverManager.getConnection(URL, USER, PASSWORD);
             statement = connection.createStatement();
             System.out.println("Connected to the database.");
         } catch (SQLException e) {
             e.printStackTrace();
         }
     }

     // Method to close the connection
    public static void close() {
        try {
            if (statement != null)
                statement.close();
            if (connection != null)
                connection.close();
            System.out.println("Disconnected from database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
