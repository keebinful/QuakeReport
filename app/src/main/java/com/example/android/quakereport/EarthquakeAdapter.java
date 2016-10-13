package com.example.android.quakereport;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.android.quakereport.R.id.city;
import static com.example.android.quakereport.R.id.list;

/**
 * Created by kirik_000 on 10/6/2016.
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    private static final String LOCATION_SEPARATOR = "of";

    public EarthquakeAdapter(Context context, ArrayList<Earthquake> earthquakes) {
        super(context, 0, earthquakes);
    }

    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM DD, yyyy");
        return dateFormat.format(dateObject);
    }

    private String formatTime(Date timeObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(timeObject);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        String locationOffset;
        String locationCity;
        Earthquake currentListItem = getItem(position);

        TextView magnitude = (TextView) listItemView.findViewById(R.id.magnitude);
        magnitude.setText(currentListItem.getMagnitude());

        /**
         *
         * @param locationParts splits location string into two if the string contains "of"
         * @param locationOffset is the location offset string
         * @param locationCity is the city where the earthquake occured
         */
        String fullLocation = currentListItem.getLocation();
        if (fullLocation.contains(LOCATION_SEPARATOR)) {
            String[] locationParts = fullLocation.split(LOCATION_SEPARATOR);
            locationOffset = locationParts[0] + LOCATION_SEPARATOR;
            locationCity = locationParts[1];
        } else {
            locationOffset = getContext().getString(R.string.near_the);
            locationCity = fullLocation;
        }

        // Sets offsetView text to be locationOffset string
        TextView offsetView = (TextView) listItemView.findViewById(R.id.offset);
        offsetView.setText(locationOffset);

        // Sets cityView text to be locationCity string
        TextView cityView = (TextView) listItemView.findViewById(R.id.city);
        cityView.setText(locationCity);

        Date dateObject = new Date(currentListItem.getTimeInMilliseconds());
        TextView dateView = (TextView) listItemView.findViewById(R.id.date);
        dateView.setText(formatDate(dateObject));

        Date timeObject = new Date(currentListItem.getTimeInMilliseconds());
        TextView timeView = (TextView) listItemView.findViewById(R.id.time);
        timeView.setText(formatTime(timeObject));

        return listItemView;
    }
}
