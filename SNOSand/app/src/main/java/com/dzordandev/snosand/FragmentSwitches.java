package com.dzordandev.snosand;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.pes.androidmaterialcolorpickerdialog.ColorPicker;

/**
 * Created by marcin on 26.01.2016.
 */
public class FragmentSwitches extends Fragment {


    ColorPicker cp;
    Button okColor;
    int selectedColorR, selectedColorG, selectedColorB;
    int selectedColorRGB;

    FrameLayout rgbledLayout, led1Layout, led2Layout, led3Layout, led4Layout, switch1Layout, switch2Layout, switch3Layout;
    ImageView rgb_button, led1_button, led2_button, led3_button, led4_button, switch1_button, switch2_button, switch3_button;
    TextView led1_textView, led2_textView, led3_textView, led4_textView, switch1_textView, switch2_textView, switch3_textView;

    public static FragmentSwitches newInstance() {
        FragmentSwitches fragment = new FragmentSwitches();
        return fragment;
    }

    public FragmentSwitches() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.switches_layout, container, false);

        cp = new ColorPicker(getActivity(), 150, 150, 150);

        //TODO autoset na danych z jsona (inicjalizacja bogdan)

        led1Layout = (FrameLayout) rootView.findViewById(R.id.led1_layout);
        led1_button = (ImageView) rootView.findViewById(R.id.imageView);
        led1_button.setOnClickListener(led1ButtonOnClickListener);
        led1_textView = (TextView) rootView.findViewById(R.id.led1_status);


        led2Layout = (FrameLayout) rootView.findViewById(R.id.led2_layout);
        led2_button = (ImageView) rootView.findViewById(R.id.imageView2);
        led2_button.setOnClickListener(led2ButtonOnClickListener);
        led2_textView = (TextView) rootView.findViewById(R.id.led2_status);

        led3Layout = (FrameLayout) rootView.findViewById(R.id.frameLayout);
        led3_button = (ImageView) rootView.findViewById(R.id.imageView4);
        led3_button.setOnClickListener(led3ButtonOnClickListener);
        led3_textView = (TextView) rootView.findViewById(R.id.led3_status);

        led4Layout = (FrameLayout) rootView.findViewById(R.id.frameLayout2);
        led4_button = (ImageView) rootView.findViewById(R.id.imageView5);
        led4_button.setOnClickListener(led4ButtonOnClickListener);
        led4_textView = (TextView) rootView.findViewById(R.id.led4_status);

        switch1Layout = (FrameLayout) rootView.findViewById(R.id.frameLayout4);
        switch1_button = (ImageView) rootView.findViewById(R.id.imageView7);
        switch1_button.setOnClickListener(switch1ButtonOnClickListener);
        switch1_textView = (TextView) rootView.findViewById(R.id.switch1_status);

        switch2Layout = (FrameLayout) rootView.findViewById(R.id.frameLayout5);
        switch2_button = (ImageView) rootView.findViewById(R.id.imageView8);
        switch2_button.setOnClickListener(switch2ButtonOnClickListener);
        switch2_textView = (TextView) rootView.findViewById(R.id.switch2_status);

        switch3Layout = (FrameLayout) rootView.findViewById(R.id.frameLayout6);
        switch3_button = (ImageView) rootView.findViewById(R.id.imageView9);
        switch3_button.setOnClickListener(switch3ButtonOnClickListener);
        switch3_textView = (TextView) rootView.findViewById(R.id.switch3_status);

        rgbledLayout = (FrameLayout) rootView.findViewById(R.id.frameLayout3);
        rgb_button = (ImageView) rootView.findViewById(R.id.RGB_image);
        rgb_button.setOnClickListener(rgbButtonOnClickListener);

        return rootView;
    }

    private ImageView.OnClickListener rgbButtonOnClickListener = new ImageView.OnClickListener() {
        public void onClick(View arg0) {
            cp.show();
            okColor = (Button) cp.findViewById(R.id.okColorButton);
            okColor.setOnClickListener(ColorPickerButtonOnClickListener);
        }
    };


    private ImageView.OnClickListener led1ButtonOnClickListener = new ImageView.OnClickListener() {
        public void onClick(View arg0) {
            //TODO send request to server
            //TODO thread and loading screen

            if (led1_textView.getText() == "LED 1: OFF") {

                led1Layout.setBackgroundColor(Color.parseColor("#00A3E8"));
                led1_textView.setText("LED 1: ON");
            } else {
                led1Layout.setBackgroundColor(Color.parseColor("#A9A9A9"));
                led1_textView.setText("LED 1: OFF");
            }
        }
    };

    private ImageView.OnClickListener led2ButtonOnClickListener = new ImageView.OnClickListener() {
        public void onClick(View arg0) {
            //TODO send request to server
            //TODO thread and loading screen

            if (led2_textView.getText() == "LED 2: OFF") {

                led2Layout.setBackgroundColor(Color.parseColor("#00A3E8"));
                led2_textView.setText("LED 2: ON");
            } else {
                led2Layout.setBackgroundColor(Color.parseColor("#A9A9A9"));
                led2_textView.setText("LED 2: OFF");
            }
        }
    };

    private ImageView.OnClickListener led3ButtonOnClickListener = new ImageView.OnClickListener() {
        public void onClick(View arg0) {
            //TODO send request to server
            //TODO thread and loading screen

            if (led3_textView.getText() == "LED 3: OFF") {

                led3Layout.setBackgroundColor(Color.parseColor("#00A3E8"));
                led3_textView.setText("LED 3: ON");
            } else {
                led3Layout.setBackgroundColor(Color.parseColor("#A9A9A9"));
                led3_textView.setText("LED 3: OFF");
            }
        }
    };
    private ImageView.OnClickListener led4ButtonOnClickListener = new ImageView.OnClickListener() {
        public void onClick(View arg0) {
            //TODO send request to server
            //TODO thread and loading screen

            if (led4_textView.getText() == "LED 4: OFF") {

                led4Layout.setBackgroundColor(Color.parseColor("#00A3E8"));
                led4_textView.setText("LED 4: ON");
            } else {
                led4Layout.setBackgroundColor(Color.parseColor("#A9A9A9"));
                led4_textView.setText("LED 4: OFF");
            }
        }
    };

    private ImageView.OnClickListener switch1ButtonOnClickListener = new ImageView.OnClickListener() {
        public void onClick(View arg0) {
            //TODO send request to server
            //TODO thread and loading screen

            if (switch1_textView.getText() == "Kontakt 1: OFF") {

                switch1Layout.setBackgroundColor(Color.parseColor("#00A3E8"));
                switch1_textView.setText("Kontakt 1: ON");
            } else {
                switch1Layout.setBackgroundColor(Color.parseColor("#A9A9A9"));
                switch1_textView.setText("Kontakt 1: OFF");
            }
        }
    };

    private ImageView.OnClickListener switch2ButtonOnClickListener = new ImageView.OnClickListener() {
        public void onClick(View arg0) {
            //TODO send request to server
            //TODO thread and loading screen

            if (switch2_textView.getText() == "Kontakt 2: OFF") {

                switch2Layout.setBackgroundColor(Color.parseColor("#00A3E8"));
                switch2_textView.setText("Kontakt 2: ON");
            } else {
                switch2Layout.setBackgroundColor(Color.parseColor("#A9A9A9"));
                switch2_textView.setText("Kontakt 2: OFF");
            }
        }
    };

    private ImageView.OnClickListener switch3ButtonOnClickListener = new ImageView.OnClickListener() {
        public void onClick(View arg0) {
            //TODO send request to server
            //TODO thread and loading screen

            if (switch3_textView.getText() == "Kontakt 3: OFF") {

                switch3Layout.setBackgroundColor(Color.parseColor("#00A3E8"));
                switch3_textView.setText("Kontakt 3: ON");
            } else {
                switch3Layout.setBackgroundColor(Color.parseColor("#A9A9A9"));
                switch3_textView.setText("Kontakt 3: OFF");
            }
        }
    };

    private Button.OnClickListener ColorPickerButtonOnClickListener = new Button.OnClickListener() {

        @Override
        public void onClick(View v) {

            selectedColorR = cp.getRed();
            selectedColorG = cp.getGreen();
            selectedColorB = cp.getBlue();

            selectedColorRGB = cp.getColor();

            setRBGbuttonColor(selectedColorRGB);

            cp.dismiss();
        }
    };

    private void setRBGbuttonColor(int color) {

        rgbledLayout.setBackgroundColor(color);

    }

/*
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((ActivityMain) activity).onSectionAttached(1);
    }
    */
}