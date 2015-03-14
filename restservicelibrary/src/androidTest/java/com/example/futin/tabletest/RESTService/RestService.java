package com.example.futin.tabletest.RESTService;

import com.example.futin.tabletest.RESTService.request.RSCreateEmployeeRequest;
import com.example.futin.tabletest.RESTService.task.RSCreateEmployeeTask;

/**
 * Created by Futin on 3/3/2015.
 */
public class RestService {

    public void createEmployee(String username, String password, String firstName, String lastName){
        new RSCreateEmployeeTask(new RSCreateEmployeeRequest(username, password, firstName, lastName)).execute((Void) null);
    }
}
