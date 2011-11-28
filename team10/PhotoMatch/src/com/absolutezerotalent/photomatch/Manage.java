package com.absolutezerotalent.photomatch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class Manage extends Activity{
	
	ImageButton newImage, browse;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.manage);
		
		// get screen elements
		newImage = (ImageButton)findViewById(R.id.newPhoto);
		browse = (ImageButton)findViewById(R.id.browse);
		
		// make her do stuff
		newImage.setOnClickListener(new OnClickListener() {
			
			//@Override
			public void onClick(View v) {
				Intent i = new Intent(Manage.this, NewPhoto.class);
				startActivity(i);
				
			}
		});
		
		browse.setOnClickListener(new OnClickListener() {
			
			//@Override
			public void onClick(View v) {
				Intent i = new Intent(Manage.this, Browser.class);
				startActivity(i);
			}
		});
	}
}
