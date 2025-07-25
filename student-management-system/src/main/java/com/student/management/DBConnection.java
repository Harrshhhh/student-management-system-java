package com.student.management;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;
public class DBConnection {
    public static Connection getConnection() {
        Connection connection = null;
        try (InputStream inputStream = DBConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
            Properties properties = new Properties();

            if (inputStream == null) {
                System.out.println("Sorry, unable to find db.properties.");
                return null;
            }
            properties.load(inputStream);
            String url = properties.getProperty("db.url");
            String username = properties.getProperty("db.username");
            String password = properties.getProperty("db.password");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}