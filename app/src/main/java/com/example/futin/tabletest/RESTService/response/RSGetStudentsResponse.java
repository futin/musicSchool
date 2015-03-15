package com.example.futin.tabletest.RESTService.response;

import com.example.futin.tabletest.RESTService.models.Student;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;

/**
 * Created by Futin on 3/9/2015.
 */
public class RSGetStudentsResponse extends BaseApiResponse{
    ArrayList<Student>listOfStudents;

    public RSGetStudentsResponse(HttpStatus status, String statusName, ArrayList<Student> listOfStudents) {
        super(status, statusName);
        this.listOfStudents = listOfStudents;
    }

    public RSGetStudentsResponse(HttpStatus status, String statusName) {
        super(status, statusName);
    }

    public ArrayList<Student> getStudents() {
        return listOfStudents;
    }
}
