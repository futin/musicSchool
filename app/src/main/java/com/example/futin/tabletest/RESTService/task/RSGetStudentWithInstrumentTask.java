package com.example.futin.tabletest.RESTService.task;

import android.os.AsyncTask;
import android.util.Log;

import com.example.futin.tabletest.RESTService.data.RSDataSingleton;
import com.example.futin.tabletest.RESTService.listeners.ReturnStudentWithInstrumentData;
import com.example.futin.tabletest.RESTService.models.Employee;
import com.example.futin.tabletest.RESTService.models.Instrument;
import com.example.futin.tabletest.RESTService.models.Student;
import com.example.futin.tabletest.RESTService.request.RSInsertStudentWithInstrumentRequest;
import com.example.futin.tabletest.RESTService.response.RSGetStudentWithInstrumentResponse;
import com.example.futin.tabletest.RESTService.response.RSInsertStudentWithInstrumentResponse;

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
 * Created by Futin on 3/21/2015.
 */
public class RSGetStudentWithInstrumentTask extends AsyncTask<Void, Void, RSGetStudentWithInstrumentResponse>
{

    final String TAG="getStudentWithInst";
    RestTemplate restTemplate;
    ReturnStudentWithInstrumentData returnData;

    public RSGetStudentWithInstrumentTask(ReturnStudentWithInstrumentData returnData) {
        this.returnData = returnData;
        restTemplate=new RestTemplate();
    }

    @Override
    protected RSGetStudentWithInstrumentResponse doInBackground(Void... params) {
        try {
            HttpHeaders header = new HttpHeaders();
            header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            header.set("Connection", "Close");
            String jsonText = "";
            HttpEntity<String> entity = new HttpEntity<>(jsonText, header);
            String address = RSDataSingleton.getInstance().getServerUrl().getStudentWIthInstrumentUrl();

            Log.i(TAG, "Address: " + address);
            Log.i(TAG, "Before response ");
            ResponseEntity response = restTemplate.exchange(address, HttpMethod.GET, entity, String.class);
            Log.i(TAG, "After response ");
            if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                return new RSGetStudentWithInstrumentResponse(HttpStatus.NOT_FOUND,
                        HttpStatus.NOT_FOUND.name());
            } else if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
                return new RSGetStudentWithInstrumentResponse(HttpStatus.BAD_REQUEST,
                        HttpStatus.BAD_REQUEST.name());
            }else if (response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                return new RSGetStudentWithInstrumentResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                        HttpStatus.INTERNAL_SERVER_ERROR.name());
            } else {
                Log.i(TAG, "Response ok ");

                String jsonBody=response.getBody().toString();
                Log.i(TAG, "Json body "+jsonBody);

                JSONArray array=new JSONArray(jsonBody);

                ArrayList<Employee>listOfEmployees=new ArrayList<>();
                for (int i=0; i<array.length();i++){
                    JSONObject objEmployee=array.getJSONObject(i);

                    //student
                    String studentId=objEmployee.getString("studentId");
                    String studFirstName=objEmployee.getString("firstName");
                    String studentLastName=objEmployee.getString("lastName");
                    //instrument
                    int instrumentId=objEmployee.getInt("instrumentId");
                    String instName=objEmployee.getString("instrumentName");
                    //
                    //studentWithInstrument
                    String employeeName=objEmployee.getString("employeeName");
                    String date=objEmployee.getString("date");
                    int numberOfInstrument=objEmployee.getInt("numberOfInstruments");

                    Employee employee=new Employee(employeeName, new Student(studentId, new Instrument(instrumentId,
                             instName),studFirstName, studentLastName, numberOfInstrument),date);

                    listOfEmployees.add(employee);
                }
                Log.i(TAG, "Employee: "+listOfEmployees.toString());

                return new RSGetStudentWithInstrumentResponse(HttpStatus.OK,
                        HttpStatus.OK.name(),listOfEmployees);
            }



        } catch (HttpClientErrorException e) {
            Log.e(TAG, "Http Status: " + e.getStatusCode());
            Log.e(TAG, "Http Error: " + e.getResponseBodyAsString());
            return new RSGetStudentWithInstrumentResponse(e.getStatusCode(),
                    e.getStatusText());
        } catch (HttpServerErrorException e) {
            Log.e(TAG, "Http Status: " + e.getStatusCode());
            Log.e(TAG, "Http Error: " + e.getResponseBodyAsString());
            return new RSGetStudentWithInstrumentResponse(e.getStatusCode(),
                    e.getStatusText());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return new RSGetStudentWithInstrumentResponse(null, null);
        }
    }

    @Override
    protected void onPostExecute(RSGetStudentWithInstrumentResponse rsGetStudentWithInstrumentResponse) {
        super.onPostExecute(rsGetStudentWithInstrumentResponse);
        Log.i(TAG, "onPostExecute ok");
        returnData.returnStudentWithInstrumentDataOnPostExecute(rsGetStudentWithInstrumentResponse);
    }
}
