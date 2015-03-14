package com.example.futin.tabletest.RESTService.response;

import com.example.futin.tabletest.RESTService.models.Employee;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Futin on 3/14/2015.
 */
public class RSGetEmployeesResponse extends BaseApiResponse {
    ArrayList<Employee> listOfEmployees;

    public RSGetEmployeesResponse(HttpStatus status, String statusName, ArrayList<Employee> listOfEmployees) {
        super(status, statusName);
        this.listOfEmployees = listOfEmployees;
    }

    public RSGetEmployeesResponse(HttpStatus status, String statusName) {
        super(status, statusName);
    }

    public ArrayList<Employee> getListOfEmployees() {
        return listOfEmployees;
    }
}
