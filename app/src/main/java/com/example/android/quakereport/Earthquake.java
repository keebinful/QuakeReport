package com.example.android.quakereport;

/**
 * Created by kirik_000 on 10/6/2016.
 */

public class Earthquake {

    private double mMagnitude;
    private String mLocation;
    private long mTimeInMilliseconds;

    /**
     * @param magnitude          is the magnitude (size) of the earthquake
     * @param location           is the city location of the earthquake
     * @param timeInMilliseconds is the time in milliseconds (from the Epoch) when the earthquake happened
     */
    public Earthquake(double magnitude, String location, long timeInMilliseconds) {
        mMagnitude = magnitude;
        mLocation = location;
        mTimeInMilliseconds = timeInMilliseconds;
    }

    // Returns mMagnitude defined in constructor
    public double getMagnitude() {
        return mMagnitude;
    }

    // Returns mLocation defined in constructor
    public String getLocation() {
        return mLocation;
    }

    // Returns mTimeInMilliseconds defined in constructor
    public long getTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }
}
