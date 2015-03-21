package com.example.futin.tabletest.RESTService.response;

import com.example.futin.tabletest.RESTService.models.Employee;

import org.springframework.http.HttpStatus;

/**
 * Created by Futin on 3/20/2015.
 */
public class RSInsertStudentWithInstrumentResponse extends BaseApiResponse {
    Employee employee;

    public RSInsertStudentWithInstrumentResponse(HttpStatus status, String statusName, Employee employee) {
        super(status, statusName);
        this.employee = employee;
    }

    public RSInsertStudentWithInstrumentResponse(HttpStatus status, String statusName) {
        super(status, statusName);
    }

    public Employee getEmployee() {
        return employee;
    }

}
