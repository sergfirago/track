package com.firago.serg.tracktest.util;

import com.firago.serg.tracktest.domain.LocationData;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CoordinateConverterTest {

    @Test
    public void testPetersburgLongitude() {

        LocationData locationPetersburg = new LocationData(0, 60, 30.308611);
        LocationData locationLongitude001 = new LocationData(0, 60, 30.308611 + 0.001);

        Coordinate coordinate = CoordinateConverter.map(locationPetersburg, locationLongitude001);
        assertEquals(coordinate.getY(), 0, 0.01);
        assertEquals(coordinate.getX(), 55.8, 0.01);
    }
    @Test
    public void testPetersburgLatitude() {

        LocationData locationPetersburg = new LocationData(0, 60, 30.308611);
        LocationData locationLongitude001 = new LocationData(0, 60+0.001, 30.308611);

        Coordinate coordinate = CoordinateConverter.map(locationPetersburg, locationLongitude001);
        assertEquals(coordinate.getY(), 111.41, 0.01);
        assertEquals(coordinate.getX(), 0, 0.01);
    }

    @Test
    public void testEquatorLongitude() {

        LocationData location = new LocationData(0, 0, 0);
        LocationData location001 = new LocationData(0, 0, 0.001);

        Coordinate coordinate = CoordinateConverter.map(location, location001);
        assertEquals(coordinate.getY(), 0, 0.1);
        assertEquals(coordinate.getX(), 111.32, 0.1);
    }

    @Test
    public void testEquatorLatitude() {

        LocationData location = new LocationData(0, 0, 0);
        LocationData location001 = new LocationData(0, 0.001, 0);

        Coordinate coordinate = CoordinateConverter.map(location, location001);
        assertEquals(coordinate.getY(), 110.57, 0.1);
        assertEquals(coordinate.getX(), 0, 0.1);
    }
    @Test
    public void testNewOrleansLatitude() {

        LocationData location = new LocationData(0, 30, 0);
        LocationData location001 = new LocationData(0, 30 + 0.001, 0);

        Coordinate coordinate = CoordinateConverter.map(location, location001);
        assertEquals(coordinate.getY(), 110.85, 0.01);
        assertEquals(coordinate.getX(), 0, 0.01);
    }
    @Test
    public void testNewOrleansLongitude() {

        LocationData location = new LocationData(0, 30, 0);
        LocationData location001 = new LocationData(0, 30, 0.001);

        Coordinate coordinate = CoordinateConverter.map(location, location001);
        assertEquals(coordinate.getY(), 0, 0.01);
        assertEquals(coordinate.getX(), 96.49, 0.01);
    }

    @Test
    public void testTrackOneItem() {
        List<LocationData> locations = new ArrayList<>();
        LocationData location = new LocationData(0, 1, 2);
        locations.add(location);

        List<Coordinate> track = CoordinateConverter.getTrackReverce(locations);

        assertEquals(1, track.size());
    }

    @Test
    public void testTrackTwoItems() {
        List<LocationData> locations = new ArrayList<>();
        LocationData location1 = new LocationData(0, 1, 2);
        LocationData location2 = new LocationData(0, 2, 2);
        locations.add(location1);
        locations.add(location2);

        List<Coordinate> track = CoordinateConverter.getTrackReverce(locations);

        assertEquals(2, track.size());
        assertEquals(new Coordinate(0,0), track.get(0));
        assertEquals(CoordinateConverter.map(location2, location1), track.get(1));
    }
    @Test
    public void testTrackThreeItems() {
        List<LocationData> locations = new ArrayList<>();
        LocationData location1 = new LocationData(0, 1, 2);
        LocationData location2 = new LocationData(0, 2, 5);
        LocationData location3 = new LocationData(0, 1, 2);

        locations.add(location1);
        locations.add(location2);
        locations.add(location3);

        List<Coordinate> track = CoordinateConverter.getTrackReverce(locations);

        assertEquals(3, track.size());
        assertEquals(new Coordinate(0,0), track.get(2));
//        assertEquals(CoordinateConverter.map(location2, location1), track.get(1));
    }
}