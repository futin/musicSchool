package com.example.futin.tabletest.userInterface.tables;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.futin.tabletest.R;
import com.example.futin.tabletest.RESTService.RestService;
import com.example.futin.tabletest.RESTService.listeners.AsyncTaskReturnData;
import com.example.futin.tabletest.RESTService.models.City;
import com.example.futin.tabletest.RESTService.response.RSGetCitiesResponse;
import com.example.futin.tabletest.userInterface.login.LoginAndRegistration;
import com.example.futin.tabletest.userInterface.mainPage.MainPage;

import java.util.ArrayList;

public class ShowCitiesTableView extends ActionBarActivity implements AsyncTaskReturnData{

    SharedPreferences sharedPreferences;
    RSGetCitiesResponse returnData;
    ArrayList<City> listOfCities;

    RelativeLayout cityTableLayout;
    TableLayout tblLayout;

    TextView cityIdColumn;
    TextView cityNameColumn;
    TextView cityPttColumn;
    int idCounter=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_cities_table_view);

        sharedPreferences=getSharedPreferences("employee", Context.MODE_PRIVATE);

        cityTableLayout= (RelativeLayout) findViewById(R.id.cityTableLayout);
        tblLayout= (TableLayout) findViewById(R.id.tblLayout);
        cityIdColumn= (TextView) findViewById(R.id.cityIdColumn);
        cityNameColumn= (TextView) findViewById(R.id.cityNameColumn);
        cityPttColumn= (TextView) findViewById(R.id.cityPttColumn);


        RestService rs=new RestService(this);
        rs.getCities();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
                                Intent loginActivity=new Intent(ShowCitiesTableView.this, LoginAndRegistration.class);
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

    public void setTableView(){
        for (City city : listOfCities ){
            idCounter++;
            String id=String.valueOf(idCounter);
            String name=city.getCityName();
            String ptt=String.valueOf(city.getCityPtt());

            TableRow row=new TableRow(this);

            TextView cityId=new TextView(this);
            TextView cityName=new TextView(this);
            TextView cityPtt=new TextView(this);

            cityId.setText(id);
            cityName.setText(name);
            cityPtt.setText(ptt);

            cityIdColumn.setGravity(Gravity.CENTER);
            cityNameColumn.setGravity(Gravity.CENTER);
            cityPttColumn.setGravity(Gravity.CENTER);

            cityIdColumn.setBackground(getResources().getDrawable(R.drawable.cell_shape_first_row));
            cityNameColumn.setBackground(getResources().getDrawable(R.drawable.cell_shape_first_row));
            cityPttColumn.setBackground(getResources().getDrawable(R.drawable.cell_shape_first_row));

            //LayoutParams for cityId
            TableRow.LayoutParams paramsCityId=(TableRow.LayoutParams)cityIdColumn.getLayoutParams();
          //  paramsCityId.span=1;
          //  paramsCityId.column=1;
            cityId.setLayoutParams(paramsCityId);

            //LayoutParams for cityName
            TableRow.LayoutParams paramsCityName=(TableRow.LayoutParams)cityNameColumn.getLayoutParams();
          //  paramsCityId.span=3;
          //  paramsCityId.column=1;
            cityName.setLayoutParams(paramsCityName);

            //LayoutParams for cityPtt
            TableRow.LayoutParams paramsCityPtt=(TableRow.LayoutParams)cityPttColumn.getLayoutParams();
         //   paramsCityId.span=3;
          //  paramsCityId.column=1;
            cityPtt.setLayoutParams(paramsCityPtt);

            cityId.setBackground(getResources().getDrawable(R.drawable.cell_shape));
            cityName.setBackground(getResources().getDrawable(R.drawable.cell_shape));
            cityPtt.setBackground(getResources().getDrawable(R.drawable.cell_shape_last_column));

            cityId.setTextSize(22);
            cityName.setTextSize(22);
            cityPtt.setTextSize(22);

            cityId.setGravity(Gravity.CENTER);
            cityName.setGravity(Gravity.CENTER);
            cityPtt.setGravity(Gravity.CENTER);

            row.addView(cityId);
            row.addView(cityName);
            row.addView(cityPtt);

            tblLayout.addView(row);
        }
    }

    @Override
    public void returnDataOnPostExecute(Object obj) {
        returnData= (RSGetCitiesResponse) obj;
        listOfCities=returnData.getListOFCities();
        setTableView();
    }
}
