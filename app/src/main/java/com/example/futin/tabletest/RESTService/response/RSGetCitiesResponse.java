package com.example.futin.tabletest.RESTService.response;

import com.example.futin.tabletest.RESTService.models.City;
import com.example.futin.tabletest.RESTService.models.Student;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;

/**
 * Created by Futin on 3/9/2015.
 */
public class RSGetCitiesResponse extends BaseApiResponse{
    ArrayList<City>listOFCities;
    public RSGetCitiesResponse(HttpStatus status, String statusName, ArrayList<City>listOFCities) {
        super(status, statusName);
        this.listOFCities = listOFCities;
    }

    public RSGetCitiesResponse(HttpStatus status, String statusName) {
        super(status, statusName);
    }

    public ArrayList<City> getListOFCities() {
        return listOFCities;
    }
}
