package com.firago.serg.tracktest.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.firago.serg.tracktest.domain.LocationData;

@Database(entities = {LocationData.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract LocationDao getDao();
}
