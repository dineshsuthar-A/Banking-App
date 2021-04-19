package com.example.banking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class customerdetail extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customerdetail);
        Toolbar mytoolbar = (Toolbar) findViewById(R.id.customerdetail_toolbar);
        setSupportActionBar(mytoolbar);
        Intent i = getIntent();
        viewdata vd = (viewdata) i.getSerializableExtra("viewdata");
        ImageButton backbtn = (ImageButton) findViewById(R.id.imgback);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(in);
            }
        });

        final TextView accountno = (TextView) findViewById(R.id.detailAccountNumber);
        final TextView customername = (TextView) findViewById(R.id.detailCustomerName);
        final TextView dob = (TextView) findViewById(R.id.detailDob);
        final TextView balance = (TextView) findViewById(R.id.detailBalance);
        accountno.setText(String.valueOf(vd.accountId));
        customername.setText(vd.customerName);
        dob.setText(vd.date);
        balance.setText(String.valueOf(vd.balance));

        Button transfermoney = (Button) findViewById(R.id.transactionBtn);
        transfermoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startint = new Intent(getApplicationContext(),Transaction.class);
                startint.putExtra("id",vd.accountId);
                startActivity(startint);
            }
        });


    }
}