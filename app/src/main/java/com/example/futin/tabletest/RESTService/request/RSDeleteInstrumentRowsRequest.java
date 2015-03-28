package com.example.futin.tabletest.RESTService.request;

import java.util.ArrayList;

/**
 * Created by Futin on 3/28/2015.
 */
public class RSDeleteInstrumentRowsRequest {
    ArrayList<Integer>listOfInstrumentIds;

    public RSDeleteInstrumentRowsRequest(ArrayList<Integer> listOfInstrumentsNames) {
        this.listOfInstrumentIds = listOfInstrumentsNames;
    }

    public ArrayList<Integer> listOfInstrumentIds() {
        return listOfInstrumentIds;
    }

    public void setListOfInstrumentIds(ArrayList<Integer> listOfInstrumentIds) {
        this.listOfInstrumentIds = listOfInstrumentIds;
    }

    @Override
    public String toString() {
        return listOfInstrumentIds.toString();
    }
}
