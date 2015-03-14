package com.example.futin.tabletest.RESTService.response;

import org.springframework.http.HttpStatus;

/**
 * Created by Futin on 3/3/2015.
 */
public class BaseApiResponse {
    HttpStatus status;
    String statusName;
    ;

    public BaseApiResponse(HttpStatus status, String statusName) {
        this.status = status;
        this.statusName = statusName;
    }

    public BaseApiResponse() {
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getStatusName() {
        return statusName;
    }
}
