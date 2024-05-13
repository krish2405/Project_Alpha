
import java.sql.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
// import java.util.Date;
import java.util.*;

public class DatabaseManager {
    // JDBC URL, username, and password of MySQL server
    private static final String URL = "jdbc:mysql://localhost:3306/kishlaydb";
    private static final String USER = "root";
    private static final String PASSWORD = "Kishlay123@";

    // JDBC variables for opening, closing, and managing connection
    private static Connection connection;
    private static Statement statement;

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

    public static void createbookstable(String tableName,String columnDefinitions){
        try {
            String query = "CREATE TABLE IF NOT EXISTS " + tableName + " (" + columnDefinitions + ")";
            statement.executeUpdate(query);
            System.out.println("Table created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



    // Login for user and admin
    @SuppressWarnings("resource")
    public static String login() {

        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Please Enter your name");
            String name = sc.nextLine();
           
            System.out.println("Please Enter your password");
            String password = sc.nextLine();


            String query = "SELECT email FROM adminuser WHERE name = ? AND Password = ?";
            // String query2="SELECT * FROM adminuser";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            // System.out.println(resultSet);


            if (resultSet.next()) {
                // System.out.println(resultSet.getString(1));
                String adminornot=resultSet.getString(1);
                // PreparedStatement diStatement = connection.prepareStatement(query2);
                // System.out.println(resultSet.next());
                System.out.println("USer login successfully");
                return adminornot;


            }
            else{
                System.out.println("No user found or password incorrect");
            }
            sc.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Table for user and admin
    public static void createTable(String tableName, String columnDefinitions) {
        try {
            String query = "CREATE TABLE IF NOT EXISTS " + tableName + " (" + columnDefinitions + ")";
            statement.executeUpdate(query);
            System.out.println("Table created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to insert data into the table of useradmin
    // @SuppressWarnings("resource")
    @SuppressWarnings("resource")
    public static void insertData() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter name to insert:");
            String name = scanner.nextLine();

            System.out.println("Enter password to insert:");
            String password = scanner.nextLine();


            System.out.println("Enter age to insert:");
            int age = scanner.nextInt();
            // Consume newline character
            scanner.nextLine(); //
                        System.out.print("Enter DOB (YYYY-MM-DD): ");
                        String dobString = scanner.nextLine();
                        Date dob = null;
            
                        try {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            dob = new Date(dateFormat.parse(dobString).getTime());
                        } catch (ParseException e) {
                            System.err.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
                            e.printStackTrace();
                            return;
                        }
            System.out.println("Enter email to insert:");
            String email = scanner.nextLine();
            System.out.print("Is user an admin? (true/false): ");
            boolean isAdmin = Boolean.parseBoolean(scanner.nextLine());

            // Adjusted the query to exclude the DOB column and use CURRENT_DATE for Signup_date and Modification_date
            String query = "INSERT INTO adminuser (name,Password, age, DOB,Signup_date, Modification_date, email, isadmin) VALUES (?,?,?,?, CURRENT_DATE, CURRENT_DATE, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            preparedStatement.setInt(3, age);
            preparedStatement.setDate(4, dob);
            preparedStatement.setString(5, email);
            preparedStatement.setBoolean(6, isAdmin);
            int rowinserted = preparedStatement.executeUpdate();
            if (rowinserted > 0) {
                System.out.println("Insertion successful");
            }

            System.out.println("Data inserted successfully.");
            scanner.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void ShowAlluser(){
        try {
            String query = "SELECT * FROM adminuser";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
           
            ResultSet rs=preparedStatement.executeQuery();
            System.out.println("Here is the information.");
            while(rs.next()){
                
                System.out.print("User_id : "+ rs.getString(1)+", ");
                System.out.print("Name : "+ rs.getString(2)+", ");
                System.out.print("Age : "+ rs.getString(4)+", ");
                System.out.print("DOB : "+rs.getString(5)+", ");
                System.out.print("Signup_date : "+ rs.getString(6)+", ");
                System.out.print("Modification_date : "+ rs.getString(7)+", ");
                System.out.print("email : "+rs.getString(8)+", ");
                System.out.println("Isadmin :" + rs.getString(9));

            }
           
               
            
        } catch (Exception e) {
            
            e.printStackTrace();
        }

    }

    public static void ShowAllBooks(){
        try {
            String query = "SELECT * FROM books";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
           
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

    public static void USerinfo(){
        try {
            Scanner sc =new Scanner(System.in);
            System.out.println("Enter name:");
            String name=sc.nextLine();

            System.out.println("Enter user ID:");
            int u_id=sc.nextInt();
            
            String query = "SELECT * FROM adminuser WHERE name=? AND u_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, u_id);
            ResultSet rs=preparedStatement.executeQuery();
            if(rs.next()){
                System.out.println("Here is the information.");
                System.out.print("User_id : "+ rs.getString(1)+", ");
                System.out.print("Name : "+ rs.getString(2)+", ");
                System.out.print("Age : "+ rs.getString(4)+", ");
                System.out.print("DOB : "+rs.getString(5)+", ");
                System.out.print("Signup_date : "+ rs.getString(6)+", ");
                System.out.print("Modification_date : "+ rs.getString(7)+", ");
                System.out.print("email : "+rs.getString(8)+", ");
                System.out.println("Isadmin :" + rs.getString(9));

            }
            else{
                System.out.println("User not found in database ");
            }
            
            sc.close();
            
        } catch (Exception e) {
            
            e.printStackTrace();
        }
    } 

    // Method to delete data from the table useradmin
    public static void deleteUserData() {
        try {
            Scanner sc =new Scanner(System.in);
            System.out.println("Enter name:");
            String name=sc.nextLine();

            System.out.println("Enter user ID:");
            int u_id=sc.nextInt();

            String query = "DELETE FROM adminuser WHERE name=? and u_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, u_id);

            int r=preparedStatement.executeUpdate();
            System.out.println(r);
            if(r==1){
            System.out.println("Data deleted successfully.");}
            else{
                System.out.println("No data found");
            }

            sc.close(); 
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // insert book info into books table
    
    public static void insertBooksData() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter Book name :");
            String b_name = scanner.nextLine();

            System.out.println("Enter Geners:");
            String geners = scanner.nextLine();


            System.out.println("Enter author:");
            String author = scanner.nextLine();

            System.out.println("Enter quantity:");
            int quantity = scanner.nextInt();
            
           
            System.out.println("Enter Price:");
            Double price = scanner.nextDouble();

            System.out.println("Enter reviews ");
            Double Reviews = scanner.nextDouble();

            // Adjusted the query to exclude the DOB column and use CURRENT_DATE for Signup_date and Modification_date
            String query = "INSERT INTO books (book_name,geners, author, quantity, price , Reviews) VALUES (?,?,?,?,?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
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
            scanner.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void borrow(){
        try {
            Scanner sc=new Scanner(System.in);
            System.out.println("Enter the book name: ");
            String name=sc.nextLine();
            String query = "SELECT quantity FROM books WHERE book_name=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            ResultSet rs=preparedStatement.executeQuery();
            if (rs.next()) {
                
                int quant=Integer.parseInt(rs.getString(1)) ;
                if(quant>=0){
                    int newquantity=quant-1;
                    String updatequery="UPDATE books SET quantity = ? WHERE book_name=?";
                    PreparedStatement preparedStatement2 = connection.prepareStatement(updatequery);
                    preparedStatement2.setInt(1, newquantity);
                    preparedStatement2.setString(2, name);
                    int count=preparedStatement2.executeUpdate();
                    System.out.println(count);


                }
                else{
                    System.out.println("Sorry book is out of stock ");
                }

            }
            else{
                System.out.println("No book found");
            }
            sc.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Returning book
    public static void returnbook(){
        try {
            Scanner sc=new Scanner(System.in);
            System.out.println("Enter the book name: ");
            String book_name=sc.nextLine();

            System.out.println("Enter your name: ");
            String name=sc.nextLine();
            
            
            String query = "SELECT quantity FROM books WHERE book_name=?";
            String namequery="SELECT name FROM adminuser WHERE name=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, book_name);
            ResultSet rs=preparedStatement.executeQuery();

            PreparedStatement preparedStatementname = connection.prepareStatement(namequery);
            preparedStatementname.setString(1, name);
            ResultSet rs2=preparedStatementname.executeQuery();

            if (rs.next() && rs2.next()) {
                int quant=Integer.parseInt(rs.getString(1)) ;
                if(quant>=0){
                    int newquantity=quant+1;
                    String updatequery="UPDATE books SET quantity = ? WHERE book_name=?";
                    PreparedStatement preparedStatement2 = connection.prepareStatement(updatequery);
                    preparedStatement2.setInt(1, newquantity);
                    preparedStatement2.setString(2, book_name);
                    int count=preparedStatement2.executeUpdate();
                    System.out.println(count);
                }
                else{
                    System.out.println("Sorry book is out of stock ");
                }
            }
            else{
                System.out.println("No book found");
            }
            sc.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // Book information
    public static void Bookinfo(){
        try {
            Scanner sc =new Scanner(System.in);
            System.out.println("Enter Book name:");
            String book_name=sc.nextLine();
            
            String query = "SELECT * FROM books WHERE book_name=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
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
            
            sc.close();
            
        } catch (Exception e) {
            
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

    public static void main(String[] args) {
        connect();

        // Creating a table for adminuser
        // String tableName = "adminuser";
        // String columns = "u_id INT AUTO_INCREMENT PRIMARY KEY, " +
        //         "name VARCHAR(255), " +
        //         "Password VARCHAR(255), "+
        //         "age INT, " + // Added the age column
        //         "DOB DATE, " + 
        //         "Signup_date DATE, " +
        //         "Modification_date DATE, " +
        //         "email VARCHAR(255), " +
        //         "isadmin BOOLEAN";

        // // Create the table
        // createTable(tableName, columns);
        
        // inserting information in useradmin table
        // insertData();

    //    Logging into the system 
        // String admincheck=login();
        // if (admincheck=="0"){
        //     System.out.println("Hello user");
        // }
        // else if (admincheck=="1"){
        //     System.out.println("Hello admin");
        // }
        // if (admincheck==null){
        //     System.out.println("Relogin again");
        // }
        
        // Creating booktable 
        // String tablename="Books";
        // String columndefintion="b_id INT AUTO_INCREMENT PRIMARY KEY, "+
        //                         "book_name VARCHAR(255), "+
        //                         "geners VARCHAR(255), "+
        //                         "author VARCHAR(255), "+
        //                         "quantity INT, "+
        //                         "price DECIMAL(10,2), "+
        //                         "Reviews DECIMAL(10,2) ";


        // createbookstable(tablename,columndefintion);

        // Inserting books info in books table 
        // insertBooksData();
        // borrow();
        // returnbook();
        // Bookinfo();
        // USerinfo();
        // deleteUserData();
        // ShowAlluser();
        ShowAllBooks();

        close();
    }
}

