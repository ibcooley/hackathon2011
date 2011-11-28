package com.sparcedge.groceryscanner;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewListActivity extends Activity {
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createnew);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

//        createButton.setOnClickListener(createListener);
        Button submitButton = (Button)findViewById(R.id.submitname);
        submitButton.setOnClickListener(createListener);


    }
    
    private OnClickListener createListener = new OnClickListener() {
        public void onClick(View v) {
        	String listName = ((EditText)findViewById(R.id.listname)).getText().toString().trim();
        	Intent i = new Intent(NewListActivity.this,AddItemActivity.class);
        	i.putExtra("myKey",listName);
        	NewListActivity.this.startActivity(i);
        	
        }
    };

}
