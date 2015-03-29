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
import com.example.futin.tabletest.RESTService.listeners.DeleteRows;
import com.example.futin.tabletest.RESTService.listeners.ReturnStudentData;
import com.example.futin.tabletest.RESTService.listeners.ReturnStudentWithInstrumentData;
import com.example.futin.tabletest.RESTService.listeners.SearchData;
import com.example.futin.tabletest.RESTService.models.Employee;
import com.example.futin.tabletest.RESTService.models.Student;
import com.example.futin.tabletest.RESTService.response.RSDeleteStudentRowsResponse;
import com.example.futin.tabletest.RESTService.response.RSGetStudentWithInstrumentResponse;
import com.example.futin.tabletest.RESTService.response.RSGetStudentsResponse;
import com.example.futin.tabletest.RESTService.response.RSSearchForStudentResponse;
import com.example.futin.tabletest.userInterface.login.LoginAndRegistration;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ShowStudentsTableView extends ActionBarActivity implements ReturnStudentData, SearchData,
        DeleteRows, ReturnStudentWithInstrumentData{

    SharedPreferences sharedPreferences;
    RestService rs;
    RSGetStudentsResponse returnData;
    RSSearchForStudentResponse returnDataSearch;
    RSDeleteStudentRowsResponse returnDeletedData;
    RSGetStudentWithInstrumentResponse returnStudWithInstData;

    RelativeLayout studentTableLayout;
    TableLayout tblLayoutStudent;
    TextView studentIdColumn;
    TextView studentFirstAndLastNameColumn;
    TextView studentPttColumn;
    TextView txtNoResultStudents;
    TextView studentOrdinalNumbColumn;
    EditText searchStudent;
    Button btnDeleteRowStudent;

    ArrayList<Student> listOfStudents=new ArrayList<>();
    ArrayList<Employee>listOfEmployees=new ArrayList<>();

    //innerClass onClickRow
    int counter;
    boolean deleteMode=false;
    boolean isLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_students_table_view);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        sharedPreferences=getSharedPreferences("employee", Context.MODE_PRIVATE);

        studentTableLayout= (RelativeLayout) findViewById(R.id.studentTableLayout);
        tblLayoutStudent = (TableLayout) findViewById(R.id.tblLayoutStudent);
        studentIdColumn = (TextView) findViewById(R.id.studentIdColumn);
        studentFirstAndLastNameColumn= (TextView) findViewById(R.id.studentFirstAndLastNameColumn);
        studentPttColumn= (TextView) findViewById(R.id.studentPttColumn);
        searchStudent= (EditText) findViewById(R.id.txtSearchStudent);
        txtNoResultStudents= (TextView) findViewById(R.id.txtNoResultStudents);
        btnDeleteRowStudent= (Button) findViewById(R.id.btnDeleteRowStudent);
        studentOrdinalNumbColumn= (TextView) findViewById(R.id.studentOrdnalNumbColumn);

        rs=new RestService();
        rs.setReturnStudentData(this);
        rs.setSearchData(this);
        rs.setDeleteRowsData(this);
        rs.setReturnStudentWithInstrumentData(this);
        rs.getStudents();
        rs.getStudentWithInstrument();

        btnDeleteRowStudent.setEnabled(false);
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
                break;
            case R.id.delete_mode:
                deleteMode=!deleteMode;
                if(deleteMode){
                    btnDeleteRowStudent.setEnabled(true);
                    getCheckboxFromTable(View.VISIBLE);

                }else{
                    btnDeleteRowStudent.setEnabled(false);
                    getCheckboxFromTable(View.INVISIBLE);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setTableSearchView(){
        counter=0;
        tblLayoutStudent.removeAllViews();
        if(listOfStudents !=null){
            txtNoResultStudents.setVisibility(View.INVISIBLE);
            for (Student student : listOfStudents) {
            counter++;

            String id = String.valueOf(student.getStudentId());
            String name = student.getFirstName() + " " + student.getLastName();
            String ptt = String.valueOf(student.getCity().getCityPtt());


            TableRow row = new TableRow(this);

            final TextView ordinalNumber=new TextView(this);
            final TextView studId = new TextView(this);
            final TextView studName = new TextView(this);
            final TextView studCityPtt = new TextView(this);
            final CheckBox checkBoxStudent=new CheckBox(this);

            studId.setText(id);
            studName.setText(name);
            studCityPtt.setText(ptt);
            ordinalNumber.setText(String.valueOf(counter));

            studentIdColumn.setGravity(Gravity.CENTER);
            studentFirstAndLastNameColumn.setGravity(Gravity.CENTER);
            studentPttColumn.setGravity(Gravity.CENTER);

            studentIdColumn.setBackground(getResources().getDrawable(R.drawable.cell_shape_first_row));
            studentFirstAndLastNameColumn.setBackground(getResources().getDrawable(R.drawable.cell_shape_first_row));
            studentPttColumn.setBackground(getResources().getDrawable(R.drawable.cell_shape_first_row));

            checkBoxStudent.setButtonDrawable(R.drawable.custom_checkbox);

            //LayoutParams for studCityPtt
            TableRow.LayoutParams paramsStudON = (TableRow.LayoutParams) studentOrdinalNumbColumn.getLayoutParams();
            //   paramsCityId.span=3;
            //  paramsCityId.column=1;
            ordinalNumber.setLayoutParams(paramsStudON);

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

            ordinalNumber.setTextSize(19);
            studId.setTextSize(19);
            studName.setTextSize(19);
            studCityPtt.setTextSize(19);
            checkBoxStudent.setTextSize(19);

            ordinalNumber.setGravity(Gravity.CENTER);
            studId.setGravity(Gravity.CENTER);
            studName.setGravity(Gravity.CENTER);
            studCityPtt.setGravity(Gravity.CENTER);


            if (counter % 2 == 0) {
                ordinalNumber.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                studId.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                studName.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                studCityPtt.setBackground(getResources().getDrawable(R.drawable.cell_shape_last_column));
            } else {
                ordinalNumber.setBackground(getResources().getDrawable(R.drawable.cell_shape_different_background));
                studId.setBackground(getResources().getDrawable(R.drawable.cell_shape_different_background));
                studName.setBackground(getResources().getDrawable(R.drawable.cell_shape_different_background));
                studCityPtt.setBackground(getResources().getDrawable(R.drawable.cell_shape_last_column_different_background));
            }
            row.addView(ordinalNumber);
            row.addView(studId);
            row.addView(studName);
            row.addView(studCityPtt);
            row.addView(checkBoxStudent);

                checkBoxStudent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView oldOrdNumb=ordinalNumber;
                        TextView oldStudId = studId;
                        TextView oldStudName = studName;
                        TextView oldStudCityPtt = studCityPtt;

                        //changing checked rows background
                        if (checkBoxStudent.isChecked()){
                            ordinalNumber.setBackground(getResources().getDrawable(R.drawable.cell_shape_picked_column));
                            studId.setBackground(getResources().getDrawable(R.drawable.cell_shape_picked_column));
                            studName.setBackground(getResources().getDrawable(R.drawable.cell_shape_picked_column));
                            studCityPtt.setBackground(getResources().getDrawable(R.drawable.cell_shape_picked_last_column));
                        } else {
                            //if it is not checked, return to previous state
                            if (Integer.parseInt(ordinalNumber.getText().toString()) % 2 == 0) {
                                oldOrdNumb.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                                oldStudId.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                                oldStudName.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                                oldStudCityPtt.setBackground(getResources().getDrawable(R.drawable.cell_shape_last_column));
                            } else {
                                oldOrdNumb.setBackground(getResources().getDrawable(R.drawable.cell_shape_different_background));
                                oldStudId.setBackground(getResources().getDrawable(R.drawable.cell_shape_different_background));
                                oldStudName.setBackground(getResources().getDrawable(R.drawable.cell_shape_different_background));
                                oldStudCityPtt.setBackground(getResources().getDrawable(R.drawable.cell_shape_last_column_different_background));
                            }
                        }
                    }
                });
            tblLayoutStudent.addView(row);
            }
        }else{
            txtNoResultStudents.setVisibility(View.VISIBLE);
            txtNoResultStudents.setText("No results for these parameters");
            txtNoResultStudents.setGravity(Gravity.CENTER);
        }

        //only way to set checkbox invisible on start
        if (btnDeleteRowStudent.isEnabled()){
            getCheckboxFromTable(View.VISIBLE);
        }else{
            getCheckboxFromTable(View.INVISIBLE);
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

    @Override
    public void deleteRowsReturnData(Object o) {
        returnDeletedData= (RSDeleteStudentRowsResponse) o;
    }

    @Override
    public void returnStudentWithInstrumentDataOnPostExecute(Object o) {
        returnStudWithInstData= (RSGetStudentWithInstrumentResponse) o;
        listOfEmployees=returnStudWithInstData.getListOfStudentsWithInstrument();
    }

    public void makeToast(String text){
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    void getCheckboxFromTable(int type){
        for (int i = 0; i < tblLayoutStudent.getChildCount(); i++) {
            //iterate through whole table
            TableRow checked = (TableRow) tblLayoutStudent.getChildAt(i);
            //take 9-th column (our checkbox)
            CheckBox c = (CheckBox) checked.getVirtualChildAt(12);
            c.setVisibility(type);
        }
    }

    public void deleteRow(View v) {
        final ArrayList<String> listOfCheckedStudents = new ArrayList<>();

        for (int i = 0; i < tblLayoutStudent.getChildCount(); i++) {

            //iterate through whole table
            TableRow checked = (TableRow) tblLayoutStudent.getChildAt(i);
            //take 9-th column (our checkbox)
            CheckBox c = (CheckBox) checked.getVirtualChildAt(12);
            //take primary key from table
            TextView studentIdPK = (TextView) checked.getVirtualChildAt(1);
            if (c.isChecked()) {
                String idPK=studentIdPK.getText().toString();
                //put all integers into list
                listOfCheckedStudents.add(idPK);
            }
        }
        boolean isFound = false;

        if (listOfCheckedStudents != null && listOfCheckedStudents.size() > 0 &&
                listOfEmployees != null && listOfEmployees.size() > 0) {
            //take list of checked students and parse them into individual Strings
            String listOfChecked = listOfCheckedStudents.toString();
            String listWithNoBrackets = listOfChecked.substring(1, listOfChecked.length() - 1);
            String[] parsedList = listWithNoBrackets.split(", ");
            //match checked student's id with id from studentWithInstrument database
            for (int i = 0; i < parsedList.length; i++) {
                for (Employee employee : listOfEmployees) {
                    if (employee.getStudent().getStudentId().equalsIgnoreCase(parsedList[i])){
                        //there is a student in database, so it cannot be deleted
                        isFound = true;
                        break;
                    }
                }
            }
        }
        if (!isFound){
            if(listOfCheckedStudents.size() > 0) {
                new AlertDialog.Builder(this)
                        .setTitle("Deleting rows")
                        .setMessage("Are u sure you want to delete selected row/rows?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                rs.deleteStudentRows(listOfCheckedStudents);
                                rs.getStudents();
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
            if(listOfCheckedStudents.size()==1)
                Toast.makeText(getApplicationContext(), "You cannot delete this row!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getApplicationContext(), "You cannot delete these rows!", Toast.LENGTH_SHORT).show();

        }

    }
}
