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
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.futin.tabletest.R;
import com.example.futin.tabletest.RESTService.RestService;
import com.example.futin.tabletest.RESTService.listeners.AsyncTaskReturnData;
import com.example.futin.tabletest.RESTService.listeners.ReturnInstrumentData;
import com.example.futin.tabletest.RESTService.listeners.SearchInstrumentData;
import com.example.futin.tabletest.RESTService.models.City;
import com.example.futin.tabletest.RESTService.models.Instrument;
import com.example.futin.tabletest.RESTService.response.RSGetInstrumentsResponse;
import com.example.futin.tabletest.RESTService.response.RSSearchForInstrumentResponse;
import com.example.futin.tabletest.userInterface.login.LoginAndRegistration;

import java.util.ArrayList;

public class ShowInstrumentsTableView extends ActionBarActivity implements ReturnInstrumentData, SearchInstrumentData {

    SharedPreferences sharedPreferences;
    RSGetInstrumentsResponse returnData;
    RSSearchForInstrumentResponse returnSearchedData;

    RelativeLayout instrumentTableLayout;
    ArrayList<Instrument>listOfInstruments;
    RestService rs;

    TableLayout tblLayoutInstrument;
    TableLayout tblLayoutInstrumentHeader;
    //normal
    TextView instrumentIdColumnHeader;
    TextView instrumentNameColumnHeader;
    TextView instrumentTypeColumnHeader;
    TextView instrumentInStockColumnHeader;

    //header
    TextView instrumentIdColumn;
    TextView instrumentNameColumn;
    TextView instrumentTypeColumn;
    TextView instrumentInStockColumn;

    TextView txtNoResultInstrument;
    EditText txtSearchInstrument;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_instruments_table_view);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        sharedPreferences=getSharedPreferences("employee", Context.MODE_PRIVATE);

        instrumentTableLayout= (RelativeLayout) findViewById(R.id.instrumentTableLayout);

        tblLayoutInstrument= (TableLayout) findViewById(R.id.tblLayoutInstrument);
        //normal
        instrumentIdColumn= (TextView) findViewById(R.id.instrumentIdColumn);
        instrumentNameColumn= (TextView) findViewById(R.id.instrumentNameColumn);
        instrumentTypeColumn= (TextView) findViewById(R.id.instrumentTypeColumn);
        instrumentInStockColumn= (TextView) findViewById(R.id.instrumentInStockColumn);
        //header
        tblLayoutInstrumentHeader= (TableLayout) findViewById(R.id.tblLayoutInstrumentHeader);
        instrumentIdColumnHeader= (TextView) findViewById(R.id.instrumentIdColumnHeader);
        instrumentNameColumnHeader= (TextView) findViewById(R.id.instrumentNameColumnHeader);
        instrumentTypeColumnHeader= (TextView) findViewById(R.id.instrumentTypeColumnHeader);
        instrumentInStockColumnHeader= (TextView) findViewById(R.id.instrumentInStockColumnHeader);

        //instantiate header
        instrumentIdColumnHeader.setGravity(Gravity.CENTER);
        instrumentNameColumnHeader.setGravity(Gravity.CENTER);
        instrumentTypeColumnHeader.setGravity(Gravity.CENTER);
        instrumentInStockColumnHeader.setGravity(Gravity.CENTER);

        instrumentIdColumnHeader.setBackground(getResources().getDrawable(R.drawable.cell_shape_first_row));
        instrumentNameColumnHeader.setBackground(getResources().getDrawable(R.drawable.cell_shape_first_row));
        instrumentTypeColumnHeader.setBackground(getResources().getDrawable(R.drawable.cell_shape_first_row));
        instrumentInStockColumnHeader.setBackground(getResources().getDrawable(R.drawable.cell_shape_first_row));

        txtNoResultInstrument= (TextView) findViewById(R.id.txtNoResultInstrument);
        txtSearchInstrument= (EditText) findViewById(R.id.txtSearchInstrument);

        rs=new RestService();
        rs.setReturnInstrumentData(this);
        rs.setSearchInstrumentData(this);
        rs.getInstruments();
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
        }
        return super.onOptionsItemSelected(item);
    }

    public void setTableView(){
        int counter=0;
        tblLayoutInstrument.removeAllViews();
        if(listOfInstruments != null) {
            for (Instrument inst : listOfInstruments) {
                txtNoResultInstrument.setVisibility(View.INVISIBLE);
                counter++;

                String id = String.valueOf(inst.getInstrumentId());
                String name = inst.getInstrumentName();
                String type = inst.getInstrumentType();
                String inStock = String.valueOf(inst.getInstrumentsInStock());

                TableRow row = new TableRow(this);

                TextView instId = new TextView(this);
                TextView instName = new TextView(this);
                TextView instType = new TextView(this);
                TextView instInStock = new TextView(this);

                instId.setText(id);
                instName.setText(name);
                instType.setText(type);
                instInStock.setText(inStock);

                instrumentIdColumn.setGravity(Gravity.CENTER);
                instrumentNameColumn.setGravity(Gravity.CENTER);
                instrumentTypeColumn.setGravity(Gravity.CENTER);
                instrumentInStockColumn.setGravity(Gravity.CENTER);

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

                tblLayoutInstrument.addView(row);
            }
        }else{
            txtNoResultInstrument.setVisibility(View.INVISIBLE);
            txtNoResultInstrument.setGravity(Gravity.CENTER);
            txtNoResultInstrument.setText("No result for these parameters");

        }
    }

    @Override
    public void returnInstrumentDataOnPostExecute(Object o) {
        returnData= (RSGetInstrumentsResponse) o;
        listOfInstruments=returnData.getListOfInstruments();
        setTableView();
    }

    @Override
    public void searchInstrumentReturnData(Object o) {
        returnSearchedData= (RSSearchForInstrumentResponse) o;
        listOfInstruments=returnSearchedData.getListOfSearchedInstruments();
        setTableView();
    }
}
