import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class userbookdatamanagement {

     public static void createuserwithbookTable(String tableName, String columnDefinitions) {
        try {
            String query = "CREATE TABLE IF NOT EXISTS " + tableName + " (" + columnDefinitions + ")";
            Databaseconnection.statement.executeUpdate(query);
            System.out.println("Table created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("resource")
    public static void userswithbookinfo(){
        try {
             Scanner sc =new Scanner(System.in);


            System.out.println("Enter user ID:");
            int u_id=sc.nextInt();

            String b_idquery = "SELECT b_id FROM userwithbook WHERE u_id=?";
            PreparedStatement b_iPreparedStatement = Databaseconnection.connection.prepareStatement(b_idquery);
            b_iPreparedStatement.setInt(1, u_id);
            ResultSet rs3ResultSet = b_iPreparedStatement.executeQuery();
            while(rs3ResultSet.next()){
                int b_id=Integer.parseInt( rs3ResultSet.getString(1));
                // System.out.println(b_id);
                
                String querym="SELECT DISTINCT u.u_id, u.name, b.b_id, b.book_name,ub.borrow_date " +
                "FROM adminuser u " +
                "JOIN userwithbook ub ON u.u_id = ub.u_id " +
                "JOIN books b ON ub.b_id = b.b_id " +
                "WHERE u.u_id = ? AND b.b_id = ?";
                PreparedStatement qPreparedStatement=Databaseconnection.connection.prepareStatement(querym);
                qPreparedStatement.setInt(1,u_id);
                qPreparedStatement.setInt(2,b_id);
                
               
                ResultSet resultSet=qPreparedStatement.executeQuery();
        

                while (resultSet.next()) {
                    int userId = resultSet.getInt(1);
                    String username = resultSet.getString(2);
                    int bookId = resultSet.getInt(3);
                    String bookTitle = resultSet.getString(4);
                    String date=resultSet.getString(5);

                    System.out.println("User ID: " + userId + ", Username: " + username + ", Book ID: " + bookId
                            + ", Title: " + bookTitle + " ,borrow_date: " + date);
                }

            }
            
            // sc.close();


        } catch (Exception e) {
            e.printStackTrace();
           
        }
    }

    public static void alluserwithbookinfo(){
        try{
               String idquery = "SELECT DISTINCT u_id FROM userwithbook";
                PreparedStatement iPreparedStatement = Databaseconnection.connection.prepareStatement(idquery);
            // b_iPreparedStatement.setInt(1, u_id);
                ResultSet rs3ResultSet = iPreparedStatement.executeQuery();

            while(rs3ResultSet.next()){
                int u_id=Integer.parseInt(rs3ResultSet.getString(1));
                // System.out.println(u_id);

                String b_idquery = "SELECT b_id FROM userwithbook where u_id=?";
                PreparedStatement b_iPreparedStatement = Databaseconnection.connection.prepareStatement(b_idquery);
                b_iPreparedStatement.setInt(1, u_id);
                ResultSet B_rs3ResultSet = b_iPreparedStatement.executeQuery();
                while(B_rs3ResultSet.next()){
                int b_id=Integer.parseInt( B_rs3ResultSet.getString(1));
                // System.out.println(b_id);
                
                String querym="SELECT u.u_id, u.name, b.b_id, b.book_name ,ub.borrow_date " +
                "FROM adminuser u " +
                "JOIN userwithbook ub ON u.u_id = ub.u_id " +
                "JOIN books b ON ub.b_id = b.b_id " +
                "WHERE u.u_id = ? AND b.b_id = ?";
                PreparedStatement qPreparedStatement=Databaseconnection.connection.prepareStatement(querym);
                qPreparedStatement.setInt(1,u_id);
                qPreparedStatement.setInt(2,b_id);
               
               
                ResultSet resultSet=qPreparedStatement.executeQuery();
                

                while (resultSet.next()) {
                    int userId = resultSet.getInt(1);
                    String username = resultSet.getString(2);
                    int bookId = resultSet.getInt(3);
                    String bookTitle = resultSet.getString(4);
                    String date=resultSet.getString(5);

                    System.out.println("User ID: " + userId + ", Username: " + username + ", Book ID: " + bookId
                            + ", Title: " + bookTitle+ " ,borrow_date: " + date);
                }

            }

        }
             

        } catch (Exception e) {
            e.printStackTrace();
           
        }

    }
    
}
