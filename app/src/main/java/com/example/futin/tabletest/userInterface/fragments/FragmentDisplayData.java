package com.example.futin.tabletest.userInterface.fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.futin.tabletest.R;
import com.example.futin.tabletest.userInterface.tables.ShowCitiesTableView;
import com.example.futin.tabletest.userInterface.tables.ShowInstrumentsTableView;
import com.example.futin.tabletest.userInterface.tables.ShowStudentsTableView;
import com.example.futin.tabletest.userInterface.tables.ShowStudentsWithInsturmentsTableView;

public class FragmentDisplayData extends Fragment implements View.OnClickListener{

    RelativeLayout displayDataLayout;
    Button btnShowStudents;
    Button btnShowInstruments;
    Button btnShowStudWithInst;
    Button btnShowCities;


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

        return view;
    }

    public void makeToast(String text){
        Toast.makeText(getActivity().getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnShowStudents:
                Intent intentStud=new Intent(getActivity(), ShowStudentsTableView.class);
                startActivity(intentStud);
            case R.id.btnShowInstruments:
                Intent intentInst=new Intent(getActivity().getApplicationContext(), ShowInstrumentsTableView.class);
                startActivity(intentInst);
                break;
            case R.id.btnShowStudWithInst:
                Intent intentStuWithInst=new Intent(getActivity().getApplicationContext(), ShowStudentsWithInsturmentsTableView.class);
                startActivity(intentStuWithInst);
                break;
            case R.id.btnShowCities:
                Intent intentCity=new Intent(getActivity().getApplicationContext(), ShowCitiesTableView.class);
                startActivity(intentCity);
                break;
            default:
                return;
        }
    }


}
