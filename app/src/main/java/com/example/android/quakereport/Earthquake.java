package com.example.android.quakereport;

/**
 * Created by kirik_000 on 10/6/2016.
 */

public class Earthquake {

    private double mMagnitude;
    private String mLocation;
    private long mTimeInMilliseconds;
    private String mUrl;

    /**
     * @param magnitude          is the magnitude (size) of the earthquake
     * @param location           is the city location of the earthquake
     * @param timeInMilliseconds is the time in milliseconds (from the Epoch) when the earthquake happened
     */
    public Earthquake(double magnitude, String location, long timeInMilliseconds, String url) {
        mMagnitude = magnitude;
        mLocation = location;
        mTimeInMilliseconds = timeInMilliseconds;
        mUrl = url;
    }

    // Returns mMagnitude parsed from JSON response
    public double getMagnitude() {
        return mMagnitude;
    }

    // Returns mLocation parsed from JSON response
    public String getLocation() {
        return mLocation;
    }

    // Returns mTimeInMilliseconds parsed from JSON response
    public long getTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }

    // Returns mUrl parsed from JSON response
    public String getUrl() {
        return mUrl;
    }
}
