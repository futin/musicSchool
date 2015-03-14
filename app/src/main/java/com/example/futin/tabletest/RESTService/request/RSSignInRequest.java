package com.example.futin.tabletest.RESTService.request;

/**
 * Created by Futin on 3/7/2015.
 */
public class RSSignInRequest {
    String username;
    String password;

    public RSSignInRequest(String username, String password) {
        this.username = username;
        this.password = password;
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

    @Override
    public String toString() {
        return "username="+username+"&password="+password;
    }
}
