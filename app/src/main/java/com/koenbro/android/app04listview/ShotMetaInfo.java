package com.koenbro.android.app04listview;

import android.text.format.Time;
import android.widget.Toast;

/**
 * Meta information for a shot
 * @author laszlo
 * @date 2/26/15.
 */
public class ShotMetaInfo {
    private String day;
    private String time;
    private Time now;
    private GPSTracker gps;
    private double latitude;
    private double longitude;

    public ShotMetaInfo() {
        now = new Time(Time.getCurrentTimezone());
        gps = new GPSTracker(ApplicationContextProvider.getContext());
    }

    private void timeStamp(){
        now.setToNow();
        day =  now.year +"-"+(now.month+1)+"-"+now.monthDay;
        time = now.format("%k:%M:%S");
    }

    private void locationStamp() {
        // check if GPS enabled
        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            // \n is for new line
            Toast.makeText(ApplicationContextProvider.getContext(), "Your Location is - \nLat: " +
                    latitude + "\nLong: " + longitude, Toast.LENGTH_SHORT).show();
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
    }

    /**
     * Obtain and return the current day
     * @return  day  formatted as y-m-d
     */
    public String getDay() {
        timeStamp(); //need to obtain fresh time
        return day;
    }

    /**
     * Obtain and return the current time
     * @return  time    formatted as h:m:s
     */
    public String getTime() {
        timeStamp(); //need to obtain fresh time
        return time;
    }

    /**
     * Obtain a fresh location and return the latitude
     * @return  double  latitude
     */
    public double getLatitude() {
        locationStamp();
        return latitude;
    }

    /**
     * Obtain a fresh location and return the longitude
     * @return  double  longitude
     */
    public double getLongitude() {
        locationStamp();
        return longitude;
    }

}
