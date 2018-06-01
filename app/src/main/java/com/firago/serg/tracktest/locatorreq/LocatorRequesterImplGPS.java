package com.firago.serg.tracktest.locatorreq;


import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.location.LocationManager;
import android.util.Log;

import com.firago.serg.tracktest.LocatorRequester;
import com.firago.serg.tracktest.trackservice.TrackService;

public class LocatorRequesterImplGPS implements LocatorRequester {
    private static final String TAG = "LocatorGps";
    private Context context;

    private PendingIntent pendingIntent;
    private LocationManager manager;
    public LocatorRequesterImplGPS(Context context) {
        this.context = context;

    }

    @SuppressLint("MissingPermission")
    @Override
    public void startLocationUpdates() {
        if(isActive()) return;
        if (manager==null) {
            manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        }
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            Log.d(TAG, "startLocationUpdates: GPS disabled");
        }
        pendingIntent = TrackService.getPendingIntent(context);
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                0,0, pendingIntent);
    }

    @Override
    public boolean isActive() {
        return pendingIntent!=null;
    }

    @Override
    public void stopLocationUpdates() {
        if (isActive()) {
            manager.removeUpdates(pendingIntent);
            pendingIntent = null;
        }
    }
}
