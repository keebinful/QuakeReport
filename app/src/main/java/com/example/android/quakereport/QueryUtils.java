package com.example.android.quakereport;

import android.text.TextUtils;
import android.util.Log;
import android.util.StringBuilderPrinter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static android.R.id.input;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    /**
     * Sample URL for a USGS query
     */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    // Creates URL, then makes HTTP request to it to retrieve jsonResponse. List data is parsed from the response and returned.
    public static List<Earthquake> fetchEarthquakeData(String requestUrl) {

        String jsonResponse = "";
        URL urlObject = createURL(requestUrl);

        try {
            jsonResponse = makeHttpRequest(urlObject);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Connection error.", e);
        }
        List<Earthquake> earthquakes = extractEarthquakes(jsonResponse);
        return earthquakes;
    }

    // Creates URL object from String. Returns null if URL is malformed.
    private static URL createURL(String urlString) {
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem creating URL object from String.", e);
        }
        return url;
    }

    // Makes HTTP request to inputted URL and returns a JSON response.
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // Return early if url is null.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /*milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If request is successful, read input stream and parse String jsonResponse.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Connection error code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving JSON results", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    // Convert HTTP connection's InputStream to String containing full JSON response.
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link Earthquake} objects that has been built up from
     * parsing a JSON response.
     */
    public static List<Earthquake> extractEarthquakes(String jsonResponse) {

        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        List<Earthquake> earthquakes = new ArrayList<>();

        // Try to parse the SAMPLE_URL. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            // Store JSON response in a JSON object
            JSONObject jsonRootObject = new JSONObject(jsonResponse);

            // Navigate to JSONArray with key "features"
            JSONArray featuresArray = jsonRootObject.getJSONArray("features");

            for (int i = 0; i < featuresArray.length(); i++) {
                // Store JSON object from within features array at index i
                JSONObject jsonObject = featuresArray.getJSONObject(i);

                // Store JSON object with key "properties" within object
                JSONObject propertyObject = jsonObject.getJSONObject("properties");

                // Navigate to and store magnitude, city, and time in variables
                double magnitude = propertyObject.getDouble("mag");
                String city = propertyObject.getString("place");
                long time = propertyObject.getLong("time");
                String url = propertyObject.getString("url");

                // Add stored String variables to earthquakes Array List
                earthquakes.add(new Earthquake(magnitude, city, time, url));
            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }


        // Return the list of earthquakes
        return earthquakes;
    }
}