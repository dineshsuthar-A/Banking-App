package com.example.banking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class Transactioninfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactioninfo);
        Toolbar mytoolbar = (Toolbar) findViewById(R.id.transactioninfo_toolbar);
        setSupportActionBar(mytoolbar);
        final DBHelper db;
        ImageButton img = (ImageButton) findViewById(R.id.imgback);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(in);
            }
        });
        final ListView listView = (ListView) findViewById(R.id.tlistView);
        final SearchView searchView = (SearchView) findViewById(R.id.searchView);
        final ArrayList<transdata> vd = new ArrayList<>();
        db =new DBHelper(this);//getting the context object
        Cursor c= db.transgetdata();
        try
        {
            //for holding retrieve data from query and store in the form of rows

            //Move the cursor to the first row.
            if(c.moveToFirst())
            {
                do
                {
                    transdata vtd =new transdata();
                    vtd.fromid=Integer.parseInt(c.getString(c.getColumnIndex("fromid")));
                    vtd.toid=Integer.parseInt(c.getString(c.getColumnIndex("toid")));
                    vtd.amount=Float.parseFloat(c.getString(c.getColumnIndex("amount")));
                    vtd.date = c.getString(c.getColumnIndex("date"));
                    vd.add(vtd);


                }while(c.moveToNext());//Move the cursor to the next row.
                listAdapter adap = new listAdapter(this,vd,null);
                listView.setAdapter(adap);
                //listView.setOnItemClickListener(this);
            }
            else
            {
                Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_LONG).show();
            }

        }catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), "No data found"+e.getMessage(), Toast.LENGTH_LONG).show();
        }



    }


}