import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
// import java.text.ParseException;
// import java.text.SimpleDateFormat;
import java.util.Scanner;
// import java.sql.*;

public class BooksDatamangement {

    public static boolean validateStringInput(String input) {
        // Input should not be empty or null
        return input != null && !input.trim().isEmpty();
    }

    public static boolean validateQuantity(int quantity) {
        // Quantity should be a positive integer
        return quantity > 0;
    }

    public static boolean validateReviews(double reviews) {
        // Reviews should be in the range 0 to 5
        return reviews >= 0 && reviews <= 5;
    }

    // Creating Books table
     public static void createbookstable(String tableName,String columnDefinitions){
        try {
            String query = "CREATE TABLE IF NOT EXISTS " + tableName + " (" + columnDefinitions + ")";
            Databaseconnection.statement.executeUpdate(query);
            System.out.println("Table created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // Check if table exists
    public static boolean tableExists(String tableName) {
        try {
            DatabaseMetaData metaData = Databaseconnection.connection.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, tableName, null);
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Create table if it doesn't exist
    public static void createTableIfNotExists(String tableName, String columnDefinitions) {
        if (!tableExists(tableName)) {
            createbookstable(tableName, columnDefinitions);
        } else {
            System.out.println("Table already exists.");
        }
    }

     public static void ShowAllBooks(){
        try {
            String query = "SELECT * FROM books";
            PreparedStatement preparedStatement = Databaseconnection.connection.prepareStatement(query);
           
            ResultSet rs=preparedStatement.executeQuery();
            System.out.println("Here is the information.");
            while(rs.next()){
                
                System.out.print("Book_id : "+ rs.getString(1)+", ");
                System.out.print(" Book_Name : "+ rs.getString(2)+", ");
                System.out.print("Geners : "+ rs.getString(3)+", ");
                System.out.print("author : "+rs.getString(4)+", ");
                System.out.print("quantity : "+ rs.getString(5)+", ");
                System.out.print("Price : "+ rs.getString(6)+", ");
                System.out.println("Reviews : "+rs.getString(7)+".");
                // System.out.println("Isadmin :" + rs.getString(9));

            }
           
               
            
        } catch (Exception e) {
            
            e.printStackTrace();
        }

    }

    // insert book info into books table
    @SuppressWarnings("resource")
    public static void insertBooksData() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter Book name :");
            String b_name = scanner.nextLine();
            if (!validateStringInput(b_name)) {
                System.out.println("Book name cannot be empty.");
                return;
            }



            System.out.println("Enter Geners:");
            String geners = scanner.nextLine();
            if (!validateStringInput(geners)) {
                System.out.println("Genre cannot be empty.");
                return;
            }


            System.out.println("Enter author:");
            String author = scanner.nextLine();
            if (!validateStringInput(author)) {
                System.out.println("Author cannot be empty.");
                return;
            }

            System.out.println("Enter quantity:");
            int quantity = scanner.nextInt();
            if (!validateQuantity(quantity)) {
                System.out.println("Quantity must be a positive integer.");
                return;
            }
            
           
            System.out.println("Enter Price:");
            Double price = scanner.nextDouble();

            System.out.println("Enter reviews (0 -5) ");
            Double Reviews = scanner.nextDouble();
            if (!validateReviews(Reviews)) {
                System.out.println("Reviews must be in the range 0 to 5.");
                return;
            }

            // Adjusted the query to exclude the DOB column and use CURRENT_DATE for Signup_date and Modification_date
            String query = "INSERT INTO books (book_name,geners, author, quantity, price , Reviews) VALUES (?,?,?,?,?, ?)";
            PreparedStatement preparedStatement = Databaseconnection.connection.prepareStatement(query);
            preparedStatement.setString(1, b_name);
            preparedStatement.setString(2, geners);
            preparedStatement.setString(3, author);
            preparedStatement.setInt(4, quantity);
            preparedStatement.setDouble(5, price);
            preparedStatement.setDouble(6, Reviews);
            int rowinserted = preparedStatement.executeUpdate();
            if (rowinserted > 0) {
                System.out.println("Insertion successful");
            }

            System.out.println("Data inserted successfully.");
            // scanner.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("resource")
    public static void borrow(){
        try {
            Scanner sc=new Scanner(System.in);
            
           
            System.out.println("Enter the Book id : ");
            int b_id=sc.nextInt();

            System.out.println("Enter the User id : ");
            int u_id=sc.nextInt();
            
            String idcheck="SELECT COUNT(*) FROM adminuser WHERE u_id=?";
            PreparedStatement idcheckPreparedStatement= Databaseconnection.connection.prepareStatement(idcheck);
            idcheckPreparedStatement.setInt(1, u_id);
            ResultSet idc = idcheckPreparedStatement.executeQuery();
            idc.next();
            int count1 = idc.getInt(1);

        if (count1 == 0) {
            System.out.println("No user found");
            // sc.close();
            return;

        }

        String bidcheck="SELECT COUNT(*) FROM books WHERE b_id=?";
        PreparedStatement bidcheckPreparedStatement= Databaseconnection.connection.prepareStatement(bidcheck);
        bidcheckPreparedStatement.setInt(1, b_id);
        ResultSet bidc = bidcheckPreparedStatement.executeQuery();
        bidc.next();
        int countr = bidc.getInt(1);
        if (countr == 0) {
            System.out.println("No Book found");
            // sc.close();
            return;

        }

            


            String checkQuery = "SELECT COUNT(*) FROM userwithbook WHERE b_id = ? AND u_id = ?";
            PreparedStatement checkStatement = Databaseconnection.connection.prepareStatement(checkQuery);
            checkStatement.setInt(1, b_id);
            checkStatement.setInt(2, u_id);
            ResultSet checkResult = checkStatement.executeQuery();
            checkResult.next();
            int count = checkResult.getInt(1);

        if (count > 0) {
            System.out.println("User already has the book.");
        } else {
            sc.nextLine();
            System.out.println("Enter the book name: ");
            String name=sc.nextLine();
            String query = "SELECT quantity FROM books WHERE book_name=? AND b_id=?";
            PreparedStatement preparedStatement = Databaseconnection.connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, b_id);
            ResultSet rs=preparedStatement.executeQuery();
            if (rs.next()) {
                
                int quant=Integer.parseInt(rs.getString(1)) ;
                if(quant>0){
                    int newquantity=quant-1;
                    String updatequery="UPDATE books SET quantity = ? WHERE book_name=?";
                    PreparedStatement preparedStatement2 = Databaseconnection.connection.prepareStatement(updatequery);
                    preparedStatement2.setInt(1, newquantity);
                    preparedStatement2.setString(2, name);
                    int count2=preparedStatement2.executeUpdate();
                    System.out.println(count2);

                     // Get the current date for borrow_date
                     java.util.Date currentDate = new java.util.Date();
                     java.sql.Date borrowDate = new java.sql.Date(currentDate.getTime());

                     // Add the borrowed book to the userwithbook table
                     String addUserBookQuery = "INSERT INTO userwithbook (b_id, u_id, borrow_date) VALUES (?, ?, ?)";
                     PreparedStatement addUserBookStatement = Databaseconnection.connection
                             .prepareStatement(addUserBookQuery);
                     addUserBookStatement.setInt(1, b_id);
                     addUserBookStatement.setInt(2, u_id);
                     addUserBookStatement.setDate(3, borrowDate);
                     int insertCount = addUserBookStatement.executeUpdate();
                     System.out.println(insertCount);
                   

                }
                else{
                    System.out.println("Sorry book is out of stock ");
                }

            }
            else{
                System.out.println("No book found");
            }
            // sc.close();
        } }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Returning book
    @SuppressWarnings("resource")
    public static void returnbook(){
        try {
            Scanner sc=new Scanner(System.in);
            System.out.println("Enter the book id: ");
            int b_id=sc.nextInt();

            System.out.println("Enter your id: ");
            int u_id=sc.nextInt();

            String idcheck="SELECT COUNT(*) FROM adminuser WHERE u_id=?";
            PreparedStatement idcheckPreparedStatement= Databaseconnection.connection.prepareStatement(idcheck);
            idcheckPreparedStatement.setInt(1, u_id);
            ResultSet idc = idcheckPreparedStatement.executeQuery();
            idc.next();
            int count1 = idc.getInt(1);

        if (count1 == 0) {
            System.out.println("No user found");
            // sc.close();
            return;

        }

        String bidcheck="SELECT COUNT(*) FROM books WHERE b_id=?";
        PreparedStatement bidcheckPreparedStatement= Databaseconnection.connection.prepareStatement(bidcheck);
        bidcheckPreparedStatement.setInt(1, b_id);
        ResultSet bidc = bidcheckPreparedStatement.executeQuery();
        bidc.next();
        int countr = bidc.getInt(1);
        if (countr == 0) {
            System.out.println("No Book found");
            // sc.close();
            return;

        }

        String checkQuery = "SELECT COUNT(*) FROM userwithbook WHERE b_id = ? AND u_id = ?";
        PreparedStatement checkStatement = Databaseconnection.connection.prepareStatement(checkQuery);
        checkStatement.setInt(1, b_id);
        checkStatement.setInt(2, u_id);
        ResultSet checkResult = checkStatement.executeQuery();
        checkResult.next();
        int count = checkResult.getInt(1);

    if (count > 0) {
        // System.out.println("User already has the book.");}
            
            String query = "SELECT quantity FROM books WHERE b_id=?";
            String namequery="SELECT name FROM adminuser WHERE  u_id=?";
            PreparedStatement preparedStatement = Databaseconnection.connection.prepareStatement(query);
            preparedStatement.setInt(1, b_id);
            ResultSet rs=preparedStatement.executeQuery();

            PreparedStatement preparedStatementname = Databaseconnection.connection.prepareStatement(namequery);
            preparedStatementname.setInt(1, u_id);
            ResultSet rs2=preparedStatementname.executeQuery();

            if (rs.next() && rs2.next()) {
                int quant=Integer.parseInt(rs.getString(1)) ;
                // if(quant>=0){
                    int newquantity=quant+1;
                    String updatequery="UPDATE books SET quantity = ? WHERE b_id=?";
                    PreparedStatement preparedStatement2 = Databaseconnection.connection.prepareStatement(updatequery);
                    preparedStatement2.setInt(1, newquantity);
                    preparedStatement2.setInt(2, b_id);
                    int count12=preparedStatement2.executeUpdate();
                    System.out.println(count12);

                    // java.util.Date currentDate = new java.util.Date();
                    //  java.sql.Date returnDate = new java.sql.Date(currentDate.getTime());

                     // Add the borrowed book to the userwithbook table
                     String addUserBookQuery = "DELETE FROM userwithbook where b_id=? AND u_id=?";
                     PreparedStatement addUserBookStatement = Databaseconnection.connection
                             .prepareStatement(addUserBookQuery);
                     addUserBookStatement.setInt(1, b_id);
                     addUserBookStatement.setInt(2, u_id);
                    //  addUserBookStatement.setDate(3, returnDate);
                     int insertCount = addUserBookStatement.executeUpdate();
                     System.out.println(insertCount);

                
            }
            else{
                System.out.println("No book found");
            }
            // sc.close();
        }
        else{
            System.out.println("You don't have any book to return.");
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    
    // Book information
    @SuppressWarnings("resource")
    public static void Bookinfo(){
        try {
            Scanner sc =new Scanner(System.in);
            System.out.println("Enter Book name:");
            String book_name=sc.nextLine();
            
            String query = "SELECT * FROM books WHERE book_name=?";
            PreparedStatement preparedStatement = Databaseconnection.connection.prepareStatement(query);
            preparedStatement.setString(1, book_name);
            ResultSet rs=preparedStatement.executeQuery();
            if(rs.next()){
                System.out.println("Here is the information.");
                System.out.print("id : "+ rs.getString(1)+", ");
                System.out.print("Book name : "+ rs.getString(2)+", ");
                System.out.print("Gener : "+ rs.getString(3)+", ");
                System.out.print("Author : "+rs.getString(4)+", ");
                System.out.print("Quantity : "+ rs.getString(5)+", ");
                System.out.print("Price : "+ rs.getString(6)+", ");
                System.out.println("Reviews : "+rs.getString(7)+".");

            }
            else{
                System.out.println("Book not found in database ");
            }
            
            // sc.close();
            
        } catch (Exception e) {
            
            e.printStackTrace();
        }
    } 

    @SuppressWarnings("resource")
    public static void Update_Book(){
        try {

            // java.util.Date currentDate = new java.util.Date();
            // java.sql.Date sqlCurrentDate = new java.sql.Date(currentDate.getTime());
                Scanner sc =new Scanner(System.in);
                System.out.println("Enter Your U_id");
                int u_id=sc.nextInt();
                sc.nextLine();
                System.out.println("Enter Your Password");
                String Password=sc.nextLine();

                String querylogin = "SELECT isadmin FROM adminuser WHERE u_id=? AND Password=?";
                PreparedStatement preparedStatementlogin=Databaseconnection.connection.prepareStatement(querylogin);
                preparedStatementlogin.setInt(1,u_id) ;
                preparedStatementlogin.setString(2,Password);
                
                ResultSet adminlogin=preparedStatementlogin.executeQuery();

                if(adminlogin.next()){
                 System.out.println(adminlogin.getString(1));
                if(adminlogin.getString(1).equals("1")){
                

                System.out.println("Enter field to update :");
                
                System.out.println("1.)Book Name 2.) Gener 3.) Author 4.) Quantity 5) Price  6.)Reviews 7.) Exit ");
                int choice=sc.nextInt();
                sc.nextLine();
                System.out.println("Enter Your b_id");
                int b_id=sc.nextInt();
                
                switch (choice) {

                    
                    case 1:
                    sc.nextLine();
                    System.out.println("Enter Your Book Name");
                    String name=sc.nextLine();
                    

                    String query = "UPDATE books SET book_name=? where b_id=?";
                    PreparedStatement preparedStatement = Databaseconnection.connection.prepareStatement(query);
                    preparedStatement.setString(1,name) ;
                    preparedStatement.setInt(2, b_id);
                    int rowsUpdatedName = preparedStatement.executeUpdate();
                    if (rowsUpdatedName > 0) {
                        System.out.println("Book name updated successfully.");
                    }
                    else{
                        System.out.println("Sorry Password or User id does not match");
                    }
                        break;

                    case 2:
                    sc.nextLine();
                    System.out.println("Enter Geners:");
                    String geners = sc.nextLine();
                    
                
                    String query2 = "UPDATE books SET geners=? where b_id=?";
                    PreparedStatement preparedStatement2 = Databaseconnection.connection.prepareStatement(query2);
                    preparedStatement2.setString(1,geners) ;
                    preparedStatement2.setInt(2, b_id);
                    int rowsUpdatedAge = preparedStatement2.executeUpdate();
                    if (rowsUpdatedAge > 0) {
                        System.out.println("Geners updated successfully.");
                    }
                    else{
                        System.out.println("Sorry Password or User id does not match");
                    }
                        break;

                    case 3:
                    sc.nextLine();
                        System.out.println("Enter Author:");
                        String author = sc.nextLine();
                        String queryPassword = "UPDATE books SET author=? where b_id=?";
                        PreparedStatement preparedstatementauthor = Databaseconnection.connection.prepareStatement(queryPassword);
                        preparedstatementauthor.setString(1, author);
                        preparedstatementauthor.setInt(2, b_id);
                      
                        int rowsUpdatedPassword = preparedstatementauthor.executeUpdate();
                        if (rowsUpdatedPassword > 0) {
                            System.out.println("Password updated successfully.");
                        }
                        else{
                            System.out.println("Sorry Password or User id does not match");
                        }
                        break;

                    case 4:
                    sc.nextLine();
                        System.out.println("Quantity");
                        int Quantity = sc.nextInt();
                       
                        String queryQunatity= "UPDATE books SET quantity=? WHERE b_id=?";
                        PreparedStatement preparedstatmentquantity = Databaseconnection.connection.prepareStatement(queryQunatity);
                        preparedstatmentquantity.setInt(1, Quantity);
                        
                        preparedstatmentquantity.setInt(2, b_id);
                    
                        int rowupdatequant = preparedstatmentquantity.executeUpdate();
                        if (rowupdatequant > 0) {
                            System.out.println("Quantity updated successfully.");
                        }
                        else{
                            System.out.println("Sorry Password or User id does not match");
                        }
                        break;
                    
                    case 5:
                    sc.nextLine();
                        System.out.println("Enter price:");
                        Double Price = sc.nextDouble();
                        String queryPrice = "UPDATE books SET price=? WHERE b_id=?";
                        PreparedStatement preparedStatementprice = Databaseconnection.connection.prepareStatement(queryPrice);
                        preparedStatementprice.setDouble(1, Price);
                        preparedStatementprice.setInt(2, b_id);

                        int rowsUpdatedprice = preparedStatementprice.executeUpdate();
                        if (rowsUpdatedprice > 0) {
                            System.out.println("Price updated successfully.");
                        }
                        else{
                            System.out.println("Sorry Password or User id does not match");
                        }
                        break;

                        case 6:
                        sc.nextLine();
                        System.out.println("Enter Reviews (1 to 5):");
                        Double Reviews = sc.nextDouble();
                        String queryreviews = "UPDATE books SET Reviews=? WHERE b_id=? ";
                        PreparedStatement preparedStatementreviews = Databaseconnection.connection.prepareStatement(queryreviews);
                        preparedStatementreviews.setDouble(1, Reviews);
                        preparedStatementreviews.setInt(2, b_id);
                        int rowsupdatereviews = preparedStatementreviews.executeUpdate();
                        if (rowsupdatereviews > 0) {
                            System.out.println("Price updated successfully.");
                        }
                        else{
                            System.out.println("Sorry Password or User id does not match");
                        }
                        break;
                
                    default:
                        break;
                }

                // sc.close();
            
        } }
        else{
            System.out.println("You are not admin");
        }
    }
    
        catch (Exception e) {
            e.printStackTrace();
        }
               

    }
 
}
