package com.example.futin.tabletest.userInterface.login;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import com.example.futin.tabletest.R;
import android.os.Handler;


public class Welcome extends ActionBarActivity {

    private final int DELAYED_TIME = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent loginActivity=new Intent(Welcome.this, LoginAndRegistration.class);
                startActivity(loginActivity);
                finish();

            }
        },DELAYED_TIME );
    }


}
