package com.dzordandev.snosand;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by marci on 26.01.2016.
 */
public class FragmentData extends Fragment {


    public ActivityMain myActivity;
    TextView userText, tempInText, humInText, tempOutText, pressText, deviceIDText;
    View myInflatedView;
    private SharedPreferences loginPreferences;
    private CurrentValues currentValues;
    private ProgressDialog dialog;

    public Runnable loadThings = new Runnable() {

        public void run() {
            try {
                currentValues = ParseJSON(QueryServer(loginPreferences.getString("serverToken", ""), loginPreferences.getString("serverIP", "")));
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        setFields(currentValues);

                    }
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    };

    public FragmentData() {
    }

    public static FragmentData newInstance() {
        FragmentData fragment = new FragmentData();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myInflatedView = inflater.inflate(R.layout.data_layout, container, false);


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


        loginPreferences = this.getActivity().getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);

        userText = (TextView) myInflatedView.findViewById(R.id.textView_user);
        userText.setText("Użytkownik: \n" + loginPreferences.getString("login", null));

        new Thread(loadThings).start();

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                new Thread(loadThings).start();
            }
        }, 0, 60000);

        return myInflatedView;
    }

    private String QueryServer(String token, String SERVERIP) {

        String Result = null;

        String qString = SERVERIP + "currentSensorsValues?androidToken=" + token;

        Log.i("dataHTTP", qString);

        //String qString = "http://83.21.111.47:2137/androidLogin?login=gumball300@gmail.com&password=test";

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

    private CurrentValues ParseJSON(String json) {

        CurrentValues currentVal = new CurrentValues();
        String deviceID;
        int tempInside;
        int humOutside;
        int tempOutside;
        int pressure;

        try {
            JSONObject JsonObject = new JSONObject(json);
            //deviceID = JsonObject.getString("deviceId");
            //currentVal.setDeviceID(deviceID);
            tempInside = JsonObject.getInt("tempInside");
            currentVal.setTempInside(tempInside);
            humOutside = JsonObject.getInt("humInside");
            currentVal.setHumInside(humOutside);
            tempOutside = JsonObject.getInt("tempOutside");
            currentVal.setTempOutside(tempOutside);
            pressure = JsonObject.getInt("pressure");
            currentVal.setPressure(pressure);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return currentVal;
    }

    private void setFields(CurrentValues values) {

        tempInText = (TextView) myInflatedView.findViewById(R.id.tempInsideText);
        tempOutText = (TextView) myInflatedView.findViewById(R.id.tempOutsideText);
        humInText = (TextView) myInflatedView.findViewById(R.id.humText);
        pressText = (TextView) myInflatedView.findViewById(R.id.pressureText);
        deviceIDText = (TextView) myInflatedView.findViewById(R.id.textView9);


        tempInText.setText(Integer.toString(values.tempInside) + "\u00b0C");
        humInText.setText(Integer.toString(values.humInside) + "%");
        tempOutText.setText(Integer.toString(values.tempOutside) + "\u00b0C");
        pressText.setText(Integer.toString(values.pressure) + "hPa");
        //deviceIDText.setText("Urządzenie:\n" + values.getDeviceID());

    }

    @Override
    public void onAttach(Activity myActivity) {
        super.onAttach(myActivity);
        this.myActivity = (ActivityMain) myActivity;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("variables", currentValues);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            currentValues = (CurrentValues) savedInstanceState.getSerializable("variables");
            setFields(currentValues);
        }
    }


}