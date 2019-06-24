package com.example.anudev.miwokapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class wordAdapter extends ArrayAdapter{

    wordAdapter(Context c, ArrayList al){
        super(c,0,al);
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
          convertView = LayoutInflater.from(getContext()).inflate(R.layout.my_layout,parent,false);
        }

        word w = (word) getItem(position);

        TextView tv1 = convertView.findViewById(R.id.miwk);
        TextView tv2 = convertView.findViewById(R.id.eng);
        ImageView iv = convertView.findViewById(R.id.img);

        tv1.setText(w.getmwk());
        tv2.setText(w.geteng());

        if(w.getid()!=-1)
            iv.setImageResource(w.getid());

        else
            iv.setVisibility(View.GONE);



        return convertView;
    }
}
