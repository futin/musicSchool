package com.example.futin.tabletest.RESTService.data;

/**
 * Created by Futin on 3/3/2015.
 */
public class RSServerURL {


    private final static String API_BASE_URL="http://localhost";
    private final static String API_ROOT_URL="/musicSchool";
    private final static String API_EMPLOYEES_REGISTRATION_URL="/loginService.php";
    private final static String API_EMPLOYEES_LOGIN_URL="/registrationService.php";

    public String getBaseUrl(){
        return API_BASE_URL;
    }
    public String getRootUrl(){
        return getBaseUrl()+API_ROOT_URL;
    }
    public String getEmployeeRegistrationUrl(){
        return getRootUrl()+API_EMPLOYEES_REGISTRATION_URL;
    }
    public String getEmployeeLoginUrl(){
        return getRootUrl()+API_EMPLOYEES_LOGIN_URL;
    }

}
