package com.test;

import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.test.LocationActivity.LocationListenerImpl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class AndroidAngler4Activity extends MapActivity {

	private final int GROUP_DEFAULT = 0;
	
	private final int MENU_CONDITIONS = 1;
	private final int MENU_SPOT = 2;
	private final int MENU_VIEW_SPOTS = 3;
	private final int MENU_VIEW_CATCHES = 4;
	
	private static final int ACTIVITY_CONDITIONS=0;
    private static final int ACTIVITY_SPOT=1;
    private static final int ACTIVITY_LOCATIONS=2;
    private static final int ACTIVITY_CATCH_FISH=3;
    private static final int ACTIVITY_LIST_SPOTS=4;
    private static final int ACTIVITY_LIST_CATCHES=5;

    private static final int CONDITIONS_ID = Menu.FIRST;
    private static final int NEW_SPOT_ID = Menu.FIRST + 1;
    private static final int LIST_SPOTS = Menu.FIRST + 2;
    private static final int LIST_CATCHES = Menu.FIRST + 3;
    
    private double latitude;
	private double longitude;
	
	//private CaughtFishItemizedOverlay itemizedoverlay;
	private List<Overlay> mapOverlays;
	
	AnglerDbAdapter mDbHelper;
	Spinner mSpotSpinner;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mDbHelper = new AnglerDbAdapter(this);
        mDbHelper.open();

        mSpotSpinner = (Spinner) findViewById(R.id.main_todaysspot);
        
        Button catchFishButton = (Button) findViewById(R.id.main_fishcaught);
        catchFishButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	fishCaught();
            }
        });
        
        MapView mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        
        List<Overlay> mapOverlays = mapView.getOverlays();
        Drawable drawable = this.getResources().getDrawable(R.drawable.icon);
        //CaughtFishItemizedOverlay itemizedoverlay = new CaughtFishItemizedOverlay(drawable,this);
        //CaughtFishItemizedOverlay itemizedoverlay = new CaughtFishItemizedOverlay(drawable);
        
        
        //GeoPoint point = new GeoPoint(19240000,-99120000);
        //OverlayItem overlayitem = new OverlayItem(point, "Hola, Mundo!", "I'm in Mexico City!");
        //itemizedoverlay.addOverlay(overlayitem);
        //mapOverlays.add(itemizedoverlay);
        //addOverlayItem(overlayitem);
        
        fillData();
        
        /* Use the LocationManager class to obtain GPS locations */ 
		LocationManager mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		LocationListener mlocListener = new LocationListenerImpl();
		mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, mlocListener);
    }
    
    private void fishCaught() {
    	Intent i = new Intent(this, CaughtFishActivity.class);
        i.putExtra("latitude", this.latitude);
        i.putExtra("longitude", this.longitude);
        startActivityForResult(i, ACTIVITY_CATCH_FISH);
    }
    
    private void addOverlayItem(OverlayItem item) {
 //   	itemizedoverlay.addOverlay(item);
 //   	mapOverlays.add(itemizedoverlay);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        // Do something after catch fish        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	menu.add(GROUP_DEFAULT, MENU_CONDITIONS, 0, "Edit Conditions");
    	menu.add(GROUP_DEFAULT, MENU_SPOT, 1, "Add Fishing Spot");
    	menu.add(GROUP_DEFAULT, MENU_VIEW_SPOTS, 2, "View Spots");
    	//menu.add(GROUP_DEFAULT, MENU_VIEW_CATCHES, 3, "View Catches");
    	return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()) {
    	case MENU_CONDITIONS:
    		editConditions();
    		return true;
    	case MENU_SPOT:
    		newSpot();
    		return true;
    	case MENU_VIEW_SPOTS:
    		listSpots();
    		return true;
    	case MENU_VIEW_CATCHES:
    		listCatches();
    		return true;
		}
    	
    	return super.onOptionsItemSelected(item);
    }
    
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch(item.getItemId()) {
            case CONDITIONS_ID:
                editConditions();
                return true;
            case NEW_SPOT_ID:
                newSpot();
                return true;
            case LIST_SPOTS:
                listSpots();
                return true;
            case LIST_CATCHES:
                listCatches();
                return true;
        }

        return super.onMenuItemSelected(featureId, item);
    }
    
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
    
	private void listSpots() {
		Intent i = new Intent(this, FishingSpotsListActivity.class);
        startActivityForResult(i, ACTIVITY_LIST_SPOTS);
	}
	
	private void listCatches() {
		Intent i = new Intent(this, FishingCatchesListActivity.class);
        startActivityForResult(i, ACTIVITY_LIST_CATCHES);
	}
	
    private void editConditions() {
        Intent i = new Intent(this, FishingConditionsEditActivity.class);
        startActivityForResult(i, ACTIVITY_CONDITIONS);
    }
    
    private void newSpot() {
        Intent i = new Intent(this, FishingSpotEditActivity.class);
        startActivityForResult(i, ACTIVITY_SPOT);
    }
    
    private void fillData() {
        // Get all of the rows from the database and create the item list
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        mSpotSpinner.setAdapter(adapter);

        Cursor fishingSpotsCursor = mDbHelper.fetchAllFishingSpots();
        startManagingCursor(fishingSpotsCursor);
        
        int spotNameIndex = fishingSpotsCursor.getColumnIndexOrThrow(AnglerDbAdapter.SPOT_NAME);
        if(fishingSpotsCursor.moveToFirst()){
            do{
                adapter.add(fishingSpotsCursor.getString(spotNameIndex));
            } while(fishingSpotsCursor.moveToNext());
        }
    }
    
    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
    
    public class LocationListenerImpl implements LocationListener

	{

		public void onLocationChanged(Location loc)

		{

			double lat = loc.getLatitude();
			double lon = loc.getLongitude();
			
			AndroidAngler4Activity.this.setLatitude(lat);
			AndroidAngler4Activity.this.setLongitude(lon);

//			String text = "My current location is: " +
//			"Latitude = " + lat +
//			"Longitude = " + lon;

//			Toast.makeText(getApplicationContext(),
//                           text,
//                           Toast.LENGTH_SHORT).show();
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
}