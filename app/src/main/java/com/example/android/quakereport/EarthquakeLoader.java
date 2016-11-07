package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by kirik_000 on 11/5/2016.
 */

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {

    private String mUrl;
    List<Earthquake> earthquakeList;

    public EarthquakeLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Earthquake> loadInBackground() {
        // TODO: Implement this method
        // Don't perform request if there are no URLs or if first URL is null
        if (mUrl == null) {
            return null;
        }
        earthquakeList = QueryUtils.fetchEarthquakeData(mUrl);
        return earthquakeList;
    }
}
