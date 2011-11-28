package com.sparcedge.groceryscanner;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


public class GroceryScanner extends Activity {
	
	public static DBAdapter db = null;

	public static final int REQUEST_BARCODE_SCAN = 100;
	public static final int REQUEST_SPLASH = 101;
	
    static String GSPREFS = "GS-prefs";
	static public Boolean first_time = true;
	Menu menu = null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //set up listeners for our buttons
        Button createButton = (Button)findViewById(R.id.create);
        createButton.setOnClickListener(createListener);
        Button viewButton = (Button)findViewById(R.id.view);
        viewButton.setOnClickListener(viewListener);
        
        
        db = new DBAdapter(this);
        /*
        db.open();
        ArrayList<ItemInfo> itemList = null;
        itemList = db.getList("Vg");
        if(itemList != null)
        for(ItemInfo item : itemList)
        {
        	LOG(item.toString());
        }
        
        ArrayList<String> lists = db.getListNames();
        LOG("lists: " + lists.toString());
        db.close();
        */

        //UpcDecoder bc = new UpcDecoder("083089660105");
        //bc.get();
        //LOG("product: " + bc.getProductName());
        
        loadPreferences();
        //if(first_time) {
	        Intent sndMsgIntent = new Intent(this, SplashActivity.class);
	        startActivityForResult(sndMsgIntent, REQUEST_SPLASH);
        //}

        

    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.zombie:
        	break;
        }
        return false;
    }
        
    @Override
    public boolean onCreateOptionsMenu(Menu _menu) {
        menu = _menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }
    
    public static void LOG(String s) {
    	Log.v("GroceryScanner", s);
    }
    
    public void savePreferences() {
        LOG("savePreferences() called. "); 
        SharedPreferences mySharedPrefs = getSharedPreferences(GSPREFS,
                        Activity.MODE_WORLD_WRITEABLE);
        SharedPreferences.Editor editor = mySharedPrefs.edit();
        //editor.putBoolean("first_time", first_time);
        editor.commit();
    }
    
    public void loadPreferences() {
        LOG("loadPreferences() called.");
        SharedPreferences mySharedPrefs = getSharedPreferences(GSPREFS,
                        Activity.MODE_WORLD_WRITEABLE);
        //first_time = mySharedPrefs.getBoolean("first_time", true);
    }
    
    
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        LOG( "onActivityResult: " + requestCode + " " + resultCode);
        switch (requestCode) {
        case REQUEST_SPLASH:
        		first_time = false;
                savePreferences();
                break;
        case REQUEST_BARCODE_SCAN:
            if (resultCode == RESULT_OK) {
            	String scan = data.getStringExtra("SCAN_RESULT");
            	LOG("barcode: " + scan);
            	UpcDecoder bc = new UpcDecoder(scan);
            	bc.get();
            	//bc.getImageURL();
            	//bc.getProductName();
            } else if (resultCode == RESULT_CANCELED) {
            	//Debug("scan canceled");
            }
            break;
   
        }
    }
    
    private OnClickListener createListener = new OnClickListener() {
        public void onClick(View v) {
        	Intent i = new Intent(GroceryScanner.this,NewListActivity.class);
        	GroceryScanner.this.startActivity(i);

        }
    };
    
    private OnClickListener viewListener = new OnClickListener() {
        public void onClick(View v) {
        	Intent i = new Intent(GroceryScanner.this,ViewListsActivity.class);
        	GroceryScanner.this.startActivity(i);
        }
    };

    
}