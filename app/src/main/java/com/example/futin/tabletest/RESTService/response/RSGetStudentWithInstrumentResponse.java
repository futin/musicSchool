package com.example.futin.tabletest.RESTService.response;

import com.example.futin.tabletest.RESTService.models.Employee;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;

/**
 * Created by Futin on 3/21/2015.
 */
public class RSGetStudentWithInstrumentResponse extends BaseApiResponse {
    ArrayList<Employee>listOfEmployees;

    public RSGetStudentWithInstrumentResponse(HttpStatus status, String statusName, ArrayList<Employee>listOfEmployees) {
        super(status, statusName);
        this.listOfEmployees = listOfEmployees;
    }

    public RSGetStudentWithInstrumentResponse(HttpStatus status, String statusName) {
        super(status, statusName);
    }

    public ArrayList<Employee> getListOfEmployees() {
        return listOfEmployees;
    }
}
