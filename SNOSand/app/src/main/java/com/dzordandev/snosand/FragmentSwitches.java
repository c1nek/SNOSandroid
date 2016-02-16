package com.dzordandev.snosand;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pes.androidmaterialcolorpickerdialog.ColorPicker;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created by marcin on 26.01.2016.
 */
public class FragmentSwitches extends Fragment {


    Button okColor;
    int selectedColorR, selectedColorG, selectedColorB;
    int selectedColorRGB;
    CurrentSwitches currentSwitches;
    FrameLayout rgbledLayout, led1Layout, led2Layout, led3Layout, led4Layout, switch1Layout, switch2Layout, switch3Layout;
    ImageView rgb_button, led1_button, led2_button, led3_button, led4_button, switch1_button, switch2_button, switch3_button;
    TextView led1_textView, led2_textView, led3_textView, led4_textView, switch1_textView, switch2_textView, switch3_textView;
    private ColorPicker cp;
    private SharedPreferences loginPreferences;
    public Runnable loadThings = new Runnable() {

        public void run() {
            try {
                currentSwitches = ParseJSON(QueryServer(loginPreferences.getString("serverToken", ""), loginPreferences.getString("serverIP", "")));
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        setFields(currentSwitches);

                    }
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    };
    private ProgressDialog dialog;
    private ImageView.OnClickListener led1ButtonOnClickListener = new ImageView.OnClickListener() {
        public void onClick(View arg0) {
            if (led1_textView.getText() == "LED 1: OFF") {
                new FragmentSwitchesThread(led1Layout, led1_textView, "led1", true, loginPreferences.getString("serverIP", ""), loginPreferences.getString("serverToken", "")).start();
                led1_textView.setText("LED 1: ON");
                led1Layout.setBackgroundColor(Color.parseColor("#00A3E8"));
            } else {
                new FragmentSwitchesThread(led1Layout, led1_textView, "led1", false, loginPreferences.getString("serverIP", ""), loginPreferences.getString("serverToken", "")).start();
                led1_textView.setText("LED 1: OFF");
                led1Layout.setBackgroundColor(Color.parseColor("#A9A9A9"));
            }
        }
    };
    private ImageView.OnClickListener led2ButtonOnClickListener = new ImageView.OnClickListener() {
        public void onClick(View arg0) {
            if (led2_textView.getText() == "LED 2: OFF") {
                new FragmentSwitchesThread(led2Layout, led2_textView, "led2", true, loginPreferences.getString("serverIP", ""), loginPreferences.getString("serverToken", "")).start();
                led2Layout.setBackgroundColor(Color.parseColor("#00A3E8"));
                led2_textView.setText("LED 2: ON");
            } else {
                new FragmentSwitchesThread(led2Layout, led2_textView, "led2", false, loginPreferences.getString("serverIP", ""), loginPreferences.getString("serverToken", "")).start();
                led2Layout.setBackgroundColor(Color.parseColor("#A9A9A9"));
                led2_textView.setText("LED 2: OFF");
            }
        }
    };
    private ImageView.OnClickListener led3ButtonOnClickListener = new ImageView.OnClickListener() {
        public void onClick(View arg0) {
            if (led3_textView.getText() == "LED 3: OFF") {
                new FragmentSwitchesThread(led3Layout, led3_textView, "led3", true, loginPreferences.getString("serverIP", ""), loginPreferences.getString("serverToken", "")).start();
                led3Layout.setBackgroundColor(Color.parseColor("#00A3E8"));
                led3_textView.setText("LED 3: ON");
            } else {
                new FragmentSwitchesThread(led3Layout, led3_textView, "led3", false, loginPreferences.getString("serverIP", ""), loginPreferences.getString("serverToken", "")).start();
                led3Layout.setBackgroundColor(Color.parseColor("#A9A9A9"));
                led3_textView.setText("LED 3: OFF");
            }
        }
    };
    private ImageView.OnClickListener led4ButtonOnClickListener = new ImageView.OnClickListener() {
        public void onClick(View arg0) {
            if (led4_textView.getText() == "LED 4: OFF") {
                new FragmentSwitchesThread(led4Layout, led4_textView, "led4", true, loginPreferences.getString("serverIP", ""), loginPreferences.getString("serverToken", "")).start();
                led4Layout.setBackgroundColor(Color.parseColor("#00A3E8"));
                led4_textView.setText("LED 4: ON");
            } else {
                new FragmentSwitchesThread(led4Layout, led4_textView, "led4", false, loginPreferences.getString("serverIP", ""), loginPreferences.getString("serverToken", "")).start();
                led4Layout.setBackgroundColor(Color.parseColor("#A9A9A9"));
                led4_textView.setText("LED 4: OFF");
            }
        }
    };
    private ImageView.OnClickListener switch1ButtonOnClickListener = new ImageView.OnClickListener() {
        public void onClick(View arg0) {
            if (switch1_textView.getText() == "Kontakt 1: OFF") {
                new FragmentSwitchesThread(switch1Layout, switch1_textView, "switch1", true, loginPreferences.getString("serverIP", ""), loginPreferences.getString("serverToken", "")).start();
                switch1Layout.setBackgroundColor(Color.parseColor("#00A3E8"));
                switch1_textView.setText("Kontakt 1: ON");
            } else {
                new FragmentSwitchesThread(switch1Layout, switch1_textView, "switch1", false, loginPreferences.getString("serverIP", ""), loginPreferences.getString("serverToken", "")).start();
                switch1Layout.setBackgroundColor(Color.parseColor("#A9A9A9"));
                switch1_textView.setText("Kontakt 1: OFF");
            }
        }
    };
    private ImageView.OnClickListener switch2ButtonOnClickListener = new ImageView.OnClickListener() {
        public void onClick(View arg0) {

            if (switch2_textView.getText() == "Kontakt 2: OFF") {
                new FragmentSwitchesThread(switch2Layout, switch2_textView, "switch2", true, loginPreferences.getString("serverIP", ""), loginPreferences.getString("serverToken", "")).start();
                switch2Layout.setBackgroundColor(Color.parseColor("#00A3E8"));
                switch2_textView.setText("Kontakt 2: ON");
            } else {
                new FragmentSwitchesThread(switch2Layout, switch2_textView, "switch2", false, loginPreferences.getString("serverIP", ""), loginPreferences.getString("serverToken", "")).start();
                switch2Layout.setBackgroundColor(Color.parseColor("#A9A9A9"));
                switch2_textView.setText("Kontakt 2: OFF");
            }
        }
    };
    private ImageView.OnClickListener switch3ButtonOnClickListener = new ImageView.OnClickListener() {
        public void onClick(View arg0) {
            if (switch3_textView.getText() == "Kontakt 3: OFF") {
                new FragmentSwitchesThread(switch3Layout, switch3_textView, "switch3", true, loginPreferences.getString("serverIP", ""), loginPreferences.getString("serverToken", "")).start();
                switch3Layout.setBackgroundColor(Color.parseColor("#00A3E8"));
                switch3_textView.setText("Kontakt 3: ON");
            } else {
                new FragmentSwitchesThread(switch3Layout, switch3_textView, "switch3", false, loginPreferences.getString("serverIP", ""), loginPreferences.getString("serverToken", "")).start();
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
            new FragmentSwitchesRGBLedThread("ledRGB", LeftPad(String.valueOf(selectedColorR)) + LeftPad(String.valueOf(selectedColorG)) + LeftPad(String.valueOf(selectedColorB)), loginPreferences.getString("serverIP", ""), loginPreferences.getString("serverToken", "")).start();
            setRBGbuttonColor(selectedColorRGB);
            cp.dismiss();
        }
    };
    private ImageView.OnClickListener rgbButtonOnClickListener = new ImageView.OnClickListener() {
        public void onClick(View arg0) {
            cp.show();
            okColor = (Button) cp.findViewById(R.id.okColorButton);
            okColor.setOnClickListener(ColorPickerButtonOnClickListener);
        }
    };

    public FragmentSwitches() {
    }

    public static String LeftPad(String str) {
        return String.format("%1$" + 3 + "s", str).replace(" ", "0");
    }

    public static FragmentSwitches newInstance() {
        FragmentSwitches fragment = new FragmentSwitches();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.switches_layout, container, false);

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
        }, 2000);

        loginPreferences = this.getActivity().getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);

        new Thread(loadThings).start();


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

    private CurrentSwitches ParseJSON(String json) {

        CurrentSwitches currentSwi = new CurrentSwitches();

        try {
            JSONObject JsonObject = new JSONObject(json);
            JSONObject data = JsonObject.getJSONObject("switches");
            if (data.getInt("led1") == 1) {
                currentSwi.setLed1(true);
            } else {
                currentSwi.setLed1(false);
            }
            if (data.getInt("led2") == 1) {
                currentSwi.setLed2(true);
            } else {
                currentSwi.setLed2(false);
            }
            if (data.getInt("led3") == 1) {
                currentSwi.setLed3(true);
            } else {
                currentSwi.setLed3(false);
            }
            if (data.getInt("led4") == 1) {
                currentSwi.setLed4(true);
            } else {
                currentSwi.setLed4(false);
            }
            currentSwi.setLedRGB(data.getString("ledRGB"));
            if (data.getInt("switch1") == 1) {
                currentSwi.setSwitch1(true);
            } else {
                currentSwi.setSwitch1(false);
            }
            if (data.getInt("switch2") == 1) {
                currentSwi.setSwitch2(true);
            } else {
                currentSwi.setSwitch2(false);
            }
            if (data.getInt("switch3") == 1) {
                currentSwi.setSwitch3(true);
            } else {
                currentSwi.setSwitch3(false);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return currentSwi;
    }

    private String QueryServer(String token, String SERVERIP) {

        String Result = null;

        String qString = SERVERIP + "currentSwitchesValues?androidToken=" + token;

        Log.i("dataHTTP", qString);

        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(qString);
        try {
            HttpEntity httpEntity = httpClient.execute(httpGet).getEntity();

            if (httpEntity != null) {
                InputStream inputStream = httpEntity.getContent();
                Reader in = new InputStreamReader(inputStream);
                BufferedReader bufferedreader = new BufferedReader(in);
                StringBuilder stringBuilder = new StringBuilder();

                String stringReadLine = null;

                while ((stringReadLine = bufferedreader.readLine()) != null) {
                    stringBuilder.append(stringReadLine + "\n");
                }
                Result = stringBuilder.toString();
            }

        } catch (ClientProtocolException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return Result;
    }

    private void setFields(CurrentSwitches values) {

        if(values != null){
        if (values.isLed1()) {
            led1Layout.setBackgroundColor(Color.parseColor("#00A3E8"));
            led1_textView.setText("LED 1: ON");
        } else {
            led1Layout.setBackgroundColor(Color.parseColor("#A9A9A9"));
            led1_textView.setText("LED 1: OFF");
        }
        if (values.isLed2()) {
            led2Layout.setBackgroundColor(Color.parseColor("#00A3E8"));
            led2_textView.setText("LED 2: ON");
        } else {
            led2Layout.setBackgroundColor(Color.parseColor("#A9A9A9"));
            led2_textView.setText("LED 2: OFF");
        }
        if (values.isLed3()) {
            led3Layout.setBackgroundColor(Color.parseColor("#00A3E8"));
            led3_textView.setText("LED 3: ON");
        } else {
            led3Layout.setBackgroundColor(Color.parseColor("#A9A9A9"));
            led3_textView.setText("LED 3: OFF");
        }
        if (values.isLed4()) {
            led4Layout.setBackgroundColor(Color.parseColor("#00A3E8"));
            led4_textView.setText("LED 4: ON");
        } else {
            led4Layout.setBackgroundColor(Color.parseColor("#A9A9A9"));
            led4_textView.setText("LED 4: OFF");
        }
        if (values.isSwitch1()) {
            switch1Layout.setBackgroundColor(Color.parseColor("#00A3E8"));
            switch1_textView.setText("Kontakt 1: ON");
        } else {
            switch1Layout.setBackgroundColor(Color.parseColor("#A9A9A9"));
            switch1_textView.setText("Kontakt 1: OFF");
        }
        if (values.isSwitch2()) {
            switch2Layout.setBackgroundColor(Color.parseColor("#00A3E8"));
            switch2_textView.setText("Kontakt 2: ON");
        } else {
            switch2Layout.setBackgroundColor(Color.parseColor("#A9A9A9"));
            switch2_textView.setText("Kontakt 2: OFF");
        }
        if (values.isSwitch3()) {
            switch3Layout.setBackgroundColor(Color.parseColor("#00A3E8"));
            switch3_textView.setText("Kontakt 3: ON");
        } else {
            switch3Layout.setBackgroundColor(Color.parseColor("#A9A9A9"));
            switch3_textView.setText("Kontakt 3: OFF");
        }


            selectedColorR = Integer.parseInt(values.ledRGB.substring(0, 3));
            selectedColorG = Integer.parseInt(values.ledRGB.substring(3, 6));
            selectedColorB = Integer.parseInt(values.ledRGB.substring(6, 9));

            int rgbColor = new Color().rgb(selectedColorR, selectedColorG, selectedColorB);
            setRBGbuttonColor(rgbColor);

            cp = new ColorPicker(getActivity(), selectedColorR, selectedColorG, selectedColorB);
        }
        else{
            Toast.makeText(getActivity(), "Bład komunikacji z serwerem", Toast.LENGTH_SHORT);
        }

    }

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