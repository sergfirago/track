package com.firago.serg.tracktest;



public interface LocatorRequester {

    void startLocationUpdates();

    boolean isActive();

    void stopLocationUpdates();
}
