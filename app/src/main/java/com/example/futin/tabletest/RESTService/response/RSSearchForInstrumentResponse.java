package com.example.futin.tabletest.RESTService.response;

import com.example.futin.tabletest.RESTService.models.Instrument;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;

/**
 * Created by Futin on 3/22/2015.
 */
public class RSSearchForInstrumentResponse extends BaseApiResponse {
    ArrayList<Instrument>listOfSearchedInstruments;

    public RSSearchForInstrumentResponse(HttpStatus status, String statusName, ArrayList<Instrument> listOfSearchedInstruments) {
        super(status, statusName);
        this.listOfSearchedInstruments = listOfSearchedInstruments;
    }

    public RSSearchForInstrumentResponse(HttpStatus status, String statusName) {
        super(status, statusName);
    }

    public ArrayList<Instrument> getListOfSearchedInstruments() {
        return listOfSearchedInstruments;
    }
}
