package com.example.banking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context,"Bank.db",  null,  1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table banking(accountid numeric(16,0) primary key ,customername Text,dob date,balance numeric(12,2))");
        db.execSQL("create Table transfer( fromid numeric(16,0) , toid numeric(16,0) , amount numeric(12,2), date DATE)");
        db.execSQL("insert into banking values(100000001,'Dinesh Suthar','1999-10-09',100000.22)");
        db.execSQL("insert into banking values(100000002,'Prem Kumar','1995-08-11',1002.12)");
        db.execSQL("insert into banking values(100000003,'Shaik Akbar','2000-01-12',125671.00)");
        db.execSQL("insert into banking values(100000004,'Manish Doyal','2001-08-15',21002.12)");
        db.execSQL("insert into banking values(100000005,'Vikram Doyal','1998-09-21',10571.20)");
        db.execSQL("insert into banking values(100000006,'Ankit Dave','1978-11-11',1006.22)");
        db.execSQL("insert into banking values(100000007,'Priyanshu Sharma','1992-12-12',60670.22)");
        db.execSQL("insert into banking values(100000008,'Aniket Tiwari','1996-07-12',12100.22)");
        db.execSQL("insert into banking values(100000009,'Shiv Gupta','2001-01-01',14600.22)");
        db.execSQL("insert into banking values(100000010,'Krishna','1990-10-02',9461000.22)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("drop table  if exist inventory");

    }


   public Boolean transfermoney(int senderid,int recieverid,float amount)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("select * from banking where accountid = "+senderid,null);
        c.moveToFirst();
        if(c.getCount()==0)
            return false;
        else {
            float balance = Float.parseFloat(c.getString(c.getColumnIndex("balance")));
            balance = balance - amount;
            db.execSQL("update banking SET balance = "+ balance +" where accountid = "+senderid);
        }
        c = db.rawQuery("select * from banking where accountid = "+ recieverid,null);
        c.moveToFirst();
        if(c.getCount()==0)
            return false;
        else {
            float balance2 = Float.parseFloat(c.getString(c.getColumnIndex("balance")));
            balance2 = balance2 + amount;
            db.execSQL("update banking SET balance = "+balance2+" where accountid = "+recieverid);
            return true;
        }

    }
    public Boolean insert(int from,int to, Float amount){
        SQLiteDatabase db = this.getWritableDatabase();
        if(from ==0 && to == 0 && amount==0)
            return false;
        else {
            db.execSQL("insert into transfer values("+from+" , "+to+" , "+amount+" , CURRENT_TIMESTAMP  )");
            return true;
        }

    }

    public Cursor transgetdata()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from transfer order by date Desc ",null);
        return cursor;

    }


    public Cursor getdata()
    {

        SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("select * from banking ",null);
            return cursor;
    }
    public Cursor getvalues(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from banking where accountid = "+id,null);
        return  cursor;
    }


    public  Cursor filteredData(String searchText){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from banking where customername like '" + searchText + "%'" ,null);
        return cursor;
    }




}
