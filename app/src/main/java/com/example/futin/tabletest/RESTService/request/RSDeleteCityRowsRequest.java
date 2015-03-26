package com.example.futin.tabletest.RESTService.request;

import java.util.ArrayList;

/**
 * Created by Futin on 3/26/2015.
 */
public class RSDeleteCityRowsRequest {
    ArrayList<Integer>listOfPtt;

    public RSDeleteCityRowsRequest(ArrayList<Integer> listOfPtt) {
        this.listOfPtt = listOfPtt;
    }

    public ArrayList<Integer> getListOfPtt() {
        return listOfPtt;
    }

    public void setListOfPtt(ArrayList<Integer> listOfPtt) {
        this.listOfPtt = listOfPtt;
    }

    @Override
    public String toString() {
        return listOfPtt.toString();
    }
}
