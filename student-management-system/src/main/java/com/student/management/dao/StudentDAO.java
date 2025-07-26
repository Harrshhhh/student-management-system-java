package com.student.management.dao;

import com.student.management.DBConnection;
import com.student.management.model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentDAO {

    //add a new student
    public boolean addStudent(Student student) {
        String query = "INSERT INTO students(name, email, branch, year, marks) VALUES(? , ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection()) {
            if (connection == null){
                System.err.println("Failed to establish database connection.");
                return false;
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                if (student == null) {
                    return false;
                }
                preparedStatement.setString(1, student.getName());
                preparedStatement.setString(2, student.getEmail());
                preparedStatement.setString(3, student.getBranch());
                preparedStatement.setInt(4, student.getYear());
                preparedStatement.setDouble(5, student.getMarks());

                return preparedStatement.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //fetch a student by their ID.
    public Optional<Student> getStudentById(int id) {
        String query = "SELECT * FROM students WHERE id = ?";

        try (Connection connection = DBConnection.getConnection()) {
            if (connection == null) {
                System.err.println("Failed to establish database connection.");
                return Optional.empty();
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {

                    if (resultSet.next()) {
                        Student student = new Student();
                        student.setId(resultSet.getInt("id"));
                        student.setName(resultSet.getString("name"));
                        student.setEmail(resultSet.getString("email"));
                        student.setBranch(resultSet.getString("branch"));
                        student.setYear(resultSet.getInt("year"));
                        student.setMarks(resultSet.getDouble("marks"));
                        student.setCreated_at(resultSet.getTimestamp("created_at"));
                        return Optional.of(student);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    //Update Student
    public boolean updateStudent(Student student) {
        String query = "UPDATE students SET name = ?, email = ?, branch = ?, year = ?, marks = ? WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            if (student == null) {
                return false;
            }
            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getEmail());
            preparedStatement.setString(3, student.getBranch());
            preparedStatement.setInt(4, student.getYear());
            preparedStatement.setDouble(5, student.getMarks());
            preparedStatement.setInt(6, student.getId());

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //delete a student....
    public boolean deleteStudent(int id) {
        String query = "DELETE FROM students WHERE id = ?";
        try (Connection connection = DBConnection.getConnection()) {
            if (connection == null) {
                System.err.println("Failed to establish database connection.");
                return false;
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);
                return preparedStatement.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Student> listAllStudents() {
        String query = "SELECT * FROM students";
        List<Student> students = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()) {
            if (connection == null) {
                System.err.println("Failed to establish database connection");
                return students;
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Student student = new Student();
                        student.setId(resultSet.getInt("id"));
                        student.setName(resultSet.getString("name"));
                        student.setEmail(resultSet.getString("email"));
                        student.setBranch(resultSet.getString("branch"));
                        student.setYear(resultSet.getInt("year"));
                        student.setMarks(resultSet.getDouble("marks"));
                        student.setCreated_at(resultSet.getTimestamp("created_at"));
                        students.add(student);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

}
