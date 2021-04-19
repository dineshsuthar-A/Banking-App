package com.example.banking;

import java.io.Serializable;

public class transdata implements Serializable {


    int fromid,toid;
    float amount;
    String date;
    public int getFromid() {
        return fromid;
    }
    public int getToid(){
        return toid;
    }
    public float getAmount(){
        return amount;
    }
    public String getDate()
    {
        return date;
    }

    @Override
    public String toString() {
        return "transdata{" +
                "fromid=" + fromid +
                ", toid=" + toid +
                ", amount=" + amount +
                ", date='" + date + '\'' +
                '}';
    }
}
