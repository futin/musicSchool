package com.example.futin.tabletest.RESTService.request;

/**
 * Created by Futin on 3/22/2015.
 */
public class RSSearchForCityRequest {
    String findCityText;

    public RSSearchForCityRequest(String findCityText) {
        this.findCityText = findCityText;
    }

    public String getFindCityText() {
        return findCityText;
    }

    public void setFindCityText(String findCityText) {
        this.findCityText = findCityText;
    }

    @Override
    public String toString() {
        return "findCityText="+findCityText;
    }
}
