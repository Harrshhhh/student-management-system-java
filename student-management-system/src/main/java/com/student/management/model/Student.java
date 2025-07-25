package com.student.management.model;

import java.sql.Timestamp;

public class Student {
    private int id;
    private String name;
    private String email;
    private String branch;
    private int year;
    private double marks;
    private Timestamp created_at;

    //no-arg constructor....
    public Student() {
    }

    //arguments for new student....
    public Student(String name, String email, String branch, int year, double marks) {
        this.name = name;
        this.email = email;
        this.branch = branch;
        this.year = year;
        this.marks = marks;
    }

    //Getters & Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getMarks() {
        return marks;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }

    //full argument
    public Student(int id, String name, String email, String branch, int year, double marks, Timestamp created_at) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.branch = branch;
        this.year = year;
        this.marks = marks;
        this.created_at = created_at;
    }

    //Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "Student{id=" + id + ", name='" + name + "', email='" + email + "', branch='" + branch + "', year=" + year + ", marks=" + marks + ", created_at=" + created_at + "}";
    }

}

