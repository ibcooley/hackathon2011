package com.sparcedge.groceryscanner;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ViewListsActivity extends ListActivity {
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  
	  GroceryScanner.db.open();
	  List<String> names = GroceryScanner.db.getListNames();
	  GroceryScanner.db.close();
	  

	  setListAdapter(new ArrayAdapter<String>(this, R.layout.viewitem, names));

	  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

	  ListView lv = getListView();
	  lv.setTextFilterEnabled(true);

	  lv.setOnItemClickListener(new OnItemClickListener() {
	    public void onItemClick(AdapterView<?> parent, View view,
	        int position, long id) {
	    	// When clicked, show a toast with the TextView text
	    	Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
	    			Toast.LENGTH_SHORT).show();

	    	Intent i = new Intent(ViewListsActivity.this,ViewItemDetailsActivity.class);
	    	String listName = (String)((TextView) view).getText();
	    	i.putExtra("myKey",listName);
	    	ViewListsActivity.this.startActivity(i);
	    }
	  });
	}
	
	  static final String[] COUNTRIES = new String[] {
		    "List One", "List Two", "List Three", "List Four", "List Five",
		    "List Six", "List Seven", "List Eight"
		  };
}
