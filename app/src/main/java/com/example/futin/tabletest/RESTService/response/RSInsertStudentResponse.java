package com.example.futin.tabletest.RESTService.response;

import com.example.futin.tabletest.RESTService.models.Student;

import org.springframework.http.HttpStatus;

/**
 * Created by Futin on 3/9/2015.
 */
public class RSInsertStudentResponse extends BaseApiResponse{
    Student student;

    public RSInsertStudentResponse(HttpStatus status, String statusName, Student student) {
        super(status, statusName);
        this.student = student;
    }

    public RSInsertStudentResponse(HttpStatus status, String statusName) {
        super(status, statusName);
    }
}
