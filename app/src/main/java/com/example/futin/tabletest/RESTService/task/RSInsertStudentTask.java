package com.example.futin.tabletest.RESTService.task;

import android.os.AsyncTask;
import android.util.Log;

import com.example.futin.tabletest.RESTService.data.AsyncTaskReturnData;
import com.example.futin.tabletest.RESTService.data.RSDataSingleton;
import com.example.futin.tabletest.RESTService.request.RSCreateStudentRequest;
import com.example.futin.tabletest.RESTService.response.RSInsertStudentResponse;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Futin on 3/9/2015.
 */
public class RSInsertStudentTask extends AsyncTask<Void, Void, RSInsertStudentResponse> {

    final String TAG="crateStudentTask";
    RestTemplate restTemplate;
    RSCreateStudentRequest request;
    AsyncTaskReturnData returnData;

    public RSInsertStudentTask(RSCreateStudentRequest request, AsyncTaskReturnData returnData) {
        this.request = request;
        this.returnData=returnData;
        restTemplate=new RestTemplate();
    }

    @Override
    protected RSInsertStudentResponse doInBackground(Void... params) {
        try {
            HttpHeaders header = new HttpHeaders();
            header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            header.set("Connection", "Close");
            String jsonText = request.toString();
            HttpEntity<String> entity = new HttpEntity<>(jsonText, header);
            String address = RSDataSingleton.getInstance().getServerUrl().getInsertStudentUrl();

            Log.i(TAG, "Address: " + address);
            Log.i(TAG, "Entity: " + entity);
            Log.i(TAG, "Before response ");
            ResponseEntity response = restTemplate.exchange(address, HttpMethod.POST, entity, String.class);
            Log.i(TAG, "After response ");
            if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                return new RSInsertStudentResponse(HttpStatus.NOT_FOUND,
                        HttpStatus.NOT_FOUND.name());
            } else if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
                return new RSInsertStudentResponse(HttpStatus.BAD_REQUEST,
                        HttpStatus.BAD_REQUEST.name());
            }else if (response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                return new RSInsertStudentResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                        HttpStatus.INTERNAL_SERVER_ERROR.name());
            } else {
                Log.i(TAG, "Response ok ");



                return new RSInsertStudentResponse(HttpStatus.OK,
                        HttpStatus.OK.name());
            }


        } catch (HttpClientErrorException e) {
            Log.e(TAG, "Http Status: " + e.getStatusCode());
            Log.e(TAG, "Http Error: " + e.getResponseBodyAsString());
            return new RSInsertStudentResponse(e.getStatusCode(),
                    e.getStatusText());
        } catch (HttpServerErrorException e) {
            Log.e(TAG, "Http Status: " + e.getStatusCode());
            Log.e(TAG, "Http Error: " + e.getResponseBodyAsString());
            return new RSInsertStudentResponse(e.getStatusCode(),
                    e.getStatusText());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return new RSInsertStudentResponse(null, null);
        }
    }

    @Override
    protected void onPostExecute(RSInsertStudentResponse rsInsertStudentResponse) {
        super.onPostExecute(rsInsertStudentResponse);
        returnData.returnDoneTask(rsInsertStudentResponse);
    }
}
