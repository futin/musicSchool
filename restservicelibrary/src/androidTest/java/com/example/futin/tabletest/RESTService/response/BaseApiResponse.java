package com.example.futin.tabletest.RESTService.response;

/**
 * Created by Futin on 3/3/2015.
 */
public class BaseApiResponse {
    String httpStatus;
    String httpCode;

    public BaseApiResponse(String httpStatus, String httpCode) {
        this.httpStatus = httpStatus;
        this.httpCode = httpCode;
    }

    public BaseApiResponse() {
    }
}
