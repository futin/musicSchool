package com.example.futin.tabletest.RESTService.task;

import android.os.AsyncTask;
import android.util.Log;

import com.example.futin.tabletest.RESTService.data.RSDataSingleton;
import com.example.futin.tabletest.RESTService.request.RSCreateEmployeeRequest;
import com.example.futin.tabletest.RESTService.response.RSCreateEmployeeResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import org.apache.http.HttpResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
/**
 * Created by Futin on 3/3/2015.
 */
public class RSCreateEmployeeTask extends AsyncTask<Void, Void, RSCreateEmployeeResponse> {

    final String TAG="createEmployee";
    RSCreateEmployeeRequest request;
    RestTemplate restTemplate;

    public RSCreateEmployeeTask(RSCreateEmployeeRequest request) {
        this.request = request;
        restTemplate=new RestTemplate();
    }

    @Override
    protected RSCreateEmployeeResponse doInBackground(Void... params) {
        try {
        HttpHeaders header=new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);

        ObjectWriter ow=new ObjectMapper().writer().withDefaultPrettyPrinter();

        String jsonText=ow.writeValueAsString(request);
            HttpEntity<String> entity=new HttpEntity<>(jsonText, header);
            Log.i(TAG,"Pre responsa ");

            ResponseEntity response= restTemplate.exchange(RSDataSingleton.getInstance().getServerUrl().getEmployeeRegistrationUrl(),
                    HttpMethod.POST, entity, String.class);
            Log.i(TAG,"Nakon responsa ");

            if(response.getStatusCode()== HttpStatus.BAD_REQUEST){
                new RSCreateEmployeeResponse(HttpStatus.BAD_REQUEST.toString(),HttpStatus.BAD_REQUEST.getReasonPhrase());
            }else if(response.getStatusCode()==HttpStatus.OK){

                String jsonBody=response.getBody().toString();
                Log.i(TAG,"json text: "+jsonBody);
                new RSCreateEmployeeResponse(HttpStatus.OK.toString(),HttpStatus.OK.getReasonPhrase());
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(RSCreateEmployeeResponse rsCreateEmployeeResponse) {
        super.onPostExecute(rsCreateEmployeeResponse);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
