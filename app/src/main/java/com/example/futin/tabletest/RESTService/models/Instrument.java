package com.example.futin.tabletest.RESTService.models;

/**
 * Created by Futin on 3/17/2015.
 */
public class Instrument {
    int instrumentId;
    String instrumentName;
    String instrumentType;
    int instrumentsInStock;


    public Instrument(int instrumentId,String instrumentName, String instrumentType, int instrumentsInStock) {
        this.instrumentId=instrumentId;
        this.instrumentName = instrumentName;
        this.instrumentType = instrumentType;
        this.instrumentsInStock = instrumentsInStock;
    }

    public Instrument(int instrumentId, int instrumentsInStock) {
        this.instrumentId = instrumentId;
        this.instrumentsInStock = instrumentsInStock;
    }

    public String getInstrumentName() {
        return instrumentName;
    }

    public void setInstrumentName(String instrumentName) {
        this.instrumentName = instrumentName;
    }

    public String getInstrumentType() {
        return instrumentType;
    }

    public void setInstrumentType(String instrumentType) {
        this.instrumentType = instrumentType;
    }

    public int getInstrumentsInStock() {
        return instrumentsInStock;
    }

    public void setInstrumentsInStock(int instrumentsInStock) {
        this.instrumentsInStock = instrumentsInStock;
    }

    public int getInstrumentId() {
        return instrumentId;
    }

    @Override
    public String toString() {
        return instrumentName;
    }
}
