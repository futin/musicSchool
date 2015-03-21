package com.example.futin.tabletest.RESTService.task;

import android.os.AsyncTask;
import android.util.Log;

import com.example.futin.tabletest.RESTService.listeners.AsyncTaskReturnData;
import com.example.futin.tabletest.RESTService.data.RSDataSingleton;
import com.example.futin.tabletest.RESTService.models.City;
import com.example.futin.tabletest.RESTService.response.RSGetCitiesResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

/**
 * Created by Futin on 3/9/2015.
 */
public class RSGetCitiesTask extends AsyncTask<Void, Void, RSGetCitiesResponse> {
    final String TAG="getCities";
    RestTemplate restTemplate;
    AsyncTaskReturnData returnData;

    public RSGetCitiesTask( AsyncTaskReturnData returnData) {
        this.returnData = returnData;
        restTemplate=new RestTemplate();
    }

    @Override
    protected RSGetCitiesResponse doInBackground(Void... params) {
        try {
            HttpHeaders header = new HttpHeaders();
            header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            header.set("Connection", "Close");
            String jsonText = "";
            HttpEntity<String> entity = new HttpEntity<>(jsonText, header);
            String address = RSDataSingleton.getInstance().getServerUrl().getCityUrl();

            Log.i(TAG, "Address: " + address);
            Log.i(TAG, "Before response ");
            ResponseEntity response = restTemplate.exchange(address, HttpMethod.GET, entity, String.class);
            Log.i(TAG, "After response ");
            if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                return new RSGetCitiesResponse(HttpStatus.NOT_FOUND,
                        HttpStatus.NOT_FOUND.name());
            } else if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
                return new RSGetCitiesResponse(HttpStatus.BAD_REQUEST,
                        HttpStatus.BAD_REQUEST.name());
            }else if (response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                return new RSGetCitiesResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                        HttpStatus.INTERNAL_SERVER_ERROR.name());
            } else {
                Log.i(TAG, "Response ok ");
                String jsonBody=response.getBody().toString();
                JSONArray array=new JSONArray(jsonBody);
                ArrayList<City>listOfCities=new ArrayList<>();
                for (int i=0; i<array.length();i++){
                    JSONObject objCity=array.getJSONObject(i);

                    int cityPtt=objCity.getInt("cityPtt");
                    String cityName=objCity.getString("cityName");

                    City city=new City(cityPtt, cityName);
                    listOfCities.add(city);
                }
                Log.i(TAG, "Cities: "+listOfCities.toString());

                return new RSGetCitiesResponse(HttpStatus.OK,
                        HttpStatus.OK.name(),listOfCities);
            }


        } catch (HttpClientErrorException e) {
            Log.e(TAG, "Http Status: " + e.getStatusCode());
            Log.e(TAG, "Http Error: " + e.getResponseBodyAsString());
            return new RSGetCitiesResponse(e.getStatusCode(),
                    e.getStatusText());
        } catch (HttpServerErrorException e) {
            Log.e(TAG, "Http Status: " + e.getStatusCode());
            Log.e(TAG, "Http Error: " + e.getResponseBodyAsString());
            return new RSGetCitiesResponse(e.getStatusCode(),
                    e.getStatusText());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return new RSGetCitiesResponse(null, null);
        }
    }

    @Override
    protected void onPostExecute(RSGetCitiesResponse rsGetCitiesResponse) {
        super.onPostExecute(rsGetCitiesResponse);
        Log.e(TAG, "On post city ok");
        returnData.returnDataOnPostExecute(rsGetCitiesResponse);
    }
}
