package com.example.futin.tabletest.RESTService.request;

/**
 * Created by Futin on 3/22/2015.
 */
public class RSSearchForStudentWithInstrumentRequest {
    String findStudentWithInstrumentText;

    public RSSearchForStudentWithInstrumentRequest(String findStudentWithInstrumentText) {
        this.findStudentWithInstrumentText = findStudentWithInstrumentText;
    }

    public String getFindStudentWithInstrumentText() {
        return findStudentWithInstrumentText;
    }

    public void setFindStudentWithInstrumentText(String findStudentWithInstrumentText) {
        this.findStudentWithInstrumentText = findStudentWithInstrumentText;
    }

    @Override
    public String toString() {
        return "findStudentWithInstrumentText="+findStudentWithInstrumentText;
    }
}
