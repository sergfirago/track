package com.firago.serg.tracktest.presentation.ui;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firago.serg.tracktest.App;
import com.firago.serg.tracktest.R;
import com.firago.serg.tracktest.domain.LocationData;
import com.firago.serg.tracktest.presentation.model.MainViewModel;
import com.firago.serg.tracktest.util.Coordinate;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private MainViewModel model;
    private TrackDrawer trackDrawer;
    private TextView tvLength;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        model = ViewModelProviders.of(this).get(MainViewModel.class);
        requestLocationPermission();
        Button btStart = findViewById(R.id.btStart);
        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.startLocationUpdates();
            }
        });
        Button btStop = findViewById(R.id.btStop);
        btStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.stopLocationUpdates();
            }
        });
        trackDrawer = findViewById(R.id.trackDrawer);
        tvLength = findViewById(R.id.tvLength);

        observeLocations();
    }

    private void observeLocations() {
        final TextView tvMessage = findViewById(R.id.tvMessage);
        model.getLocations().observe(this, new Observer<List<LocationData>>() {
            @Override
            public void onChanged(@Nullable List<LocationData> locations) {
                if (locations==null || locations.isEmpty()){
                    tvMessage.setText(R.string.main_no_location_data);
                }else {
                    LocationData locationData = locations.get(locations.size() - 1);
                    tvMessage.setText(
                            getString(R.string.main_location, locationData.latitude,
                                    locationData.longitude));
                }
            }
        });
        model.getTrack().observe(this, new Observer<List<Coordinate>>() {
            @Override
            public void onChanged(@Nullable List<Coordinate> coordinates) {
                trackDrawer.setData(coordinates);
            }
        });
        model.getLength().observe(this, new Observer<Double>() {

            @Override
            public void onChanged(@Nullable Double aDouble) {
                int length = 0;
                if (aDouble!=null) length = aDouble.intValue();
                tvLength.setText(getString(R.string.main_length, length));
            }
        });

    }

    private void requestLocationPermission() {
        Log.d(TAG, "startSendingLocation: start");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                //todo no permission

            }
        }
        Log.d(TAG, "startSendingLocation: request");
    }


}
