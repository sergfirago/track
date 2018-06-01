package com.firago.serg.tracktest.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class LocationData {
@PrimaryKey(autoGenerate = true)
    public int id;
    public double latitude;
    public double longitude;

    public LocationData(int id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
