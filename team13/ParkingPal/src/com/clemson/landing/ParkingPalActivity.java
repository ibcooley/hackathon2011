package com.clemson.landing;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class ParkingPalActivity extends Activity {
	/** Called when the activity is first created. */
	private static Integer COUNT_FIRST=1;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		if(COUNT_FIRST == 1) {
			setCoordsSingleton();
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	public void markSpot(View v1) {
		//TODO: GEt Input from USer
		CoordsSingleton coordSingleton = CoordsSingleton.getInstance();
		coordSingleton.getCoords()[0].setName("Sample");
		coordSingleton.getCoords()[0].setAccX(5);
		coordSingleton.getCoords()[0].setAccY(2);
		coordSingleton.getCoords()[0].setAccZ(0);

	}

	public void findSpot(View v1) {
		//Make a service call
		Intent intent = new Intent(this, MapPopActivity.class); 
		startActivity(intent);
	}

	public void locateCar(View v1) {
		/*Intent intent = new Intent(this, TestCameraOverlay.class); 
		startActivity(intent);*/
		Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps" +
				"?saddr=32.900153,-79.916316&daddr=32.899655,-79.916038"));
		startActivity(intent);
	}
	
	public void locateCar2(View v1) {
		Intent intent = new Intent(this, TestCameraOverlay.class); 
		startActivity(intent);
		/*Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=32.900153,-79.916316&daddr=32.899655,-79.916038"));
		startActivity(intent);*/
	}
	
	private void setCoordsSingleton() {
		Coords coords = new Coords();
		Coords[] coordArray = new Coords[10];
		coords.setLatitude(32.899655);
		coords.setLongitude(-79.916038);
		coordArray[0]=coords;
		
		coords = new Coords();
		coords.setLatitude(32.899666);
		coords.setLongitude(-79.916021);
		coordArray[1]=coords;
		
		coords = new Coords();
		coords.setLatitude(32.899681);
		coords.setLongitude(-79.915999);
		coordArray[2]=coords;
		
		coords = new Coords();
		coords.setLatitude(32.899696);
		coords.setLongitude(-79.915975);
		coordArray[3]=coords;
		
		coords = new Coords();
		coords.setLatitude(32.899713);
		coords.setLongitude(-79.915952);
		coordArray[4]=coords;
		
		coords = new Coords();
		coords.setLatitude(32.899729);
		coords.setLongitude(-79.915929);
		coordArray[5]=coords;
		
		
		coords = new Coords();
		coords.setLatitude(32.899742);
		coords.setLongitude(-79.915916);
		coordArray[6]=coords;
		
		coords = new Coords();
		coords.setLatitude(32.899975);
		coords.setLongitude(-79.915561);
		coordArray[7]=coords;
		
		coords = new Coords();
		coords.setLatitude(32.899990);
		coords.setLongitude(-79.915527);
		coordArray[8]=coords;
		
		coords = new Coords();
		coords.setLatitude(32.900001);
		coords.setLongitude(-79.9155);
		coordArray[9]=coords;
		
		CoordsSingleton coordSingleton = CoordsSingleton.getInstance();
		coordSingleton.setCoords(coordArray);
		
		
	}
}