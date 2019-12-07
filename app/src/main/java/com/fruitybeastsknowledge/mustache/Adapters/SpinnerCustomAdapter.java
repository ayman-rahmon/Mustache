package com.fruitybeastsknowledge.mustache.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.fruitybeastsknowledge.mustache.R;

import java.util.ArrayList;

/**
 * Created by akumanotatsujin on 8/1/17.
 */

public class SpinnerCustomAdapter extends BaseAdapter {

    Activity activity ;
    ArrayList<String> arrayList ;
    LayoutInflater inflater ;

    public SpinnerCustomAdapter(Activity activity , ArrayList<String>  arrayList){
        this.activity = activity ;
        this.arrayList  =  arrayList ;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = inflater.inflate(R.layout.spinner_row,null);

        TextView titleview = (TextView)view.findViewById(R.id.title);

        titleview.setText(arrayList.get(position));

        return view ;
    }





}
