package com.dzordandev.snosand;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import android.util.Log;

import android.widget.PopupWindow;
import android.widget.Toast;

/**
 * Created by dzordanDev on 2016.
 */
public class LoginActivity  extends ActionBarActivity {

    ImageView logoImage;
    TextView login_TextView, password_TextView;
    Button login_Button;
    PopupWindow popupMessage;
    String token = "";
    String connectionString = "http://83.21.107.154:2137/androidLogin?";
    String login, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        getSupportActionBar().hide();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        logoImage = (ImageView) findViewById(R.id.imagelogo);
        login_TextView = (TextView) findViewById(R.id.editText_login);
        password_TextView = (TextView) findViewById(R.id.editText_password);
        login_Button = (Button) findViewById(R.id.button_login);

        login_Button.setOnClickListener(loginButtonOnClickListener);






    }

    private Button.OnClickListener loginButtonOnClickListener = new Button.OnClickListener() {
        public void onClick(View arg0) {
            login = login_TextView.getText().toString();
            pass = password_TextView.getText().toString();
            token = QueryServer(login, pass);

            if(token != null) {

                Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
                myIntent.putExtra("key", 0); //Optional parameters
                LoginActivity.this.startActivity(myIntent);
            }
            else
            {
                Toast.makeText(LoginActivity.this, "Niepoprawne dane logowania. Sprobój jeszcze raz.", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private String QueryServer(String login, String password) {

        String Result = null;

        //String qString = connectionString + "login=" + login + "&password=" + pass;

        String qString = "http://83.21.107.154:2137/androidLogin?login=kubarat8@wp.pl&password=test";

        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(qString);
        try {
            HttpEntity httpEntity = httpClient.execute(httpGet).getEntity();

            if (httpEntity != null){
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

}



