package com.example.futin.tabletest.RESTService.task;

import android.os.AsyncTask;
import android.util.Log;

import com.example.futin.tabletest.RESTService.data.AsyncTaskReturnData;
import com.example.futin.tabletest.RESTService.data.RSDataSingleton;
import com.example.futin.tabletest.RESTService.models.Employee;
import com.example.futin.tabletest.RESTService.request.RSSignInRequest;
import com.example.futin.tabletest.RESTService.response.RSSignInResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

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

/**
 * Created by Futin on 3/7/2015.
 */
public class RSSignInTask extends AsyncTask<Void, Void, RSSignInResponse> {

    final String TAG="signInTask";
    RestTemplate restTemplate=null;
    RSSignInRequest request;
    AsyncTaskReturnData returnData;

    public RSSignInTask(RSSignInRequest request, AsyncTaskReturnData returnData) {
        this.request = request;
        this.returnData=returnData;
        restTemplate=new RestTemplate();
    }

    @Override
    protected RSSignInResponse doInBackground(Void... params) {
        try {
            HttpHeaders header = new HttpHeaders();
            header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            //It is necessary for keepItAlive protocol, without it connection would crash every second time
            header.set("Connection", "Close");
            String jsonText = request.toString();
            HttpEntity<String> entity = new HttpEntity<>(jsonText, header);
            String address = RSDataSingleton.getInstance().getServerUrl().getEmployeeLoginUrl();

            Log.i(TAG, "Address: " + address);
            Log.i(TAG, "Entity: " + entity);
            Log.i(TAG, "Before response ");
            ResponseEntity response = restTemplate.exchange(address, HttpMethod.POST, entity, String.class);
            Log.i(TAG, "After response ");
            if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                return new RSSignInResponse(HttpStatus.NOT_FOUND,
                        HttpStatus.NOT_FOUND.name());
            } else if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
                return new RSSignInResponse(HttpStatus.BAD_REQUEST,
                        HttpStatus.BAD_REQUEST.name());
            }else if (response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                return new RSSignInResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                        HttpStatus.INTERNAL_SERVER_ERROR.name());
            } else {
                Log.i(TAG, "Response ok ");
                String jsonBody=response.getBody().toString();

                JSONArray array=new JSONArray(jsonBody);
                JSONObject obj=array.getJSONObject(0);

                String user=obj.getString("username");
                String pass=obj.getString("password");
                String fn=obj.getString("firstName");
                String ln=obj.getString("lastName");

                Employee e=new Employee(user,pass,fn,ln);
                return new RSSignInResponse(HttpStatus.OK,
                        HttpStatus.OK.name(),e);
            }


        } catch (HttpClientErrorException e) {
            Log.e(TAG, "Http Status: " + e.getStatusCode());
            Log.e(TAG, "Http Error: " + e.getResponseBodyAsString());
            return new RSSignInResponse(e.getStatusCode(),
                    e.getStatusText());
        } catch (HttpServerErrorException e) {
            Log.e(TAG, "Http Status: " + e.getStatusCode());
            Log.e(TAG, "Http Error: " + e.getResponseBodyAsString());
            return new RSSignInResponse(e.getStatusCode(),
                    e.getStatusText());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return new RSSignInResponse(null, null);
        }
    }

    @Override
    protected void onPostExecute(RSSignInResponse rsSignInResponse) {
        super.onPostExecute(rsSignInResponse);
        returnData.returnDoneTask(rsSignInResponse);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
