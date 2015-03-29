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
import android.util.Log;
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
import com.example.futin.tabletest.RESTService.listeners.ReturnStudentWithInstrumentData;
import com.example.futin.tabletest.RESTService.listeners.SearchData;
import com.example.futin.tabletest.RESTService.models.Employee;
import com.example.futin.tabletest.RESTService.response.RSGetStudentWithInstrumentResponse;
import com.example.futin.tabletest.RESTService.response.RSSearchForStudentWIthInstrumentsResponse;
import com.example.futin.tabletest.userInterface.login.LoginAndRegistration;

import java.util.ArrayList;

public class ShowStudentsWithInstrumentsTableView extends ActionBarActivity
        implements ReturnStudentWithInstrumentData, SearchData {

    SharedPreferences sharedPreferences;
    RestService rs;

    RSGetStudentWithInstrumentResponse returnData;
    RSSearchForStudentWIthInstrumentsResponse returnSearchedData;

    ArrayList<Employee>listOfEmployees;

    RelativeLayout instrumentTableLayout;
    TableLayout tblLayoutStudentWithInstrument;

    TextView studentNameColumn;
    TextView employeeNameColumn;
    TextView instrumentNameColumn;
    TextView numberOfInstrumentsColumn;
    TextView dateColumn;
    TextView txtNoResultStudWithInst;
    EditText txtSearchStudentWithInstrument;
    Button btnDeleteRowStudWithInst;

    //innerClass onClickRow
    boolean deleteMode=false;
    boolean isLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_students_with_insturments_table_view);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        sharedPreferences = getSharedPreferences("employee", Context.MODE_PRIVATE);

        instrumentTableLayout= (RelativeLayout) findViewById(R.id.instrumentTableLayout);
        tblLayoutStudentWithInstrument = (TableLayout) findViewById(R.id.tblLayoutStudentWithInstrument);
        studentNameColumn = (TextView) findViewById(R.id.studentNameColumn);
        employeeNameColumn= (TextView) findViewById(R.id.employeeNameColumn);
        instrumentNameColumn= (TextView) findViewById(R.id.instrumentNameColumn);
        numberOfInstrumentsColumn= (TextView) findViewById(R.id.numberOfInstrumentsColumn);
        dateColumn= (TextView) findViewById(R.id.dateColumn);
        txtNoResultStudWithInst= (TextView) findViewById(R.id.txtNoResultStudWithInst);
        txtSearchStudentWithInstrument= (EditText) findViewById(R.id.txtSearchStudentWithInstrument);
        btnDeleteRowStudWithInst= (Button) findViewById(R.id.btnDeleteRowStudWithInst);

        rs=new RestService();
        rs.setReturnStudentWithInstrumentData(this);
        rs.setSearchData(this);
        rs.getStudentWithInstrument();

        btnDeleteRowStudWithInst.setEnabled(false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        txtSearchStudentWithInstrument.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                rs.searchForStudentWithInstrument(s.toString());
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
                                Intent loginActivity=new Intent(ShowStudentsWithInstrumentsTableView.this, LoginAndRegistration.class);
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
                    btnDeleteRowStudWithInst.setEnabled(true);
                    getCheckboxFromTable(View.VISIBLE);

                }else{
                    btnDeleteRowStudWithInst.setEnabled(false);
                    getCheckboxFromTable(View.INVISIBLE);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setTableView(){
        tblLayoutStudentWithInstrument.removeAllViews();
        int counter=0;
        if(listOfEmployees != null) {
            txtNoResultStudWithInst.setVisibility(View.INVISIBLE);
            for (Employee employee : listOfEmployees) {
                counter++;

                String counterId=String.valueOf(counter);
                //student
                String firstName = employee.getStudent().getFirstName();
                String lastName = employee.getStudent().getLastName();
                String newFirstName = firstName.substring(0, 1) + ".";

                String studName = newFirstName + " " + lastName;
                //studentWithInstruments
                String name = employee.getFirstName();
                Log.i("emp name: ", name);
                String[] splitName = name.split(" ");
                String firstEmpName = splitName[0].substring(0, 1) + ".";
                String lastEmpName = splitName[1];


                String empName = firstEmpName + " " + lastEmpName;
                String date = employee.getDate();
                String numberOfInst = String.valueOf(employee.getStudent().getNumberOfInstruments());
                //instrument
                String instName = employee.getStudent().getInstrument().getInstrumentName();

                TableRow row = new TableRow(this);

                final TextView ordNumb=new TextView(this);
                final TextView studentName = new TextView(this);
                final TextView employeeName = new TextView(this);
                final TextView instrumentName = new TextView(this);
                final TextView numberOfInstruments = new TextView(this);
                final TextView dateView = new TextView(this);
                final CheckBox checkboxStudWithInst=new CheckBox(this);

                checkboxStudWithInst.setButtonDrawable(R.drawable.custom_checkbox);

                ordNumb.setText(counterId);
                studentName.setText(studName);
                employeeName.setText(empName);
                instrumentName.setText(instName);
                numberOfInstruments.setText(numberOfInst);
                dateView.setText(date);

                studentNameColumn.setGravity(Gravity.CENTER);
                instrumentNameColumn.setGravity(Gravity.CENTER);
                employeeNameColumn.setGravity(Gravity.CENTER);
                numberOfInstrumentsColumn.setGravity(Gravity.CENTER);
                dateColumn.setGravity(Gravity.CENTER);

                studentNameColumn.setBackground(getResources().getDrawable(R.drawable.cell_shape_first_row));
                instrumentNameColumn.setBackground(getResources().getDrawable(R.drawable.cell_shape_first_row));
                employeeNameColumn.setBackground(getResources().getDrawable(R.drawable.cell_shape_first_row));
                numberOfInstrumentsColumn.setBackground(getResources().getDrawable(R.drawable.cell_shape_first_row));
                dateColumn.setBackground(getResources().getDrawable(R.drawable.cell_shape_first_row));

                //LayoutParams for studName
                TableRow.LayoutParams paramsStudName = (TableRow.LayoutParams) studentNameColumn.getLayoutParams();
                //  paramsStudName.span=3;
                //  paramsCityId.column=1;
                studentName.setLayoutParams(paramsStudName);

                //LayoutParams for empName
                TableRow.LayoutParams paramsEmpName = (TableRow.LayoutParams) employeeNameColumn.getLayoutParams();
                //  paramsCityId.span=3;
                //  paramsCityId.column=1;
                employeeName.setLayoutParams(paramsEmpName);

                //LayoutParams for instName
                TableRow.LayoutParams paramsInstName = (TableRow.LayoutParams) instrumentNameColumn.getLayoutParams();
                //   paramsCityId.span=3;
                //  paramsCityId.column=1;
                instrumentName.setLayoutParams(paramsInstName);

                //LayoutParams for numbOfInst
                TableRow.LayoutParams paramsNumbOfInst = (TableRow.LayoutParams) numberOfInstrumentsColumn.getLayoutParams();
                //   paramsCityId.span=3;
                //  paramsCityId.column=1;
                numberOfInstruments.setLayoutParams(paramsNumbOfInst);
                //LayoutParams for date
                TableRow.LayoutParams paramsDate = (TableRow.LayoutParams) dateColumn.getLayoutParams();
                //   paramsCityId.span=3;
                //  paramsCityId.column=1;
                dateView.setLayoutParams(paramsDate);

                ordNumb.setTextSize(16);
                studentName.setTextSize(16);
                employeeName.setTextSize(16);
                instrumentName.setTextSize(16);
                numberOfInstruments.setTextSize(16);
                dateView.setTextSize(16);
                checkboxStudWithInst.setTextSize(16);

                studentName.setGravity(Gravity.CENTER);
                employeeName.setGravity(Gravity.CENTER);
                instrumentName.setGravity(Gravity.CENTER);
                numberOfInstruments.setGravity(Gravity.CENTER);
                dateView.setGravity(Gravity.CENTER);

                if (counter % 2 == 0) {
                    ordNumb.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                    studentName.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                    employeeName.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                    instrumentName.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                    numberOfInstruments.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                    dateView.setBackground(getResources().getDrawable(R.drawable.cell_shape_last_column));

                } else {
                    ordNumb.setBackground(getResources().getDrawable(R.drawable.cell_shape_different_background));
                    studentName.setBackground(getResources().getDrawable(R.drawable.cell_shape_different_background));
                    employeeName.setBackground(getResources().getDrawable(R.drawable.cell_shape_different_background));
                    instrumentName.setBackground(getResources().getDrawable(R.drawable.cell_shape_different_background));
                    numberOfInstruments.setBackground(getResources().getDrawable(R.drawable.cell_shape_different_background));
                    dateView.setBackground(getResources().getDrawable(R.drawable.cell_shape_last_column_different_background));
                }
                row.addView(ordNumb);
                row.addView(studentName);
                row.addView(employeeName);
                row.addView(instrumentName);
                row.addView(numberOfInstruments);
                row.addView(dateView);
                row.addView(checkboxStudWithInst);

                checkboxStudWithInst.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        TextView oldOrdNumb = ordNumb;
                        TextView oldStudentName = studentName;
                        TextView oldEmployeeName = employeeName;
                        TextView oldInstrumetName = instrumentName;
                        TextView oldNumberOfInstruments = numberOfInstruments;
                        TextView oldDateView = dateView;

                        //changing checked rows background
                        if (checkboxStudWithInst.isChecked()){
                            ordNumb.setBackground(getResources().getDrawable(R.drawable.cell_shape_picked_column));
                            studentName.setBackground(getResources().getDrawable(R.drawable.cell_shape_picked_column));
                            employeeName.setBackground(getResources().getDrawable(R.drawable.cell_shape_picked_column));
                            instrumentName.setBackground(getResources().getDrawable(R.drawable.cell_shape_picked_column));
                            numberOfInstruments.setBackground(getResources().getDrawable(R.drawable.cell_shape_picked_column));
                            dateView.setBackground(getResources().getDrawable(R.drawable.cell_shape_picked_last_column));
                        } else {
                            //if it is not checked, return to previous state
                            if (Integer.parseInt(ordNumb.getText().toString()) % 2 == 0) {

                                oldOrdNumb.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                                oldStudentName.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                                oldEmployeeName.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                                oldInstrumetName.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                                oldNumberOfInstruments.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                                oldDateView.setBackground(getResources().getDrawable(R.drawable.cell_shape_last_column));
                            } else {
                                oldOrdNumb.setBackground(getResources().getDrawable(R.drawable.cell_shape_different_background));
                                oldStudentName.setBackground(getResources().getDrawable(R.drawable.cell_shape_different_background));
                                oldEmployeeName.setBackground(getResources().getDrawable(R.drawable.cell_shape_different_background));
                                oldInstrumetName.setBackground(getResources().getDrawable(R.drawable.cell_shape_different_background));
                                oldNumberOfInstruments.setBackground(getResources().getDrawable(R.drawable.cell_shape_different_background));
                                oldDateView.setBackground(getResources().getDrawable(R.drawable.cell_shape_last_column_different_background));

                            }
                        }
                    }
                });

                tblLayoutStudentWithInstrument.addView(row);
            }
        }else{
            txtNoResultStudWithInst.setVisibility(View.VISIBLE);
            txtNoResultStudWithInst.setGravity(Gravity.CENTER);
            txtNoResultStudWithInst.setText("No results for these parameters");

        }
        //only way to set checkbox invisible on start
        if (btnDeleteRowStudWithInst.isEnabled()){
            getCheckboxFromTable(View.VISIBLE);
        }else{
            getCheckboxFromTable(View.INVISIBLE);
        }
    }

    @Override
    public void returnStudentWithInstrumentDataOnPostExecute(Object o) {
        returnData= (RSGetStudentWithInstrumentResponse) o;
        listOfEmployees=returnData.getListOfStudentsWithInstrument();
        setTableView();

    }

    @Override
    public void searchData(Object o) {
        returnSearchedData= (RSSearchForStudentWIthInstrumentsResponse) o;
        listOfEmployees=returnSearchedData.getListOfEmployees();
        setTableView();
    }


    void getCheckboxFromTable(int type){
        for (int i = 0; i < tblLayoutStudentWithInstrument.getChildCount(); i++) {
            //iterate through whole table
            TableRow checked = (TableRow) tblLayoutStudentWithInstrument.getChildAt(i);
            //take 4-th column (our checkbox)
            CheckBox c = (CheckBox) checked.getVirtualChildAt(15);
            c.setVisibility(type);

        }
    }

    public void makeToast(String text){
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }
}