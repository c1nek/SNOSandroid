package com.dzordandev.snosand;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.CheckBox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.util.Log;

import android.widget.PopupWindow;
import android.widget.Toast;

/**
 * Created by dzordanDev on 2016.
 */
public class LoginActivity  extends ActionBarActivity {


    private static String SERVERIP = "http://192.168.43.18:8080/";

    ImageView logoImage;
    TextView login_TextView, password_TextView;
    Button login_Button;
    CheckBox rememberMe;
    PopupWindow popupMessage;
    String token = "";
    String loginString = "/androidLogin?";
    String getInfoString = "/carinfo?";
    String getFuelString = "/refueling?token=";
    String login, pass;
    carDetails car_details;

    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;


    List<RowBean> listaTankowan = new ArrayList<RowBean>();

    objectToIntent toIntent;


    private Handler handler;
    private ProgressDialog dialog;

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

    private Button.OnClickListener loginButtonOnClickListener = new Button.OnClickListener() {
        public void onClick(View arg0) {
            dialog.show();

            new Thread() {
                public void run() {

                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
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


                    //TODO shared preferencef with ip, login, device, token
                    //TODO add deviceID to SP
                    //token = QueryServer(login, pass);

                    /*
                    if(token.length() <25){
                        Toast.makeText(LoginActivity.this, "Niepoprawne dane logowania. Sprobój jeszcze raz.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        token = token.replace("\n", "").replace("\r", "");
                        Log.i("token", token);

                        loginPrefsEditor.putString("token", token);

                        String json_string = QueryServer(token);
                        Log.i("json_string", json_string);

                        car_details = ParseJSON(json_string);
                        car_details.setUser(login);

                        String fuel_json_string = QueryServer(token, 1);
                        Log.i("fuel_json_string", fuel_json_string);
                        ParseJSONFuel(fuel_json_string);
                        */


                    handler.sendEmptyMessage(0);


                    runOnUiThread(new Runnable() {

                        public void run() {

                            toIntent = new objectToIntent(listaTankowan, car_details);

                            Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
                            myIntent.putExtra("carClass", toIntent); //Optional parameters
                            LoginActivity.this.startActivity(myIntent);

                        }
                    });


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

            String qString = SERVERIP + loginString + "login=" + login + "&password=" + pass;

            //String qString = "http://83.21.107.154:2137/androidLogin?login=kubarat8@wp.pl&password=test";

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

        private String QueryServer(String _token) {
            String Result = null;

            String qString = SERVERIP + getInfoString + "token=" + URLEncoder.encode(_token);

            //String qString = "http://83.21.107.154:2137/androidLogin?login=kubarat8@wp.pl&password=test";

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

        private String QueryServer(String _token, int i) {
            String Result = null;

            String qString = SERVERIP + getFuelString + URLEncoder.encode(_token);

            //String qString = "http://83.21.107.154:2137/androidLogin?login=kubarat8@wp.pl&password=test";

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

        private carDetails ParseJSON(String json) {

            String _producent = "";
            String _model = "";
            int _fuel = 0;
            int _mileage = 0;
            int _motor = 0;

            try {
                JSONObject JsonObject = new JSONObject(json);

                _producent = JsonObject.getString("mark");
                _model = JsonObject.getString("model");
                _fuel = JsonObject.getInt("fuel");
                _mileage = JsonObject.getInt("mileage");
                _motor = JsonObject.getInt("engineCapacity");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            carDetails car__details = new carDetails(_producent, _model, _fuel, _mileage, _motor);

            return car__details;
        }

        /*
        private void ParseJSONFuel(String json) {

            Date _data = null;
            float _zalitr = 0;
            int _przebiegl = 0;
            int _litorow = 0;

            try {
                JSONObject JsonObject = new JSONObject(json);
                JSONArray jsonfuel = JsonObject.getJSONArray("table");
                for (int i = 0; i < jsonfuel.length(); i++) {
                    JSONObject rec = jsonfuel.getJSONObject(i);
                    _litorow = rec.getInt("liter");
                    _zalitr = rec.getLong("price");
                    _przebiegl = rec.getInt("mileage");
                    // _data = rec.getInt("time");
                    RowBean rzadek = new RowBean(_litorow, _data, _przebiegl, _zalitr);
                    listaTankowan.add(rzadek);


                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

*/


    };
}






