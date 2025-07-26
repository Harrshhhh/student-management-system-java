package com.student.management.dao;

import com.student.management.DBConnection;
import com.student.management.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAO {

    // Add new user - hash password before insert
    public boolean addUser(User user) {
        if (user == null) {
            return false;
        }

        String plainPassword = user.getPassword();
        if (plainPassword == null || plainPassword.isEmpty()) {
            System.err.println("Password cannot be null or empty.");
            return false;
        }

        String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt());

        String query = "INSERT INTO users(username, password_hash, role) VALUES(?, ?, ?)";

        try (Connection connection = DBConnection.getConnection()) {
            if (connection == null) {
                System.err.println("Failed to connect to the database.");
                return false;
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setString(2, hashedPassword);
                preparedStatement.setString(3, user.getRole());

                return preparedStatement.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Find user by username
    public Optional<User> findByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return Optional.empty();
        }

        String query = "SELECT * FROM users WHERE username = ?";

        try (Connection connection = DBConnection.getConnection()) {
            if (connection == null) {
                System.err.println("Failed to establish database connection.");
                return Optional.empty();
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        User user = new User();
                        user.setId(resultSet.getInt("id"));
                        user.setUsername(resultSet.getString("username"));
                        user.setPasswordHash(resultSet.getString("password_hash"));
                        user.setRole(resultSet.getString("role"));
                        return Optional.of(user);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // Get user by ID
    public Optional<User> getUserById(int id) {
        String query = "SELECT * FROM users WHERE id = ?";

        try (Connection connection = DBConnection.getConnection()) {
            if (connection == null) {
                System.err.println("Failed to establish database connection.");
                return Optional.empty();
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        User user = new User();
                        user.setId(resultSet.getInt("id"));
                        user.setUsername(resultSet.getString("username"));
                        user.setPasswordHash(resultSet.getString("password_hash"));
                        user.setRole(resultSet.getString("role"));
                        return Optional.of(user);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // Update user info (hash password if plain password is provided)
    public boolean updateUser(User user) {
        if (user == null) {
            return false;
        }

        String query = "UPDATE users SET username = ?, password_hash = ?, role = ? WHERE id = ?";

        try (Connection connection = DBConnection.getConnection()) {
            if (connection == null) {
                System.err.println("Failed to establish database connection.");
                return false;
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, user.getUsername());

                String passwordHashToUse;

                // If transient plain password is provided, hash it; else keep existing hash
                if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                    passwordHashToUse = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
                } else {
                    passwordHashToUse = user.getPasswordHash();
                }

                preparedStatement.setString(2, passwordHashToUse);
                preparedStatement.setString(3, user.getRole());
                preparedStatement.setInt(4, user.getId());

                return preparedStatement.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete user by ID
    public boolean deleteUser(int id) {
        String query = "DELETE FROM users WHERE id = ?";

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

    // List all users
    public List<User> listAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";

        try (Connection connection = DBConnection.getConnection()) {
            if (connection == null) {
                System.err.println("Failed to establish database connection.");
                return users;
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setUsername(resultSet.getString("username"));
                    user.setPasswordHash(resultSet.getString("password_hash"));
                    user.setRole(resultSet.getString("role"));
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
