package com.example.futin.tabletest.RESTService.response;

import com.example.futin.tabletest.RESTService.models.Instrument;
import com.fasterxml.jackson.databind.deser.Deserializers;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;

/**
 * Created by Futin on 3/17/2015.
 */
public class RSGetInstrumentsResponse extends BaseApiResponse {
    ArrayList<Instrument>listOfInstruments;

    public RSGetInstrumentsResponse(HttpStatus status, String statusName, ArrayList<Instrument> listOfInstruments) {
        super(status, statusName);
        this.listOfInstruments = listOfInstruments;
    }

    public RSGetInstrumentsResponse(HttpStatus status, String statusName) {
        super(status, statusName);
    }

    public ArrayList<Instrument> getListOfInstruments() {
        return listOfInstruments;
    }
}
