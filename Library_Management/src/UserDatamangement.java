import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
// import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UserDatamangement {

    // Validations
    public static boolean validateName(String name) {
        // Name should not be empty
        return !name.trim().isEmpty();
    }

    public static boolean validatePassword(String password) {
        // Password should have at least one special character
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }

    public static boolean validateEmail(String email) {
        // Email should contain '@'
        return email.contains("@");
    }



    // Table for user and admin
    public static void createTable(String tableName, String columnDefinitions) {
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
    public static int createTableIfNotExists(String tableName, String columnDefinitions) {
        if (!tableExists(tableName)) {
            createTable(tableName, columnDefinitions);
            System.out.println("Table has been created");
            return 1;
        } else {
            System.out.println("Table already exists.");
            return 0;
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


            String query = "SELECT isadmin FROM adminuser WHERE name = ? AND Password = ?";
            PreparedStatement preparedStatement = Databaseconnection.connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();


            if (resultSet.next()) {
                String adminornot=resultSet.getString(1);
                System.out.println(adminornot);
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

    public static void createadmin(){
        try {
            String query = "INSERT INTO adminuser (name,Password, age, DOB,Signup_date, Modification_date, email, isadmin) VALUES (?,?,?,?, CURRENT_DATE, CURRENT_DATE, ?, ?)";
            PreparedStatement preparedStatement = Databaseconnection.connection.prepareStatement(query);
            String dobstring="2002-06-12";
            Date dob = null;
            
                        try {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            dob = new Date(dateFormat.parse(dobstring).getTime());
                        } catch (ParseException e) {
                            System.err.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
                            e.printStackTrace();
                            return;
                        }
            preparedStatement.setString(1, "admin1");
            preparedStatement.setString(2, "password@123");
            preparedStatement.setInt(3, 23);
            preparedStatement.setDate(4, dob);
            preparedStatement.setString(5, "admin@123");
            preparedStatement.setBoolean(6, true);
            int rowinserted = preparedStatement.executeUpdate();
            if (rowinserted > 0) {
                System.out.println("Insertion successful");
            }

            System.out.println("Data inserted successfully.");

            
        } catch (SQLException e) {
            e.printStackTrace();
            
        }

    }

    // Method to insert data into the table of useradmin
    
    @SuppressWarnings("resource")
    public static void insertData() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter name to insert:");
            String name = scanner.nextLine();
            if (!validateName(name)) {
                System.out.println("Name cannot be empty.");
                return;
            }

            System.out.println("Enter password to insert:");
            String password = scanner.nextLine();
            if (!validatePassword(password)) {
                System.out.println("Password must contain at least one special character.");
                return;
            }


            
            int age;
           
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

            // Calculate age
        Calendar dobCal = Calendar.getInstance();
        dobCal.setTime(dob);
        Calendar now = Calendar.getInstance();
        age = now.get(Calendar.YEAR) - dobCal.get(Calendar.YEAR);
        if (now.get(Calendar.DAY_OF_YEAR) < dobCal.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
            System.out.println("Enter email to insert:");
            String email = scanner.nextLine();
            if (!validateEmail(email)) {
                System.out.println("Email must contain '@'.");
                return;
            }

            System.out.print("Is user an admin? (true/false): ");
            boolean isAdmin = Boolean.parseBoolean(scanner.nextLine());

            // Adjusted the query to exclude the DOB column and use CURRENT_DATE for Signup_date and Modification_date
            String query = "INSERT INTO adminuser (name,Password, age, DOB,Signup_date, Modification_date, email, isadmin) VALUES (?,?,?,?, CURRENT_DATE, CURRENT_DATE, ?, ?)";
            PreparedStatement preparedStatement = Databaseconnection.connection.prepareStatement(query);
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
            // scanner.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void ShowAlluser(){
        try {
            String query = "SELECT * FROM adminuser";
            PreparedStatement preparedStatement = Databaseconnection.connection.prepareStatement(query);
           
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

   
    @SuppressWarnings("resource")
    public static void USerinfo(){
        try {
            Scanner sc =new Scanner(System.in);
            System.out.println("Enter name:");
            String name=sc.nextLine();

            System.out.println("Enter user ID:");
            int u_id=sc.nextInt();
            
            String query = "SELECT * FROM adminuser WHERE name=? AND u_id=?";
            PreparedStatement preparedStatement = Databaseconnection.connection.prepareStatement(query);
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
            
            // sc.close();
            
        } catch (Exception e) {
            
            e.printStackTrace();
        }
    } 

    // Method to delete data from the table useradmin
    @SuppressWarnings("resource")
    public static void deleteUserData() {
        try {
            Scanner sc =new Scanner(System.in);
            System.out.println("Enter name:");
            String name=sc.nextLine();

            System.out.println("Enter user ID:");
            int u_id=sc.nextInt();

            String checkQuery = "SELECT COUNT(*) FROM userwithbook WHERE u_id = ?";
            PreparedStatement checkStatement = Databaseconnection.connection.prepareStatement(checkQuery);
            // checkStatement.setInt(1, b_id);
            checkStatement.setInt(1, u_id);
            ResultSet checkResult = checkStatement.executeQuery();
            checkResult.next();
            int count = checkResult.getInt(1);

        if (count > 0) {
            System.out.println("You have book in your account .Your id caanot be deleted.");
            return ;
        }

            String query = "DELETE FROM adminuser WHERE name=? and u_id=?";
            PreparedStatement preparedStatement = Databaseconnection.connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, u_id);

            int r=preparedStatement.executeUpdate();
            System.out.println(r);
            if(r==1){
            System.out.println("Data deleted successfully.");}
            else{
                System.out.println("No data found");
            }

            // sc.close(); 
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
  
    @SuppressWarnings("resource")
    public static void Update_user(){
        try {

            java.util.Date currentDate = new java.util.Date();
            java.sql.Date sqlCurrentDate = new java.sql.Date(currentDate.getTime());
                Scanner sc =new Scanner(System.in);
                System.out.println("Enter Your U_id");
                int u_id=sc.nextInt();
                sc.nextLine();
                System.out.println("Enter Your Password");
                String Password=sc.nextLine();
                System.out.println("Enter field to update :");
                System.out.println("1.)Name 2.) Password 3.)DOB 4) Email 5.)Exit");
                int choice=sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1:
                    System.out.println("Enter Your Name");
                    String name=sc.nextLine();
                    String query = "UPDATE adminuser SET name=? , Modification_date =? where u_id=? and Password=?";
                    PreparedStatement preparedStatement = Databaseconnection.connection.prepareStatement(query);
                    preparedStatement.setString(1,name) ;
                    preparedStatement.setDate(2, sqlCurrentDate);
                    preparedStatement.setInt(3, u_id);
                    preparedStatement.setString(4, Password);
                    int rowsUpdatedName = preparedStatement.executeUpdate();
                    if (rowsUpdatedName > 0) {
                        System.out.println("Name updated successfully.");
                    }
                    else{
                        System.out.println("Sorry Password or User id does not match");
                    }
                        break;

                    // case 2:
                    // System.out.println("Enter Your Age:");
                    // int age = sc.nextInt();
                    // String query2 = "UPDATE adminuser SET age=? , Modification_date =? where u_id=? and Password=?";
                    // PreparedStatement preparedStatement2 = Databaseconnection.connection.prepareStatement(query2);
                    // preparedStatement2.setInt(1,age) ;
                    // preparedStatement2.setDate(2, sqlCurrentDate);
                    // preparedStatement2.setInt(3, u_id);
                    // preparedStatement2.setString(4, Password);
                    // int rowsUpdatedAge = preparedStatement2.executeUpdate();
                    // if (rowsUpdatedAge > 0) {
                    //     System.out.println("Age updated successfully.");
                    // }
                    // else{
                    //     System.out.println("Sorry Password or User id does not match");
                    // }
                    //     break;

                    case 2:
                        System.out.println("Enter Your Password:");
                        String password = sc.nextLine();
                        String queryPassword = "UPDATE adminuser SET Password=?, Modification_date=? WHERE u_id=? AND Password=?";
                        PreparedStatement preparedStatementPassword = Databaseconnection.connection.prepareStatement(queryPassword);
                        preparedStatementPassword.setString(1, password);
                        preparedStatementPassword.setDate(2, sqlCurrentDate);
                        preparedStatementPassword.setInt(3, u_id);
                        preparedStatementPassword.setString(4, Password);
                        int rowsUpdatedPassword = preparedStatementPassword.executeUpdate();
                        if (rowsUpdatedPassword > 0) {
                            System.out.println("Password updated successfully.");
                        }
                        else{
                            System.out.println("Sorry Password or User id does not match");
                        }
                        break;

                    case 3:
                        System.out.println("Enter Your DOB (YYYY-MM-DD):");
                        String dobString = sc.nextLine();
                        Date dob = null;
                        try {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            dob = new Date(dateFormat.parse(dobString).getTime());
                        } catch (ParseException e) {
                            System.err.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
                            e.printStackTrace();
                        }
                        String queryDOB = "UPDATE adminuser SET DOB=?, Modification_date=? WHERE u_id=? AND Password=?";
                        PreparedStatement preparedStatementDOB = Databaseconnection.connection.prepareStatement(queryDOB);
                        preparedStatementDOB.setDate(1, dob);
                        preparedStatementDOB.setDate(2, sqlCurrentDate);
                        preparedStatementDOB.setInt(3, u_id);
                        preparedStatementDOB.setString(4, Password);
                        int rowsUpdatedDOB = preparedStatementDOB.executeUpdate();
                        if (rowsUpdatedDOB > 0) {
                            System.out.println("DOB updated successfully.");
                        }
                        else{
                            System.out.println("Sorry Password or User id does not match");
                        }
                        break;
                    
                    case 4:
                        System.out.println("Enter Your Email:");
                        String email = sc.nextLine();
                        String queryEmail = "UPDATE adminuser SET email=?, Modification_date=? WHERE u_id=? AND Password=?";
                        PreparedStatement preparedStatementEmail = Databaseconnection.connection.prepareStatement(queryEmail);
                        preparedStatementEmail.setString(1, email);
                        preparedStatementEmail.setDate(2, sqlCurrentDate);
                        preparedStatementEmail.setInt(3, u_id);
                        preparedStatementEmail.setString(4, Password);
                        int rowsUpdatedEmail = preparedStatementEmail.executeUpdate();
                        if (rowsUpdatedEmail > 0) {
                            System.out.println("Email updated successfully.");
                        }
                        else{
                            System.out.println("Sorry Password or User id does not match");
                        }
                        break;
                
                    default:
                        break;
                }

                // sc.close();
            
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
               

    }

    

}
