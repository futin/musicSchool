package com.example.futin.tabletest.RESTService.request;

/**
 * Created by Futin on 3/30/2015.
 */
public class RSUpdateInstrumentsInStockRequest {
    int quantity;
    int instrumentId;

    public RSUpdateInstrumentsInStockRequest(int quantity, int instrumentId) {
        this.quantity = quantity;
        this.instrumentId = instrumentId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(int instrumentId) {
        this.instrumentId = instrumentId;
    }

    @Override
    public String toString() {
        return "quantity="+quantity+"&instrumentId="+instrumentId;
    }
}
