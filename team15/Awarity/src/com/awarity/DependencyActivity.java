/*
 ******************************************************************************
 * Parts of this code sample are licensed under Apache License, Version 2.0   *
 * Copyright (c) 2009, Android Open Handset Alliance. All rights reserved.    *
 *                                                                            *                                                                         *
 * Except as noted, this code sample is offered under a modified BSD license. *
 * Copyright (C) 2010, Motorola Mobility, Inc. All rights reserved.           *
 *                                                                            *
 * For more details, see MOTODEV_Studio_for_Android_LicenseNotices.pdf        * 
 * in your installation folder.                                               *
 ******************************************************************************
 */

package com.awarity;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class DependencyActivity extends ListActivity implements OnClickListener
{

	private String[] listItems = null;
	private AutoCompleteTextView dependencyActv;
	private long mRowId;
	private DbAdapter mDb;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dependency);
		
		Bundle extras = getIntent().getExtras();
		mRowId = extras.getLong(Globals.ROW_ID);

		mDb = new DbAdapter(this);

		dependencyActv = (AutoCompleteTextView) findViewById(R.id.dependency_who_actv);
		
		Button exitBtn = (Button) findViewById(R.id.dependency_exit_btn);
		exitBtn.setOnClickListener(this);
		// newBtn.setBackgroundResource(0);

		Button continueBtn = (Button) findViewById(R.id.dependency_continue_btn);
		continueBtn.setOnClickListener(this);
		
		Button addBtn = (Button) findViewById(R.id.dependency_add_another_btn);
		addBtn.setOnClickListener(this);
		// existingBtn.setBackgroundResource(0);

		// load list
		Resources res = getResources();
		listItems = res.getStringArray(R.array.sample_simple_list_items);

		// maps an array to TextViews

//		updateListAdapter();
		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, listItems));
	}

	private void updateListAdapter()
	{
		// TODO Auto-generated method stub
		mDb.open();
		Cursor mCursor = mDb.fetchDependencies(mRowId);
		startManagingCursor(mCursor);
//		ArrayList<String> mArrayList = new ArrayList<String>();
//		for(mCursor.moveToFirst(); mCursor.moveToNext(); mCursor.isAfterLast()) {
//		    // The Cursor is now set to the right position
//		    mArrayList.add(mCursor.getString(1));
//		}

		ListAdapter adapter = new SimpleCursorAdapter(this, // Context.
				android.R.layout.two_line_list_item, // Specify the row template
														// to use (here, two
														// columns bound to the
														// two retrieved cursor
														// rows).
				mCursor, // Pass in the cursor to bind to.
				// Array of cursor columns to bind to.
//				new String[] { ContactsContract.Contacts._ID,
//						ContactsContract.Contacts.DISPLAY_NAME },
				DbAdapter.DEPENDENCIES,
				// Parallel array of which template objects to bind to those
				// columns.
				new int[] { android.R.id.text1, android.R.id.text2 }
				
		);

		// Bind to our new adapter.
		setListAdapter(adapter);
		mCursor.close();
		mDb.close();
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.new_details_exit_btn:
			setResult(Globals.RC_DEPENDENCY);
			finish();
			//(Globals.RC_NEWDETAILS);
			break;
		case R.id.new_details_continue_btn:
			// save data to database
			setResult(Globals.RC_DEPENDENCY);
			finish();
			// Check for null entry
//			if (dependencyActv.getText().length() == 0)
//				popToast("Must enter dependency");
//			else
//				goToNew();
			break;
		case R.id.dependency_add_another_btn:
			saveDependency();
			updateListAdapter();
			reset();
		break;
		}
	}

	private void reset()
	{
		dependencyActv.setText("");
	}

	private void saveDependency()
	{
		Dependency dependency = new Dependency();
		dependency.setWho(dependencyActv.getText().toString());	
		dependency.setTaskNumber(mRowId);
		mDb.open();
		mDb.createDependencies(dependency);
		mDb.close();
	}

	private void popToast(String toastString)
	{
		Toast.makeText(this, toastString, Toast.LENGTH_LONG).show();
	}
	
	@Override
	protected void onListItemClick(ListView lv, View v, int position, long id)
	{
		// your action here
		Toast.makeText(
				lv.getContext(),
				getString(R.string.sample_simple_list_selected)
						+ lv.getItemAtPosition(position).toString(),
				Toast.LENGTH_SHORT).show();
	}
}