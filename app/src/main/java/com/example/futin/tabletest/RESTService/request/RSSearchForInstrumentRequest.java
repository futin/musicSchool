package com.example.futin.tabletest.RESTService.request;

/**
 * Created by Futin on 3/22/2015.
 */
public class RSSearchForInstrumentRequest {
    String findInstrumentText;

    public RSSearchForInstrumentRequest(String findInstrumentText) {
        this.findInstrumentText = findInstrumentText;
    }

    public String getFindInstrumentText() {
        return findInstrumentText;
    }

    public void setFindInstrumentText(String findInstrumentText) {
        this.findInstrumentText = findInstrumentText;
    }

    @Override
    public String toString() {
        return "findInstrumentText="+findInstrumentText;
    }
}
