package com.example.futin.tabletest.userInterface.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.futin.tabletest.R;
import com.example.futin.tabletest.RESTService.RestService;
import com.example.futin.tabletest.RESTService.interfaces.AsyncTaskReturnData;
import com.example.futin.tabletest.RESTService.interfaces.SignInReturnData;
import com.example.futin.tabletest.RESTService.models.Employee;
import com.example.futin.tabletest.RESTService.response.RSGetEmployeesResponse;
import com.example.futin.tabletest.RESTService.response.RSSignInResponse;
import com.example.futin.tabletest.userInterface.mainPage.MainPage;

import java.util.ArrayList;

public class FragmentLogin extends Fragment implements AsyncTaskReturnData, SignInReturnData {

    //from acctivity_fragment_login
    EditText txtUsername;
    EditText txtPassword;
    Button btnLogin;
    //RestService references
    RestService rs;
    RSGetEmployeesResponse responseGetEmployees =null;
    RSSignInResponse responseSignIn=null;
    boolean logedIn=false;
    //set these attributes from editText fields
    String username;
    String password;

    ArrayList<Employee> listOfEmployees;
    String status;
    boolean found=false;

    //from signInTask
    String firstName;
    String lastName;

    @Override
    public void returnDoneTask(Object obj) {
    responseGetEmployees =(RSGetEmployeesResponse) obj;
        listOfEmployees= responseGetEmployees.getListOfEmployees();
        status= responseGetEmployees.getStatusName();
        checkEmployee();

    }

    @Override
    public void returnEmployeeInterface(Object obj) {
        responseSignIn= (RSSignInResponse) obj;
        firstName=responseSignIn.getEmployee().getFirstName();
        lastName=responseSignIn.getEmployee().getLastName();
        Log.i("asdfw", firstName+" "+lastName);
        goToMainPage();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.activity_fragment_login,container,false);
        txtUsername= (EditText) view.findViewById(R.id.txtUsername);
        txtPassword= (EditText) view.findViewById(R.id.txtPassword);
        btnLogin=(Button) view.findViewById(R.id.btnLogin);
        rs=new RestService(this);
        rs.setReturnDataSignIn(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 username=txtUsername.getText().toString();
                 password=txtPassword.getText().toString();
                rs.getEmployees();
            }
        });
        return view;
    }

    void checkEmployee() {
        Toast.makeText(getActivity().getApplicationContext(), status, Toast.LENGTH_SHORT).show();
        for (int i = 0; i < listOfEmployees.size(); i++) {
            if (username.equalsIgnoreCase(listOfEmployees.get(i).getUsername()) &&
                    password.equalsIgnoreCase(listOfEmployees.get(i).getPassword())) {
                found = true;
                break;
            }
        }
        if (status.equalsIgnoreCase("OK")) {
            if (found == true) {
                rs.signIn(username, password);
                found = false;
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "Employee does not exist", Toast.LENGTH_SHORT).show();
            }
        } else {
                Toast.makeText(getActivity().getApplicationContext(), "Check your connection", Toast.LENGTH_SHORT).show();

        }
    }
    void goToMainPage(){
        //store his info in sharedPreferences
        logedIn=true;
         SharedPreferences sharedPreferences= getActivity().getSharedPreferences("employee", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("firstName", firstName);
        editor.putString("lastName", lastName);
        editor.putBoolean("isLoggedIn", true);
        editor.apply();
        Intent i = new Intent(getActivity().getApplicationContext(), MainPage.class);
        i.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }


}
