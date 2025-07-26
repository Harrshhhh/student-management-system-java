package com.student.management.model;

public class User {
    private int id;
    private String username;
    private transient String password;    // plain password input (transient = not serialized)
    private String passwordHash;          // hashed password stored in DB
    private String role;

    // No-args constructor
    public User() {
    }

    // Constructor for new user registration (with plain password)
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Full constructor (usually when loading from DB with hashed password)
    public User(int id, String username, String passwordHash, String role) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Plain password (transient)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Stored hashed password
    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        // Do not print password or password hash
        return "User{id=" + id + ", username='" + username + '\'' +
                ", role='" + role + '\'' + '}';
    }
}
