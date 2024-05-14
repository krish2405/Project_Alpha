

Library Management System

Overview
The Library Management System is a Java application designed to facilitate the management of books, users, and library operations. It provides functionalities for both users and administrators to handle tasks related to borrowing, returning, and maintaining library records.

Functionalities

User Management:

Login: Users and administrators can log in to the system using their credentials. For administrators, this grants access to additional functionalities.

User Information: Users can insert their information into the system, including name, password, age, date of birth, email, and admin status.

Display User Data: The system can display user information, including user ID, name, age, date of birth, signup date, modification date, email, and admin status.

Delete User Data: Administrators can delete user data from the system based on user ID and name.


Book Management:

Table Creation: The system allows for the creation and management of a table for books, including columns for book ID, name, genre, author, quantity, price, and reviews.

Insert Book Data: Users and administrators can insert book information into the system, including book name, genre, author, quantity, price, and reviews.

Display Book Data: The system can display book information, including book ID, name, genre, author, quantity, price, and reviews.

Borrowing and Returning Books: Users can borrow books from the library, which reduces the book's quantity in the system. They can also return books, increasing the quantity accordingly.

Database Connectivity:

Connection Establishment: The system establishes a connection to a MySQL database with specific configurations (host, port, database name, username, password) to store and retrieve data.

Connection Closure: After completing operations, the system closes the database connection to ensure proper resource management.


Requirements
To run this project, you need the following:

Java Development Kit (JDK): Ensure that you have Java installed on your system to compile and run Java programs.
MySQL Database: Set up a MySQL database with the required configurations (host, port, database name, username, password) as specified in the project code.


How to Run
Clone the Repository: Clone the project repository to your local machine using Git or download it as a ZIP file and extract it.
Open in Java IDE: Open the project in your preferred Java Integrated Development Environment (IDE) such as Eclipse, IntelliJ IDEA, or NetBeans.
Import MySQL JDBC Driver: If not already included in the project, import the MySQL JDBC driver to enable database connectivity.
Run the Application: Run the DatabaseManager.java file from your IDE to start the application.


Usage
Login: Upon running the application, users and administrators will be prompted to log in using their credentials.
Perform Operations: Follow the on-screen instructions to perform various operations such as inserting user information, inserting book information, borrowing/returning books, displaying user/book data, etc.


Notes
MySQL Server: Ensure that your MySQL server is running before running the application to establish a connection and perform database operations.
Exception Handling: The application handles exceptions such as invalid inputs, database connection issues, etc., to provide a smooth user experience and prevent runtime errors.
