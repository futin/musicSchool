package com.example.futin.tabletest.userInterface.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.InputType;
import android.transition.TransitionManager;
import android.os.Handler;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.futin.tabletest.R;
import com.example.futin.tabletest.RESTService.RestService;
import com.example.futin.tabletest.RESTService.listeners.AsyncTaskReturnData;
import com.example.futin.tabletest.RESTService.listeners.ReturnInstrumentData;
import com.example.futin.tabletest.RESTService.listeners.ReturnStudentData;
import com.example.futin.tabletest.RESTService.listeners.ReturnStudentWithInstrumentData;
import com.example.futin.tabletest.RESTService.models.City;
import com.example.futin.tabletest.RESTService.models.Employee;
import com.example.futin.tabletest.RESTService.models.Instrument;
import com.example.futin.tabletest.RESTService.models.Student;
import com.example.futin.tabletest.RESTService.response.RSGetCitiesResponse;
import com.example.futin.tabletest.RESTService.response.RSGetInstrumentsResponse;
import com.example.futin.tabletest.RESTService.response.RSGetStudentsResponse;
import com.example.futin.tabletest.RESTService.response.RSInsertStudentWithInstrumentResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;

public class FragmentEnterData extends Fragment implements View.OnClickListener, AsyncTaskReturnData,
        ReturnStudentData, ReturnInstrumentData {

    private final int DELAYED_TIME = 500;

    Button btnEnterStudent;
    Button btnEnterStudentsInstrument;

    ViewGroup enterDataLayout;
    ViewGroup instrumentLayout;

    //RelativeLayout instrumentLayout
    SimpleDateFormat dateFormatter;
    DatePickerDialog pickDate;

    Button btnCancelInst;
    Button btnSaveInst;

    Spinner spinnerStudent;
    Spinner spinnerInstrument;
    EditText txtNumberOfInstruments;
    EditText txtDate;

    String studentId;
    int instrumentId;

    //RelativeLayout studentLayout
    ViewGroup studentLayout;

    Button btnCancel;
    Button btnSave;

    EditText txtStudentId;
    EditText txtFirstName;
    EditText txtLastName;
    Spinner spinnerCity;
    int cityPtt=0;

    //RestService
    RestService rs;
    RSGetStudentsResponse responseStudent;
    RSGetCitiesResponse responseCity;
    RSGetInstrumentsResponse responseInstrument;
    RSInsertStudentWithInstrumentResponse responseInsertStudentWithInstrument;

    ArrayList<City>listOfCities;
    ArrayList<Student>listOfStudents;
    ArrayList<Instrument>listOfInstruments;
    Employee employee=null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_fragment_enter_data, container,false);

        dateFormatter = new SimpleDateFormat("MM-dd-yyyy");

        findViewById(view);

        setDateTimeField();

        //Get lists from RestService
        rs=new RestService(this);
        rs.setReturnReturnStudentData(this);
        rs.setReturnInstrumentData(this);
        rs.getCities();
        rs.getStudents();
        rs.getInstruments();

        //get that value for the first time
        getCityFromSpinner();
        getStudentFromSpinner();
        getInstrumentFromSpinner();
        return view;
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.btnEnterStudent:
               changeButtonPosition(btnEnterStudent, RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.ALIGN_PARENT_LEFT);
               changeButtonPosition(btnEnterStudentsInstrument, RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.ALIGN_PARENT_RIGHT);
               instrumentLayout.setVisibility(View.INVISIBLE);
                studentLayout.setVisibility(View.VISIBLE);
                setCitySpinner();
                break;

            case R.id.btnEnterStudentsInstrument:
                changeButtonPosition(btnEnterStudent, RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.ALIGN_PARENT_RIGHT);
                changeButtonPosition(btnEnterStudentsInstrument, RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.ALIGN_PARENT_LEFT);
                studentLayout.setVisibility(View.INVISIBLE);
                instrumentLayout.setVisibility(View.VISIBLE);
                setInstrumentSpinner();
                setStudentSpinner();
               break;

            case R.id.btnSave:
                btnSaveClicked();
                break;

            case R.id.btnCancel:
                changeButtonPosition(btnEnterStudent, RelativeLayout.CENTER_IN_PARENT, RelativeLayout.ALIGN_PARENT_LEFT);
                changeButtonPosition(btnEnterStudentsInstrument, RelativeLayout.CENTER_IN_PARENT, RelativeLayout.ALIGN_PARENT_RIGHT);
                studentLayout.setVisibility(View.INVISIBLE);
                makeDefaultStudentLayout();
                break;

            case R.id.btnSaveInst:
                btnSaveInstClicked();
                break;

            case R.id.btnCancelInst:
                changeButtonPosition(btnEnterStudent, RelativeLayout.CENTER_IN_PARENT, RelativeLayout.ALIGN_PARENT_LEFT);
                changeButtonPosition(btnEnterStudentsInstrument, RelativeLayout.CENTER_IN_PARENT, RelativeLayout.ALIGN_PARENT_RIGHT);
                instrumentLayout.setVisibility(View.INVISIBLE);
                makeDefaultInstrumentLayout();
                break;
            case R.id.txtDate:
                pickDate.show();
                break;
            default:
                return;
        }
    }


    public void changeButtonPosition(Button btn, int relativeLayoutX, int relativeLayoutY){
        int width=btn.getWidth();
        int height=btn.getHeight();
        TransitionManager.beginDelayedTransition(enterDataLayout);
        //change Position
        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(
                width,height);
        params.addRule(relativeLayoutX, RelativeLayout.TRUE);
        params.addRule(relativeLayoutY, RelativeLayout.TRUE);
        btn.setLayoutParams(params);
    }




    public void setCitySpinner(){

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayAdapter<City> cityAdapter = new ArrayAdapter<>(getActivity().getApplicationContext(),
                        R.layout.my_spinner_dropdown_item, listOfCities);
                spinnerCity.setAdapter(cityAdapter);
            }
        }, DELAYED_TIME);

        }
    public void setInstrumentSpinner(){

       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               ArrayAdapter<Instrument> instrumentAdapter = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.my_spinner_dropdown_item, listOfInstruments);
               spinnerInstrument.setAdapter(instrumentAdapter);
           }
       }, DELAYED_TIME);

    }
    public void setStudentSpinner(){

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayAdapter<Student> studentAdapter = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.my_spinner_dropdown_item, listOfStudents);
                spinnerStudent.setAdapter(studentAdapter);
            }
        }, DELAYED_TIME);


    }

    public boolean checkStudentId(){

        if(txtStudentId.getText().length()==13){
            txtStudentId.setBackground(getResources().getDrawable(R.drawable.normal_rectangle));
            return true;
        }else{
            txtStudentId.setBackground(getResources().getDrawable(R.drawable.error_rectangle));
            txtStudentId.setHint("Only "+txtStudentId.getText().length()+" digits");
            return false;
        }
    }
    public boolean checkInstrumentFields(){
        if(!txtNumberOfInstruments.getText().toString().equalsIgnoreCase("") &&
                !txtDate.getText().toString().equalsIgnoreCase("")){
            return true;
        }else{
            return false;
        }
    }
    public boolean checkEmptyText() {
        String studentId = txtStudentId.getText().toString();
        String firstName = txtFirstName.getText().toString();
        String lastName = txtLastName.getText().toString();

        //check studentId
        if (studentId.equalsIgnoreCase(""))
        {
            txtStudentId.setBackground(getResources().getDrawable(R.drawable.error_rectangle));
            //check firstName
            if (firstName.equalsIgnoreCase(""))
            {
                txtFirstName.setBackground(getResources().getDrawable(R.drawable.error_rectangle));
                //check lastName
                if (lastName.equalsIgnoreCase(""))
                {
                    txtLastName.setBackground(getResources().getDrawable(R.drawable.error_rectangle));
                    return false;
                }
                //lastName ok
                else
                {
                    txtLastName.setBackground(getResources().getDrawable(R.drawable.normal_rectangle));
                    //studentId and firstName not ok
                    return false;
                }
            }
            //firstName ok
            else
            {
                txtFirstName.setBackground(getResources().getDrawable(R.drawable.normal_rectangle));
                //check lastName again
                if (lastName.equalsIgnoreCase(""))
                {
                    txtLastName.setBackground(getResources().getDrawable(R.drawable.error_rectangle));
                    return false;
                }
                //lastName ok
                else
                {
                    txtLastName.setBackground(getResources().getDrawable(R.drawable.normal_rectangle));
                    //studentId not ok
                    return false;
                }
            }
        }
        //studentId ok
        else
        {
            txtStudentId.setBackground(getResources().getDrawable(R.drawable.normal_rectangle));
            //check firstName
            if (firstName.equalsIgnoreCase(""))
            {
                txtFirstName.setBackground(getResources().getDrawable(R.drawable.error_rectangle));
                //check lastName
                if (lastName.equalsIgnoreCase(""))
                {
                    txtLastName.setBackground(getResources().getDrawable(R.drawable.error_rectangle));
                    return false;
                }
                //lastName ok
                else
                {
                    txtLastName.setBackground(getResources().getDrawable(R.drawable.normal_rectangle));
                    //studentId and firstName not ok
                    return false;
                }
            }
            //firstName ok
            else
            {
                txtFirstName.setBackground(getResources().getDrawable(R.drawable.normal_rectangle));
                //check lastName again
                if (lastName.equalsIgnoreCase(""))
                {
                    txtLastName.setBackground(getResources().getDrawable(R.drawable.error_rectangle));
                    return false;
                }
                //lastName ok
                else
                {
                    txtLastName.setBackground(getResources().getDrawable(R.drawable.normal_rectangle));
                    //all ok
                    return true;
                }
            }
        }
    }

    public void makeDefaultStudentLayout(){
        txtFirstName.setText("");
        txtLastName.setText("");
        txtStudentId.setText("");

        txtFirstName.setBackground(getResources().getDrawable(R.drawable.normal_rectangle));
        txtLastName.setBackground(getResources().getDrawable(R.drawable.normal_rectangle));
        txtStudentId.setBackground(getResources().getDrawable(R.drawable.normal_rectangle));

        setCitySpinner();

    }
    public void makeDefaultInstrumentLayout(){
        txtDate.setText("");
        txtNumberOfInstruments.setText("");
        setInstrumentSpinner();
        setStudentSpinner();
    }

  public boolean isStudentInDatabase(){
      boolean found=false;
      for (int i=0;i<listOfStudents.size();i++){
          if(txtStudentId.getText().toString().equalsIgnoreCase(listOfStudents.get(i).getStudentId())){
              found=true;
              break;
          }
      }
      if(found){
          return true;
      }else{
          return false;
      }
  }

    @Override
    public void returnDataOnPostExecute(Object obj) {
        responseCity = (RSGetCitiesResponse) obj;
        listOfCities= responseCity.getListOFCities();
    }

    @Override
    public void returnStudentDataOnPostExecute(Object o) {
        responseStudent=(RSGetStudentsResponse)o;
        listOfStudents=responseStudent.getStudents();
        isStudentInDatabase();
    }

    @Override
    public void returnInstrumentDataOnPostExecute(Object o) {
        responseInstrument = (RSGetInstrumentsResponse) o;
        listOfInstruments= responseInstrument.getListOfInstruments();

    }

    public int getCityFromSpinner(){
        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position) instanceof City) {
                    City city = (City) parent.getItemAtPosition(position);
                    cityPtt = city.getCityPtt();
                } else {
                    makeToast("Not instance of city");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return cityPtt;
    }

    public String getStudentFromSpinner(){
        spinnerStudent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position) instanceof  Student){
                    Student student= (Student) parent.getItemAtPosition(position);
                    studentId =student.getStudentId();
                }else{
                    makeToast("Not instance of Student");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return studentId;
    }

    public int getInstrumentFromSpinner(){

        spinnerInstrument.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position) instanceof Instrument){
                    Instrument instrument= (Instrument) parent.getItemAtPosition(position);
                    instrumentId =instrument.getInstrumentId();
                }else{
                    makeToast("Not instance of Instrument");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return instrumentId;
    }

    public void btnSaveClicked(){
        String studentId=txtStudentId.getText().toString();
        String firstName=txtFirstName.getText().toString();
        String lastName=txtLastName.getText().toString();
        int cityPtt= getCityFromSpinner();
        if(checkEmptyText() && checkStudentId()) {
            if(!isStudentInDatabase()) {

                rs.insertStudent(studentId, firstName, lastName, cityPtt);
                changeButtonPosition(btnEnterStudent, RelativeLayout.CENTER_IN_PARENT, RelativeLayout.ALIGN_PARENT_LEFT);
                changeButtonPosition(btnEnterStudentsInstrument, RelativeLayout.CENTER_IN_PARENT, RelativeLayout.ALIGN_PARENT_RIGHT);
                studentLayout.setVisibility(View.INVISIBLE);
                makeToast("Student successfully saved");
                makeDefaultStudentLayout();
            }else{
                makeToast("Error! Student already in database");
            }
        }
    }
    public void btnSaveInstClicked(){
        if(checkInstrumentFields()) {
            String studentId = getStudentFromSpinner();
            int instrumentId = getInstrumentFromSpinner();
            int numberOfInstruments= Integer.parseInt(txtNumberOfInstruments.getText().toString());
          SharedPreferences sharedPreferences= getActivity().getSharedPreferences("employee",
                  Context.MODE_PRIVATE);
            String employeeName=sharedPreferences.getString("firstName","");

            //Splitting date from txtDate
            StringTokenizer tokens = new StringTokenizer(txtDate.getText().toString(), "-");
            String month = tokens.nextToken();
            String day = tokens.nextToken();
            String year = tokens.nextToken();

            String newDate=year+"-"+month+"-"+day;
            rs.insertStudentWithInstrument(studentId,instrumentId,employeeName,
                    numberOfInstruments,newDate);
            changeButtonPosition(btnEnterStudent, RelativeLayout.CENTER_IN_PARENT, RelativeLayout.ALIGN_PARENT_LEFT);
            changeButtonPosition(btnEnterStudentsInstrument, RelativeLayout.CENTER_IN_PARENT, RelativeLayout.ALIGN_PARENT_RIGHT);
            instrumentLayout.setVisibility(View.INVISIBLE);

            makeDefaultInstrumentLayout();
            makeToast("Successfully saved student with his junk");
        }else{
            makeToast("Try again");
        }
    }

    public void makeToast(String text){
        Toast.makeText(getActivity().getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    public void setDateTimeField() {
        final Context context=getActivity();
        Calendar newCalendar = Calendar.getInstance();
        pickDate = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                txtDate.setText(dateFormatter.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    public void findViewById(View view){

        btnEnterStudent= (Button) view.findViewById(R.id.btnEnterStudent);
        btnEnterStudentsInstrument= (Button) view.findViewById(R.id.btnEnterStudentsInstrument);
        enterDataLayout= (ViewGroup) view.findViewById(R.id.enterDataLayout);

        btnEnterStudent.setOnClickListener(this);
        btnEnterStudentsInstrument.setOnClickListener(this);
        studentLayout= (ViewGroup) view.findViewById(R.id.studentLayout);
        instrumentLayout=(ViewGroup) view.findViewById(R.id.instrumentLayout);
        studentLayout.setVisibility(View.INVISIBLE);
        instrumentLayout.setVisibility(View.INVISIBLE);

        //studentLayout
        btnCancel= (Button) studentLayout.findViewById(R.id.btnCancel);
        btnSave= (Button) studentLayout.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        spinnerCity = (Spinner) studentLayout.findViewById(R.id.citySpinner);
        txtStudentId= (EditText) studentLayout.findViewById(R.id.txtStudentId);
        txtFirstName=(EditText) studentLayout.findViewById(R.id.txtFirstName);
        txtLastName=(EditText) studentLayout.findViewById(R.id.txtLastName);

        //instrumentLayout
        btnCancelInst= (Button) instrumentLayout.findViewById(R.id.btnCancelInst);
        btnSaveInst= (Button) instrumentLayout.findViewById(R.id.btnSaveInst);
        btnCancelInst.setOnClickListener(this);
        btnSaveInst.setOnClickListener(this);
        spinnerInstrument = (Spinner) instrumentLayout.findViewById(R.id.spinnerInstrument);
        spinnerStudent = (Spinner) instrumentLayout.findViewById(R.id.spinnerStudent);
        txtDate= (EditText) instrumentLayout.findViewById(R.id.txtDate);
        txtNumberOfInstruments= (EditText) instrumentLayout.findViewById(R.id.txtNumberOfInstruments);

        txtDate.setOnClickListener(this);
        txtDate.setInputType(InputType.TYPE_NULL);
    }

}
