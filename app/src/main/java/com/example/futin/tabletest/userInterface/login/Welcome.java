package com.example.futin.tabletest.userInterface.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import com.example.futin.tabletest.R;
import com.example.futin.tabletest.userInterface.mainPage.MainPage;

import android.os.Handler;
import android.view.Window;


public class Welcome extends ActionBarActivity {

    //TODO make it on 2000
    private final int DELAYED_TIME = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences= getSharedPreferences("employee", Context.MODE_PRIVATE);
                boolean loggedIn=sharedPreferences.getBoolean("isLoggedIn",false);
                if(loggedIn){
                    Intent i = new Intent(Welcome.this, MainPage.class);
                    startActivity(i);
                }else{
                    Intent loginActivity=new Intent(Welcome.this, LoginAndRegistration.class);
                    startActivity(loginActivity);
                }
                finish();

            }
        },DELAYED_TIME );


    }


}
