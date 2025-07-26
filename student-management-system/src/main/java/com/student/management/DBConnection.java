package com.student.management;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    public static Connection getConnection() {
        Connection connection = null;
        try (InputStream inputStream = DBConnection.class.getClassLoader().getResourceAsStream("db.properties")) {

            if (inputStream == null) {
                System.out.println("Sorry, unable to find db.properties.");
                return null;
            }

            Properties properties = new Properties();
            properties.load(inputStream);

            String url = properties.getProperty("db.url");
            String username = properties.getProperty("db.username");
            String password = properties.getProperty("db.password");

            // Optional: Load PostgreSQL JDBC driver explicitly
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                System.out.println("PostgreSQL JDBC Driver not found. Include it in your library path.");
                e.printStackTrace();
                return null;
            }

            // Establish the connection
            connection = DriverManager.getConnection(url, username, password);

        } catch (SQLException e) {
            System.out.println("Failed to create the database connection.");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
