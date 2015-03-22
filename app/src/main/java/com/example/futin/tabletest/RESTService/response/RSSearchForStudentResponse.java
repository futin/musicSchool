package com.example.futin.tabletest.RESTService.response;

import com.example.futin.tabletest.RESTService.models.Student;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;

/**
 * Created by Futin on 3/22/2015.
 */
public class RSSearchForStudentResponse extends BaseApiResponse {
    ArrayList<Student>listOfSearchedStudents;

    public RSSearchForStudentResponse(HttpStatus status, String statusName, ArrayList<Student> listOfSearchedStudents) {
        super(status, statusName);
        this.listOfSearchedStudents = listOfSearchedStudents;
    }

    public RSSearchForStudentResponse(HttpStatus status, String statusName) {
        super(status, statusName);
    }

    public ArrayList<Student> getListOfSearchedStudents() {
        return listOfSearchedStudents;
    }
}
