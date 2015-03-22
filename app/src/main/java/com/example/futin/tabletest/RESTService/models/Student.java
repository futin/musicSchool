package com.example.futin.tabletest.RESTService.models;

/**
 * Created by Futin on 3/9/2015.
 */
public class Student {
    String studentId;
    String firstName;
    String lastName;
    City city;
    Instrument instrument;
    int numberOfInstruments;

    public Student(String studentId, String firstName, String lastName, City city) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
    }

    public Student(String studentId, Instrument instrument, String firstName, String lastName,int numberOfInstruments) {
        this.studentId = studentId;
        this.instrument = instrument;
        this.firstName=firstName;
        this.lastName=lastName;
        this.numberOfInstruments=numberOfInstruments;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    public int getNumberOfInstruments() {
        return numberOfInstruments;
    }

    public void setNumberOfInstruments(int numberOfInstruments) {
        this.numberOfInstruments = numberOfInstruments;
    }

    @Override
    public String toString() {
        return firstName+" "+lastName;
    }
}
