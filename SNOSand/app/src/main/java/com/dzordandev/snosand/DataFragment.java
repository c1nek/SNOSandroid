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

        carInfo = new carDetails(myActivity.getCarDetails());

        producentValue = (TextView) myInflatedView.findViewById(R.id.brand);
        producentValue.setText(carInfo.getProducent());

        modelValue = (TextView) myInflatedView.findViewById(R.id.textView11);
        modelValue.setText(carInfo.getModel());

        mileageValue = (TextView) myInflatedView.findViewById(R.id.textView9);
        mileageValue.setText(String.valueOf(carInfo.getMileage()));

        motorValue = (TextView) myInflatedView.findViewById(R.id.textView7);
        motorValue.setText(String.valueOf(carInfo.getMotor()));

        fuelValue = (TextView) myInflatedView.findViewById(R.id.fueltype);
        if(carInfo.getFuel() == 0){
            fuelValue.setText("PB");
        } else if (carInfo.getFuel() == 1){
            fuelValue.setText("ON");
        } else if (carInfo.getFuel() == 2){
            fuelValue.setText("PB+LPG");
        }   else if (carInfo.getFuel() == 3){
            fuelValue.setText("CNG");
        }







        return myInflatedView;
    }




    @Override
    public void onAttach(Activity myActivity) {
        super.onAttach(myActivity);
        this.myActivity= (MainActivity) myActivity;
    }
}