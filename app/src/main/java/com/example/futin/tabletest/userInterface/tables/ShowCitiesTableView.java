package com.example.futin.tabletest.userInterface.tables;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.example.futin.tabletest.RESTService.listeners.DeleteRows;
import com.example.futin.tabletest.RESTService.listeners.ReturnStudentData;
import com.example.futin.tabletest.RESTService.listeners.SearchData;
import com.example.futin.tabletest.RESTService.models.City;
import com.example.futin.tabletest.RESTService.models.Student;
import com.example.futin.tabletest.RESTService.response.RSDeleteCityRowsResponse;
import com.example.futin.tabletest.RESTService.response.RSGetCitiesResponse;
import com.example.futin.tabletest.RESTService.response.RSGetStudentsResponse;
import com.example.futin.tabletest.RESTService.response.RSSearchForCityResponse;
import com.example.futin.tabletest.userInterface.login.LoginAndRegistration;

import java.util.ArrayList;

public class ShowCitiesTableView extends ActionBarActivity implements AsyncTaskReturnData, SearchData,
        DeleteRows, ReturnStudentData
{
    SharedPreferences sharedPreferences;
    RestService rs;
    RSGetCitiesResponse returnData;
    RSSearchForCityResponse returnSearchedData;
    RSDeleteCityRowsResponse returnDeletedData;
    RSGetStudentsResponse returnStudentsData;

    ArrayList<City> listOfCities;
    ArrayList<Student>listOfStudents;

    RelativeLayout cityTableLayout;
    TableLayout tblLayout;
    Button btnDeleteRow;
    EditText txtSearchCity;

    //header columns
    TextView txtCityOnHeader;
    TextView txtCityNameHeader;
    TextView txtCityPttHeader;

    //tableLayout
    TextView cityIdColumn;
    TextView cityNameColumn;
    TextView cityPttColumn;
    TextView txtNoResultCity;
    TextView checkboxCity;

    //innerClass onClickRow
    int idCounter;
    boolean deleteMode=false;
    boolean isLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_cities_table_view);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        sharedPreferences=getSharedPreferences("employee", Context.MODE_PRIVATE);
        cityTableLayout= (RelativeLayout) findViewById(R.id.cityTableLayout);

        //header
        txtCityOnHeader= (TextView) findViewById(R.id.txtCityOnHeader);
        txtCityNameHeader= (TextView) findViewById(R.id.txtCityNameHeader);
        txtCityPttHeader= (TextView) findViewById(R.id.txtCityPttHeader);

        tblLayout= (TableLayout) findViewById(R.id.tblLayoutCity);
        cityIdColumn= (TextView) findViewById(R.id.cityIdColumn);
        cityNameColumn= (TextView) findViewById(R.id.cityNameColumn);
        cityPttColumn= (TextView) findViewById(R.id.cityPttColumn);
        txtNoResultCity= (TextView) findViewById(R.id.txtNoResultCity);
        txtSearchCity= (EditText) findViewById(R.id.txtSearchCity);
        checkboxCity= (TextView) findViewById(R.id.checkBoxCity);
        btnDeleteRow= (Button) findViewById(R.id.btnDeleteRowCity);

        rs=new RestService(this);
        rs.setSearchData(this);
        rs.setDeleteRowsData(this);
        rs.setReturnStudentData(this);
        rs.getCities();
        rs.getStudents();

        btnDeleteRow.setEnabled(false);
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
        isLoggedIn=sharedPreferences.getBoolean("isLoggedIn", false);
        if (isLoggedIn)
        inflater.inflate(R.menu.menu_item_table_view, menu);
        else
        inflater.inflate(R.menu.menu_item_table_view_not_logged_in, menu);
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
                break;
            case R.id.delete_mode:
                deleteMode=!deleteMode;
                if(deleteMode){
                    btnDeleteRow.setEnabled(true);
                    getCheckboxFromTable(View.VISIBLE);

                }else{
                    btnDeleteRow.setEnabled(false);
                   getCheckboxFromTable(View.INVISIBLE);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    //entering rows into tableLayout
    public void setTableView(){
         idCounter=0;
        tblLayout.removeAllViews();
        if(listOfCities != null) {

            txtNoResultCity.setVisibility(View.INVISIBLE);
            //set columns visible
            txtCityOnHeader.setVisibility(View.VISIBLE);
            txtCityNameHeader.setVisibility(View.VISIBLE);
            txtCityPttHeader.setVisibility(View.VISIBLE);
            for (City city : listOfCities) {
                idCounter++;
                final String id = String.valueOf(idCounter);
                String name = city.getCityName();
                String ptt = String.valueOf(city.getCityPtt());

                final TableRow row = new TableRow(this);

                final TextView ordNumb = new TextView(this);
                final TextView cityName = new TextView(this);
                final TextView cityPtt = new TextView(this);
                final CheckBox checkBox = new CheckBox(this);

                ordNumb.setText(id);
                cityName.setText(name);
                cityPtt.setText(ptt);

                cityIdColumn.setGravity(Gravity.CENTER);
                cityNameColumn.setGravity(Gravity.CENTER);
                cityPttColumn.setGravity(Gravity.CENTER);

                checkBox.setButtonDrawable(R.drawable.custom_checkbox);

                cityIdColumn.setBackground(getResources().getDrawable(R.drawable.cell_shape_first_row));
                cityNameColumn.setBackground(getResources().getDrawable(R.drawable.cell_shape_first_row));
                cityPttColumn.setBackground(getResources().getDrawable(R.drawable.cell_shape_first_row));

                //LayoutParams for ordNumb
                TableRow.LayoutParams paramsCityId = (TableRow.LayoutParams) cityIdColumn.getLayoutParams();
                //  paramsCityId.span=1;
                //  paramsCityId.column=1;
                ordNumb.setLayoutParams(paramsCityId);

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

                ordNumb.setTextSize(22);
                cityName.setTextSize(22);
                cityPtt.setTextSize(22);
                checkBox.setTextSize(22);

                ordNumb.setGravity(Gravity.CENTER);
                cityName.setGravity(Gravity.CENTER);
                cityPtt.setGravity(Gravity.CENTER);
                //for different background of every row

                if (idCounter % 2 == 0) {
                    ordNumb.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                    cityName.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                    cityPtt.setBackground(getResources().getDrawable(R.drawable.cell_shape_last_column));
                } else {
                    ordNumb.setBackground(getResources().getDrawable(R.drawable.cell_shape_different_background));
                    cityName.setBackground(getResources().getDrawable(R.drawable.cell_shape_different_background));
                    cityPtt.setBackground(getResources().getDrawable(R.drawable.cell_shape_last_column_different_background));
                }

                //setting rows
                row.addView(ordNumb);
                row.addView(cityName);
                row.addView(cityPtt);
                row.addView(checkBox);

                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView oldOrdNumb = ordNumb;
                        TextView oldCityName = cityName;
                        TextView oldCityPtt = cityPtt;
                        //changing checked rows background
                        if (checkBox.isChecked()){
                            ordNumb.setBackground(getResources().getDrawable(R.drawable.cell_shape_picked_column));
                            cityName.setBackground(getResources().getDrawable(R.drawable.cell_shape_picked_column));
                            cityPtt.setBackground(getResources().getDrawable(R.drawable.cell_shape_picked_last_column));
                        } else {
                            //if it is not checked, return to previous state
                            if (Integer.parseInt(id) % 2 == 0) {
                                oldOrdNumb.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                                oldCityName.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                                oldCityPtt.setBackground(getResources().getDrawable(R.drawable.cell_shape_last_column));
                            } else {
                                oldOrdNumb.setBackground(getResources().getDrawable(R.drawable.cell_shape_different_background));
                                oldCityName.setBackground(getResources().getDrawable(R.drawable.cell_shape_different_background));
                                oldCityPtt.setBackground(getResources().getDrawable(R.drawable.cell_shape_last_column_different_background));
                            }
                        }
                    }
                });
                tblLayout.addView(row);
            }
        }
        else{
            txtNoResultCity.setText("No results for these parameters");
            txtNoResultCity.setGravity(Gravity.CENTER);
            txtNoResultCity.setVisibility(View.VISIBLE);
            //set columns invisible
            txtCityOnHeader.setVisibility(View.INVISIBLE);
            txtCityNameHeader.setVisibility(View.INVISIBLE);
            txtCityPttHeader.setVisibility(View.INVISIBLE);
        }
        //only way to set checkbox invisible on start
        if (btnDeleteRow.isEnabled()){
            getCheckboxFromTable(View.VISIBLE);
        }else{
            getCheckboxFromTable(View.INVISIBLE);
        }

    }

    @Override
    public void returnDataOnPostExecute(Object obj) {
        returnData= (RSGetCitiesResponse) obj;
        listOfCities=returnData.getListOFCities();
        setTableView();
    }

    @Override
    public void searchData(Object o) {
        returnSearchedData= (RSSearchForCityResponse) o;
        listOfCities=returnSearchedData.getListOfSearchedCities();
        setTableView();

    }

     @Override
     public void deleteRowsReturnData(Object o) {
        returnDeletedData= (RSDeleteCityRowsResponse) o;
     }

      @Override
     public void returnStudentDataOnPostExecute(Object o) {
        returnStudentsData= (RSGetStudentsResponse) o;
          listOfStudents=returnStudentsData.getStudents();
     }

    public void deleteRow(View v) {
        final ArrayList<Integer> listOfCheckedCities = new ArrayList<>();

        for (int i = 0; i < tblLayout.getChildCount(); i++) {

            //iterate through whole table
            TableRow checked = (TableRow) tblLayout.getChildAt(i);
            //take 4-th row (our checkbox)
            CheckBox c = (CheckBox) checked.getVirtualChildAt(3);
            //take primary key from table
            TextView pttView = (TextView) checked.getVirtualChildAt(2);
            if (c.isChecked()) {
                int ptt = Integer.parseInt(pttView.getText().toString());
                //put all integers into list
                listOfCheckedCities.add(ptt);
            }
        }
        boolean isFound = false;

        if (listOfCheckedCities != null && listOfCheckedCities.size() > 0 &&
                listOfStudents != null && listOfStudents.size() > 0) {
            //take list of checked cities and parse them into individual Strings
            String listOfChecked = listOfCheckedCities.toString();
            String listWithNoBrackets = listOfChecked.substring(1, listOfChecked.length() - 1);
            String[] parsedList = listWithNoBrackets.split(", ");
            //match checked city's ptt with student's ptt from database.
            for (int i = 0; i < parsedList.length; i++) {
                for (Student student : listOfStudents) {
                    if (student.getCity().getCityPtt() == Integer.parseInt(parsedList[i])) {
                        //there is a student using this particular city
                        isFound = true;
                        break;
                    }
                }
            }
        }
        if (!isFound){
            if(listOfCheckedCities.size() > 0) {
                new AlertDialog.Builder(this)
                        .setTitle("Deleting rows")
                        .setMessage("Are u sure you want to delete selected row/rows?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                rs.deleteCityRows(listOfCheckedCities);
                                txtSearchCity.setText("");
                                makeToast("City successfully deleted");
                                rs.getCities();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        }else{
            //check to make difference between single and multiple rows
            if(listOfCheckedCities.size()==1)
                makeToast("You cannot delete this row!");
            else
                makeToast("You cannot delete these rows!");

        }

}

    void getCheckboxFromTable(int type){
        for (int i = 0; i < tblLayout.getChildCount(); i++) {
            //iterate through whole table
            TableRow checked = (TableRow) tblLayout.getChildAt(i);
            //take 4-th column (our checkbox)
            CheckBox c = (CheckBox) checked.getVirtualChildAt(3);
            c.setVisibility(type);

        }
    }

    public void makeToast(String text){
        Toast.makeText(getApplicationContext(),text, Toast.LENGTH_SHORT).show();
    }

}
