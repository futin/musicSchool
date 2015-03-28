package com.example.futin.tabletest.RESTService.request;

import java.util.ArrayList;

/**
 * Created by Futin on 3/28/2015.
 */
public class RSDeleteStudentWithInstrumentRowsRequest {
    ArrayList<String>listOfStudentsWithInstrumentsIds;

    public RSDeleteStudentWithInstrumentRowsRequest(ArrayList<String> listOfStudentsWithInstrumentsIds) {
        this.listOfStudentsWithInstrumentsIds = listOfStudentsWithInstrumentsIds;
    }

    public ArrayList<String> getListOfStudentsWithInstrumentsIds() {
        return listOfStudentsWithInstrumentsIds;
    }

    public void setListOfStudentsWithInstrumentsIds(ArrayList<String> listOfStudentsWithInstrumentsIds) {
        this.listOfStudentsWithInstrumentsIds = listOfStudentsWithInstrumentsIds;
    }

    @Override
    public String toString() {
        return listOfStudentsWithInstrumentsIds.toString();
    }
}
