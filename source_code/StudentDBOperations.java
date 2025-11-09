import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import  java.sql.ResultSet;
import  java.sql.Date;

public class StudentDBOperations {

    private final String url = "jdbc:postgresql://localhost:5432/A3Students";
    private final String username = "postgres";
    private final String password = "Yellowbear9";


    //Prints all the students data
    public void getAllStudents(){
        //SQL Query
        String SQL = "select * from students";

        //Attempting Query
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
             ResultSet rs = pstmt.executeQuery();
             while (rs.next()){
                 //Print Statements
                 System.out.println("Student ID: " + rs.getString("student_id"));
                 System.out.println("First Name: " + rs.getString("first_name"));
                 System.out.println("Last Name: " + rs.getString("last_name"));
                 System.out.println("Email: " + rs.getString("email"));
                 System.out.println("Enrollment Date: " + rs.getString("enrollment_date"));
                 System.out.println();

             }

        //Failed Attempt
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Add a student
    public void addStudent(String first_name, String last_name, String email, Date enrollment_date) {
        //SQL Statement that takes in all variables and Inserts a Student
        String SQL = "INSERT INTO students(first_name,last_name,email,enrollment_date) VALUES(?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            //Set the variables to the Parameter
            pstmt.setString(1, first_name);
            pstmt.setString(2, last_name);
            pstmt.setString(3, email);
            pstmt.setDate(4, enrollment_date);
            pstmt.executeUpdate();
            System.out.println("Student added successfully!");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Update student's email based on Student ID
    public void updateStudentEmail(int student_id, String new_email) {
        //SQL Statement to Update
        String SQL = "UPDATE students SET email=? WHERE student_id=?";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            //Set Parameter to Variables
            pstmt.setInt(2, student_id);
            pstmt.setString(1, new_email);
            pstmt.executeUpdate();
            System.out.println("student email updated!");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Delete student based on student ID
    public void deleteStudent(int student_id) {
        String SQL = "DELETE FROM students WHERE student_id=?";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            //Set Parameters up
            pstmt.setInt(1, student_id);
            pstmt.executeUpdate();
            System.out.println("student deleted!");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) {
        StudentDBOperations dbOps = new StudentDBOperations();
        Scanner scanner = new Scanner(System.in);

        //Scanner that asks for you to augment Data with predefined names
        System.out.println("Would you like see all Students? (yes/no)");
        if (scanner.nextLine().equalsIgnoreCase("yes")) {
            dbOps.getAllStudents();
        }

        // Add a student
        System.out.println("Would you like to add a student? (yes/no)");
        if (scanner.nextLine().equalsIgnoreCase("yes")) {
            dbOps.addStudent("Kim", "Dokja","KimDokja@example.com" ,Date.valueOf("2023-09-09"));
            dbOps.getAllStudents();
        }

        // Modify student's email
        System.out.println("Would you like to modify a student's email? (yes/no)");
        if (scanner.nextLine().equalsIgnoreCase("yes")) {
            dbOps.updateStudentEmail(4 , "KimDokja1@example.com");
            dbOps.getAllStudents();
        }

        // Delete student
        System.out.println("Would you like to delete a student? (yes/no)");
        if (scanner.nextLine().equalsIgnoreCase("yes")) {
            dbOps.deleteStudent(4);
            dbOps.getAllStudents();
        }

        scanner.close();
    }
}