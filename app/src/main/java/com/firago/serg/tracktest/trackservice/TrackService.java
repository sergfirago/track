package com.firago.serg.tracktest.trackservice;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;

import com.firago.serg.tracktest.App;
import com.firago.serg.tracktest.domain.LocationData;

import static android.location.LocationManager.KEY_LOCATION_CHANGED;


public class TrackService extends IntentService {
    private static final String ACTION_INSERT_TO_BASE = "com.firago.serg.tracktest.trackservice.action.TO_BASE";
    private final String TAG = "TrackService";
    public TrackService() {
        super("TrackService");
    }

    public static PendingIntent getPendingIntent(Context context) {
         Intent intent = new Intent(context, TrackService.class);
         intent.setAction(ACTION_INSERT_TO_BASE);
        PendingIntent pendingIntent = PendingIntent.getService(context,
                0, intent,0);

        return pendingIntent;
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_INSERT_TO_BASE.equals(action)) {
                Log.d(TAG, "onHandleIntent: start action");
                if (intent.hasExtra(KEY_LOCATION_CHANGED)){
                    // LocationManager
                    Location location = intent.getParcelableExtra(KEY_LOCATION_CHANGED);
                    insertLocation(location);
                }
            }

        }
    }

    private void insertLocation(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        App.insert(new LocationData(0, latitude, longitude));
        Log.d(TAG, "onHandleIntent: "+latitude+";"+longitude);
    }

}
