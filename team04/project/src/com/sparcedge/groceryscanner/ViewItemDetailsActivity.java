package com.sparcedge.groceryscanner;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ViewItemDetailsActivity extends ListActivity {
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
      String name = ViewItemDetailsActivity.this.getIntent().getStringExtra("myKey");    
      GroceryScanner.db.open();
      List<ItemInfo> itemInfo = GroceryScanner.db.getList(name);
      ArrayList<String> stringArray = new ArrayList<String>();
      for(ItemInfo item : itemInfo)
      {
    	  stringArray.add(item.getList());
    	  
      }
	  setListAdapter(new ArrayAdapter<String>(this, R.layout.viewitem, stringArray));
	}
	
}
