package com.firago.serg.tracktest.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.firago.serg.tracktest.domain.LocationData;

import java.util.List;

@Dao
public interface LocationDao {
    @Query("SELECT * FROM LocationData")
    LiveData<List<LocationData>> getAllLocations();

    @Query("DELETE FROM locationdata")
    void deleteAll();

    @Insert
    void insert(LocationData locationData);
}
