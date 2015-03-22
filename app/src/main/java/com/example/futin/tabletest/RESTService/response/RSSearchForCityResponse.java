package com.example.futin.tabletest.RESTService.response;

import com.example.futin.tabletest.RESTService.models.City;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;

/**
 * Created by Futin on 3/22/2015.
 */
public class RSSearchForCityResponse extends BaseApiResponse {
    ArrayList<City>listOfSearchedCities;

    public RSSearchForCityResponse(HttpStatus status, String statusName, ArrayList<City> listOfSearchedCities) {
        super(status, statusName);
        this.listOfSearchedCities = listOfSearchedCities;
    }

    public RSSearchForCityResponse(HttpStatus status, String statusName) {
        super(status, statusName);
    }

    public ArrayList<City> getListOfSearchedCities() {
        return listOfSearchedCities;
    }
}
