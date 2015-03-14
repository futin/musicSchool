package com.example.futin.tabletest.RESTService;

import com.example.futin.tabletest.RESTService.data.AsyncTaskReturnData;
import com.example.futin.tabletest.RESTService.models.City;
import com.example.futin.tabletest.RESTService.request.RSCreateEmployeeRequest;
import com.example.futin.tabletest.RESTService.request.RSCreateStudentRequest;
import com.example.futin.tabletest.RESTService.request.RSGetStudentWithIdRequest;
import com.example.futin.tabletest.RESTService.request.RSSignInRequest;
import com.example.futin.tabletest.RESTService.task.RSGetEmployeesTask;
import com.example.futin.tabletest.RESTService.task.RSInsertEmployeeTask;
import com.example.futin.tabletest.RESTService.task.RSInsertStudentTask;
import com.example.futin.tabletest.RESTService.task.RSGetCitiesTask;
import com.example.futin.tabletest.RESTService.task.RSGetStudentWithIdTask;
import com.example.futin.tabletest.RESTService.task.RSSignInTask;

/**
 * Created by Futin on 3/3/2015.
 */
public class RestService {

    AsyncTaskReturnData returnData=null;

    public RestService(AsyncTaskReturnData returnData) {
        this.returnData = returnData;
    }

    public void getEmployees(){
        new RSGetEmployeesTask(returnData).execute((Void) null);
    }

    public void createEmployee(String username, String password, String firstName, String lastName){
        new RSInsertEmployeeTask(new RSCreateEmployeeRequest(username, password, firstName, lastName),returnData).execute((Void) null);
    }
    public void signIn(String username, String password){
        new RSSignInTask(new RSSignInRequest(username, password), returnData).execute((Void) null);
    }
    public void getCities(){
        new RSGetCitiesTask(returnData).execute((Void) null);
    }

    public void createStudent(String studentId, String firstName, String lastName, City cityPtt){
        new RSInsertStudentTask(new RSCreateStudentRequest(studentId, firstName, lastName,cityPtt), returnData).execute((Void) null);
    }
    public void getStudentWithId(String studentId){
        new RSGetStudentWithIdTask(new RSGetStudentWithIdRequest(studentId), returnData).execute((Void) null);
    }



}
