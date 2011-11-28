package com.test;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

public class LocationActivity extends Activity {
	
	private double latitude;
	private double longitude;

	protected void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	protected void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		/* Use the LocationManager class to obtain GPS locations */
		LocationManager mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

		LocationListener mlocListener = new LocationListenerImpl();


		mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, mlocListener);
	}


	/* Class My Location Listener */

	public class LocationListenerImpl implements LocationListener

	{

		public void onLocationChanged(Location loc)

		{

			double lat = loc.getLatitude();
			double lon = loc.getLongitude();
			
			LocationActivity.this.setLatitude(lat);
			LocationActivity.this.setLongitude(lon);

			String text = "My current location is: " +
			"Latitude = " + lat +
			"Longitude = " + lon;

			Toast.makeText(getApplicationContext(),
                           text,
                           Toast.LENGTH_SHORT).show();
		}


		public void onProviderDisabled(String provider)

		{
			Toast.makeText(getApplicationContext(),
                           "Gps Disabled", Toast.LENGTH_SHORT ).show();

		}


		public void onProviderEnabled(String provider)

		{
            Toast.makeText(getApplicationContext(),
                           "Gps Enabled",
                           Toast.LENGTH_SHORT).show();
		}


		public void onStatusChanged(String provider, int status, Bundle extras)

		{

		}

	}// class LocationListenerImpl 

}// class LocationActivity 

