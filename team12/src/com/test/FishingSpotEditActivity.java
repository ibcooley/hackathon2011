package com.test;

import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class FishingSpotEditActivity extends Activity {

	private static final int CAMERA_PIC_REQUEST = 0;
	private EditText txtFishingSpotName;
	private Button btnTakePhoto;
	private ImageView imgFishingSpot;
	private Button btnSave;
	
    private AnglerDbAdapter mDbHelper;
    private String filename = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		mDbHelper = new AnglerDbAdapter(this);
        mDbHelper.open();
		
		setContentView(R.layout.fishing_spot);
        setTitle(R.string.fishing_spot);

        imgFishingSpot = (ImageView)findViewById(R.id.spot_image);
        txtFishingSpotName = (EditText)findViewById(R.id.spot_fishingspot);
        btnTakePhoto = (Button)findViewById(R.id.spot_takephoto);
        btnSave = (Button)findViewById(R.id.spot_spotsavebutton);
        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	takePhoto();
            }
        });
        
        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	saveFishingSpot();
            }
        });
	}
	
	private void takePhoto() {
	    // Create an intent to take a picture using the device camera, as defined by the content provider URI
		Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
	    startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
	}
	
	private void saveFishingSpot() {
		setResult(RESULT_OK);
        finish();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    // If the request went well (OK) and the request was PICK_CONTACT_REQUEST
	    if (resultCode == Activity.RESULT_OK && requestCode == CAMERA_PIC_REQUEST) {
	    	Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
	    	imgFishingSpot.setImageBitmap(thumbnail);
	    	filename = System.currentTimeMillis() + ".png";
	    	try {
	    		FileOutputStream out = new FileOutputStream(filename);
	    		thumbnail.compress(Bitmap.CompressFormat.PNG, 90, out);
	    	}
	    	catch(Exception e){}
	    }
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
		//outState.putSerializable(NotesDbAdapter.KEY_ROWID, mRowId);
	}
	
	private void saveState() {
		String name = txtFishingSpotName.getText().toString();
		double lat = 0;
		double lon = 0;
		mDbHelper.createFishingSpot(name, lat, lon, filename);
	}
}
