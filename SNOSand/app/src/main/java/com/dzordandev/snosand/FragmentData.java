package com.dzordandev.snosand;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by marci on 26.01.2016.
 */
public class FragmentData extends Fragment {

    TextView userText;

    public ActivityMain myActivity;

    private SharedPreferences loginPreferences;


    carDetails carInfo;

    public static FragmentData newInstance() {
        FragmentData fragment = new FragmentData();
        return fragment;
    }

    public FragmentData() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View myInflatedView = inflater.inflate(R.layout.data_layout, container, false);

        loginPreferences = this.getActivity().getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);

        userText = (TextView) myInflatedView.findViewById(R.id.textView_user);

        userText.setText("Użytkownik: \n" + loginPreferences.getString("login", null));


        return myInflatedView;
    }


    @Override
    public void onAttach(Activity myActivity) {
        super.onAttach(myActivity);
        this.myActivity = (ActivityMain) myActivity;
    }
}