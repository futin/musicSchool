package com.example.futin.tabletest.RESTService.request;

/**
 * Created by Futin on 3/20/2015.
 */
public class RSInsertStudentWithInstrumentRequest {
    String studentId;
    int instrumentId;
    String employeeName;
    int numberOfInstruments;
    String date;

    public RSInsertStudentWithInstrumentRequest(String studentId, int instrumentId,
                                                String employeeName, int numberOfInstruments, String date) {
        this.studentId = studentId;
        this.instrumentId = instrumentId;
        this.employeeName = employeeName;
        this.numberOfInstruments = numberOfInstruments;
        this.date = date;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public int getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(int instrumentId) {
        this.instrumentId = instrumentId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public int getNumberOfInstruments() {
        return numberOfInstruments;
    }

    public void setNumberOfInstruments(int numberOfInstruments) {
        this.numberOfInstruments = numberOfInstruments;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "studentId="+studentId+"&instrumentId="+instrumentId+"&employeeName="+employeeName
                +"&numberOfInstruments="+numberOfInstruments+"&date="+date;
    }
}
