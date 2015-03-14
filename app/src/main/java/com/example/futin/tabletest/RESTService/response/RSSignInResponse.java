package com.example.futin.tabletest.RESTService.response;

import com.example.futin.tabletest.RESTService.models.Employee;

import org.springframework.http.HttpStatus;

/**
 * Created by Futin on 3/7/2015.
 */
public class RSSignInResponse extends BaseApiResponse {
    Employee employee;

    public RSSignInResponse(HttpStatus status, String statusName, Employee employee) {
        super(status, statusName);
        this.employee = employee;
    }

    public RSSignInResponse(HttpStatus status, String statusName) {
        super(status, statusName);
    }

    public Employee getEmployee() {
        return employee;
    }

}
