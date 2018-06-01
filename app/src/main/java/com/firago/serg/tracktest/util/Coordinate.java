package com.firago.serg.tracktest.util;

import static java.lang.Math.abs;

public class Coordinate {
    private double x;
    private double y;

    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return Double.compare(that.x, x) == 0 &&
                Double.compare(that.y, y) == 0;
    }

    public double length(Coordinate coordinate){
        double dx = abs(coordinate.x - x);
        double dy = abs(coordinate.y - y);

        return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2) );
    }

}
