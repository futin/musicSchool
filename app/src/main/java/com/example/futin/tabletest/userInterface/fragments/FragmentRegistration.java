package com.example.futin.tabletest.userInterface.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.futin.tabletest.R;
import com.example.futin.tabletest.RESTService.RestService;
import com.example.futin.tabletest.RESTService.interfaces.AsyncTaskReturnData;
import com.example.futin.tabletest.RESTService.models.Employee;
import com.example.futin.tabletest.RESTService.response.RSGetEmployeesResponse;

import java.util.ArrayList;

/**
 * Created by Futin on 3/3/2015.
 */
public class FragmentRegistration extends Fragment implements AsyncTaskReturnData {

    //from studentLayout
    EditText txtUsername;
    EditText txtPassword;
    EditText txtFirstName;
    EditText txtLastName;
    Button btnRegistration;
//Rest Service references
    RSGetEmployeesResponse responseGet=null;
    RestService rs;

    //set these attributes from editText fields

    String username;
    String password;
    String firstName;
    String lastName;

    ArrayList<Employee>listOfEmployees;
    String status;
    boolean found=false;

    //methods from AsyncTaskReturnData Interface, contains data done by AsyncTask
    @Override
    public void returnDataOnPostExecute(Object obj) {
        responseGet= (RSGetEmployeesResponse) obj;
        listOfEmployees=responseGet.getListOfEmployees();
        status=responseGet.getStatusName();
        messageToClient();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_fragment_registration, container, false);

        txtUsername= (EditText) view.findViewById(R.id.txtUsername);
        txtPassword= (EditText) view.findViewById(R.id.txtPassword);
        txtFirstName= (EditText) view.findViewById(R.id.txtFirstName);
        txtLastName= (EditText) view.findViewById(R.id.txtLastName);
        btnRegistration=(Button) view.findViewById(R.id.btnRegistration);

        rs=new RestService(this);
        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                 username=txtUsername.getText().toString();
                 password=txtPassword.getText().toString();
                 firstName=txtFirstName.getText().toString();
                 lastName=txtLastName.getText().toString();

                //checking all EditText fields
                if(username.equalsIgnoreCase("")||password.equalsIgnoreCase("")||
                        firstName.equalsIgnoreCase("")||lastName.equalsIgnoreCase("")){
                    Toast.makeText(getActivity().getApplicationContext(), "You have to enter all parameters!", Toast.LENGTH_SHORT).show();
                }else{
                    rs.getEmployees();
                }

            }
        });
        return view;
    }
    //message that will inform client if his registration was good
    public void messageToClient(){
            for (int i=0;i<listOfEmployees.size();i++){
                if(username.equalsIgnoreCase(listOfEmployees.get(i).getUsername()) &&
                        password.equalsIgnoreCase(listOfEmployees.get(i).getPassword())){
                    found=true;
                    break;
                }
            }
        if(status.equalsIgnoreCase("OK") && found==true){
            Toast.makeText(getActivity().getApplicationContext(), "Try with different username or password", Toast.LENGTH_SHORT).show();
            txtUsername.setBackground(getResources().getDrawable(R.drawable.error_rectangle));
            txtPassword.setBackground(getResources().getDrawable(R.drawable.error_rectangle));
            found=false;
        }else{
            txtUsername.setBackground(getResources().getDrawable(R.drawable.normal_rectangle));
            txtPassword.setBackground(getResources().getDrawable(R.drawable.normal_rectangle));
            Toast.makeText(getActivity().getApplicationContext(), "Employee created successfully", Toast.LENGTH_SHORT).show();
            rs.insertEmployee(username, password, firstName, lastName);

        }



    }
}
