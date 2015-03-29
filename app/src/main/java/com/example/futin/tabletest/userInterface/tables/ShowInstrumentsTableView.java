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
import com.example.futin.tabletest.RESTService.listeners.ReturnInstrumentData;
import com.example.futin.tabletest.RESTService.listeners.ReturnStudentWithInstrumentData;
import com.example.futin.tabletest.RESTService.listeners.SearchData;
import com.example.futin.tabletest.RESTService.models.Employee;
import com.example.futin.tabletest.RESTService.models.Instrument;
import com.example.futin.tabletest.RESTService.models.Student;
import com.example.futin.tabletest.RESTService.response.RSDeleteInstrumentResponse;
import com.example.futin.tabletest.RESTService.response.RSGetInstrumentsResponse;
import com.example.futin.tabletest.RESTService.response.RSGetStudentWithInstrumentResponse;
import com.example.futin.tabletest.RESTService.response.RSSearchForInstrumentResponse;
import com.example.futin.tabletest.userInterface.login.LoginAndRegistration;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ShowInstrumentsTableView extends ActionBarActivity implements ReturnInstrumentData,
        SearchData,ReturnStudentWithInstrumentData, DeleteRows {

    SharedPreferences sharedPreferences;
    RestService rs;
    RSGetInstrumentsResponse returnData;
    RSSearchForInstrumentResponse returnSearchedData;
    RSDeleteInstrumentResponse returnDeletedData;
    RSGetStudentWithInstrumentResponse returnStudWithInstData;

    ArrayList<Instrument>listOfInstruments;
    //used for getting studentWithInstrument
    ArrayList<Employee>listOfEmployees;

    RelativeLayout instrumentTableLayout;
    TableLayout tblLayoutInstrument;
    TextView instrumentIdColumn;
    TextView instrumentNameColumn;
    TextView instrumentTypeColumn;
    TextView instrumentInStockColumn;
    TextView txtNoResultInstrument;
    EditText txtSearchInstrument;
    Button btnDeleteRowInstrument;

    //innerClass onClickRow
    int idCounter;
    boolean deleteMode=false;
    boolean isLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_instruments_table_view);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        sharedPreferences=getSharedPreferences("employee", Context.MODE_PRIVATE);

        instrumentTableLayout= (RelativeLayout) findViewById(R.id.instrumentTableLayout);

        tblLayoutInstrument= (TableLayout) findViewById(R.id.tblLayoutInstrument);
        instrumentIdColumn= (TextView) findViewById(R.id.instrumentIdColumn);
        instrumentNameColumn= (TextView) findViewById(R.id.instrumentNameColumn);
        instrumentTypeColumn= (TextView) findViewById(R.id.instrumentTypeColumn);
        instrumentInStockColumn= (TextView) findViewById(R.id.instrumentInStockColumn);
        txtNoResultInstrument= (TextView) findViewById(R.id.txtNoResultInstrument);
        txtSearchInstrument= (EditText) findViewById(R.id.txtSearchInstrument);
        btnDeleteRowInstrument= (Button) findViewById(R.id.btnDeleteRowInstrument);

        rs=new RestService();
        rs.setReturnInstrumentData(this);
        rs.setDeleteRowsData(this);
        rs.setSearchData(this);
        rs.setReturnStudentWithInstrumentData(this);
        rs.getInstruments();
        rs.getStudentWithInstrument();

        btnDeleteRowInstrument.setEnabled(false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        txtSearchInstrument.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                rs.searchForInstrument(s.toString());
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
                                Intent loginActivity=new Intent(ShowInstrumentsTableView.this, LoginAndRegistration.class);
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
                    btnDeleteRowInstrument.setEnabled(true);
                    getCheckboxFromTable(View.VISIBLE);

                }else{
                    btnDeleteRowInstrument.setEnabled(false);
                    getCheckboxFromTable(View.INVISIBLE);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setTableView(){
        int counter=0;
        tblLayoutInstrument.removeAllViews();
        if(listOfInstruments != null) {
            for (final Instrument inst : listOfInstruments) {
                txtNoResultInstrument.setVisibility(View.INVISIBLE);
                counter++;

                final String id = String.valueOf(inst.getInstrumentId());
                String name = inst.getInstrumentName();
                String type = inst.getInstrumentType();
                String inStock = String.valueOf(inst.getInstrumentsInStock());

                final TableRow row = new TableRow(this);

                final TextView instId = new TextView(this);
                final TextView instName = new TextView(this);
                final TextView instType = new TextView(this);
                final TextView instInStock = new TextView(this);
                final CheckBox checkboxInstrument=new CheckBox(this);

                instId.setText(id);
                instName.setText(name);
                instType.setText(type);
                instInStock.setText(inStock);

                instrumentIdColumn.setGravity(Gravity.CENTER);
                instrumentNameColumn.setGravity(Gravity.CENTER);
                instrumentTypeColumn.setGravity(Gravity.CENTER);
                instrumentInStockColumn.setGravity(Gravity.CENTER);

                checkboxInstrument.setButtonDrawable(R.drawable.custom_checkbox);

                //LayoutParams for instId
                TableRow.LayoutParams paramsInstId = (TableRow.LayoutParams) instrumentIdColumn.getLayoutParams();
                //  paramsCityId.span=1;
                //  paramsCityId.column=1;
                instId.setLayoutParams(paramsInstId);

                //LayoutParams for instName
                TableRow.LayoutParams paramsInstName = (TableRow.LayoutParams) instrumentNameColumn.getLayoutParams();
                //  paramsCityId.span=3;
                //  paramsCityId.column=1;
                instName.setLayoutParams(paramsInstName);

                //LayoutParams for instType
                TableRow.LayoutParams paramsInstType = (TableRow.LayoutParams) instrumentTypeColumn.getLayoutParams();
                //   paramsCityId.span=3;
                //  paramsCityId.column=1;
                instType.setLayoutParams(paramsInstType);

                //LayoutParams for instInStock
                TableRow.LayoutParams paramsInstInStock = (TableRow.LayoutParams) instrumentInStockColumn.getLayoutParams();
                // paramsInstInStock.span=3;
                //  paramsCityId.column=1;
                instInStock.setLayoutParams(paramsInstInStock);

                instId.setTextSize(16);
                instName.setTextSize(16);
                instType.setTextSize(16);
                instInStock.setTextSize(16);
                checkboxInstrument.setTextSize(16);

                instId.setGravity(Gravity.CENTER);
                instName.setGravity(Gravity.CENTER);
                instType.setGravity(Gravity.CENTER);
                instInStock.setGravity(Gravity.CENTER);


                if (counter % 2 == 0) {
                    instId.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                    instName.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                    instType.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                    instInStock.setBackground(getResources().getDrawable(R.drawable.cell_shape_last_column));
                } else {
                    instId.setBackground(getResources().getDrawable(R.drawable.cell_shape_different_background));
                    instName.setBackground(getResources().getDrawable(R.drawable.cell_shape_different_background));
                    instType.setBackground(getResources().getDrawable(R.drawable.cell_shape_different_background));
                    instInStock.setBackground(getResources().getDrawable(R.drawable.cell_shape_last_column_different_background));
                }
                row.addView(instId);
                row.addView(instName);
                row.addView(instType);
                row.addView(instInStock);
                row.addView(checkboxInstrument);
                checkboxInstrument.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView oldInstId = instId;
                        TextView oldInstName = instName;
                        TextView oldInstType = instType;
                        TextView oldInstInStock=instInStock;


                        //changing checked rows background
                        if (checkboxInstrument.isChecked()) {
                            instId.setBackground(getResources().getDrawable(R.drawable.cell_shape_picked_column));
                            instName.setBackground(getResources().getDrawable(R.drawable.cell_shape_picked_column));
                            oldInstType.setBackground(getResources().getDrawable(R.drawable.cell_shape_picked_column));
                            instInStock.setBackground(getResources().getDrawable(R.drawable.cell_shape_picked_last_column));
                        } else {
                            //if it is not checked, return to previous state
                            if (Integer.parseInt(id) % 2 == 0) {
                                oldInstId.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                                oldInstName.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                                oldInstType.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                                oldInstInStock.setBackground(getResources().getDrawable(R.drawable.cell_shape_last_column));
                            } else {
                                oldInstId.setBackground(getResources().getDrawable(R.drawable.cell_shape_different_background));
                                oldInstName.setBackground(getResources().getDrawable(R.drawable.cell_shape_different_background));
                                oldInstType.setBackground(getResources().getDrawable(R.drawable.cell_shape_different_background));
                                oldInstInStock.setBackground(getResources().getDrawable(R.drawable.cell_shape_last_column_different_background));
                            }
                        }
                    }
                });
                tblLayoutInstrument.addView(row);
            }
        }else{
            txtNoResultInstrument.setVisibility(View.VISIBLE);
            txtNoResultInstrument.setGravity(Gravity.CENTER);
            txtNoResultInstrument.setText("No result for these parameters");
        }
        //only way to set checkbox invisible on start
        if (btnDeleteRowInstrument.isEnabled()){
            getCheckboxFromTable(View.VISIBLE);
        }else{
            getCheckboxFromTable(View.INVISIBLE);
        }
    }

    void getCheckboxFromTable(int type){
        for (int i = 0; i < tblLayoutInstrument.getChildCount(); i++) {
            //iterate through whole table
            TableRow checked = (TableRow) tblLayoutInstrument.getChildAt(i);
            //take 4-th row (our checkbox)
            CheckBox c = (CheckBox) checked.getVirtualChildAt(8);
            c.setVisibility(type);

        }
    }
    public void deleteRow(View v) {
        final ArrayList<Integer> listOfCheckedInstruments = new ArrayList<>();

        for (int i = 0; i < tblLayoutInstrument.getChildCount(); i++) {

            //iterate through whole table
            TableRow checked = (TableRow) tblLayoutInstrument.getChildAt(i);
            //take 9-th row (our checkbox) from tableLayout
            CheckBox c = (CheckBox) checked.getVirtualChildAt(8);
            //take primary key from table
            TextView instrumentId = (TextView) checked.getVirtualChildAt(0);
            if (c.isChecked()) {
                int id = Integer.parseInt(instrumentId.getText().toString());
                //put all integers into list
                listOfCheckedInstruments.add(id);
            }
        }
        boolean isFound = false;
        Log.i("testtttt2: ",listOfEmployees.toString());

        if (listOfCheckedInstruments != null && listOfCheckedInstruments.size() > 0 &&
                listOfEmployees != null && listOfEmployees.size() > 0) {
            //take list of students and parse it to get each cityPtt from the list
            String listOfChecked = listOfCheckedInstruments.toString();
            String listWithNoBrackets = listOfChecked.substring(1, listOfChecked.length() - 1);
            String[] parsedList = listWithNoBrackets.split(", ");
            //check for match instrument's id with real id from database
            for (int i = 0; i < parsedList.length; i++) {
                Log.i("check is found: ",parsedList[i]);

                for (Employee employee : listOfEmployees) {
                    Log.i("check Employee: ",""+employee.getStudent().getInstrument().getInstrumentId());

                    if (employee.getStudent().getInstrument().getInstrumentId() == Integer.parseInt(parsedList[i])) {
                        isFound = true;
                        break;
                    }
                }
            }
        }
        if (!isFound){
            if(listOfCheckedInstruments.size() > 0) {
                new AlertDialog.Builder(this)
                        .setTitle("Deleting rows")
                        .setMessage("Are u sure you want to delete selected row/rows?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                rs.deleteInstrumentRows(listOfCheckedInstruments);
                                rs.getInstruments();
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
            if(listOfCheckedInstruments.size()==1)
                Toast.makeText(getApplicationContext(), "You cannot delete this row!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getApplicationContext(), "You cannot delete these rows!", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void returnInstrumentDataOnPostExecute(Object o) {
        returnData= (RSGetInstrumentsResponse) o;
        listOfInstruments=returnData.getListOfInstruments();
        setTableView();
    }

    @Override
    public void searchData(Object o) {
        returnSearchedData= (RSSearchForInstrumentResponse) o;
        listOfInstruments=returnSearchedData.getListOfSearchedInstruments();
        setTableView();
    }

    @Override
    public void deleteRowsReturnData(Object o) {
        returnDeletedData= (RSDeleteInstrumentResponse) o;

    }

    @Override
    public void returnStudentWithInstrumentDataOnPostExecute(Object o) {
        returnStudWithInstData= (RSGetStudentWithInstrumentResponse) o;
        listOfEmployees=returnStudWithInstData.getListOfStudentsWithInstrument();
        Log.i("testtttt: ",listOfEmployees.toString());
    }
}
