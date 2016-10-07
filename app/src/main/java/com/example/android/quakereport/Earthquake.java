package com.example.android.quakereport;

/**
 * Created by kirik_000 on 10/6/2016.
 */

public class Earthquake {

    private String mString;
    private String mString2;
    private String mString3;

    public Earthquake(String string, String string2, String string3){
        mString = string;
        mString2 = string2;
        mString3 = string3;
    }

    // Returns mString defined in constructor
    public String getMagnitude() {
        return mString;
    }

    // Returns mString2 defined in constructor
    public String getCity() {
        return mString2;
    }

    // Returns mString2 defined in constructor
    public String getDate() {
        return mString3;
    }
}
