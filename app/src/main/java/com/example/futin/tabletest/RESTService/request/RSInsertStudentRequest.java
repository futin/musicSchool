package com.example.futin.tabletest.RESTService.request;

import com.example.futin.tabletest.RESTService.models.City;

/**
 * Created by Futin on 3/9/2015.
 */
public class RSInsertStudentRequest {
    String studentId;
    String firstName;
    String lastName;
    int cityPtt;

    public RSInsertStudentRequest(String studentId, String firstName, String lastName, int cityPtt) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cityPtt = cityPtt;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
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

    public int getCityPtt() {
        return cityPtt;
    }

    public void setCityPtt(int cityPtt) {
        this.cityPtt = cityPtt;
    }

    @Override
    public String toString() {
        return "studentId="+studentId+"&firstName="+firstName+"&lastName="+lastName+"&cityPtt="+cityPtt;
    }
}
