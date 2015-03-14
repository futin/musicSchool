package com.example.futin.tabletest.RESTService.models;

/**
 * Created by Futin on 3/9/2015.
 */
public class City {
    int cityPtt;
    String cityName;

    public City(int cityPtt, String cityName) {
        this.cityPtt = cityPtt;
        this.cityName = cityName;
    }
    public int getCityPtt() {
        return cityPtt;
    }
    public void setCityPtt(int cityPtt) {
        this.cityPtt = cityPtt;
    }
    public String getCityName() {
        return cityName;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public String toString() {
        return cityName;
    }
}
