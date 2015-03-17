package com.example.futin.tabletest.RESTService.task;

import android.os.AsyncTask;
import android.util.Log;

import com.example.futin.tabletest.RESTService.data.RSDataSingleton;
import com.example.futin.tabletest.RESTService.interfaces.ReturnInstrumentData;
import com.example.futin.tabletest.RESTService.models.Employee;
import com.example.futin.tabletest.RESTService.models.Instrument;
import com.example.futin.tabletest.RESTService.response.RSGetEmployeesResponse;
import com.example.futin.tabletest.RESTService.response.RSGetInstrumentsResponse;

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
 * Created by Futin on 3/17/2015.
 */
public class RSGetInstrumentsTask extends AsyncTask<Void, Void, RSGetInstrumentsResponse> {

    final String TAG="getInstrumentsTask";
    RestTemplate restTemplate=null;
    ReturnInstrumentData returnData;

    public RSGetInstrumentsTask(ReturnInstrumentData returnData) {
        this.returnData = returnData;
        restTemplate=new RestTemplate();
    }

    @Override
    protected RSGetInstrumentsResponse doInBackground(Void... params) {
        try {
            HttpHeaders header = new HttpHeaders();
            header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            header.set("Connection", "Close");
            String jsonText = "";
            HttpEntity<String> entity = new HttpEntity<>(jsonText, header);
            String address = RSDataSingleton.getInstance().getServerUrl().getInstrumentsUrl();

            Log.i(TAG, "Address: " + address);
            Log.i(TAG, "Before response ");
            ResponseEntity response = restTemplate.exchange(address, HttpMethod.GET, entity, String.class);
            Log.i(TAG, "After response ");
            if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                return new RSGetInstrumentsResponse(HttpStatus.NOT_FOUND,
                        HttpStatus.NOT_FOUND.name());
            } else if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
                return new RSGetInstrumentsResponse(HttpStatus.BAD_REQUEST,
                        HttpStatus.BAD_REQUEST.name());
            }else if (response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                return new RSGetInstrumentsResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                        HttpStatus.INTERNAL_SERVER_ERROR.name());
            } else {
                Log.i(TAG, "Response ok ");
                String jsonBody=response.getBody().toString();
                Log.i(TAG, "Json : "+jsonBody);
                JSONArray array=new JSONArray(jsonBody);
                ArrayList<Instrument> listOfInstruments=new ArrayList<>();
                for (int i=0; i<array.length();i++){
                    JSONObject objInstrument=array.getJSONObject(i);

                    String instName=objInstrument.getString("instrumentName");
                    String instType=objInstrument.getString("instrumentType");
                    int instInStock=objInstrument.getInt("instrumentInStock");

                    Instrument instrument=new Instrument(instName, instType, instInStock);

                    listOfInstruments.add(instrument);
                }
                Log.i(TAG, "Instruments: "+listOfInstruments.toString());

                return new RSGetInstrumentsResponse(HttpStatus.OK,
                        HttpStatus.OK.name(), listOfInstruments);
            }


        } catch (HttpClientErrorException e) {
            Log.e(TAG, "Http Status: " + e.getStatusCode());
            Log.e(TAG, "Http Error: " + e.getResponseBodyAsString());
            return new RSGetInstrumentsResponse(e.getStatusCode(),
                    e.getStatusText());
        } catch (HttpServerErrorException e) {
            Log.e(TAG, "Http Status: " + e.getStatusCode());
            Log.e(TAG, "Http Error: " + e.getResponseBodyAsString());
            return new RSGetInstrumentsResponse(e.getStatusCode(),
                    e.getStatusText());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return new RSGetInstrumentsResponse(null, null);
        }
    }

    @Override
    protected void onPostExecute(RSGetInstrumentsResponse rsGetInstrumentsResponse) {
        super.onPostExecute(rsGetInstrumentsResponse);
        returnData.returnInstrumentData(rsGetInstrumentsResponse);
    }
}
