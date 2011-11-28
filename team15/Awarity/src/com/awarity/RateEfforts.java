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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class RateEfforts extends ListActivity
{
	private final String TEXT_KEY = "text";
	private final String IMG_KEY = "img";

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rate_efforts);

		// adds listener to list view
		ListView listView = getListView();
		listView.setOnItemClickListener(new OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3)
			{
				Toast.makeText(arg1.getContext(),
						getString(R.string.selected) + " " + arg2,
						Toast.LENGTH_SHORT).show();
			}
		});

		// list data
		List<Map<String, Object>> resourceNames = new ArrayList<Map<String, Object>>();

		generateData(resourceNames);

		// adapter that will build the list items
		SimpleAdapter adapter = new SimpleAdapter(this, resourceNames,
				R.layout.rate_efforts_listrow, new String[]
				{ TEXT_KEY, IMG_KEY }, new int[]
				{ R.id.text01, R.id.img01 });

		setListAdapter(adapter);
	}

	private void generateData(List<Map<String, Object>> resourceNames)
	{
		// number of list items
		int NUM_ITEMS = 50;
		Map<String, Object> data;

		for (int i = 0; i <= NUM_ITEMS; i++)
		{
			data = new HashMap<String, Object>();
			data.put(TEXT_KEY,
					getString(R.string.list_item) + " " + Integer.toString(i));
			data.put(IMG_KEY, R.drawable.listicon1);
			resourceNames.add(data);
		}
	}
}