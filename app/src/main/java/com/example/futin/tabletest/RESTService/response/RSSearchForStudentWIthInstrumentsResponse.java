package com.example.futin.tabletest.RESTService.response;

import com.example.futin.tabletest.RESTService.models.Employee;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;

/**
 * Created by Futin on 3/22/2015.
 */
public class RSSearchForStudentWIthInstrumentsResponse extends BaseApiResponse {
    ArrayList<Employee>listOfSearchedStudentsWithInstrument;

    public RSSearchForStudentWIthInstrumentsResponse(HttpStatus status, String statusName, ArrayList<Employee> listOfSearchedStudentsWithInstrument) {
        super(status, statusName);
        this.listOfSearchedStudentsWithInstrument = listOfSearchedStudentsWithInstrument;
    }

    public RSSearchForStudentWIthInstrumentsResponse(HttpStatus status, String statusName) {
        super(status, statusName);
    }

    public ArrayList<Employee> getListOfEmployees() {
        return listOfSearchedStudentsWithInstrument;
    }
}
