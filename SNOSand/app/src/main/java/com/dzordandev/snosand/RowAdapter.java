package com.dzordandev.snosand;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by marci on 27.01.2016.
 */
public class RowAdapter extends ArrayAdapter<RowBean> {

    Context context;
    int layoutResourceId;
    RowBean data[] = null;

    public RowAdapter(Context context, int layoutResourceId, RowBean[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RowBeanHolder holder = null;

        if(row == null)
        {
            //ayoutInflater inflater = ((Activity)context).getLayoutInflater();
            LayoutInflater inflater = LayoutInflater.from(context);
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new RowBeanHolder();
            holder.txt_data = (TextView)row.findViewById(R.id.Row_data);
            holder.txt_przebieg = (TextView)row.findViewById(R.id.row_mileage);
            holder.txt_kasa = (TextView)row.findViewById(R.id.row_pln);
            holder.txt_litorw = (TextView)row.findViewById(R.id.row_liters);

            row.setTag(holder);
        }
        else
        {
            holder = (RowBeanHolder)row.getTag();
        }

        RowBean object = data[position];

        Date date = null;

        String newstring = new SimpleDateFormat("yyyy-MM-dd").format(object.dataa);

        holder.txt_data.setText(newstring);
        holder.txt_przebieg.setText(String.valueOf(object.przebieg) + " km");
        holder.txt_kasa.setText(String.valueOf(object.koszt) + " zł");
        holder.txt_litorw.setText(String.valueOf(object.litrow) + " l");

        return row;
    }

    static class RowBeanHolder
    {
        TextView txt_data;
        TextView txt_przebieg;
        TextView txt_litorw;
        TextView txt_kasa;
    }
}