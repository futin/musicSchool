package com.example.futin.tabletest.RESTService.task;

import android.os.AsyncTask;
import android.util.Log;

import com.example.futin.tabletest.RESTService.data.RSDataSingleton;
import com.example.futin.tabletest.RESTService.listeners.ReturnStudentData;
import com.example.futin.tabletest.RESTService.models.City;
import com.example.futin.tabletest.RESTService.models.Student;
import com.example.futin.tabletest.RESTService.response.RSGetStudentsResponse;

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
public class RSGetStudentsTask extends AsyncTask<Void, Void, RSGetStudentsResponse>{

    final String TAG="getStudentsTask";
    RestTemplate restTemplate;
    ReturnStudentData returnData;

    public RSGetStudentsTask(ReturnStudentData returnData) {
        this.returnData = returnData;
        restTemplate=new RestTemplate();
    }

    @Override
    protected RSGetStudentsResponse doInBackground(Void... params) {
        try {
            HttpHeaders header = new HttpHeaders();
            header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            header.set("Connection", "Close");
            String jsonText = "";
            HttpEntity<String> entity = new HttpEntity<>(jsonText, header);
            String address = RSDataSingleton.getInstance().getServerUrl().getStudentsUrl();

            Log.i(TAG, "Address: " + address);
            Log.i(TAG, "Entity: " + entity);
            Log.i(TAG, "Before response ");
            ResponseEntity response = restTemplate.exchange(address, HttpMethod.GET, entity, String.class);
            Log.i(TAG, "After response ");
            if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                return new RSGetStudentsResponse(HttpStatus.NOT_FOUND,
                        HttpStatus.NOT_FOUND.name());
            } else if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
                return new RSGetStudentsResponse(HttpStatus.BAD_REQUEST,
                        HttpStatus.BAD_REQUEST.name());
            }else if (response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                return new RSGetStudentsResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                        HttpStatus.INTERNAL_SERVER_ERROR.name());
            } else {
                Log.i(TAG, "Response ok ");
                String jsonBody=response.getBody().toString();
                JSONArray array=new JSONArray(jsonBody);
                ArrayList<Student> listOfStudents=new ArrayList<>();
                for (int i=0; i<array.length();i++){
                    JSONObject objStudent=array.getJSONObject(i);

                    String studentId=objStudent.getString("studentId");
                    String firstName=objStudent.getString("firstName");
                    String lastName=objStudent.getString("lastName");
                    int cityPtt=objStudent.getInt("cityPtt");

                    City city=new City(cityPtt);
                    Student student=new Student(studentId, firstName, lastName, city);

                    Log.i(TAG, "City "+city);
                    Log.i(TAG, "Student "+student);

                    listOfStudents.add(student);
                }
                Log.i(TAG, "Students: "+listOfStudents.toString());
                return new RSGetStudentsResponse(HttpStatus.OK,
                        HttpStatus.OK.name(), listOfStudents);
            }


        } catch (HttpClientErrorException e) {
            Log.e(TAG, "Http Status: " + e.getStatusCode());
            Log.e(TAG, "Http Error: " + e.getResponseBodyAsString());
            return new RSGetStudentsResponse(e.getStatusCode(),
                    e.getStatusText());
        } catch (HttpServerErrorException e) {
            Log.e(TAG, "Http Status: " + e.getStatusCode());
            Log.e(TAG, "Http Error: " + e.getResponseBodyAsString());
            return new RSGetStudentsResponse(e.getStatusCode(),
                    e.getStatusText());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return new RSGetStudentsResponse(null, null);
        }
    }

    @Override
    protected void onPostExecute(RSGetStudentsResponse rsGetStudentWIthIdResponse) {
        super.onPostExecute(rsGetStudentWIthIdResponse);
        returnData.returnStudentDataOnPostExecute(rsGetStudentWIthIdResponse);
        Log.i(TAG, "onPostExecute ok ");

    }
}
