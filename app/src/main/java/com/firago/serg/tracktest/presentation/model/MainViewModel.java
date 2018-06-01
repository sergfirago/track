package com.firago.serg.tracktest.presentation.model;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.firago.serg.tracktest.App;
import com.firago.serg.tracktest.domain.LocationData;
import com.firago.serg.tracktest.util.Coordinate;
import com.firago.serg.tracktest.util.CoordinateConverter;

import java.util.List;

public class MainViewModel extends ViewModel {
    public LiveData<List<LocationData>> getLocations(){
        return App.getLocations();
    }
    public LiveData<List<Coordinate>> getTrack(){
        return Transformations.map(getLocations(), new Function<List<LocationData>, List<Coordinate>>() {
            @Override
            public List<Coordinate> apply(List<LocationData> input) {
                return CoordinateConverter.getTrack(input);
            }
        });
    }
    public LiveData<Double> getLength(){
        return Transformations.map(getTrack(), new Function<List<Coordinate>, Double>() {
            @Override
            public Double apply(List<Coordinate> input) {
                return CoordinateConverter.getTrackLength(input);
            }
        });
    }
}
