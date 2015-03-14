package com.example.futin.tabletest.userInterface.mainPage;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.futin.tabletest.R;
import com.example.futin.tabletest.userInterface.adapter.TabMainPageAdapter;

public class MainPage extends ActionBarActivity {

    TabMainPageAdapter tabAdapter;
    ViewPager myViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        tabAdapter=new TabMainPageAdapter(getSupportFragmentManager());
        myViewPager= (ViewPager) findViewById(R.id.MainPagePager);
        myViewPager.setAdapter(tabAdapter);

    }
}
