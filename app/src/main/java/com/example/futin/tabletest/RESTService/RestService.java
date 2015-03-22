package com.example.futin.tabletest.RESTService;

import com.example.futin.tabletest.RESTService.listeners.AsyncTaskReturnData;
import com.example.futin.tabletest.RESTService.listeners.ReturnInstrumentData;
import com.example.futin.tabletest.RESTService.listeners.ReturnStudentData;
import com.example.futin.tabletest.RESTService.listeners.ReturnStudentWithInstrumentData;
import com.example.futin.tabletest.RESTService.listeners.SearchStudentData;
import com.example.futin.tabletest.RESTService.listeners.SignInReturnData;
import com.example.futin.tabletest.RESTService.request.RSInsertEmployeeRequest;
import com.example.futin.tabletest.RESTService.request.RSInsertStudentRequest;
import com.example.futin.tabletest.RESTService.request.RSInsertStudentWithInstrumentRequest;
import com.example.futin.tabletest.RESTService.request.RSSearchForStudentRequest;
import com.example.futin.tabletest.RESTService.request.RSSignInRequest;
import com.example.futin.tabletest.RESTService.task.RSGetEmployeesTask;
import com.example.futin.tabletest.RESTService.task.RSGetInstrumentsTask;
import com.example.futin.tabletest.RESTService.task.RSGetStudentWithInstrumentTask;
import com.example.futin.tabletest.RESTService.task.RSGetStudentsTask;
import com.example.futin.tabletest.RESTService.task.RSInsertEmployeeTask;
import com.example.futin.tabletest.RESTService.task.RSInsertStudentTask;
import com.example.futin.tabletest.RESTService.task.RSGetCitiesTask;
import com.example.futin.tabletest.RESTService.task.RSInsertStudentWithInstrumentTask;
import com.example.futin.tabletest.RESTService.task.RSSearchForStudentTask;
import com.example.futin.tabletest.RESTService.task.RSSignInTask;

/**
 * Created by Futin on 3/3/2015.
 */
public class RestService {

    AsyncTaskReturnData returnData=null;
    SignInReturnData returnDataSignIn=null;
    ReturnStudentData returnReturnStudentData =null;
    ReturnInstrumentData returnInstrumentData=null;
    ReturnStudentWithInstrumentData returnStudentWithInstrumentData=null;

    //search listeners
    SearchStudentData searchStudentData;

    public RestService(AsyncTaskReturnData returnData) {
        this.returnData = returnData;
    }

    public RestService() {
    }

    //setters for listeners
    public void setReturnDataSignIn(SignInReturnData returnDataSignIn) {
        this.returnDataSignIn = returnDataSignIn;
    }
    public void setReturnReturnStudentData(ReturnStudentData returnReturnStudentData) {
        this.returnReturnStudentData = returnReturnStudentData;
    }
    public void setReturnStudentWithInstrumentData(ReturnStudentWithInstrumentData returnStudentWithInstrumentData) {
        this.returnStudentWithInstrumentData = returnStudentWithInstrumentData;
    }
    public void setReturnInstrumentData(ReturnInstrumentData returnInstrumentData) {
        this.returnInstrumentData = returnInstrumentData;
    }

    public void setSearchStudentData(SearchStudentData searchStudentData) {
        this.searchStudentData = searchStudentData;
    }

    //GET methods
    public void getEmployees(){
        new RSGetEmployeesTask(returnData).execute((Void) null);
    }

    public void getCities(){
        new RSGetCitiesTask(returnData).execute((Void) null);
    }

    public void getStudents(){
        new RSGetStudentsTask(returnReturnStudentData).execute((Void) null);
    }
    public void getInstruments(){
        new RSGetInstrumentsTask(returnInstrumentData).execute((Void) null);
    }
    public void getStudentWithInstrument(){
        new RSGetStudentWithInstrumentTask(returnStudentWithInstrumentData).execute((Void) null);
    }
    //INSERT methods
    public void insertEmployee(String username, String password, String firstName, String lastName){
        new RSInsertEmployeeTask(new RSInsertEmployeeRequest(username, password, firstName, lastName),returnData).execute((Void) null);
    }
    public void signIn(String username, String password){
        new RSSignInTask(new RSSignInRequest(username, password), returnDataSignIn).execute((Void) null);
    }
    public void insertStudent(String studentId, String firstName, String lastName, int cityPtt){
        new RSInsertStudentTask(new RSInsertStudentRequest(studentId, firstName, lastName,cityPtt), returnData).execute((Void) null);
    }
    public void insertStudentWithInstrument(String studentId, int instrumentId, String employeeName,
                                            int numberOfInstruments, String date){
        new RSInsertStudentWithInstrumentTask(new RSInsertStudentWithInstrumentRequest(studentId,
                instrumentId, employeeName, numberOfInstruments,date)).execute((Void) null);
    }

    //SEARCH methods
    public void searchForStudent(String searchText){
        new RSSearchForStudentTask(new RSSearchForStudentRequest(searchText), searchStudentData).execute((Void) null);
    }
}
