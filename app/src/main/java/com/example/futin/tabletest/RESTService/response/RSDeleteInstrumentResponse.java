package com.example.futin.tabletest.RESTService.response;

import org.springframework.http.HttpStatus;

/**
 * Created by Futin on 3/28/2015.
 */
public class RSDeleteInstrumentResponse extends  BaseApiResponse {
    public RSDeleteInstrumentResponse(HttpStatus status, String statusName) {
        super(status, statusName);
    }
}
