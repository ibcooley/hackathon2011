package com.sparc;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

class MyLocationListener implements LocationListener {
    private double currentLat;
    private double currentLong;

    public void onLocationChanged(Location location) {
        if (location != null) {
            currentLat = location.getLatitude();
            currentLong = location.getLongitude();
            Log.d("LOCATION CHANGED", location.getLatitude() + "");
            Log.d("LOCATION CHANGED", location.getLongitude() + "");
//        Toast.makeText(MainActivity.this,
//            location.getLatitude() + "" + location.getLongitude(),
//            Toast.LENGTH_LONG).show();
        }
    }

    public void onProviderDisabled(String provider) {
    }

    public void onProviderEnabled(String provider) {
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    public double getCurrentLat() {
        return currentLat;
    }

    public double getCurrentLong() {
        return currentLong;
    }
}