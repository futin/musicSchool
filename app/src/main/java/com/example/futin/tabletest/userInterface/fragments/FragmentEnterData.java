package com.example.futin.tabletest.userInterface.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.Log;
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
import com.example.futin.tabletest.RESTService.interfaces.AsyncTaskReturnData;
import com.example.futin.tabletest.RESTService.interfaces.StudentWIthIdData;
import com.example.futin.tabletest.RESTService.models.City;
import com.example.futin.tabletest.RESTService.models.Student;
import com.example.futin.tabletest.RESTService.response.RSGetCitiesResponse;
import com.example.futin.tabletest.RESTService.response.RSGetStudentWIthIdResponse;

import java.util.ArrayList;

public class FragmentEnterData extends Fragment implements View.OnClickListener, AsyncTaskReturnData, StudentWIthIdData {

    Button btnEnterStudent;
    Button btnEnterStudentsInstrument;

    ViewGroup enterDataLayout;
    ViewGroup studentLayout;
    ViewGroup instrumentLayout;

    //RelativeLayout studentLayout
    Button btnCancel;
    Button btnSave;

    EditText txtStudentId;
    EditText txtFirstName;
    EditText txtLastName;
    Spinner spinner;
    int cityPtt=0;

    //RestService
    RestService rs;
    RSGetStudentWIthIdResponse responseStudent;
    RSGetCitiesResponse responseCity;
    ArrayList<City>listOfCities;
    Student student;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_fragment_enter_data, container,false);
        btnEnterStudent= (Button) view.findViewById(R.id.btnEnterStudent);
        btnEnterStudentsInstrument= (Button) view.findViewById(R.id.btnEnterStudentsInstrument);
        enterDataLayout= (ViewGroup) view.findViewById(R.id.enterDataLayout);

        btnEnterStudent.setOnClickListener(this);
        btnEnterStudentsInstrument.setOnClickListener(this);
        studentLayout= (ViewGroup) view.findViewById(R.id.studentLayout);
        studentLayout.setVisibility(View.INVISIBLE);


        //studentLayout
        btnCancel= (Button) studentLayout.findViewById(R.id.btnCancel);
        btnSave= (Button) studentLayout.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        spinner= (Spinner) studentLayout.findViewById(R.id.citySpinner);
        txtStudentId= (EditText) studentLayout.findViewById(R.id.txtStudentId);
        txtFirstName=(EditText) studentLayout.findViewById(R.id.txtFirstName);
        txtLastName=(EditText) studentLayout.findViewById(R.id.txtLastName);

        //Get list of cities
        rs=new RestService(this);
        rs.setReturnStudentData(this);
        rs.getCities();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position) instanceof City){
                    City city= (City) parent.getItemAtPosition(position);
                    cityPtt=city.getCityPtt();
                    Toast.makeText(getActivity().getApplicationContext(),"asdasd"+ city.getCityPtt(), Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(getActivity().getApplicationContext(), "Not intstance of city", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.btnEnterStudent:
               changeButtonPosition(btnEnterStudent, RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.ALIGN_PARENT_LEFT);
               changeButtonPosition(btnEnterStudentsInstrument, RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.ALIGN_PARENT_RIGHT);
                studentLayout.setVisibility(View.VISIBLE);
                setCitySpinner();



                break;
            case R.id.btnEnterStudentsInstrument:
                changeButtonPosition(btnEnterStudent, RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.ALIGN_PARENT_RIGHT);
                changeButtonPosition(btnEnterStudentsInstrument, RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.ALIGN_PARENT_LEFT);

                break;
            case R.id.btnCancel:
                changeButtonPosition(btnEnterStudent, RelativeLayout.CENTER_IN_PARENT, RelativeLayout.ALIGN_PARENT_LEFT);
                changeButtonPosition(btnEnterStudentsInstrument, RelativeLayout.CENTER_IN_PARENT, RelativeLayout.ALIGN_PARENT_RIGHT);
                studentLayout.setVisibility(View.INVISIBLE);
                makeDefaultStudentLayout();
                break;
            case R.id.btnSave:

                String studentId=txtStudentId.getText().toString();
                String firstName=txtFirstName.getText().toString();
                String lastName=txtLastName.getText().toString();
                int cityPtt=getItemFromSpinner();

                if(checkEmtyText() && checkStudentId()) {
                    rs.getStudentWithId(studentId);

                    if(checkForNewStudent()) {
                    rs.insertStudent(studentId, firstName, lastName, cityPtt);
                        changeButtonPosition(btnEnterStudent, RelativeLayout.CENTER_IN_PARENT, RelativeLayout.ALIGN_PARENT_LEFT);
                        changeButtonPosition(btnEnterStudentsInstrument, RelativeLayout.CENTER_IN_PARENT, RelativeLayout.ALIGN_PARENT_RIGHT);
                        studentLayout.setVisibility(View.INVISIBLE);
                        Toast.makeText(getActivity().getApplicationContext(), "Student successfully saved", Toast.LENGTH_SHORT).show();
                        makeDefaultStudentLayout();
                    }else{
                        Toast.makeText(getActivity().getApplicationContext(), "Error! Student already in database", Toast.LENGTH_SHORT).show();
                    }
                }
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
        ArrayAdapter<City> cityAdapter=new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.my_spinner_dropdown_item, listOfCities);
        spinner.setAdapter(cityAdapter);
        }

    public boolean checkStudentId(){

        if(txtStudentId.getText().length()==13){
            txtStudentId.setBackground(getResources().getDrawable(R.drawable.normal_rectangle));
            return true;
        }else{
            txtStudentId.setBackground(getResources().getDrawable(R.drawable.error_rectangle));
            txtStudentId.setHint("U entered "+txtStudentId.getText().length()+" digits, need 13");
            return false;
        }
    }
    public boolean checkEmtyText() {
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
    public boolean checkForNewStudent(){
        if(student == null){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void returnDoneTask(Object obj) {
        responseCity = (RSGetCitiesResponse) obj;
        listOfCities= responseCity.getListOFCities();
    }

    @Override
    public void returnStudentData(Object o) {
        responseStudent=(RSGetStudentWIthIdResponse )o;
         student=responseStudent.getStudent();
        Log.i("test", student.toString());
        checkForNewStudent();
    }

    public int getItemFromSpinner(){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position) instanceof City){
                     City city= (City) parent.getItemAtPosition(position);
                     cityPtt=city.getCityPtt();

                }else{
                    Toast.makeText(getActivity().getApplicationContext(), "Not intstance of city", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return cityPtt;
    }
}
