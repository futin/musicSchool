package com.example.futin.tabletest.RESTService.data;

/**
 * Created by Futin on 3/3/2015.
 */
public class RSServerURL {


    private final static String API_BASE_URL="http://192.168.1.4";
    private final static String API_ROOT_URL="/musicSchool";
    private final static String API_GET_EMPLOYEES_URL="/getEmployees.php";
    private final static String API_EMPLOYEES_LOGIN_URL="/loginService.php";
    private final static String API_EMPLOYEES_REGISTRATION_URL="/registrationService.php";
    private final static String API_INSERT_STUDENT_URL="/insertStudent.php";
    private final static String API_GET_STUDENT_URL="/getStudent.php";
    private final static String API_GET_CITY_URL="/getCityName.php";


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
    public String getInsertStudentUrl(){return getRootUrl()+API_INSERT_STUDENT_URL;}
    public String getStudentUrl(){return getRootUrl()+API_GET_STUDENT_URL;}
    public String getCityUrl(){return getRootUrl()+API_GET_CITY_URL;}
    public String getEmployeesUrl(){return getRootUrl()+API_GET_EMPLOYEES_URL;}
}
