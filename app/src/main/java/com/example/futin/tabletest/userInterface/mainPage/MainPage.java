package com.example.futin.tabletest.userInterface.mainPage;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import android.app.AlertDialog;
import com.example.futin.tabletest.R;
import com.example.futin.tabletest.userInterface.adapter.TabMainPageAdapter;
import com.example.futin.tabletest.userInterface.login.LoginAndRegistration;

public class MainPage extends ActionBarActivity {

    TabMainPageAdapter tabAdapter;
    ViewPager myViewPager;

    SharedPreferences sharedPreferences;

    String firstName;
    String lastName;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        tabAdapter=new TabMainPageAdapter(getSupportFragmentManager());
        myViewPager= (ViewPager) findViewById(R.id.MainPagePager);
        myViewPager.setAdapter(tabAdapter);
        //get firstName of user
        sharedPreferences=getSharedPreferences("employee", Context.MODE_PRIVATE);
         firstName=sharedPreferences.getString("firstName","");
         lastName=sharedPreferences.getString("lastName","");

        Toast.makeText(this, "Welcome "+firstName+" "+lastName, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.log_out_menu_item:
            new AlertDialog.Builder(this)
                    .setTitle("Logging out")
                    .setMessage("Do you really want to log out?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putBoolean("isLoggedIn", false);
                    editor.apply();
                    boolean b=sharedPreferences.getBoolean("isLoggedIn",false);
                    Toast.makeText(getApplicationContext(),""+b,Toast.LENGTH_SHORT).show();
                    Intent loginActivity=new Intent(MainPage.this, LoginAndRegistration.class);
                    loginActivity.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK |
                            Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(loginActivity);

                }

            })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }
}
