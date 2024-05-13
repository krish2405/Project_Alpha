import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
// import java.io.*;
public class App {
    public static void main(String[] args) throws Exception {
        String url = "jdbc:mysql://localhost:3306/kishlaydb";
        String username = "root";
        String password = "Kishlay123@";
        String query = "select * from person";
        
        // Class.forName("com.mysql.cj.jdbc.Driver"); // Make sure the driver class is loaded
        
        try (Connection con = DriverManager.getConnection(url, username, password);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            
            System.out.println("Connection Established successfully");
            
            if (rs.next()) {
                int name = rs.getInt("cid");
                System.out.println(name);
            } else {
                System.out.println("No records found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

