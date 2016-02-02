package com.dzordandev.snosand;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity
        implements NavigationView.OnNavigationItemSelectedListener {



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


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent i = getIntent();
       // carInfo = new carDetails((carDetails)i.getSerializableExtra("carClass"));

        toIntent = new objectToIntent((objectToIntent)i.getSerializableExtra("carClass"));
        carInfo = toIntent.carInfo;
        listaTankowan = toIntent.listaTankowan;


        View header = LayoutInflater.from(this).inflate(R.layout.nav_header_main, null);
        navigationView.addHeaderView(header);
        login_TextView = (TextView) header.findViewById(R.id.loginTextView);
        car_TextView = (TextView) header.findViewById(R.id.carTextView);


        login_TextView.setText(carInfo.getUser());
        car_TextView.setText(carInfo.getProducent()+" " + carInfo.getModel());

        fragmentManager= getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.cointainer, DataFragment.newInstance())
                .commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        fragmentManager= getSupportFragmentManager();


        Class fragmentClass;
        switch(item.getItemId()) {
            case R.id.nav_data:
                fragmentManager.beginTransaction()
                        .replace(R.id.cointainer, DataFragment.newInstance())
                        .commit();
                break;
            case R.id.nav_fuel:
                fragmentManager.beginTransaction()
                        .replace(R.id.cointainer, FuelFragment.newInstance())
                        .commit();
                break;

            case R.id.nav_logout:
                Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);
                myIntent.putExtra("carClass", 0); //Optional parameters
                MainActivity.this.startActivity(myIntent);
                break;
            default:
                fragmentClass = DataFragment.class;
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
}
