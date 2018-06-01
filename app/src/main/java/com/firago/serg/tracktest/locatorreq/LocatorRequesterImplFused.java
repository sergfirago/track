package com.firago.serg.tracktest.locatorreq;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.support.annotation.NonNull;

import com.firago.serg.tracktest.LocatorRequester;
import com.firago.serg.tracktest.trackservice.TrackService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class LocatorRequesterImplFused implements LocatorRequester {
    private final FusedLocationProviderClient client;
    private final Context context;
    private PendingIntent pendingIntent;
    public LocatorRequesterImplFused(Context context) {
        this.context = context;
        client = LocationServices.getFusedLocationProviderClient(context);

    }

    @Override
    @SuppressLint("MissingPermission")
    public void startLocationUpdates() {
        pendingIntent = TrackService.getPendingIntent(context);
        LocationRequest request = getLocationRequest();
        client.requestLocationUpdates(request, pendingIntent);
    }

    @NonNull
    private LocationRequest getLocationRequest() {
        LocationRequest request = LocationRequest.create();
        request.setInterval(1000);
        request.setFastestInterval(1000);
        request.setSmallestDisplacement(10);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return request;
    }

    @Override
    public boolean isActive() {
        return pendingIntent != null;
    }
    @Override
    public void stopLocationUpdates() {
        if (isActive()) {

            client.removeLocationUpdates(pendingIntent);
            pendingIntent = null;
        }
    }
}
