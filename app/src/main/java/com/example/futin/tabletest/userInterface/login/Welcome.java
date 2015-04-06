package com.example.futin.tabletest.userInterface.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import com.example.futin.tabletest.R;
import com.example.futin.tabletest.userInterface.mainPage.MainPage;

import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;


public class Welcome extends ActionBarActivity {

    TextView txtIpAddress;
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
/*
        //return ip address

        WifiManager myWifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);

        WifiInfo myWifiInfo = myWifiManager.getConnectionInfo();
        int myIp = myWifiInfo.getIpAddress();

        int intMyIp3 = myIp/0x1000000;
        int intMyIp3mod = myIp%0x1000000;

        int intMyIp2 = intMyIp3mod/0x10000;
        int intMyIp2mod = intMyIp3mod%0x10000;

        int intMyIp1 = intMyIp2mod/0x100;
        int intMyIp0 = intMyIp2mod%0x100;
        txtIpAddress=new TextView(this);
        txtIpAddress.setText(String.valueOf(intMyIp0)
                        + "." + String.valueOf(intMyIp1)
                        + "." + String.valueOf(intMyIp2)
                        + "." + String.valueOf(intMyIp3)
        );

        Log.i("",txtIpAddress.getText().toString());
        */
    }


}
