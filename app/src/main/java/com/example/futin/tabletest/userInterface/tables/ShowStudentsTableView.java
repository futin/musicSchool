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
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.futin.tabletest.R;
import com.example.futin.tabletest.RESTService.RestService;
import com.example.futin.tabletest.RESTService.listeners.ReturnStudentData;
import com.example.futin.tabletest.RESTService.listeners.SearchData;
import com.example.futin.tabletest.RESTService.models.Student;
import com.example.futin.tabletest.RESTService.response.RSGetStudentsResponse;
import com.example.futin.tabletest.RESTService.response.RSSearchForStudentResponse;
import com.example.futin.tabletest.userInterface.login.LoginAndRegistration;

import java.util.ArrayList;

public class ShowStudentsTableView extends ActionBarActivity implements ReturnStudentData, SearchData {

    SharedPreferences sharedPreferences;

    RSGetStudentsResponse returnData;
    RSSearchForStudentResponse returnDataSearch;
    RelativeLayout studentTableLayout;
    ArrayList<Student> listOfStudents=new ArrayList<>();
    TableLayout tblLayoutStudent;
    TableLayout tblLayoutStudentHeader;

    TextView studentIdColumn;
    TextView studentFirstAndLastNameColumn;
    TextView studentPttColumn;
    TextView txtNoResultStudents;

    TextView test;
    int counter=0;

    TableRow row;
    EditText searchStudent;
    RestService rs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_students_table_view);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        sharedPreferences=getSharedPreferences("employee", Context.MODE_PRIVATE);
        row = new TableRow(this);

        studentTableLayout= (RelativeLayout) findViewById(R.id.studentTableLayout);
        tblLayoutStudent = (TableLayout) findViewById(R.id.tblLayoutStudent);
        tblLayoutStudentHeader= (TableLayout) findViewById(R.id.tblLayoutStudentHeader);
        studentIdColumn = (TextView) findViewById(R.id.studentIdColumn);
        studentFirstAndLastNameColumn= (TextView) findViewById(R.id.studentFirstAndLastNameColumn);
        studentPttColumn= (TextView) findViewById(R.id.studentPttColumn);
        searchStudent= (EditText) findViewById(R.id.txtSearchStudent);
        txtNoResultStudents= (TextView) findViewById(R.id.txtNoResultStudents);


        rs=new RestService();
        rs.setReturnStudentData(this);
        rs.setSearchData(this);
        rs.getStudents();

    }

    @Override
    protected void onStart() {
        super.onStart();
        searchStudent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
              rs.searchForStudent(s.toString());
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
                                Intent loginActivity=new Intent(ShowStudentsTableView.this, LoginAndRegistration.class);
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

    public void setTableSearchView(){
        tblLayoutStudent.removeAllViews();
        if(listOfStudents !=null){
            txtNoResultStudents.setVisibility(View.INVISIBLE);
            for (Student student : listOfStudents) {
            counter++;

            String id = String.valueOf(student.getStudentId());
            String name = student.getFirstName() + " " + student.getLastName();
            String ptt = String.valueOf(student.getCity().getCityPtt());

            TableRow row1 = new TableRow(this);

            TextView studId = new TextView(this);
            TextView studName = new TextView(this);
            TextView studCityPtt = new TextView(this);

            studId.setText(id);
            studName.setText(name);
            studCityPtt.setText(ptt);

            studentIdColumn.setGravity(Gravity.CENTER);
            studentFirstAndLastNameColumn.setGravity(Gravity.CENTER);
            studentPttColumn.setGravity(Gravity.CENTER);

                studentIdColumn.setBackground(getResources().getDrawable(R.drawable.cell_shape_first_row));
                studentFirstAndLastNameColumn.setBackground(getResources().getDrawable(R.drawable.cell_shape_first_row));
                studentPttColumn.setBackground(getResources().getDrawable(R.drawable.cell_shape_first_row));

            //LayoutParams for studId
            TableRow.LayoutParams paramsStudId = (TableRow.LayoutParams) studentIdColumn.getLayoutParams();
            //  paramsCityId.span=1;
            //  paramsCityId.column=1;
            studId.setLayoutParams(paramsStudId);

            //LayoutParams for studName
            TableRow.LayoutParams paramsStudName = (TableRow.LayoutParams) studentFirstAndLastNameColumn.getLayoutParams();
            //  paramsCityId.span=3;
            //  paramsCityId.column=1;
            studName.setLayoutParams(paramsStudName);

            //LayoutParams for studCityPtt
            TableRow.LayoutParams paramsStudType = (TableRow.LayoutParams) studentPttColumn.getLayoutParams();
            //   paramsCityId.span=3;
            //  paramsCityId.column=1;
            studCityPtt.setLayoutParams(paramsStudType);

            studId.setTextSize(16);
            studName.setTextSize(16);
            studCityPtt.setTextSize(16);

            studId.setGravity(Gravity.CENTER);
            studName.setGravity(Gravity.CENTER);
            studCityPtt.setGravity(Gravity.CENTER);


            if (counter % 2 == 0) {
                studId.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                studName.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                studCityPtt.setBackground(getResources().getDrawable(R.drawable.cell_shape_last_column));
            } else {
                studId.setBackground(getResources().getDrawable(R.drawable.cell_shape_different_background));
                studName.setBackground(getResources().getDrawable(R.drawable.cell_shape_different_background));
                studCityPtt.setBackground(getResources().getDrawable(R.drawable.cell_shape_last_column_different_background));
            }
            row1.addView(studId);
            row1.addView(studName);
            row1.addView(studCityPtt);

            tblLayoutStudent.addView(row1);
            }
        }else{
            txtNoResultStudents.setVisibility(View.VISIBLE);
            txtNoResultStudents.setText("No result for these parameters");
            txtNoResultStudents.setGravity(Gravity.CENTER);
        }
    }
    @Override
    public void returnStudentDataOnPostExecute(Object o) {
        returnData= (RSGetStudentsResponse) o;
        listOfStudents =returnData.getStudents();
        setTableSearchView();
    }

    @Override
    public void searchData(Object o) {
        returnDataSearch=(RSSearchForStudentResponse)o;
        listOfStudents=returnDataSearch.getListOfSearchedStudents();
        setTableSearchView();

    }

    public void makeToast(String text){
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }
}
