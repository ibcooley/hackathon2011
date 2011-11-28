package com.test;

import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Spinner;

public class CaughtFishActivity extends Activity {
	
	private static final int CAMERA_PIC_REQUEST = 0;
	private Spinner spinFishType;
	private Spinner spinFishSize;
	private ImageView imgFish;
	private Button btnTakePhoto;
	

	private AnglerDbAdapter mDbHelper;
	private String filename;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		mDbHelper = new AnglerDbAdapter(this);
        mDbHelper.open();
		
		setContentView(R.layout.fishcaught);
        setTitle(R.string.fish_caught);

        spinFishType = (Spinner)findViewById(R.id.FishType);
        spinFishSize = (Spinner)findViewById(R.id.FishSize);
        imgFish = (ImageView)findViewById(R.id.fishcaught_fishimage);
        btnTakePhoto = (Button)findViewById(R.id.fishcaught_takephoto);
        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	takePhoto();
            }
        });
        
        Button btnSaveCatch = (Button)findViewById(R.id.catch_save);
        btnSaveCatch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	saveCatch();
            }
        });
        
        fillData();
	}

	private void takePhoto() {
	    // Create an intent to take a picture using the device camera, as defined by the content provider URI
		Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
	    startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    // If the request went well (OK) and the request was PICK_CONTACT_REQUEST
	    if (resultCode == Activity.RESULT_OK && requestCode == CAMERA_PIC_REQUEST) {
	    	Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
	    	imgFish.setImageBitmap(thumbnail);
	    	
	    	filename = System.currentTimeMillis() + ".png";
	    	try {
	    		FileOutputStream out = new FileOutputStream(filename);
	    		thumbnail.compress(Bitmap.CompressFormat.PNG, 90, out);
	    	}
	    	catch(Exception e){}
	    }
	}
	
	private void saveCatch() {
		setResult(RESULT_OK);
        finish();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		saveState();
	}

	@Override
	protected void onResume() {
		super.onResume();
		//populateFields();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		saveState();
	}
	
	private void saveState() {
		String bait = "test bait";
		int spotId = 1;
		String fish = "bass";
		String weather = "raining";
		long time = System.currentTimeMillis();
		double lat = 35;
		double lon = -85;
		double water = 70.25d;
		
		
		mDbHelper.createFishCaught(bait, spotId, filename, fish, weather, time, lat, lon, water);
	}
	
	private void fillData() {
		ArrayAdapter fishTypeAdapter = ArrayAdapter.createFromResource( this, R.array.fish_types, android.R.layout.simple_spinner_item); fishTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinFishType.setAdapter(fishTypeAdapter);

		ArrayAdapter fishSizeAdapter = ArrayAdapter.createFromResource( this, R.array.fish_length, android.R.layout.simple_spinner_item); fishSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinFishSize.setAdapter(fishSizeAdapter);
		
	}
}
