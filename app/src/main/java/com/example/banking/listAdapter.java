package com.example.banking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class listAdapter extends BaseAdapter {
    Context context;
    int i=0;


    ArrayList<viewdata> vd2=null;
    ArrayList<transdata> vd1 = null;

    listAdapter(Context context, ArrayList<transdata> vd1,ArrayList<viewdata>vd2){
         this.context= context;
         this.vd1 = vd1;
         this.vd2=vd2;
    }



    @Override
    public int getCount() {
        if(vd1==null)
            return vd2.size();
        else
            return vd1.size();
    }

    @Override
    public Object getItem(int position) {
        if(vd1==null)
            return vd2.get(position);
        else
            return vd1.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.single_listitem, parent, false);
        }



        TextView idView = (TextView)convertView.findViewById(R.id.textBox1);
        TextView nameView=(TextView)convertView.findViewById(R.id.textBox2);
        TextView qtyView=(TextView)convertView.findViewById(R.id.textBox3);
        if(vd1==null) {
            idView.setText(String.valueOf(vd2.get(position).accountId));
            nameView.setText(vd2.get(position).customerName);
            qtyView.setText(String.valueOf(vd2.get(position).balance));
        }
        else {
            idView.setText(String.valueOf(vd1.get(position).fromid));
            nameView.setText(String.valueOf(vd1.get(position).toid));
            qtyView.setText(String.valueOf(vd1.get(position).amount));

        }


        return convertView;
    }



}
