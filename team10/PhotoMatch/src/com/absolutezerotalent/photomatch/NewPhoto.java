package com.absolutezerotalent.photomatch;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class NewPhoto extends Activity {
	
	ImageButton newPhotoImage;
	Context c;
	
	//@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newphoto);
		c = getApplicationContext();
		
		// make the image clickable
		newPhotoImage = (ImageButton)findViewById(R.id.newPhotoImage);
		
		
		newPhotoImage.setOnClickListener(new OnClickListener() {
			
			//@Override
			public void onClick(View v) {
				
				final CharSequence[] items = {"Picasa", "File System", "Take Picture"};
				// Make dialog where you choose wehre the image comes from
				AlertDialog.Builder builder = new AlertDialog.Builder(NewPhoto.this);
				builder.setTitle("Pick Image Source");
				builder.setItems(items, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int item) {
						Log.d("I've been clicked", "tete");
						/*switch(item){
						case 1:  load off of picassa hahahahahaha ;
							break;
							case 2: Intent intent = new Intent();
							intent.setType("image/*");
							intent.setAction(Intent.ACTION_GET_CONTENT);
							startActivityForResult(Intent.createChooser(intent, "Select Picture"),1);

								break;
							case 3:  take a picture.... 
								break;
							default:  nothing ; 
						}					*/	
					}
				});
				
				AlertDialog alert = builder.create();
			}
		});
		}
}
