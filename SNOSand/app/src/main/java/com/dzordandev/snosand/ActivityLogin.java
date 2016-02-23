package com.dzordandev.snosand;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class ActivityLogin extends ActionBarActivity {


    private static String SERVERIP = "http://164.132.111.23:8080/";

    ImageView logoImage;
    TextView login_TextView, password_TextView;
    Button login_Button;
    CheckBox rememberMe;
    PopupWindow popupMessage;
    String token = "";
    String login, pass;

    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
    private Handler handler;
    private ProgressDialog dialog;
    private Button.OnClickListener loginButtonOnClickListener = new Button.OnClickListener() {
        public void onClick(View arg0) {
            dialog.show();

            new Thread() {
                public void run() {

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(login_TextView.getWindowToken(), 0);

                    login = login_TextView.getText().toString();
                    Log.i("login", login);
                    pass = password_TextView.getText().toString();
                    Log.i("pass", pass);

                    loginPrefsEditor.putString("serverIP", SERVERIP);

                    if (rememberMe.isChecked()) {
                        loginPrefsEditor.putBoolean("saveLogin", true);
                        loginPrefsEditor.putString("login", login);
                        loginPrefsEditor.putString("password", pass);
                        loginPrefsEditor.commit();
                    } else {
                        loginPrefsEditor.clear();
                        loginPrefsEditor.commit();
                    }

                    token = QueryServer(login, pass);

                    if (token != null) {


                        if (token.length() < 25) {
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(ActivityLogin.this, "Niepoprawne dane logowania. Sprobój jeszcze raz.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            token = token.replace("\n", "").replace("\r", "");

                            loginPrefsEditor.putString("serverToken", token);
                            loginPrefsEditor.commit();

                            Log.i("token", token);
                            runOnUiThread(new Runnable() {

                                public void run() {

                                    Intent myIntent = new Intent(ActivityLogin.this, ActivityMain.class);
                                    ActivityLogin.this.startActivity(myIntent);

                                }
                            });

                        }
                        handler.sendEmptyMessage(0);
                    }

                }
            }.start();

            handler = new Handler() {
                public void handleMessage(android.os.Message msg) {
                    dialog.dismiss();
                }

                ;
            };

        }


        private String QueryServer(String login, String password) {


            String Result = null;

            String qString = SERVERIP + "androidLogin?login=" + login + "&password=" + pass;

            HttpClient httpClient = new DefaultHttpClient();
            HttpParams params = httpClient.getParams();
            HttpConnectionParams.setConnectionTimeout(params, 3000);
            HttpConnectionParams.setSoTimeout(params, 3000);
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
                } else {
                    Toast.makeText(ActivityLogin.this, "Brak połączenia z serwerem.", Toast.LENGTH_SHORT).show();
                    return null;
                }

            } catch (ClientProtocolException e) {

                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
            }
            return Result;
        }
    };

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
        rememberMe = (CheckBox) findViewById(R.id.checkBox);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            login_TextView.setText(loginPreferences.getString("login", ""));
            password_TextView.setText(loginPreferences.getString("password", ""));
            rememberMe.setChecked(true);
        }

        login_Button = (Button) findViewById(R.id.button_login);

        login_Button.setOnClickListener(loginButtonOnClickListener);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Proszę czekać.");
        dialog.setCancelable(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

    }
}






