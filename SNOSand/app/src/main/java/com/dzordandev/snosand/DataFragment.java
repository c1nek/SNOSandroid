package com.dzordandev.snosand;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by marci on 26.01.2016.
 */
public class DataFragment extends Fragment {

    TextView producentValue, modelValue, fuelValue, mileageValue, motorValue;

    public MainActivity myActivity;

    carDetails carInfo;

    public static DataFragment newInstance() {
        DataFragment fragment = new DataFragment();
        return fragment;
    }

    public DataFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View myInflatedView = inflater.inflate(R.layout.data_layout, container,false);


        return myInflatedView;
    }




    @Override
    public void onAttach(Activity myActivity) {
        super.onAttach(myActivity);
        this.myActivity= (MainActivity) myActivity;
    }
}