package com.example.futin.tabletest.RESTService.task;

import android.os.AsyncTask;
import android.util.Log;

import com.example.futin.tabletest.RESTService.data.RSDataSingleton;
import com.example.futin.tabletest.RESTService.listeners.ReturnUpdateData;
import com.example.futin.tabletest.RESTService.listeners.SearchData;
import com.example.futin.tabletest.RESTService.models.Employee;
import com.example.futin.tabletest.RESTService.models.Instrument;
import com.example.futin.tabletest.RESTService.models.Student;
import com.example.futin.tabletest.RESTService.request.RSSearchForStudentWithInstrumentRequest;
import com.example.futin.tabletest.RESTService.request.RSUpdateInstrumentsInStockRequest;
import com.example.futin.tabletest.RESTService.response.RSSearchForStudentWIthInstrumentsResponse;
import com.example.futin.tabletest.RESTService.response.RSUpdateInstrumentsInStockResponse;

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
 * Created by Futin on 3/30/2015.
 */
public class RSUpdateInstrumentsInStockTask extends AsyncTask<Void, Void, RSUpdateInstrumentsInStockResponse> {

    final String TAG="updateDataTask";
    RestTemplate restTemplate;
    ReturnUpdateData returnData;
    RSUpdateInstrumentsInStockRequest request;

    public RSUpdateInstrumentsInStockTask(RSUpdateInstrumentsInStockRequest request,ReturnUpdateData returnData) {
        this.returnData = returnData;
        this.request = request;
        restTemplate=new RestTemplate();
    }

    @Override
    protected RSUpdateInstrumentsInStockResponse doInBackground(Void... params) {
        try {
            HttpHeaders header = new HttpHeaders();
            header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            header.set("Connection", "Close");
            String jsonText = request.toString();
            HttpEntity<String> entity = new HttpEntity<>(jsonText, header);
            String address = RSDataSingleton.getInstance().getServerUrl().getUpdateInstrumentsInStockUrl();

            Log.i(TAG, "entity: " + jsonText);
            Log.i(TAG, "Address: " + address);
            Log.i(TAG, "Before response ");
            ResponseEntity response = restTemplate.exchange(address, HttpMethod.POST, entity, String.class);
            Log.i(TAG, "After response ");
            if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                return new RSUpdateInstrumentsInStockResponse(HttpStatus.NOT_FOUND,
                        HttpStatus.NOT_FOUND.name());
            } else if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
                return new RSUpdateInstrumentsInStockResponse(HttpStatus.BAD_REQUEST,
                        HttpStatus.BAD_REQUEST.name());
            }else if (response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                return new RSUpdateInstrumentsInStockResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                        HttpStatus.INTERNAL_SERVER_ERROR.name());
            } else {
                Log.i(TAG, "Response ok ");
                return new RSUpdateInstrumentsInStockResponse(HttpStatus.OK,
                        HttpStatus.OK.name());
            }


        } catch (HttpClientErrorException e) {
            Log.e(TAG, "Http Status: " + e.getStatusCode());
            Log.e(TAG, "Http Error: " + e.getResponseBodyAsString());
            return new RSUpdateInstrumentsInStockResponse(e.getStatusCode(),
                    e.getStatusText());
        } catch (HttpServerErrorException e) {
            Log.e(TAG, "Http Status: " + e.getStatusCode());
            Log.e(TAG, "Http Error: " + e.getResponseBodyAsString());
            return new RSUpdateInstrumentsInStockResponse(e.getStatusCode(),
                    e.getStatusText());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return new RSUpdateInstrumentsInStockResponse(null, null);
        }
    }


    @Override
    protected void onPostExecute(RSUpdateInstrumentsInStockResponse rsUpdateInstrumentsInStockResponse) {
        super.onPostExecute(rsUpdateInstrumentsInStockResponse);
        returnData.updateData(rsUpdateInstrumentsInStockResponse);
    }
}
