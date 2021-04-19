package com.example.banking;


import java.io.Serializable;

public class viewdata implements Serializable {
    int accountId;
    String customerName;
    String date;
    float balance ;

    public int getAccountId(){
        return accountId;
    }
    public String getCustomerName(){
        return customerName;
    }
    public float getBalance(){
        return balance;
    }

    public String getDate(){
        return date;
    }

    @Override
    public String toString() {
        return "viewdata{" +
                "accountId=" + accountId +
                ", customerName='" + customerName + '\'' +
                ", date='" + date + '\'' +
                ", balance=" + balance +
                '}';
    }
}
