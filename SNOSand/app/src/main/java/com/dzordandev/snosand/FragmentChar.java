package com.dzordandev.snosand;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

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
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FragmentChar extends Fragment {


    Button showData;
    BarChart chart;
    View rootView;
    Spinner spinnerType, spinnerPeroid;
    List<ChartDataObject> valuesList;
    private SharedPreferences loginPreferences;
    private Button.OnClickListener showDataButtonOnClickListener = new Button.OnClickListener() {
        public void onClick(View arg0) {

            valuesList = new ArrayList<ChartDataObject>(ParseJSON(QueryServer(loginPreferences.getString("serverToken", ""), spinnerType.getSelectedItemPosition(), spinnerPeroid.getSelectedItemPosition())));
            BarData data = new BarData(getXAxisValues(), getDataSet());
            chart.setData(data);
            chart.setDescription(" ");
            chart.animateXY(2000, 2000);
            chart.setDrawValueAboveBar(false);
            chart.invalidate();

        }
    };

    public FragmentChar() {
    }

    public static FragmentChar newInstance() {
        FragmentChar fragment = new FragmentChar();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.chart_layout, container, false);
        chart = (BarChart) rootView.findViewById(R.id.chart);
        loginPreferences = this.getActivity().getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);


        spinnerType = (Spinner) rootView.findViewById(R.id.spinner);
        spinnerPeroid = (Spinner) rootView.findViewById(R.id.spinner2);

        showData = (Button) rootView.findViewById(R.id.button);
        showData.setOnClickListener(showDataButtonOnClickListener);


        return rootView;
    }

    private ArrayList<IBarDataSet> getDataSet() {
        ArrayList<IBarDataSet> dataSets = null;

        int counter = 0;
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        for (ChartDataObject element : valuesList) {
            BarEntry temp = new BarEntry(element.getValue(), counter);
            valueSet1.add(temp);
            counter++;
        }

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, null);
        barDataSet1.setColor(Color.rgb(0, 163, 232));

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);

        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.clear();
        for (ChartDataObject element : valuesList) {
            String formattedDate = new SimpleDateFormat("dd.MM HH:mm").format(element.getDate());
            xAxis.add(formattedDate);
        }
        return xAxis;
    }

    private String QueryServer(String _token, int type, int peroid) {

        String Result = null;

        String qString = loginPreferences.getString("serverIP", "") + "chart?androidToken=" + URLEncoder.encode(_token) + "&period=" + peroid + "&type=" + type;
        Log.i("char req", qString);
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

    private List<ChartDataObject> ParseJSON(String json) {

        Date date;
        int val;
        List<ChartDataObject> valuesListTemp = new ArrayList<ChartDataObject>();

        try {
            JSONArray jsonChartTab = new JSONArray(json);
            for (int i = 0; i < jsonChartTab.length(); i++) {
                JSONObject jsonValObj = jsonChartTab.getJSONObject(i);
                date = new Date(jsonValObj.getLong("date"));
                val = jsonValObj.getInt("value");
                ChartDataObject temp = new ChartDataObject(date, val);
                valuesListTemp.add(temp);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return valuesListTemp;
    }
}
