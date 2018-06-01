package com.firago.serg.tracktest.util;

import com.firago.serg.tracktest.domain.LocationData;

import java.util.ArrayList;
import java.util.List;

public class CoordinateConverter {
    public static Coordinate map(LocationData locationZero, LocationData locationData){
        double dLatitude = locationData.latitude - locationZero.latitude;
        double lat = (locationData.latitude + locationZero.latitude)/2.0*Math.PI/180;

        double dLongitude = locationData.longitude - locationZero.longitude;

        // from
        //https://gis.stackexchange.com/questions/75528/understanding-terms-in-length-of-degree-formula
        double perDegLat = 111132.92 - 559.82 * Math.cos( 2.0 * lat ) +
                1.175 * Math.cos( 4.0 * lat) -
                0.0023 * Math.cos(6.0 * lat);
        double perDegLon = 111412.84 * Math.cos ( lat ) -
                93.5*Math.cos(3.0* lat)+
                0.118*Math.cos(5.0*lat);

        double x = dLongitude * perDegLon;
        double y = dLatitude * perDegLat;
        return new Coordinate(x,y);
    }

    public static List<Coordinate> getTrackReverce(List<LocationData> locations) {
        List<Coordinate> list = new ArrayList<>();
        list.add(new Coordinate(0,0));
        if (locations!=null && !locations.isEmpty()){
            int lastIndex = locations.size()-1;
            LocationData last = locations.get(lastIndex);
            for (int i = lastIndex - 1; i >= 0 ; i--) {
                LocationData item = locations.get(i);
                Coordinate coordinate = map(last, item);
                list.add(coordinate);
            }
        }
        return list;
    }
    public static List<Coordinate> getTrack(List<LocationData> locations) {
        List<Coordinate> list = new ArrayList<>();
        list.add(new Coordinate(0,0));
        if (locations!=null && !locations.isEmpty()){
            int firstIndex = 0;
            LocationData first = locations.get(firstIndex);
            for (int i = firstIndex+1; i < locations.size() ; i++) {
                LocationData item = locations.get(i);
                Coordinate coordinate = map(first, item);
                list.add(coordinate);
            }
        }
        return list;
    }

    public static double getTrackLength(List<Coordinate> track){
        int total = track.size();
        Coordinate pred = track.get(0);
        double sum = 0;
        for (int i = 1; i < total; pred = track.get(i), i++ ) {
            sum += pred.length(track.get(i));
        }
        return sum;
    }
}
