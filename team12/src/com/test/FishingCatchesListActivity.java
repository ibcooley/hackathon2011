package com.test;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

public class FishingCatchesListActivity extends ListActivity {

	AnglerDbAdapter mDbHelper; 
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catches_list);
        
        mDbHelper = new AnglerDbAdapter(this);
        mDbHelper.open();
        fillData();
    }
	
	private void fillData() {
        // Get all of the rows from the database and create the item list
		//Cursor cursor = mDbHelper.fetchAllFishCaught();
        //startManagingCursor(cursor);

        
        // Create an array to specify the fields we want to display in the list (only TITLE)
        /*String[] from = new String[]{AnglerDbAdapter.CAUGHT_TIME};

        // and an array of the fields we want to bind those fields to (in this case just text1)
        int[] to = new int[]{R.id.text1};

        // Now create a simple cursor adapter and set it to display
        SimpleCursorAdapter notes = new SimpleCursorAdapter(this, R.layout.spots_row, cursor, from, to);
        setListAdapter(notes);*/
    }
}
