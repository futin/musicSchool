package com.example.futin.tabletest.userInterface.login;

import android.content.pm.ActivityInfo;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import com.example.futin.tabletest.R;
import com.example.futin.tabletest.userInterface.adapter.TabLoginAdapter;

public class LoginAndRegistration extends ActionBarActivity {

    TabLoginAdapter tabAdapter;
    ViewPager myViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_and_registration);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        tabAdapter=new TabLoginAdapter(getSupportFragmentManager());
        myViewPager= (ViewPager) findViewById(R.id.pager);
        myViewPager.setAdapter(tabAdapter);
    }

}
