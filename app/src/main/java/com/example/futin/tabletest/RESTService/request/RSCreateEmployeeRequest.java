package com.example.futin.tabletest.RESTService.request;

/**
 * Created by Futin on 3/3/2015.
 */
public class RSCreateEmployeeRequest {
    String username;
    String password;
    String firstName;
    String lastName;

    public RSCreateEmployeeRequest(String username, String password, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
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

    @Override
    public String toString() {
        return "username="+username+"&password="+password+"&firstName="+firstName+"&lastName="+lastName;
    }
}
