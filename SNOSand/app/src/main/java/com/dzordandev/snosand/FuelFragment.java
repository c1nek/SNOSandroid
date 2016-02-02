package com.dzordandev.snosand;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by marci on 26.01.2016.
 */
public class FuelFragment extends Fragment {

    private ListView listView1;
    private ArrayAdapter<String> adapter ;

    public MainActivity mineActivity;

    objectToIntent tankowania;
    List<RowBean> listaTankowan = new ArrayList<RowBean>();



    public static FuelFragment newInstance() {
        FuelFragment fragment = new FuelFragment();
        return fragment;
    }

    public FuelFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View myInflatedView = inflater.inflate(R.layout.fuel_layout, container,false);




        tankowania = new objectToIntent(mineActivity.getTankowania());
        listaTankowan = tankowania.listaTankowan;


        //SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");


        RowBean[] RowBean_data = listaTankowan.toArray(new RowBean[listaTankowan.size()]);




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
        ((MainActivity) activity).onSectionAttached(1);
    }
    */
