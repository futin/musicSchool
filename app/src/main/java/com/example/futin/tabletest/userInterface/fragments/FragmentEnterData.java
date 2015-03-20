package com.example.futin.tabletest.userInterface.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.Log;
import android.os.Handler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.futin.tabletest.R;
import com.example.futin.tabletest.RESTService.RestService;
import com.example.futin.tabletest.RESTService.listeners.AsyncTaskReturnData;
import com.example.futin.tabletest.RESTService.listeners.ReturnInstrumentData;
import com.example.futin.tabletest.RESTService.listeners.ReturnStudentData;
import com.example.futin.tabletest.RESTService.models.City;
import com.example.futin.tabletest.RESTService.models.Instrument;
import com.example.futin.tabletest.RESTService.models.Student;
import com.example.futin.tabletest.RESTService.response.RSGetCitiesResponse;
import com.example.futin.tabletest.RESTService.response.RSGetInstrumentsResponse;
import com.example.futin.tabletest.RESTService.response.RSGetStudentsResponse;

import java.util.ArrayList;

public class FragmentEnterData extends Fragment implements View.OnClickListener, AsyncTaskReturnData,
        ReturnStudentData, ReturnInstrumentData {

    private final int DELAYED_TIME = 500;


    Button btnEnterStudent;
    Button btnEnterStudentsInstrument;

    ViewGroup enterDataLayout;
    ViewGroup instrumentLayout;

    //RelativeLayout instrumentLayout
    Button btnCancelInst;
    Button btnSaveInst;

    EditText txtNumberOfInstruments;
    Spinner spinnerStudent;
    Spinner spinnerInstrument;

    String studentName;
    String instrumentName;

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
    RSGetInstrumentsResponse responseInstument;

    ArrayList<City>listOfCities;
    ArrayList<Student>listOfStudents;
    ArrayList<Instrument>listOfInstruments;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_fragment_enter_data, container,false);
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
        btnCancel= (Button) studentLayout.findViewById(R.id.btnCancelInst);
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
        spinnerInstrument = (Spinner) instrumentLayout.findViewById(R.id.spinnerInstrument);
        spinnerStudent = (Spinner) instrumentLayout.findViewById(R.id.spinnerStudent);



        //Get list of cities
        rs=new RestService(this);
        rs.setReturnReturnStudentData(this);
        rs.setReturnInstrumentData(this);
        rs.getCities();
        rs.getStudents();
        rs.getInstruments();

        //get that value for the first time
        getCityFromSpinner();

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
            case R.id.btnCancelInst:
                changeButtonPosition(btnEnterStudent, RelativeLayout.CENTER_IN_PARENT, RelativeLayout.ALIGN_PARENT_LEFT);
                changeButtonPosition(btnEnterStudentsInstrument, RelativeLayout.CENTER_IN_PARENT, RelativeLayout.ALIGN_PARENT_RIGHT);
                studentLayout.setVisibility(View.INVISIBLE);
                makeDefaultStudentLayout();
                break;
            case R.id.btnSave:
                btnSaveClicked();
                    break;
            case R.id.btnSaveInst:
                btnSaveInstClicked();
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
                ArrayAdapter<Student> studentAdapter=new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.my_spinner_dropdown_item, listOfStudents);
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
  public boolean studentIsInDatabase(){
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
        studentIsInDatabase();
    }

    @Override
    public void returnInstrumentDataOnPostExecute(Object o) {
        responseInstument = (RSGetInstrumentsResponse) o;
        listOfInstruments=responseInstument.getListOfInstruments();

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
                    studentName=student.getFirstName()+" "+student.getLastName();
                }else{
                    makeToast("Not instance of Student");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return studentName;
    }

    public String getInstrumentFromSpinner(){

        spinnerInstrument.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position) instanceof Instrument){
                    Instrument instrument= (Instrument) parent.getItemAtPosition(position);
                    instrumentName=instrument.getInstrumentName();
                }else{
                    makeToast("Not instance of Instrument");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return instrumentName;
    }

    public void btnSaveClicked(){
        String studentId=txtStudentId.getText().toString();
        String firstName=txtFirstName.getText().toString();
        String lastName=txtLastName.getText().toString();
        int cityPtt= getCityFromSpinner();
        if(checkEmptyText() && checkStudentId()) {
            if(!studentIsInDatabase()) {

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
        String studentName=getStudentFromSpinner();
        String instrumentName= getInstrumentFromSpinner();
        int numberOfInstruments= Integer.parseInt(txtNumberOfInstruments.getText().toString());

    }

    public void makeToast(String text){
        Toast.makeText(getActivity().getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }
}
