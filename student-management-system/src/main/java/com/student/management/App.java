package com.student.management;

import com.student.management.dao.UserDAO;
import com.student.management.dao.StudentDAO;
import com.student.management.model.Student;
import com.student.management.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;
import java.util.Scanner;

public class App {

    private UserDAO userDAO = new UserDAO();
    private StudentDAO studentDAO = new StudentDAO();
    private Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    public void run() {
        System.out.println("===== Welcome to the Student Management System =====");
        System.out.println("\nPlease log in to continue.");

        User loggedInUser = null;

        // Login loop until successful login
        while (loggedInUser == null) {
            System.out.print("Username: ");
            String username = scanner.nextLine();

            System.out.print("Password: ");
            String password = scanner.nextLine();

            loggedInUser = attemptLogin(username, password);

            if (loggedInUser == null) {
                System.out.println("Invalid username or password. Please try again.\n");
            }
        }

        System.out.println("\nLogin successful! Welcome, " + loggedInUser.getUsername() + ".");

        // Role-based menu dispatch
        if ("admin".equalsIgnoreCase(loggedInUser.getRole())) {
            adminMenu();
        } else {
            userMenu();
        }
    }

    /**
     * Attempts to log in a user by verifying username and password.
     * Returns the User if successful, or null if the credentials are invalid.
     */
    private User attemptLogin(String username, String password) {
        Optional<User> optionalUser = userDAO.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (BCrypt.checkpw(password, user.getPasswordHash())) {
                return user;
            }
        }
        return null;
    }

    /**
     * Admin menu loop - displays options and handles admin commands.
     */
    private void adminMenu() {
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Add Student");
            System.out.println("2. View Student by ID");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. List All Students");
            System.out.println("6. Logout");
            System.out.print("Select an option (1-6): ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addStudent();
                    break;
                case "2":
                    viewStudentById();
                    break;
                case "3":
                    updateStudent();
                    break;
                case "4":
                    deleteStudent();
                    break;
                case "5":
                    listAllStudents();
                    break;
                case "6":
                    System.out.println("Logging out...");
                    return;  // Exits admin menu and return to program end or relogin if added
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    /**
     * User menu loop - displays limited options and handles commands for regular users.
     */
    private void userMenu() {
        while (true) {
            System.out.println("\n--- User Menu ---");
            System.out.println("1. View Student by ID");
            System.out.println("2. List All Students");
            System.out.println("3. Logout");
            System.out.print("Select an option (1-3): ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    viewStudentById();
                    break;
                case "2":
                    listAllStudents();
                    break;
                case "3":
                    System.out.println("Logging out...");
                    return;  // Exits user menu
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    /**
     * To add student details...
     */
    private void addStudent() {
        Student student = new Student();
        System.out.println("Fill the below Student's details.");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Branch: ");
        String branch = scanner.nextLine();
        System.out.print("Year: ");
        int year = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Marks: ");
        double marks = scanner.nextDouble();
        scanner.nextLine();

        //Set values
        student.setName(name);
        student.setEmail(email);
        student.setBranch(branch);
        student.setYear(year);
        student.setMarks(marks);

        boolean success = studentDAO.addStudent(student);
        if (success) {
            System.out.println("Student added Successfully.");
        } else {
            System.out.println("Failed to add student.");
        }
    }

    /**
     * viewStudentById
     */
    private void viewStudentById() {
        System.out.print("Provide the Id: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // consume leftover newline

        System.out.println("\n--- Student Details ---");
        try {
            Optional<Student> studentOpt = studentDAO.getStudentById(id);
            if (studentOpt.isPresent()) {
                System.out.println(studentOpt.get());
            } else {
                System.out.println("Student with ID " + id + " not found.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while fetching the student details.");
            e.printStackTrace();
        }
    }

    /**
     * Update Student
     */
    private void updateStudent() {
        System.out.print("Enter the ID of the student to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Optional<Student> existingStudentOpt = studentDAO.getStudentById(id);
        if (!existingStudentOpt.isPresent()) {
            System.out.println("Student with ID " + id + " not found.");
            return;
        }

        Student student = existingStudentOpt.get();
        System.out.println("Fill the details you want to update (leave blank to keep current values).");

        System.out.print("Name [" + student.getName() + "]: ");
        String name = scanner.nextLine();
        if (!name.trim().isEmpty()) {
            student.setName(name);
        }

        System.out.print("Email [" + student.getEmail() + "]: ");
        String email = scanner.nextLine();
        if (!email.trim().isEmpty()) {
            student.setEmail(email);
        }

        System.out.print("Branch [" + student.getBranch() + "]: ");
        String branch = scanner.nextLine();
        if (!branch.trim().isEmpty()) {
            student.setBranch(branch);
        }

        System.out.print("Year [" + student.getYear() + "]: ");
        String yearStr = scanner.nextLine();
        if (!yearStr.trim().isEmpty()) {
            try {
                int year = Integer.parseInt(yearStr);
                student.setYear(year);
            } catch (NumberFormatException e) {
                System.out.println("Invalid year input; keeping existing value.");
            }
        }

        System.out.print("Marks [" + student.getMarks() + "]: ");
        String marksStr = scanner.nextLine();
        if (!marksStr.trim().isEmpty()) {
            try {
                double marks = Double.parseDouble(marksStr);
                student.setMarks(marks);
            } catch (NumberFormatException e) {
                System.out.println("Invalid marks input; keeping existing value.");
            }
        }

        // ID was already set by fetched student, no need to set again explicitly

        boolean success = studentDAO.updateStudent(student);
        if (success) {
            System.out.println("Student updated successfully.");
        } else {
            System.out.println("Failed to update student.");
        }
    }

    /**
     * delete Student
     */
    private void deleteStudent() {
        System.out.print("Enter the ID of the student to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume leftover newline

        // Check if the student exists
        Optional<Student> studentOpt = studentDAO.getStudentById(id);
        if (!studentOpt.isPresent()) {
            System.out.println("Student with ID " + id + " not found.");
            return;
        }

        // Optional: show student info before deleting
        System.out.println("Student found: " + studentOpt.get());

        // Confirm deletion
        System.out.print("Are you sure you want to delete this student? (yes/no): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        if (!confirm.equals("yes")) {
            System.out.println("Delete operation cancelled.");
            return;
        }

        // Attempt to delete
        boolean success = studentDAO.deleteStudent(id);
        if (success) {
            System.out.println("Student deleted successfully.");
        } else {
            System.out.println("Failed to delete student.");
        }
    }

    /**
     * Lists all students by calling the StudentDAO.
     */
    private void listAllStudents() {
        System.out.println("\n--- List of Students ---");
        try {
            studentDAO.listAllStudents().forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Error fetching student list: " + e.getMessage());
        }
    }
}
