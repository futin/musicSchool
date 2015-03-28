package com.example.futin.tabletest.RESTService.request;

import java.util.ArrayList;

/**
 * Created by Futin on 3/28/2015.
 */
public class RSDeleteStudentRowsRequest {

    ArrayList<String>listOfStudentsIds;

    public RSDeleteStudentRowsRequest(ArrayList<String> listOfStudentsIds) {
        this.listOfStudentsIds = listOfStudentsIds;
    }

    public ArrayList<String> getListOfStudentsIds() {
        return listOfStudentsIds;
    }

    public void setListOfStudentsIds(ArrayList<String> listOfStudentsIds) {
        this.listOfStudentsIds = listOfStudentsIds;
    }

    @Override
    public String toString() {
        return listOfStudentsIds.toString();
    }
}
