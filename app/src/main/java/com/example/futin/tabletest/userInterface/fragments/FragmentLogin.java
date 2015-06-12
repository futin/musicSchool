package com.example.futin.tabletest.userInterface.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.futin.tabletest.R;
import com.example.futin.tabletest.RESTService.RestService;
import com.example.futin.tabletest.RESTService.listeners.AsyncTaskReturnData;
import com.example.futin.tabletest.RESTService.listeners.SignInReturnData;
import com.example.futin.tabletest.RESTService.models.Employee;
import com.example.futin.tabletest.RESTService.response.RSGetEmployeesResponse;
import com.example.futin.tabletest.RESTService.response.RSSignInResponse;
import com.example.futin.tabletest.userInterface.mainPage.MainPage;

import java.util.ArrayList;

public class FragmentLogin extends Fragment implements AsyncTaskReturnData, SignInReturnData {

    //from activity_fragment_login
    EditText txtUsername;
    EditText txtPassword;
    Button btnLogin;
    CheckBox checkBoxStayLoggedIn;
    //RestService references
    RestService rs;
    RSGetEmployeesResponse responseGetEmployees =null;
    RSSignInResponse responseSignIn=null;
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
    public void returnDataOnPostExecute(Object obj) {
    responseGetEmployees =(RSGetEmployeesResponse) obj;
        listOfEmployees= responseGetEmployees.getListOfEmployees();
        status= responseGetEmployees.getStatusName();
        checkEmployee();
        btnLogin.setAlpha(1f);
        btnLogin.setEnabled(true);


    }

    @Override
    public void returnEmployeeOnPostExecute(Object obj) {
        responseSignIn= (RSSignInResponse) obj;
        firstName=responseSignIn.getEmployee().getFirstName();
        lastName=responseSignIn.getEmployee().getLastName();
        goToMainPage();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.activity_fragment_login,container,false);
        txtUsername= (EditText) view.findViewById(R.id.txtUsername);
        txtPassword= (EditText) view.findViewById(R.id.txtPassword);
        btnLogin=(Button) view.findViewById(R.id.btnLogin);
        checkBoxStayLoggedIn= (CheckBox) view.findViewById(R.id.checkBoxStayLoggedIn);

        rs=new RestService(this);
        rs.setReturnDataSignIn(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOnline()) {
                    username = txtUsername.getText().toString();
                    password = txtPassword.getText().toString();
                    btnLogin.setAlpha(0.6f);
                    btnLogin.setEnabled(false);

                        rs.getEmployees(getActivity());

                }else{
                    Toast.makeText(getActivity().getApplicationContext(), "Check your connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    void checkEmployee() {
        if(status != null) {
            if (listOfEmployees != null && isOnline()) {
                for (int i = 0; i < listOfEmployees.size(); i++) {
                    if (username.equalsIgnoreCase(listOfEmployees.get(i).getUsername()) &&
                            password.equalsIgnoreCase(listOfEmployees.get(i).getPassword())) {
                        found = true;
                        break;
                    }
                }
                if (found == true) {
                    rs.signIn(username, password);
                    found = false;
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Employee does not exist", Toast.LENGTH_SHORT).show();
                }
            }
        }else{
            Toast.makeText(getActivity().getApplicationContext(), "Wrong IP address/server error", Toast.LENGTH_SHORT).show();
        }
    }
    void goToMainPage(){
        //store his info in sharedPreferences
         SharedPreferences sharedPreferences= getActivity().getSharedPreferences("employee", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("firstName", firstName);
        editor.putString("lastName", lastName);
        editor.putBoolean("isLoggedIn", checkBoxStayLoggedIn.isChecked());
        editor.apply();
        Intent i = new Intent(getActivity().getApplicationContext(), MainPage.class);
        if(checkBoxStayLoggedIn.isChecked()) {
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                    Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        startActivity(i);
    }

    //check if wifi is on
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return netInfo != null && netInfo.isConnected();
    }
}
