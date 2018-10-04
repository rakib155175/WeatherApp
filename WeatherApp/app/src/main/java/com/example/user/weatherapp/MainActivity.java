package com.example.user.weatherapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.user.weatherapp.CurrentWeather.CurrentWeatherApi;
import com.example.user.weatherapp.CurrentWeather.RetrofitClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity implements DeviceLocationListener{
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ProgressBar progressBar;
    public static String Latitude;
    public static String Longitude;
    public static int format=1;
    private TabPagerAdapter adapter;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private String BASE_URL_ICON = "https://openweathermap.org/img/w/";

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        progressBar=findViewById(R.id.Bar);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getDeviceLocation((DeviceLocationListener) this);



        tabLayout.addTab(tabLayout.newTab().setText("Current"));
        tabLayout.addTab(tabLayout.newTab().setText("Forecast"));

        tabLayout.setTabTextColors(Color.rgb(255, 255, 255), Color.rgb(255, 0, 0));


        adapter = new TabPagerAdapter(getSupportFragmentManager());


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 111) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getDeviceLocation((DeviceLocationListener) this);
            }
        }
    }

    private void getDeviceLocation(final DeviceLocationListener listener) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 111);
                return ;
            }
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location==null){
                        return;
                    }
                    double latitude=location.getLatitude();
                    double longitude=location.getLongitude();
                    listener.onLocationReceive(latitude,longitude);
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(1);

                    viewPager.setAdapter(adapter);
                    viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

                    tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                        @Override
                        public void onTabSelected(TabLayout.Tab tab) {
                            viewPager.setCurrentItem(tab.getPosition());
                        }

                        @Override
                        public void onTabUnselected(TabLayout.Tab tab) {

                        }

                        @Override
                        public void onTabReselected(TabLayout.Tab tab) {

                        }
                    });

                    progressBar.setVisibility(View.GONE);


                }
            });
    }

    @Override
    public void onLocationReceive(double lat, double lon) {
        Latitude=String.valueOf(lat);
        Longitude=String.valueOf(lon);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.celsius:
                format=1;
                getDeviceLocation((DeviceLocationListener)this);
                return true;
            case R.id.fahrenheit:
                format=2;
                getDeviceLocation((DeviceLocationListener)this);
                return true;

                default:
                    return super.onOptionsItemSelected(item);

        }

    }
}
