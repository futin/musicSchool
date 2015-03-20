package com.example.futin.tabletest.RESTService.task;

import android.os.AsyncTask;
import android.util.Log;

import com.example.futin.tabletest.RESTService.listeners.AsyncTaskReturnData;
import com.example.futin.tabletest.RESTService.data.RSDataSingleton;
import com.example.futin.tabletest.RESTService.request.RSInsertEmployeeRequest;
import com.example.futin.tabletest.RESTService.response.RSInsertEmployeeResponse;

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
 * Created by Futin on 3/3/2015.
 */
public class RSInsertEmployeeTask extends AsyncTask<Void, Void, RSInsertEmployeeResponse>{

    final String TAG="insertEmployee";
    RSInsertEmployeeRequest request;
    RestTemplate restTemplate;
    AsyncTaskReturnData onPostReturn;

    public RSInsertEmployeeTask(RSInsertEmployeeRequest request, AsyncTaskReturnData onPostReturn) {
        this.request = request;
        this.onPostReturn=onPostReturn;
        restTemplate=new RestTemplate();
    }

    @Override
    protected RSInsertEmployeeResponse doInBackground(Void... params) {
          try {
            HttpHeaders header = new HttpHeaders();
            header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            header.set("Connection", "Close");
            String jsonText = request.toString();
            HttpEntity<String> entity = new HttpEntity<>(jsonText, header);
              String address = RSDataSingleton.getInstance().getServerUrl().getEmployeeRegistrationUrl();

              Log.i(TAG, "Address: " + address);
              Log.i(TAG, "Entity: " + entity);
              Log.i(TAG, "Before response ");
              ResponseEntity response = restTemplate.exchange(address, HttpMethod.POST, entity, String.class);
              Log.i(TAG, "After response ");
        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            return new RSInsertEmployeeResponse(HttpStatus.NOT_FOUND,
                    HttpStatus.NOT_FOUND.name());
        } else if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
            return new RSInsertEmployeeResponse(HttpStatus.BAD_REQUEST,
                    HttpStatus.BAD_REQUEST.name());
        }else if (response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
            return new RSInsertEmployeeResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    HttpStatus.INTERNAL_SERVER_ERROR.name());
        } else {
            return new RSInsertEmployeeResponse(HttpStatus.OK,
                    HttpStatus.OK.name());
        }


    } catch (HttpClientErrorException e) {
        Log.e(TAG, "Http Status: " + e.getStatusCode());
        Log.e(TAG, "Http Error: " + e.getResponseBodyAsString());
        return new RSInsertEmployeeResponse(e.getStatusCode(),
                e.getStatusText());
    } catch (HttpServerErrorException e) {
        Log.e(TAG, "Http Status: " + e.getStatusCode());
        Log.e(TAG, "Http Error: " + e.getResponseBodyAsString());
        return new RSInsertEmployeeResponse(e.getStatusCode(),
                e.getStatusText());
    } catch (Exception e) {
        Log.e(TAG, e.getMessage());
        return new RSInsertEmployeeResponse(null, null);
    }
   }

    @Override
    protected void onPostExecute(RSInsertEmployeeResponse rsInsertEmployeeResponse) {
        super.onPostExecute(rsInsertEmployeeResponse);

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
