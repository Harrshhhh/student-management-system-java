package com.student.management;

import com.student.management.dao.UserDAO;
import com.student.management.model.User;

public class UserSetup {
    public static void main(String[] args) {
        try {
            UserDAO userDAO = new UserDAO();

            // Create a new user: username=Harsh, password=harsh, role=admin
            User user = new User();
            user.setUsername("Harsh");
            user.setPassword("harsh");  // Your UserDAO should hash this internally.
            user.setRole("user");

            boolean added = userDAO.addUser(user);
            if (added) {
                System.out.println("User 'Harsh' added successfully.");
            } else {
                System.out.println("Failed to add user 'Harsh'. It may already exist.");
            }
        } catch (Exception e) {
            System.out.println("Error while adding user:");
            e.printStackTrace();
        }
    }
}
