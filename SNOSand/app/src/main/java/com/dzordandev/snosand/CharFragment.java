package com.dzordandev.snosand;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by marci on 26.01.2016.
 */
public class CharFragment extends Fragment {

    public static CharFragment newInstance() {
        CharFragment fragment = new CharFragment();
        return fragment;
    }

    public CharFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.chart_layout, container, false);
        return rootView;
    }
/*
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(1);
    }
    */
}