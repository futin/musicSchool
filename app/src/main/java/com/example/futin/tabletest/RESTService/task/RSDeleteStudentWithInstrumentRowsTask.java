package com.example.futin.tabletest.RESTService.task;

import android.os.AsyncTask;
import android.util.Log;

import com.example.futin.tabletest.RESTService.data.RSDataSingleton;
import com.example.futin.tabletest.RESTService.listeners.DeleteRows;
import com.example.futin.tabletest.RESTService.request.RSDeleteStudentWithInstrumentRowsRequest;
import com.example.futin.tabletest.RESTService.response.RSDeleteStudentRowsResponse;
import com.example.futin.tabletest.RESTService.response.RSDeleteStudentWithInstrumentResponse;

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
 * Created by Futin on 3/28/2015.
 */
public class RSDeleteStudentWithInstrumentRowsTask extends AsyncTask<Void, Void, RSDeleteStudentWithInstrumentResponse> {

    final String TAG="deleteStudInst";
    RSDeleteStudentWithInstrumentRowsRequest request;
    RestTemplate restTemplate;
    DeleteRows returnData;

    public RSDeleteStudentWithInstrumentRowsTask(RSDeleteStudentWithInstrumentRowsRequest request, DeleteRows returnData) {
        this.request = request;
        this.returnData = returnData;
        restTemplate=new RestTemplate();
    }

    @Override
    protected RSDeleteStudentWithInstrumentResponse doInBackground(Void... params) {
        try {
            HttpHeaders header = new HttpHeaders();
            header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            header.set("Connection", "Close");
            String listOfStudentsWithInstrumentToString = request.toString();
            //take data from the list
            String listOfStudentsWithInstrumentWithNoBrackets=listOfStudentsWithInstrumentToString.substring(1,listOfStudentsWithInstrumentToString.length()-1);

            String listOfStudentsWithInstrument="listOfStudentsWithInstrument="+listOfStudentsWithInstrumentWithNoBrackets;
            Log.i(TAG, "List of studentsWithInstrument: " + listOfStudentsWithInstrument);

            HttpEntity<String> entity = new HttpEntity<>(listOfStudentsWithInstrument, header);
            String address = RSDataSingleton.getInstance().getServerUrl().getDeleteStudentWithInstrumentRowUrl();

            Log.i(TAG, "Address: " + address);
            Log.i(TAG, "Before response ");
            ResponseEntity response = restTemplate.exchange(address, HttpMethod.POST, entity, String.class);
            Log.i(TAG, "After response ");
            if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                return new RSDeleteStudentWithInstrumentResponse(HttpStatus.NOT_FOUND,
                        HttpStatus.NOT_FOUND.name());
            } else if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
                return new RSDeleteStudentWithInstrumentResponse(HttpStatus.BAD_REQUEST,
                        HttpStatus.BAD_REQUEST.name());
            }else if (response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                return new RSDeleteStudentWithInstrumentResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                        HttpStatus.INTERNAL_SERVER_ERROR.name());
            } else {
                Log.i(TAG, "Response ok ");
                return new RSDeleteStudentWithInstrumentResponse(HttpStatus.OK,
                        HttpStatus.OK.name());
            }


        } catch (HttpClientErrorException e) {
            Log.e(TAG, "Http Status: " + e.getStatusCode());
            Log.e(TAG, "Http Error: " + e.getResponseBodyAsString());
            return new RSDeleteStudentWithInstrumentResponse(e.getStatusCode(),
                    e.getStatusText());
        } catch (HttpServerErrorException e) {
            Log.e(TAG, "Http Status: " + e.getStatusCode());
            Log.e(TAG, "Http Error: " + e.getResponseBodyAsString());
            return new RSDeleteStudentWithInstrumentResponse(e.getStatusCode(),
                    e.getStatusText());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return new RSDeleteStudentWithInstrumentResponse(null, null);
        }
    }

    @Override
    protected void onPostExecute(RSDeleteStudentWithInstrumentResponse rsDeleteStudentWithInstrumentResponse) {
        super.onPostExecute(rsDeleteStudentWithInstrumentResponse);
        returnData.deleteRowsReturnData(rsDeleteStudentWithInstrumentResponse);
    }
}
