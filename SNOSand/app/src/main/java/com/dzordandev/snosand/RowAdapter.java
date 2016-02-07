package com.dzordandev.snosand;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by marci on 27.01.2016.
 */
public class RowAdapter extends ArrayAdapter<RowBean> {

    Context context;
    int layoutID;
    RowBean data[] = null;

    public RowAdapter(Context context, int layoutID, RowBean[] data) {
        super(context, layoutID, data);
        this.layoutID = layoutID;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RowBeanHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = LayoutInflater.from(context);
            row = inflater.inflate(layoutID, parent, false);

            holder = new RowBeanHolder();
            holder.txt_ID = (TextView)row.findViewById(R.id.Row_ID);
            holder.txt_date = (TextView)row.findViewById(R.id.row_Date);
            holder.txt_icon = (ImageView)row.findViewById(R.id.row_icon);

            row.setTag(holder);
        }
        else
        {
            holder = (RowBeanHolder)row.getTag();
        }

        RowBean object = data[position];

        Date date = null;

        String formattedDate = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(object.dateDate);

        holder.txt_ID.setText(String.valueOf(object.id));
        holder.txt_date.setText(formattedDate);

        switch(object.type) {
            case 0:
                holder.txt_icon.setImageResource(R.mipmap.flood_alert);
                break;
            case 1:
                holder.txt_icon.setImageResource(R.mipmap.fire_alert);
                break;
            case 2:
                holder.txt_icon.setImageResource(R.mipmap.gas_alert);
                break;
            case 3:
                holder.txt_icon.setImageResource(R.mipmap.hot_alert);
                break;
            case 4:
                holder.txt_icon.setImageResource(R.mipmap.cold_alert);
                break;
            default:
                break;
        }


        return row;
    }

    static class RowBeanHolder
    {
        TextView txt_ID;
        TextView txt_date;
        ImageView txt_icon;
    }
}