package com.dzordandev.snosand;

import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

public class FragmentSwitchesThread extends Thread {


    FrameLayout layout;
    TextView text;
    String type;
    boolean switchState;
    String serverIP;
    String token;
    public FragmentSwitchesThread(FrameLayout layout, TextView text, String type, boolean switchState, String serverIP, String token) {
        this.layout = layout;
        this.text = text;
        this.type = type;
        this.switchState = switchState;
        this.serverIP = serverIP;
        this.token = token;
    }

    public void run() {

        int state;
        if (switchState) {
            state = 1;
        } else {
            state = 0;
        }

        String qString = serverIP + "setSwitch?androidToken=" + token + "&switch=" + type + "&value=" + state;

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
