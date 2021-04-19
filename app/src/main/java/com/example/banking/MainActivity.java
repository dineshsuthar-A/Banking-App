package com.example.banking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.FilterQueryProvider;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.example.banking.DBHelper;
import com.example.banking.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    @Override
    public void onResume()
    {
        super.onResume();
        listdataloader("");
    }
    public void listdataloader(String  string)
    {
        if(string==null)
        {
            string="";
        }
        final DBHelper db;
        final ListView listView = (ListView) findViewById(R.id.listView);
        final SearchView searchView = (SearchView) findViewById(R.id.searchView);
        final ArrayList<viewdata> vd = new ArrayList<>();
        int i=0;
        db =new DBHelper(this);//getting the context object
        Cursor c= db.filteredData(string);
        try
        {
            //for holding retrieve data from query and store in the form of rows

            //Move the cursor to the first row.
            if(c.moveToFirst())
            {
                do
                {
                    viewdata vtd =new viewdata();
                    vtd.accountId=Integer.parseInt(c.getString(c.getColumnIndex("accountid")));
                    vtd.customerName=c.getString(c.getColumnIndex("customername"));
                    vtd.balance=Float.parseFloat(c.getString(c.getColumnIndex("balance")));
                    vtd.date = c.getString(c.getColumnIndex("dob"));
                    vd.add(vtd);


                }while(c.moveToNext());//Move the cursor to the next row.
                listAdapter adap = new listAdapter(this,null,vd);
                listView.setAdapter(adap);
                listView.setOnItemClickListener(this);
            }
            else
            {
                Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_LONG).show();
            }

        }catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), "No data found"+e.getMessage(), Toast.LENGTH_LONG).show();
        }


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                listdataloader(newText);
                return false;
            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar mytoolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mytoolbar);

        Button transferbutton = (Button) findViewById(R.id.transactioninfo);
        transferbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startin = new Intent(getApplicationContext(),Transactioninfo.class);
                startActivity(startin);
            }
        });
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent startint = new Intent(getApplicationContext(),customerdetail.class);
        ListView list = (ListView) findViewById(R.id.listView);
        viewdata vd;
        vd = (viewdata) list.getItemAtPosition(position);
        startint.putExtra("viewdata", (Serializable)vd);
        startActivity(startint);
    }


}
