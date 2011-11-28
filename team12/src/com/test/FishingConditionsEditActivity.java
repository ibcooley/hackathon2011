package com.test;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class FishingConditionsEditActivity extends Activity {

	private Spinner spinFishingType;
	private Spinner spinWeather;
	private Spinner spinBaitType;
	private Object btnSave;

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		saveState();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.fishing_conditions);
        setTitle(R.string.fishing_conditions);

        spinWeather = (Spinner)findViewById(R.id.conditions_weather);
        spinFishingType = (Spinner)findViewById(R.id.conditions_fishingtype);
        spinBaitType = (Spinner)findViewById(R.id.conditions_baittype);
        btnSave = (Button)findViewById(R.id.conditions_save);
        
        fillData();
	}
	
	private void fillData() {
		ArrayAdapter weatherAdapter = ArrayAdapter.createFromResource( this, R.array.weather, android.R.layout.simple_spinner_item); weatherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinWeather.setAdapter(weatherAdapter);		
		
		ArrayAdapter fishingAdapter = ArrayAdapter.createFromResource( this, R.array.fishing_types, android.R.layout.simple_spinner_item); fishingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinFishingType.setAdapter(fishingAdapter);
		
		ArrayAdapter baitAdapter = ArrayAdapter.createFromResource( this, R.array.bait_types, android.R.layout.simple_spinner_item); baitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinBaitType.setAdapter(baitAdapter);
	}
	
	private void saveState() {
		
	}
}
