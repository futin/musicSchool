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
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.futin.tabletest.R;
import com.example.futin.tabletest.RESTService.RestService;
import com.example.futin.tabletest.RESTService.listeners.ReturnStudentData;
import com.example.futin.tabletest.RESTService.models.Instrument;
import com.example.futin.tabletest.RESTService.models.Student;
import com.example.futin.tabletest.RESTService.response.RSGetStudentsResponse;
import com.example.futin.tabletest.userInterface.login.LoginAndRegistration;

import java.util.ArrayList;

public class ShowStudentsTableView extends ActionBarActivity implements ReturnStudentData{

    SharedPreferences sharedPreferences;

    RSGetStudentsResponse returnData;
    RelativeLayout studentTableLayout;
    ArrayList<Student> listOfStudents;
    TableLayout tblLayoutStudent;

    TextView studentIdColumn;
    TextView studentFirstAndLastNameColumn;
    TextView studentPttColumn;
    int counter=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_students_table_view);

        sharedPreferences=getSharedPreferences("employee", Context.MODE_PRIVATE);

        studentTableLayout= (RelativeLayout) findViewById(R.id.studentTableLayout);
        tblLayoutStudent = (TableLayout) findViewById(R.id.tblLayoutStudent);
        studentIdColumn = (TextView) findViewById(R.id.studentIdColumn);
        studentFirstAndLastNameColumn= (TextView) findViewById(R.id.studentFirstAndLastNameColumn);
        studentPttColumn= (TextView) findViewById(R.id.studentPttColumn);

        RestService rs=new RestService();
        rs.setReturnReturnStudentData(this);
        rs.getStudents();

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


    public void setTableView(){
        for (Student student : listOfStudents){
            counter++;

            String id=String.valueOf(student.getStudentId());
            String name=student.getFirstName()+" "+student.getLastName();
            String ptt=String.valueOf(student.getCity().getCityPtt());

            TableRow row=new TableRow(this);

            TextView studId=new TextView(this);
            TextView studName=new TextView(this);
            TextView studCityPtt=new TextView(this);

            studId.setText(id);
            studName.setText(name);
            studCityPtt.setText(ptt);

            studentIdColumn.setGravity(Gravity.CENTER);
            studentFirstAndLastNameColumn.setGravity(Gravity.CENTER);
            studentPttColumn.setGravity(Gravity.CENTER);

            studId.setBackground(getResources().getDrawable(R.drawable.cell_shape_first_row));
            studName.setBackground(getResources().getDrawable(R.drawable.cell_shape_first_row));
            studCityPtt.setBackground(getResources().getDrawable(R.drawable.cell_shape_first_row));

            //LayoutParams for studId
            TableRow.LayoutParams paramsStudId=(TableRow.LayoutParams) studentIdColumn.getLayoutParams();
            //  paramsCityId.span=1;
            //  paramsCityId.column=1;
            studId.setLayoutParams(paramsStudId);

            //LayoutParams for studName
            TableRow.LayoutParams paramsStudName=(TableRow.LayoutParams)studentFirstAndLastNameColumn.getLayoutParams();
            //  paramsCityId.span=3;
            //  paramsCityId.column=1;
            studName.setLayoutParams(paramsStudName);

            //LayoutParams for studCityPtt
            TableRow.LayoutParams paramsStudType=(TableRow.LayoutParams)studentPttColumn.getLayoutParams();
            //   paramsCityId.span=3;
            //  paramsCityId.column=1;
            studCityPtt.setLayoutParams(paramsStudType);

            studId.setTextSize(16);
            studName.setTextSize(16);
            studCityPtt.setTextSize(16);

            studId.setGravity(Gravity.CENTER);
            studName.setGravity(Gravity.CENTER);
            studCityPtt.setGravity(Gravity.CENTER);


            if(counter %2==0){
                studId.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                studName.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                studCityPtt.setBackground(getResources().getDrawable(R.drawable.cell_shape_last_column));
            }else {
                studId.setBackground(getResources().getDrawable(R.drawable.cell_shape_different_background));
                studName.setBackground(getResources().getDrawable(R.drawable.cell_shape_different_background));
                studCityPtt.setBackground(getResources().getDrawable(R.drawable.cell_shape_last_column_different_background));
            }
            row.addView(studId);
            row.addView(studName);
            row.addView(studCityPtt);

            tblLayoutStudent.addView(row);
        }
    }

    @Override
    public void returnStudentDataOnPostExecute(Object o) {
        returnData= (RSGetStudentsResponse) o;
        listOfStudents =returnData.getStudents();
        setTableView();
        Log.i("isntrument ", listOfStudents.toString());
    }
}
