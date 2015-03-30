package com.example.futin.tabletest.RESTService.response;

import org.springframework.http.HttpStatus;

/**
 * Created by Futin on 3/30/2015.
 */
public class RSUpdateInstrumentsInStockResponse extends BaseApiResponse {
    int quantity;

    public RSUpdateInstrumentsInStockResponse(HttpStatus status, String statusName, int quantity) {
        super(status, statusName);
        this.quantity = quantity;
    }

    public RSUpdateInstrumentsInStockResponse(HttpStatus status, String statusName) {
        super(status, statusName);
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "quantity="+quantity;
    }
}
