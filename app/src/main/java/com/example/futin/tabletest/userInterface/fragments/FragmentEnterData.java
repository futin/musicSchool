package com.example.futin.tabletest.userInterface.fragments;
import android.support.v4.app.FragmentActivity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.example.futin.tabletest.R;
import com.example.futin.tabletest.RESTService.RestService;
import com.example.futin.tabletest.RESTService.data.AsyncTaskReturnData;
import com.example.futin.tabletest.RESTService.models.City;
import com.example.futin.tabletest.RESTService.response.RSGetCitiesResponse;

import java.util.ArrayList;

public class FragmentEnterData extends Fragment implements View.OnClickListener, AsyncTaskReturnData {

    Button btnEnterStudent;
    Button btnEnterStudentsInstrument;

    ViewGroup enterDataLayout;
    ViewGroup studentLayout;
    ViewGroup instrumentLayout;

    //RelativeLayout studentLayout
    Button btnCancel;
    Button btnSave;
    Spinner spinner;
    RSGetCitiesResponse response;
    ArrayList<City>listOfCities;


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

        //Get list of cities
        RestService rs=new RestService(this);
        rs.getCities();
        spinner= (Spinner) studentLayout.findViewById(R.id.citySpinner);



        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.btnEnterStudent:
               changePositionAndSize(btnEnterStudent, RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.ALIGN_PARENT_LEFT);
               Log.i("","RelativeL : "+RelativeLayout.ALIGN_PARENT_TOP+" "+RelativeLayout.ALIGN_PARENT_LEFT);
               changePositionAndSize(btnEnterStudentsInstrument, RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.ALIGN_PARENT_RIGHT);
                studentLayout.setVisibility(View.VISIBLE);
                setCitySpinner();
                break;
            case R.id.btnEnterStudentsInstrument:
                changePositionAndSize(btnEnterStudent, RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.ALIGN_PARENT_RIGHT);
                changePositionAndSize(btnEnterStudentsInstrument, RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.ALIGN_PARENT_LEFT);

                break;
            case R.id.btnCancel:
                changePositionAndSize(btnEnterStudent, RelativeLayout.CENTER_IN_PARENT, RelativeLayout.ALIGN_PARENT_LEFT);
                changePositionAndSize(btnEnterStudentsInstrument, RelativeLayout.CENTER_IN_PARENT, RelativeLayout.ALIGN_PARENT_RIGHT);
                studentLayout.setVisibility(View.INVISIBLE);
                break;
            case R.id.btnSave:


                studentLayout.setVisibility(View.INVISIBLE);
                break;
            default:
                return;
        }
    }


    public void changePositionAndSize(Button btn, int relativeLayoutX, int relativeLayoutY){
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


    @Override
    public void returnDoneTask(Object obj) {
        response= (RSGetCitiesResponse) obj;
       listOfCities=response.getListOFCities();
    }

    public void setCitySpinner(){
        ArrayAdapter<City> cityAdapter=new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.my_spinner_dropdown_item, listOfCities);
        spinner.setAdapter(cityAdapter);
        }
}
