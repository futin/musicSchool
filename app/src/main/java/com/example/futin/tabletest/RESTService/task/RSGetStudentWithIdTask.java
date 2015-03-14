package com.example.futin.tabletest.RESTService.task;

import android.os.AsyncTask;
import android.util.Log;

import com.example.futin.tabletest.RESTService.data.AsyncTaskReturnData;
import com.example.futin.tabletest.RESTService.data.RSDataSingleton;
import com.example.futin.tabletest.RESTService.models.City;
import com.example.futin.tabletest.RESTService.models.Student;
import com.example.futin.tabletest.RESTService.request.RSGetStudentWithIdRequest;
import com.example.futin.tabletest.RESTService.response.RSGetStudentWIthIdResponse;

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
 * Created by Futin on 3/9/2015.
 */
public class RSGetStudentWithIdTask extends AsyncTask<Void, Void, RSGetStudentWIthIdResponse>{

    final String TAG="getStudentWithIdTask";
    RestTemplate restTemplate;
    RSGetStudentWithIdRequest request;
    AsyncTaskReturnData returnData;

    public RSGetStudentWithIdTask(RSGetStudentWithIdRequest request, AsyncTaskReturnData returnData) {
        this.request = request;
        this.returnData = returnData;
        restTemplate=new RestTemplate();
    }

    @Override
    protected RSGetStudentWIthIdResponse doInBackground(Void... params) {
        try {
            HttpHeaders header = new HttpHeaders();
            header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            header.set("Connection", "Close");
            String jsonText = request.toString();
            HttpEntity<String> entity = new HttpEntity<>(jsonText, header);
            String address = RSDataSingleton.getInstance().getServerUrl().getStudentUrl();

            Log.i(TAG, "Address: " + address);
            Log.i(TAG, "Entity: " + entity);
            Log.i(TAG, "Before response ");
            ResponseEntity response = restTemplate.exchange(address, HttpMethod.POST, entity, String.class);
            Log.i(TAG, "After response ");
            if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                return new RSGetStudentWIthIdResponse(HttpStatus.NOT_FOUND,
                        HttpStatus.NOT_FOUND.name());
            } else if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
                return new RSGetStudentWIthIdResponse(HttpStatus.BAD_REQUEST,
                        HttpStatus.BAD_REQUEST.name());
            }else if (response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                return new RSGetStudentWIthIdResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                        HttpStatus.INTERNAL_SERVER_ERROR.name());
            } else {
                Log.i(TAG, "Response ok ");
                String jsonBody=response.getBody().toString();
                JSONArray array=new JSONArray(jsonBody);

                JSONObject objStudent=array.getJSONObject(0);

                String studentId=objStudent.getString("studentId");
                String firstName=objStudent.getString("firstName");
                String lastName=objStudent.getString("lastName");

                int cityPtt=objStudent.getInt("cityPtt");
                String cityName=objStudent.getString("cityName");

                City city=new City(cityPtt, cityName);
                Student st=new Student(studentId, firstName, lastName, city);


                return new RSGetStudentWIthIdResponse(HttpStatus.OK,
                        HttpStatus.OK.name(), st);
            }


        } catch (HttpClientErrorException e) {
            Log.e(TAG, "Http Status: " + e.getStatusCode());
            Log.e(TAG, "Http Error: " + e.getResponseBodyAsString());
            return new RSGetStudentWIthIdResponse(e.getStatusCode(),
                    e.getStatusText());
        } catch (HttpServerErrorException e) {
            Log.e(TAG, "Http Status: " + e.getStatusCode());
            Log.e(TAG, "Http Error: " + e.getResponseBodyAsString());
            return new RSGetStudentWIthIdResponse(e.getStatusCode(),
                    e.getStatusText());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return new RSGetStudentWIthIdResponse(null, null);
        }
    }

    @Override
    protected void onPostExecute(RSGetStudentWIthIdResponse rsGetStudentWIthIdResponse) {
        super.onPostExecute(rsGetStudentWIthIdResponse);
        returnData.returnDoneTask(rsGetStudentWIthIdResponse);
    }
}
