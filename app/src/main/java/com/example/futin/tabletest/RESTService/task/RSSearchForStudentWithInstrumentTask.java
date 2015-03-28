package com.example.futin.tabletest.RESTService.task;

import android.os.AsyncTask;
import android.util.Log;

import com.example.futin.tabletest.RESTService.data.RSDataSingleton;
import com.example.futin.tabletest.RESTService.listeners.SearchData;
import com.example.futin.tabletest.RESTService.models.Employee;
import com.example.futin.tabletest.RESTService.models.Instrument;
import com.example.futin.tabletest.RESTService.models.Student;
import com.example.futin.tabletest.RESTService.request.RSSearchForStudentWithInstrumentRequest;
import com.example.futin.tabletest.RESTService.response.RSSearchForStudentWIthInstrumentsResponse;

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
public class RSSearchForStudentWithInstrumentTask extends AsyncTask<Void, Void, RSSearchForStudentWIthInstrumentsResponse> {

    final String TAG="searchForSWI";
    RestTemplate restTemplate;
    SearchData returnData;
    RSSearchForStudentWithInstrumentRequest request;

    public RSSearchForStudentWithInstrumentTask(RSSearchForStudentWithInstrumentRequest request, SearchData returnData) {
        this.returnData = returnData;
        this.request = request;
        restTemplate=new RestTemplate();
    }

    @Override
    protected RSSearchForStudentWIthInstrumentsResponse doInBackground(Void... params) {
        try {
            HttpHeaders header = new HttpHeaders();
            header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            header.set("Connection", "Close");
            String jsonText = request.toString();
            HttpEntity<String> entity = new HttpEntity<>(jsonText, header);
            String address = RSDataSingleton.getInstance().getServerUrl().getSearchForStudentWithInstrumentUrl();

            Log.i(TAG, "Address: " + address);
            Log.i(TAG, "Before response ");
            ResponseEntity response = restTemplate.exchange(address, HttpMethod.POST, entity, String.class);
            Log.i(TAG, "After response ");
            if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                return new RSSearchForStudentWIthInstrumentsResponse(HttpStatus.NOT_FOUND,
                        HttpStatus.NOT_FOUND.name());
            } else if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
                return new RSSearchForStudentWIthInstrumentsResponse(HttpStatus.BAD_REQUEST,
                        HttpStatus.BAD_REQUEST.name());
            }else if (response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                return new RSSearchForStudentWIthInstrumentsResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                        HttpStatus.INTERNAL_SERVER_ERROR.name());
            } else {
                Log.i(TAG, "Response ok ");
                String jsonBody=response.getBody().toString();
                ArrayList<Employee> listOfEmployees = new ArrayList<>();

                if (jsonBody != null) {
                    Log.i(TAG, "Json : " + jsonBody);
                    JSONArray array=new JSONArray(jsonBody);
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
                    Log.i(TAG, "Employees: " + listOfEmployees.toString());
                }
                return new RSSearchForStudentWIthInstrumentsResponse(HttpStatus.OK,
                        HttpStatus.OK.name(), listOfEmployees);
            }


        } catch (HttpClientErrorException e) {
            Log.e(TAG, "Http Status: " + e.getStatusCode());
            Log.e(TAG, "Http Error: " + e.getResponseBodyAsString());
            return new RSSearchForStudentWIthInstrumentsResponse(e.getStatusCode(),
                    e.getStatusText());
        } catch (HttpServerErrorException e) {
            Log.e(TAG, "Http Status: " + e.getStatusCode());
            Log.e(TAG, "Http Error: " + e.getResponseBodyAsString());
            return new RSSearchForStudentWIthInstrumentsResponse(e.getStatusCode(),
                    e.getStatusText());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return new RSSearchForStudentWIthInstrumentsResponse(null, null);
        }
    }

    @Override
    protected void onPostExecute(RSSearchForStudentWIthInstrumentsResponse rsSearchSWI) {
        super.onPostExecute(rsSearchSWI);
        returnData.searchData(rsSearchSWI);
    }
}
