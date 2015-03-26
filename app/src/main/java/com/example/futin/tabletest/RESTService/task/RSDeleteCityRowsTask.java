package com.example.futin.tabletest.RESTService.task;

import android.os.AsyncTask;
import android.util.Log;

import com.example.futin.tabletest.RESTService.data.RSDataSingleton;
import com.example.futin.tabletest.RESTService.listeners.DeleteCityRows;
import com.example.futin.tabletest.RESTService.models.City;
import com.example.futin.tabletest.RESTService.request.RSDeleteCityRowsRequest;
import com.example.futin.tabletest.RESTService.response.RSDeleteCityRowsResponse;
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
 * Created by Futin on 3/26/2015.
 */
public class RSDeleteCityRowsTask extends AsyncTask<Void, Void, RSDeleteCityRowsResponse> {

    final String TAG="deleteCityRowsTask";
    RSDeleteCityRowsRequest request;
    RestTemplate restTemplate;
    DeleteCityRows returnData;

    public RSDeleteCityRowsTask(RSDeleteCityRowsRequest request, DeleteCityRows returnData) {
        this.request = request;
        this.returnData = returnData;
        restTemplate=new RestTemplate();
    }

    @Override
    protected RSDeleteCityRowsResponse doInBackground(Void... params) {
        try {
            HttpHeaders header = new HttpHeaders();
            header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            header.set("Connection", "Close");
            String listOfCitiesToString = request.toString();
            //take data from the list and split them
            String listOfCitiesWithNoBrackets=listOfCitiesToString.substring(1,listOfCitiesToString.length()-1);

            String listOfCities="listOfCities="+listOfCitiesWithNoBrackets;
            Log.i(TAG, "List of cities: "+ listOfCities);

            HttpEntity<String> entity = new HttpEntity<>(listOfCities, header);
            String address = RSDataSingleton.getInstance().getServerUrl().getDeleteCityRowUrl();

            Log.i(TAG, "Address: " + address);
            Log.i(TAG, "Before response ");
            ResponseEntity response = restTemplate.exchange(address, HttpMethod.POST, entity, String.class);
            Log.i(TAG, "After response ");
            if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                return new RSDeleteCityRowsResponse(HttpStatus.NOT_FOUND,
                        HttpStatus.NOT_FOUND.name());
            } else if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
                return new RSDeleteCityRowsResponse(HttpStatus.BAD_REQUEST,
                        HttpStatus.BAD_REQUEST.name());
            }else if (response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                return new RSDeleteCityRowsResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                        HttpStatus.INTERNAL_SERVER_ERROR.name());
            } else {
                Log.i(TAG, "Response ok ");
                String jsonBody=response.getBody().toString();


                return new RSDeleteCityRowsResponse(HttpStatus.OK,
                        HttpStatus.OK.name());
            }


        } catch (HttpClientErrorException e) {
            Log.e(TAG, "Http Status: " + e.getStatusCode());
            Log.e(TAG, "Http Error: " + e.getResponseBodyAsString());
            return new RSDeleteCityRowsResponse(e.getStatusCode(),
                    e.getStatusText());
        } catch (HttpServerErrorException e) {
            Log.e(TAG, "Http Status: " + e.getStatusCode());
            Log.e(TAG, "Http Error: " + e.getResponseBodyAsString());
            return new RSDeleteCityRowsResponse(e.getStatusCode(),
                    e.getStatusText());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return new RSDeleteCityRowsResponse(null, null);
        }
    }

    @Override
    protected void onPostExecute(RSDeleteCityRowsResponse rsDeleteCityRowsResponse) {
        super.onPostExecute(rsDeleteCityRowsResponse);
        returnData.deleteCityRowsReturnData(rsDeleteCityRowsResponse);
    }
}
