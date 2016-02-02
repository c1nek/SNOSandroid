package com.dzordandev.snosand;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.pes.androidmaterialcolorpickerdialog.ColorPicker;

/**
 * Created by marci on 26.01.2016.
 */
public class SwitchesFragment extends Fragment {



    ImageView rgb_button;
    ColorPicker cp;
    int selectedColorR, selectedColorG, selectedColorB;
    int selectedColorRGB;

    public static SwitchesFragment newInstance() {
        SwitchesFragment fragment = new SwitchesFragment();
        return fragment;
    }

    public SwitchesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.switches_layout, container, false);

        cp = new ColorPicker(getActivity(), 150, 150, 150);

        rgb_button = (ImageView) rootView.findViewById(R.id.RGB_image);
        rgb_button.setOnClickListener(rgbButtonOnClickListener);

       // producentValue = (TextView) myInflatedView.findViewById(R.id.brand);


        return rootView;
    }

    private ImageView.OnClickListener rgbButtonOnClickListener = new ImageView.OnClickListener() {
        public void onClick(View arg0) {
            cp.show();
        }
        };



    /*
    Button okColor = (Button)cp.findViewById(R.id.okColorButton);

    okColor.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            selectedColorR = cp.getRed();
            selectedColorG = cp.getGreen();
            selectedColorB = cp.getBlue();

            selectedColorRGB = cp.getColor();

            cp.dismiss();
        }
    });


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(1);
    }
    */
}