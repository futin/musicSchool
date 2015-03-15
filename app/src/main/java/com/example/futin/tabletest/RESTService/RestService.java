package com.example.futin.tabletest.RESTService;

import com.example.futin.tabletest.RESTService.interfaces.AsyncTaskReturnData;
import com.example.futin.tabletest.RESTService.interfaces.ReturnStudentData;
import com.example.futin.tabletest.RESTService.interfaces.SignInReturnData;
import com.example.futin.tabletest.RESTService.request.RSInsertEmployeeRequest;
import com.example.futin.tabletest.RESTService.request.RSInsertStudentRequest;
import com.example.futin.tabletest.RESTService.request.RSSignInRequest;
import com.example.futin.tabletest.RESTService.task.RSGetEmployeesTask;
import com.example.futin.tabletest.RESTService.task.RSGetStudentsTask;
import com.example.futin.tabletest.RESTService.task.RSInsertEmployeeTask;
import com.example.futin.tabletest.RESTService.task.RSInsertStudentTask;
import com.example.futin.tabletest.RESTService.task.RSGetCitiesTask;
import com.example.futin.tabletest.RESTService.task.RSSignInTask;

/**
 * Created by Futin on 3/3/2015.
 */
public class RestService {

    AsyncTaskReturnData returnData=null;
    SignInReturnData returnDataSignIn=null;
    ReturnStudentData returnReturnStudentData =null;

    public RestService(AsyncTaskReturnData returnData) {
        this.returnData = returnData;
    }

    //setter for signIn method
    public void setReturnDataSignIn(SignInReturnData returnDataSignIn) {
        this.returnDataSignIn = returnDataSignIn;
    }
    //setter for getStudents method
    public void setReturnReturnStudentData(ReturnStudentData returnReturnStudentData) {
        this.returnReturnStudentData = returnReturnStudentData;
    }

    public void getEmployees(){
        new RSGetEmployeesTask(returnData).execute((Void) null);
    }

    public void insertEmployee(String username, String password, String firstName, String lastName){
        new RSInsertEmployeeTask(new RSInsertEmployeeRequest(username, password, firstName, lastName),returnData).execute((Void) null);
    }
    public void signIn(String username, String password){
        new RSSignInTask(new RSSignInRequest(username, password), returnDataSignIn).execute((Void) null);
    }
    public void getCities(){
        new RSGetCitiesTask(returnData).execute((Void) null);
    }

    public void insertStudent(String studentId, String firstName, String lastName, int cityPtt){
        new RSInsertStudentTask(new RSInsertStudentRequest(studentId, firstName, lastName,cityPtt), returnData).execute((Void) null);
    }
    public void getStudents(){
        new RSGetStudentsTask(returnReturnStudentData).execute((Void) null);
    }



}
