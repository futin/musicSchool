package com.example.futin.tabletest.RESTService.task;

import android.os.AsyncTask;
import android.util.Log;

import com.example.futin.tabletest.RESTService.data.RSDataSingleton;
import com.example.futin.tabletest.RESTService.listeners.SearchInstrumentData;
import com.example.futin.tabletest.RESTService.models.City;
import com.example.futin.tabletest.RESTService.models.Instrument;
import com.example.futin.tabletest.RESTService.models.Student;
import com.example.futin.tabletest.RESTService.request.RSSearchForInstrumentRequest;
import com.example.futin.tabletest.RESTService.response.RSGetInstrumentsResponse;
import com.example.futin.tabletest.RESTService.response.RSSearchForInstrumentResponse;
import com.example.futin.tabletest.RESTService.response.RSSearchForStudentResponse;

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
 * Created by Futin on 3/22/2015.
 */
public class RSSearchForInstrumentTask extends AsyncTask<Void, Void, RSSearchForInstrumentResponse> {

    final String TAG="searchForInstrumentTask";
    RestTemplate restTemplate;
    SearchInstrumentData returnData;
    RSSearchForInstrumentRequest request;

    public RSSearchForInstrumentTask(RSSearchForInstrumentRequest request, SearchInstrumentData returnData) {
        this.returnData = returnData;
        this.request = request;
        restTemplate=new RestTemplate();
    }

    @Override
    protected RSSearchForInstrumentResponse doInBackground(Void... params) {
        try {
            HttpHeaders header = new HttpHeaders();
            header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            header.set("Connection", "Close");
            String jsonText = request.toString();
            HttpEntity<String> entity = new HttpEntity<>(jsonText, header);
            String address = RSDataSingleton.getInstance().getServerUrl().getSearchForStudentUrl();

            Log.i(TAG, "Address: " + address);
            Log.i(TAG, "Before response ");
            ResponseEntity response = restTemplate.exchange(address, HttpMethod.POST, entity, String.class);
            Log.i(TAG, "After response ");
            if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                return new RSSearchForInstrumentResponse(HttpStatus.NOT_FOUND,
                        HttpStatus.NOT_FOUND.name());
            } else if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
                return new RSSearchForInstrumentResponse(HttpStatus.BAD_REQUEST,
                        HttpStatus.BAD_REQUEST.name());
            }else if (response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                return new RSSearchForInstrumentResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                        HttpStatus.INTERNAL_SERVER_ERROR.name());
            } else {
                Log.i(TAG, "Response ok ");
                String jsonBody=response.getBody().toString();
                ArrayList<Instrument> listOfInstruments = new ArrayList<>();

                if (jsonBody != null) {
                    Log.i(TAG, "Json : " + jsonBody);
                    JSONArray array = new JSONArray(jsonBody);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject objInstrument = array.getJSONObject(i);

                        int instId = objInstrument.getInt("instrumentId");
                        String instName = objInstrument.getString("instrumentName");
                        String instType = objInstrument.getString("instrumentType");
                        int instInStock = objInstrument.getInt("instrumentsInStock");

                        Instrument instrument = new Instrument(instId, instName, instType, instInStock);

                        listOfInstruments.add(instrument);
                    }
                    Log.i(TAG, "Instruments: " + listOfInstruments.toString());
                }
                return new RSSearchForInstrumentResponse(HttpStatus.OK,
                        HttpStatus.OK.name(), listOfInstruments);
            }


        } catch (HttpClientErrorException e) {
            Log.e(TAG, "Http Status: " + e.getStatusCode());
            Log.e(TAG, "Http Error: " + e.getResponseBodyAsString());
            return new RSSearchForInstrumentResponse(e.getStatusCode(),
                    e.getStatusText());
        } catch (HttpServerErrorException e) {
            Log.e(TAG, "Http Status: " + e.getStatusCode());
            Log.e(TAG, "Http Error: " + e.getResponseBodyAsString());
            return new RSSearchForInstrumentResponse(e.getStatusCode(),
                    e.getStatusText());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return new RSSearchForInstrumentResponse(null, null);
        }
    }

    @Override
    protected void onPostExecute(RSSearchForInstrumentResponse rsSearchForInstrumentResponse) {
        super.onPostExecute(rsSearchForInstrumentResponse);
        returnData.searchInstrumentReturnData(rsSearchForInstrumentResponse);
    }
}
