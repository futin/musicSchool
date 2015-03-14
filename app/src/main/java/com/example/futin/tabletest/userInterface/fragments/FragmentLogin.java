package com.example.futin.tabletest.userInterface.fragments;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.futin.tabletest.R;
import com.example.futin.tabletest.RESTService.RestService;
import com.example.futin.tabletest.RESTService.data.AsyncTaskReturnData;
import com.example.futin.tabletest.RESTService.response.RSSignInResponse;
import com.example.futin.tabletest.userInterface.mainPage.MainPage;

public class FragmentLogin extends Fragment implements AsyncTaskReturnData{

    EditText txtUsername;
    EditText txtPassword;
    Button btnLogin;
    RestService rs;
    RSSignInResponse response=null;

    @Override
    public void returnDoneTask(Object obj) {
    response=(RSSignInResponse) obj;
        String ime=response.getEmployee().getFirstName();
        Intent i=new Intent(getActivity().getApplicationContext(), MainPage.class);
        startActivity(i);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.activity_fragment_login,container,false);
        txtUsername= (EditText) view.findViewById(R.id.txtUsername);
        txtPassword= (EditText) view.findViewById(R.id.txtPassword);
        btnLogin=(Button) view.findViewById(R.id.btnLogin);
        rs=new RestService(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=txtUsername.getText().toString();
                String password=txtPassword.getText().toString();
                //TODO Enter those parameters into RSEmployeeLogin(String username, String password)
                rs.signIn(username,password);
            }
        });
        return view;
    }


}
