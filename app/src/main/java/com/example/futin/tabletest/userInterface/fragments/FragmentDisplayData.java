package com.example.futin.tabletest.userInterface.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.futin.tabletest.R;
import com.example.futin.tabletest.userInterface.tables.ShowCitiesTableView;
import com.example.futin.tabletest.userInterface.tables.ShowStudentsTableView;

public class FragmentDisplayData extends Fragment implements View.OnClickListener{

    RelativeLayout displayDataLayout;
    Button btnShowStudents;
    Button btnShowInstruments;
    Button btnShowStudWithInst;
    Button btnShowCities;
    FragmentToActivity FTAInterface;

    //employee name and time
    SharedPreferences sharedPreferences;
    TextView txtEmployeeName;
    TextView txtCurrentTime;
    String firstName;
    String lastName;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_fragment_display_data, container,false);

        displayDataLayout= (RelativeLayout) view.findViewById(R.id.displayDataLayout);
        btnShowStudents= (Button) view.findViewById(R.id.btnShowStudents);
        btnShowInstruments= (Button) view.findViewById(R.id.btnShowInstruments);
        btnShowStudWithInst= (Button) view.findViewById(R.id.btnShowStudWithInst);
        btnShowCities= (Button) view.findViewById(R.id.btnShowCities);

        btnShowStudents.setOnClickListener(this);
        btnShowInstruments.setOnClickListener(this);
        btnShowStudWithInst.setOnClickListener(this);
        btnShowCities.setOnClickListener(this);

        txtEmployeeName= (TextView) view.findViewById(R.id.txtShowEmployeeNameDisplayData);
        txtCurrentTime= (TextView) view.findViewById(R.id.txtShowTimeDisplayData);
        sharedPreferences= getActivity().getSharedPreferences("employee", Context.MODE_PRIVATE);

        firstName=sharedPreferences.getString("firstName","");
        lastName=sharedPreferences.getString("lastName","");
        txtEmployeeName.setText(firstName+" "+lastName);
        txtEmployeeName.setGravity(Gravity.CENTER);
        txtEmployeeName.setTextSize(20);


        btnShowCities.requestFocus();
        return view;
    }

    public void makeToast(String text){
        Toast.makeText(getActivity().getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onClick(View v) {
                FTAInterface.switchActivities(v);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            FTAInterface = (FragmentToActivity) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString()+" must implement FragmentToActivity interface!");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        FTAInterface=null;
    }
}
