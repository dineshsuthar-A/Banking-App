package com.example.banking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.hardware.camera2.params.BlackLevelPattern;
import android.os.Bundle;
import android.os.Debug;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.RED;

public class Transaction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        Toolbar mytoolbar = (Toolbar) findViewById(R.id.trasactiontoolbar);
        setSupportActionBar(mytoolbar);
        Intent i = getIntent();
        int a = (int) i.getSerializableExtra("id");
        ImageButton img = (ImageButton) findViewById(R.id.imgbackbtn);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),customerdetail.class);
                startActivity(in);
            }
        });
        TextView balanceshow = (TextView) findViewById(R.id.transactionbalanceTextview);
        DBHelper db = new DBHelper(this);
        final Cursor balancedata = db.getvalues(a);
        balancedata.moveToFirst();
        balanceshow.setText(balancedata.getString(balancedata.getColumnIndex("balance")));
        EditText accountno = (EditText) findViewById(R.id.transactionAccountIdTextview);
        TextView accountnoinvalid = (TextView) findViewById(R.id.transactionAccountnoInvalid);
        EditText name = (EditText) findViewById(R.id.transactionNameTextview);
        EditText amount = (EditText) findViewById(R.id.transactionAmountTextview);
        TextView amountinsuffiecientbalance = (TextView) findViewById(R.id.transactionAmountInsufficient);
        TextView alertbtn = (TextView) findViewById(R.id.transactionAlertTextview);
        alertbtn.setText("");
        accountno.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(!(accountno.getText().toString().equals(""))){
                    int accountid = Integer.parseInt(accountno.getText().toString());
                    Cursor c = db.getvalues(accountid);
                    c.moveToFirst();
                    int acount = c.getCount();
                    if (acount == 0) {
                        accountno.setTextColor(RED);
                        accountnoinvalid.setText("Account Number Invalid!");
                        name.setText("");

                    } else {
                        accountno.setTextColor(BLACK);
                        accountnoinvalid.setText("");
                        name.setText(c.getString(c.getColumnIndex("customername")));
                    }
                }
                else{
                    accountnoinvalid.setText("");
                }

            }
        });
        accountno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                return;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!(accountno.getText().toString().equals(""))){
                    int accountid = Integer.parseInt(accountno.getText().toString());
                    Cursor c = db.getvalues(accountid);
                    c.moveToFirst();
                    int acount = c.getCount();
                    if (!(acount == 0)) {
                        accountno.setTextColor(BLACK);
                        accountnoinvalid.setText("");
                    }
                }
                else{
                    accountnoinvalid.setText("");
                }


            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                    if(!(amount.getText().toString().equals(""))){
                        Float useramount = Float.parseFloat(balancedata.getString(balancedata.getColumnIndex("balance")));
                        Float amountinbox =Float.parseFloat(amount.getText().toString());
                        if (amountinbox > useramount ) {
                            amountinsuffiecientbalance.setText("Insufficient Balance!");
                            amount.setTextColor(RED);
                        } else {
                            amountinsuffiecientbalance.setText("");
                            amount.setTextColor(BLACK);

                        }
                    }
                }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Button sendmoney = (Button) findViewById(R.id.transactionsendBtn);
        sendmoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (accountno.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Enter Account Number", Toast.LENGTH_SHORT).show();
                }
                else if (name.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Enter Name", Toast.LENGTH_SHORT).show();
                }
                else if (amount.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Enter Amount ", Toast.LENGTH_SHORT).show();
                }
                else {
                    if ((Float.parseFloat(amount.getText().toString())) > Float.parseFloat(balancedata.getString(balancedata.getColumnIndex("balance")))) {
                        Toast.makeText(getApplicationContext(), "Insufficient Balance", Toast.LENGTH_SHORT).show();
                    } else {
                        Boolean result = db.transfermoney(a, Integer.parseInt(accountno.getText().toString()), Float.parseFloat(amount.getText().toString()));
                        if (result == true) {
                            Toast.makeText(getApplicationContext(), "Transaction successful", Toast.LENGTH_SHORT).show();
                            Cursor balancedat = db.getvalues(a);
                            balancedat.moveToFirst();
                            db.insert(a,Integer.parseInt(accountno.getText().toString()),Float.parseFloat(amount.getText().toString()));
                            alertbtn.setText("Rs " + (amount.getText().toString()) + " IS TRANSFERED FROM ID " + a + " TO ID " + (accountno.getText().toString()));
                            accountno.setText("");
                            name.setText("");
                            amount.setText("");
                            balanceshow.setText(balancedat.getString(balancedat.getColumnIndex("balance")));

                        } else
                            Toast.makeText(getApplicationContext(), "Transaction Failed", Toast.LENGTH_SHORT).show();
                    }
                }

            }}
            );

    }
}