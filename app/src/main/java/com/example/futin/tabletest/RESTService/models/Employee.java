package com.example.futin.tabletest.RESTService.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by Futin on 3/3/2015.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee {
    String username;
    String password;
    String firstName;
    String lastName;
    String date;
    Student student;


    public Employee(String username, String password, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Employee(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Employee(String firstName, Student student, String date) {
        this.firstName = firstName;
        this.student = student;
        this.date=date;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public Student getStudent() {
        return student;
    }
    public void setStudent(Student student) {
        this.student = student;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Employee name: "+firstName+" "+lastName+", Student: "+student+" and date: "+date;
    }
}
