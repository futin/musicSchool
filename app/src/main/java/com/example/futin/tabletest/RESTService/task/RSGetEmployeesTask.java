package com.example.futin.tabletest.RESTService.task;

import android.os.AsyncTask;
import android.util.Log;

import com.example.futin.tabletest.RESTService.interfaces.AsyncTaskReturnData;
import com.example.futin.tabletest.RESTService.data.RSDataSingleton;
import com.example.futin.tabletest.RESTService.models.Employee;
import com.example.futin.tabletest.RESTService.response.RSGetEmployeesResponse;

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
 * Created by Futin on 3/14/2015.
 */
public class RSGetEmployeesTask extends AsyncTask<Void, Void, RSGetEmployeesResponse> {

    final String TAG="getEmployees";
    RestTemplate restTemplate;
    AsyncTaskReturnData returnData;

    public RSGetEmployeesTask(AsyncTaskReturnData returnData) {
        this.returnData = returnData;
        restTemplate=new RestTemplate();
    }

    @Override
    protected RSGetEmployeesResponse doInBackground(Void... params) {
        try {
            HttpHeaders header = new HttpHeaders();
            header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            header.set("Connection", "Close");
            String jsonText = "";
            HttpEntity<String> entity = new HttpEntity<>(jsonText, header);
            String address = RSDataSingleton.getInstance().getServerUrl().getEmployeesUrl();

            Log.i(TAG, "Address: " + address);
            Log.i(TAG, "Before response ");
            ResponseEntity response = restTemplate.exchange(address, HttpMethod.GET, entity, String.class);
            Log.i(TAG, "After response ");
            if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                return new RSGetEmployeesResponse(HttpStatus.NOT_FOUND,
                        HttpStatus.NOT_FOUND.name());
            } else if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
                return new RSGetEmployeesResponse(HttpStatus.BAD_REQUEST,
                        HttpStatus.BAD_REQUEST.name());
            }else if (response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                return new RSGetEmployeesResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                        HttpStatus.INTERNAL_SERVER_ERROR.name());
            } else {
                Log.i(TAG, "Response ok ");
                String jsonBody=response.getBody().toString();
                Log.i(TAG, "Json : "+jsonBody);
                JSONArray array=new JSONArray(jsonBody);
                ArrayList<Employee> listOfEmployees=new ArrayList<>();
                for (int i=0; i<array.length();i++){
                    JSONObject objEmployee=array.getJSONObject(i);
                    String firstName=objEmployee.getString("firstName");
                    String lastName=objEmployee.getString("lastName");
                    String password=objEmployee.getString("password");
                    String username=objEmployee.getString("username");



                    Employee emp=new Employee(username, password, firstName, lastName);
                    listOfEmployees.add(emp);
                }
                Log.i(TAG, "Employees: "+listOfEmployees.toString());

                return new RSGetEmployeesResponse(HttpStatus.OK,
                        HttpStatus.OK.name(),listOfEmployees);
            }


        } catch (HttpClientErrorException e) {
            Log.e(TAG, "Http Status: " + e.getStatusCode());
            Log.e(TAG, "Http Error: " + e.getResponseBodyAsString());
            return new RSGetEmployeesResponse(e.getStatusCode(),
                    e.getStatusText());
        } catch (HttpServerErrorException e) {
            Log.e(TAG, "Http Status: " + e.getStatusCode());
            Log.e(TAG, "Http Error: " + e.getResponseBodyAsString());
            return new RSGetEmployeesResponse(e.getStatusCode(),
                    e.getStatusText());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return new RSGetEmployeesResponse(null, null);
        }
    }

    @Override
    protected void onPostExecute(RSGetEmployeesResponse rsGetEmployeesResponse) {
        super.onPostExecute(rsGetEmployeesResponse);
        returnData.returnDataOnPostExecute(rsGetEmployeesResponse);
    }
}
