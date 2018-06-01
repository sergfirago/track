package com.firago.serg.tracktest;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.support.annotation.NonNull;

import com.firago.serg.tracktest.data.AppDatabase;
import com.firago.serg.tracktest.domain.LocationData;
import com.firago.serg.tracktest.locatorreq.LocatorRequesterImplGPS;

import java.util.List;

public class App extends Application {
    private static AppDatabase database;
    private static Application context;


    public static void insert(LocationData locationData) {
        database.getDao().insert(locationData);
    }

    public static LiveData<List<LocationData>> getLocations() {
        return database.getDao().getAllLocations();
    }
    private static LocatorRequester locatorRequester;


    @Override
    public void onCreate() {
        super.onCreate();
        database = getBase();
        locatorRequester = new LocatorRequesterImplGPS(this);
//        client = LocationServices.getFusedLocationProviderClient(this);
        context = this;

    }

    @NonNull
    private AppDatabase getBase() {
        return Room
                .databaseBuilder(this, AppDatabase.class, "locationBase")
                .allowMainThreadQueries()
                .build();
    }

    @SuppressLint("MissingPermission")
    public static void startLocationUpdates() {
        if (locatorRequester.isActive()) return;
        database.getDao().deleteAll();
        locatorRequester.startLocationUpdates();

    }
    public static void stopLocationUpdates() {
        locatorRequester.stopLocationUpdates();

    }
}
