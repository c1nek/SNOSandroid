package com.dzordandev.snosand;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.ArrayList;
import java.util.List;

public class ActivityMain extends ActionBarActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    //////////////gcm////////////////////
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "ActivityMain";

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private ProgressBar mRegistrationProgressBar;
    private TextView mInformationTextView;

    //////////////gcm////////////////////

    carDetails carInfo;

    TextView login_TextView, car_TextView;

    FragmentManager fragmentManager;

    List<RowBean> listaTankowan = new ArrayList<RowBean>();

    objectToIntent toIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        //mRegistrationProgressBar = (ProgressBar) findViewById(R.id.registrationProgressBar);
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // mRegistrationProgressBar.setVisibility(ProgressBar.GONE);
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                if (sentToken) {
                    // mInformationTextView.setText(getString(R.string.gcm_send_message));
                } else {
                    // mInformationTextView.setText(getString(R.string.token_error_message));
                }
            }
        };
        // mInformationTextView = (TextView) findViewById(R.id.informationTextView);

        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent i = getIntent();


//        toIntent = new objectToIntent((objectToIntent)i.getSerializableExtra("carClass"));
        //      carInfo = toIntent.carInfo;
        //      listaTankowan = toIntent.listaTankowan;


        View header = LayoutInflater.from(this).inflate(R.layout.nav_header_main, null);
        navigationView.addHeaderView(header);
        //       login_TextView = (TextView) header.findViewById(R.id.loginTextView);
        //       car_TextView = (TextView) header.findViewById(R.id.carTextView);


//        login_TextView.setText(carInfo.getUser());
        //       car_TextView.setText(carInfo.getProducent()+" " + carInfo.getModel());

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.cointainer, FragmentData.newInstance())
                .commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            moveTaskToBack(true);
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        fragmentManager = getSupportFragmentManager();


        Class fragmentClass;
        switch (item.getItemId()) {
            case R.id.nav_data:
                fragmentManager.beginTransaction()
                        .replace(R.id.cointainer, FragmentData.newInstance())
                        .commit();
                break;
            case R.id.nav_switches:
                fragmentManager.beginTransaction()
                        .replace(R.id.cointainer, FragmentSwitches.newInstance())
                        .commit();
                break;
            case R.id.nav_charts:
                fragmentManager.beginTransaction()
                        .replace(R.id.cointainer, FragmentChar.newInstance())
                        .commit();
                break;
            case R.id.nav_alarms:
                fragmentManager.beginTransaction()
                        .replace(R.id.cointainer, FragmentAlarms.newInstance())
                        .commit();
                break;

            case R.id.nav_logout:
                Intent myIntent = new Intent(ActivityMain.this, ActivityLogin.class);
                myIntent.putExtra("carClass", 0); //Optional parameters
                ActivityMain.this.startActivity(myIntent);
                break;
            default:
                fragmentClass = FragmentData.class;
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public carDetails getCarDetails() {
        return carInfo;
    }

    public objectToIntent getTankowania() {
        return toIntent;
    }

    ////////////////gcm//////////////////
    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }


    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }


}
