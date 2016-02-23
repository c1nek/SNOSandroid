package com.dzordandev.snosand;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.TypedArrayUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class FragmentAlarms extends Fragment {

    private ListView listView1;
    private ProgressDialog dialog;
    private SharedPreferences loginPreferences;
    View myInflatedView;

    List<RowBean> alarmLists = new ArrayList<RowBean>();


    public static FragmentAlarms newInstance() {
        FragmentAlarms fragment = new FragmentAlarms();
        return fragment;
    }

    public FragmentAlarms() {
    }

    public Runnable loadThings = new Runnable() {

        public void run() {
            try {
                alarmLists = ParseJSON(QueryServer(loginPreferences.getString("serverToken", ""), loginPreferences.getString("serverIP", "")));
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        setFields(alarmLists);

                    }
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        myInflatedView = inflater.inflate(R.layout.alarm_layout, container, false);

        loginPreferences = this.getActivity().getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);

        new Thread(loadThings).start();

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

        return myInflatedView;
    }

    private String QueryServer(String token, String SERVERIP) {

        String Result = null;

        String qString = SERVERIP + "alarms?androidToken=" + token;

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

    private List<RowBean> ParseJSON(String json) {

        int id = 1;
        Date date;
        byte type;

        try {
            JSONArray jsonAlarms = new JSONArray(json);
            for(int i = 0; i < jsonAlarms.length(); i++){
                JSONObject rec = jsonAlarms.getJSONObject(i);
                type = Byte.parseByte(rec.getString("dangerType"));
                date = new Date(rec.getLong("startTime"));
                RowBean row = new RowBean(jsonAlarms.length()-i, date, type);
                alarmLists.add(row);
                id++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return alarmLists;
    }

    private void setFields(List<RowBean> alarmList){

        RowBean[] RowBean_data = alarmList.toArray(new RowBean[alarmList.size()]);

        RowAdapter adapter = new RowAdapter(getActivity().getApplicationContext(), R.layout.row, RowBean_data);

        listView1 = (ListView) myInflatedView.findViewById(R.id.listView);
        listView1.setAdapter(adapter);

    }
}
