package com.example.futin.tabletest.RESTService.response;

import com.example.futin.tabletest.RESTService.models.Employee;

/**
 * Created by Futin on 3/3/2015.
 */
public class RSCreateEmployeeResponse extends BaseApiResponse {

    Employee employee;

    public RSCreateEmployeeResponse(String httpStatus, String httpCode) {
        super(httpStatus, httpCode);
    }

    public RSCreateEmployeeResponse(String httpStatus, String httpCode, Employee employee) {
        super(httpStatus, httpCode);
        this.employee = employee;
    }
}
