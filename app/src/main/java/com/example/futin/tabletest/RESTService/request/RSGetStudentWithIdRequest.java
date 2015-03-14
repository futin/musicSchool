package com.example.futin.tabletest.RESTService.request;

/**
 * Created by Futin on 3/9/2015.
 */
public class RSGetStudentWithIdRequest {
    String studentId;

    public RSGetStudentWithIdRequest(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @Override
    public String toString() {
        return "studentId="+studentId;
    }
}
