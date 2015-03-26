package com.example.futin.tabletest.RESTService.data;

/**
 * Created by Futin on 3/3/2015.
 */
public class RSServerURL {


    private final static String API_BASE_URL="http://192.168.4.100";
    private final static String API_ROOT_URL="/musicSchool";


    private final static String API_GET_EMPLOYEES_URL="/getEmployees.php";
    private final static String API_GET_STUDENTS_URL="/getStudents.php";
    private final static String API_GET_CITY_URL="/getCityName.php";
    private final static String API_GET_INSTRUMENTS_URL="/getInstruments.php";
    private final static String API_GET_STUDENT_WITH_INSTRUMENT_URL="/getStudentWithInstrument.php";

    private final static String API_EMPLOYEES_LOGIN_URL="/loginService.php";
    private final static String API_EMPLOYEES_REGISTRATION_URL="/registrationService.php";
    private final static String API_INSERT_STUDENT_URL="/insertStudent.php";
    private final static String API_INSERT_STUDENT_WITH_INSTRUMENT_URL="/insertStudentWithInstrument.php";

    private final static String API_SEARCH_FOR_STUDENT_URL="/searchForStudent.php";
    private final static String API_SEARCH_FOR_CITY_URL="/searchForCity.php";
    private final static String API_SEARCH_FOR_INSTRUMENT_URL="/searchForInstrument.php";
    private final static String API_SEARCH_FOR_STUDENT_WITH_INSTRUMENT_URL="/searchForStudentWithInstrument.php";

    private final static String API_DELETE_CITY_ROW_URL="/deleteCityRows.php";




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
    public String getStudentsUrl(){return getRootUrl()+API_GET_STUDENTS_URL;}
    public String getCityUrl(){return getRootUrl()+API_GET_CITY_URL;}
    public String getEmployeesUrl(){return getRootUrl()+API_GET_EMPLOYEES_URL;}
    public String getInstrumentsUrl(){return getRootUrl()+API_GET_INSTRUMENTS_URL;}
    public String getStudentWIthInstrumentUrl(){return getRootUrl() + API_GET_STUDENT_WITH_INSTRUMENT_URL;}

    public String getInsertStudentWithInstrumentUrl(){return getRootUrl()+API_INSERT_STUDENT_WITH_INSTRUMENT_URL;}
    public String getInsertStudentUrl(){return getRootUrl()+API_INSERT_STUDENT_URL;}

    public String getSearchForStudentUrl(){return getRootUrl()+API_SEARCH_FOR_STUDENT_URL;}
    public String getSearchForInstrumentUrl(){return getRootUrl()+API_SEARCH_FOR_INSTRUMENT_URL;}
    public String getSearchForCityUrl(){return getRootUrl()+API_SEARCH_FOR_CITY_URL;}
    public String getSearchForStudentWithInstrumentUrl(){return getRootUrl()+API_SEARCH_FOR_STUDENT_WITH_INSTRUMENT_URL;}

    public String getDeleteCityRowUrl(){return  getRootUrl()+API_DELETE_CITY_ROW_URL;}

}
