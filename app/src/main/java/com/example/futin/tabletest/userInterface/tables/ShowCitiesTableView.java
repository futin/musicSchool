package com.example.futin.tabletest.userInterface.tables;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.futin.tabletest.R;
import com.example.futin.tabletest.RESTService.RestService;
import com.example.futin.tabletest.RESTService.listeners.AsyncTaskReturnData;
import com.example.futin.tabletest.RESTService.listeners.SearchCityData;
import com.example.futin.tabletest.RESTService.models.City;
import com.example.futin.tabletest.RESTService.response.RSGetCitiesResponse;
import com.example.futin.tabletest.RESTService.response.RSSearchForCityResponse;
import com.example.futin.tabletest.userInterface.login.LoginAndRegistration;
import com.example.futin.tabletest.userInterface.mainPage.MainPage;

import java.util.ArrayList;

public class ShowCitiesTableView extends ActionBarActivity implements AsyncTaskReturnData, SearchCityData
        {

    SharedPreferences sharedPreferences;
    RSGetCitiesResponse returnData;
    RSSearchForCityResponse returnSearchedData;
    ArrayList<City> listOfCities;

    RelativeLayout cityTableLayout;
    TableLayout tblLayout;
    TableLayout tblLayoutHeader;

    TextView cityIdColumn;
    TextView cityNameColumn;
    TextView cityPttColumn;
    TextView txtNoResultCity;

    //innerClass onClickRow
    int idCounter;
    boolean isRowPicked=false;

    EditText txtSearchCity;
    RestService rs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_cities_table_view);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        sharedPreferences=getSharedPreferences("employee", Context.MODE_PRIVATE);

        cityTableLayout= (RelativeLayout) findViewById(R.id.cityTableLayout);
        tblLayout= (TableLayout) findViewById(R.id.tblLayoutCity);
        tblLayoutHeader= (TableLayout) findViewById(R.id.tblLayoutCityHeader);
        cityIdColumn= (TextView) findViewById(R.id.cityIdColumn);
        cityNameColumn= (TextView) findViewById(R.id.cityNameColumn);
        cityPttColumn= (TextView) findViewById(R.id.cityPttColumn);
        txtNoResultCity= (TextView) findViewById(R.id.txtNoResultCity);
        txtSearchCity= (EditText) findViewById(R.id.txtSearchCity);


        rs=new RestService(this);
        rs.setSearchCityData(this);
        rs.getCities();

    }

    @Override
    protected void onStart() {
        super.onStart();
        txtSearchCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            rs.searchForCity(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
         idCounter=0;
        tblLayout.removeAllViews();
        if(listOfCities != null) {
            txtNoResultCity.setVisibility(View.INVISIBLE);
            for (City city : listOfCities) {
                idCounter++;
                final String id = String.valueOf(idCounter);
                String name = city.getCityName();
                String ptt = String.valueOf(city.getCityPtt());

               final TableRow row = new TableRow(this);

                final Drawable test=getResources().getDrawable(R.drawable.cell_shape);
                //clickable row


              final  TextView cityId = new TextView(this);
                final TextView cityName = new TextView(this);
                final  TextView cityPtt = new TextView(this);
                final CheckBox checkBox=new CheckBox(this);
                

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
                TableRow.LayoutParams paramsCityId = (TableRow.LayoutParams) cityIdColumn.getLayoutParams();
                //  paramsCityId.span=1;
                //  paramsCityId.column=1;
                cityId.setLayoutParams(paramsCityId);

                //LayoutParams for cityName
                TableRow.LayoutParams paramsCityName = (TableRow.LayoutParams) cityNameColumn.getLayoutParams();
                //  paramsCityId.span=3;
                //  paramsCityId.column=1;
                cityName.setLayoutParams(paramsCityName);

                //LayoutParams for cityPtt
                TableRow.LayoutParams paramsCityPtt = (TableRow.LayoutParams) cityPttColumn.getLayoutParams();
                //   paramsCityId.span=3;
                //  paramsCityId.column=1;
                cityPtt.setLayoutParams(paramsCityPtt);

                cityId.setTextSize(22);
                cityName.setTextSize(22);
                cityPtt.setTextSize(22);

                cityId.setGravity(Gravity.CENTER);
                cityName.setGravity(Gravity.CENTER);
                cityPtt.setGravity(Gravity.CENTER);

                if (idCounter % 2 == 0) {
                    cityId.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                    cityName.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                    cityPtt.setBackground(getResources().getDrawable(R.drawable.cell_shape_last_column));
                } else {
                    cityId.setBackground(getResources().getDrawable(R.drawable.cell_shape_different_background));
                    cityName.setBackground(getResources().getDrawable(R.drawable.cell_shape_different_background));
                    cityPtt.setBackground(getResources().getDrawable(R.drawable.cell_shape_last_column_different_background));
                }
                row.addView(cityId);
                row.addView(cityName);
                row.addView(cityPtt);
                //make textView only for counting and taking data.
                final TextView counter= (TextView) row.getChildAt(0);


                row.setClickable(true);
                row.setOnClickListener(new View.OnClickListener() {
                    TextView oldCityId=cityId;
                    TextView oldCityName=cityName;
                    TextView oldCityPtt=cityPtt;

                    @Override
                    public void onClick(View v) {
                        TableRow row = (TableRow)v;
                        TextView counter = (TextView)row.getChildAt(0);
                        isRowPicked=!isRowPicked;
                        if(!isRowPicked) {
                            cityId.setBackground(getResources().getDrawable(R.drawable.cell_shape_picked_column));
                            cityName.setBackground(getResources().getDrawable(R.drawable.cell_shape_picked_column));
                            cityPtt.setBackground(getResources().getDrawable(R.drawable.cell_shape_picked_last_column));
                        }else{
                            if (Integer.parseInt(id) % 2 == 0 ) {
                                oldCityId.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                                oldCityName.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                                oldCityPtt.setBackground(getResources().getDrawable(R.drawable.cell_shape_last_column));
                            } else {
                                oldCityId.setBackground(getResources().getDrawable(R.drawable.cell_shape_different_background));
                                oldCityName.setBackground(getResources().getDrawable(R.drawable.cell_shape_different_background));
                                oldCityPtt.setBackground(getResources().getDrawable(R.drawable.cell_shape_last_column_different_background));
                            }
                        }
                        Toast.makeText(getApplicationContext(),isRowPicked+"", Toast.LENGTH_SHORT).show();

                    }
                });
                tblLayout.addView(row);

            }
        }else{
            txtNoResultCity.setText("No result for these parameters");
            txtNoResultCity.setGravity(Gravity.CENTER);
            txtNoResultCity.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void returnDataOnPostExecute(Object obj) {
        returnData= (RSGetCitiesResponse) obj;
        listOfCities=returnData.getListOFCities();
        setTableView();
    }

    @Override
    public void searchCityReturnData(Object o) {
        returnSearchedData= (RSSearchForCityResponse) o;
        listOfCities=returnSearchedData.getListOfSearchedCities();
        setTableView();
    }


}
