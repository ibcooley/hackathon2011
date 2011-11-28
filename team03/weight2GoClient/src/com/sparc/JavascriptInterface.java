package com.sparc;

import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.Toast;

public class JavascriptInterface {
    Context mContext;

    LocationManager lm;
    MyLocationListener ll;

    /** Instantiate the interface and set the context */
    JavascriptInterface(Context c, LocationManager lm) {
        mContext = c;
        this.lm = lm;
        ll = new MyLocationListener();
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);


    }

    /** Show a toast from the web page */
    public void showToast(String toast) {

        Toast.makeText(mContext, "Longitude: " + ll.getCurrentLong() + " Latitude: " + ll.getCurrentLat(), Toast.LENGTH_SHORT).show();
    }
}