package com.clemson.landing;

import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MapPopActivity extends MapActivity {
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		MapView mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		List<Overlay> mapOverlays = mapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(R.drawable.androidmarker);
		MapItemizedOverlay itemizedoverlay = new MapItemizedOverlay(drawable,
				this);
		CoordsSingleton coords = CoordsSingleton.getInstance();
		Coords[] coordArray = coords.getCoords();
		GeoPoint point = null;
		OverlayItem overlayitem = null;
		
		for (int i=0;i<coordArray.length;i++) 
		{
			if(i==0 && (coordArray[i].getName() != null)) {
				continue;
			}
			point = new GeoPoint((int)(coordArray[i].getLatitude()*1e6),(int)(coordArray[i].getLongitude()*1e6));
			overlayitem = new OverlayItem(point, "","");
			itemizedoverlay.addOverlay(overlayitem);
			
		}
		mapOverlays.add(itemizedoverlay);
		
	}
}
