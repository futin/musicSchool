package com.example.futin.tabletest.RESTService.request;

import java.util.ArrayList;

/**
 * Created by Futin on 3/28/2015.
 */
public class RSDeleteStudentWithInstrumentRowsRequest {
    ArrayList<String>listOfStudentIds;
    ArrayList<Integer>listOfInstrumentIds;

    public RSDeleteStudentWithInstrumentRowsRequest(ArrayList<String> listOfStudentIds, ArrayList<Integer> listOfInstrumentIds) {
        this.listOfStudentIds = listOfStudentIds;
        this.listOfInstrumentIds = listOfInstrumentIds;
    }

    public ArrayList<String> getListOfStudentIds() {
        return listOfStudentIds;
    }

    public void setListOfStudentIds(ArrayList<String> listOfStudentIds) {
        this.listOfStudentIds = listOfStudentIds;
    }

    public ArrayList<Integer> getListOfInstrumentIds() {
        return listOfInstrumentIds;
    }

    public void setListOfInstrumentIds(ArrayList<Integer> listOfInstrumentIds) {
        this.listOfInstrumentIds = listOfInstrumentIds;
    }

}
