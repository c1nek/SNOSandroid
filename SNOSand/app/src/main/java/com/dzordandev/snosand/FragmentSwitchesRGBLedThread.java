package com.dzordandev.snosand;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

public class FragmentSwitchesRGBLedThread extends Thread {


    String type;
    String value;
    String serverIP;
    String token;

    public FragmentSwitchesRGBLedThread(String type, String value, String serverIP, String token) {
        this.type = type;
        this.serverIP = serverIP;
        this.token = token;
        this.value = value;
    }

    public void run() {


        String qString = serverIP + "setSwitch?androidToken=" + token + "&switch=" + type + "&value=" + value;

        Log.i(type, qString);

        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(qString);
        try {
            HttpEntity httpEntity = httpClient.execute(httpGet).getEntity();

        } catch (ClientProtocolException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }

    }


}
