package com.example.futin.tabletest.userInterface.tables;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
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
import com.example.futin.tabletest.RESTService.listeners.AsyncTaskReturnData;
import com.example.futin.tabletest.RESTService.listeners.ReturnInstrumentData;
import com.example.futin.tabletest.RESTService.models.City;
import com.example.futin.tabletest.RESTService.models.Instrument;
import com.example.futin.tabletest.RESTService.response.RSGetInstrumentsResponse;
import com.example.futin.tabletest.userInterface.login.LoginAndRegistration;

import java.util.ArrayList;

public class ShowInstrumentsTableView extends ActionBarActivity implements ReturnInstrumentData {

    SharedPreferences sharedPreferences;
    RSGetInstrumentsResponse returnData;

    RelativeLayout instrumentTableLayout;
    ArrayList<Instrument>listOfInstruments;
    TableLayout tblLayoutInstrument;
    TextView instrumentIdColumn;
    TextView instrumentNameColumn;
    TextView instrumentTypeColumn;
    TextView instrumentInStockColumn;
    int counter=0;

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

        RestService rs=new RestService();
        rs.setReturnInstrumentData(this);
        rs.getInstruments();
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
        for (Instrument inst : listOfInstruments ){
            counter++;

            String id=String.valueOf(inst.getInstrumentId());
            String name=inst.getInstrumentName();
            String type=inst.getInstrumentType();
            String inStock=String.valueOf(inst.getInstrumentsInStock());

            TableRow row=new TableRow(this);

            TextView instId=new TextView(this);
            TextView instName=new TextView(this);
            TextView instType=new TextView(this);
            TextView instInStock=new TextView(this);

            instId.setText(id);
            instName.setText(name);
            instType.setText(type);
            instInStock.setText(inStock);

            instrumentIdColumn.setGravity(Gravity.CENTER);
            instrumentNameColumn.setGravity(Gravity.CENTER);
            instrumentTypeColumn.setGravity(Gravity.CENTER);
            instrumentInStockColumn.setGravity(Gravity.CENTER);

            instrumentIdColumn.setBackground(getResources().getDrawable(R.drawable.cell_shape_first_row));
            instrumentNameColumn.setBackground(getResources().getDrawable(R.drawable.cell_shape_first_row));
            instrumentTypeColumn.setBackground(getResources().getDrawable(R.drawable.cell_shape_first_row));
            instrumentInStockColumn.setBackground(getResources().getDrawable(R.drawable.cell_shape_first_row));

            //LayoutParams for instId
            TableRow.LayoutParams paramsInstId=(TableRow.LayoutParams)instrumentIdColumn.getLayoutParams();
            //  paramsCityId.span=1;
            //  paramsCityId.column=1;
            instId.setLayoutParams(paramsInstId);

            //LayoutParams for instName
            TableRow.LayoutParams paramsInstName=(TableRow.LayoutParams)instrumentNameColumn.getLayoutParams();
            //  paramsCityId.span=3;
            //  paramsCityId.column=1;
            instName.setLayoutParams(paramsInstName);

            //LayoutParams for instType
            TableRow.LayoutParams paramsInstType=(TableRow.LayoutParams)instrumentTypeColumn.getLayoutParams();
            //   paramsCityId.span=3;
            //  paramsCityId.column=1;
            instType.setLayoutParams(paramsInstType);

            //LayoutParams for isntInStock
            TableRow.LayoutParams paramsInstInStock=(TableRow.LayoutParams)instrumentInStockColumn.getLayoutParams();
            //   paramsCityId.span=3;
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


            if(counter %2==0){
                instId.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                instName.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                instType.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                instInStock.setBackground(getResources().getDrawable(R.drawable.cell_shape_last_column));
            }else {
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
    }

    @Override
    public void returnInstrumentDataOnPostExecute(Object o) {
        returnData= (RSGetInstrumentsResponse) o;
        listOfInstruments=returnData.getListOfInstruments();
        setTableView();
        Log.i("isntrument ", listOfInstruments.toString());
    }
}
