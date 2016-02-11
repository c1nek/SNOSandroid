package com.dzordandev.snosand;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by marci on 26.01.2016.
 */
public class FragmentAlarms extends Fragment {

    private ListView listView1;

    public ActivityMain mineActivity;
    private ProgressDialog dialog;

    List<RowBean> alarmLists = new ArrayList<RowBean>();


    public static FragmentAlarms newInstance() {
        FragmentAlarms fragment = new FragmentAlarms();
        return fragment;
    }

    public FragmentAlarms() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View myInflatedView = inflater.inflate(R.layout.alarm_layout, container, false);

        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Pobieranie danych.");
        dialog.setCancelable(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                dialog.dismiss();
            }
        }, 1000);



        RowBean[] RowBean_data = new RowBean[]{
                new RowBean(7, new Date(1454885698), (byte) 0),
                new RowBean(6, new Date(1454885698), (byte) 1),
                new RowBean(5, new Date(1454885698), (byte) 2),
                new RowBean(4, new Date(1454885698), (byte) 3),
                new RowBean(3, new Date(1454885698), (byte) 4),
                new RowBean(2, new Date(1454885698), (byte) 0),
                new RowBean(1, new Date(1454885698), (byte) 1),
                new RowBean(2, new Date(1454885698), (byte) 0),
                new RowBean(1, new Date(1454885698), (byte) 1),
                new RowBean(2, new Date(1454885698), (byte) 0),
                new RowBean(1, new Date(1454885698), (byte) 1),
        };


        RowAdapter adapter = new RowAdapter(getActivity().getApplicationContext(), R.layout.row, RowBean_data);

        listView1 = (ListView) myInflatedView.findViewById(R.id.listView);

        listView1.setAdapter(adapter);


        return myInflatedView;
    }
}





/*
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((ActivityMain) activity).onSectionAttached(1);
    }
    */
