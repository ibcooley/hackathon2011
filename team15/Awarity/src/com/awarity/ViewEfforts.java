/*
 ******************************************************************************
 * Parts of this code sample are licensed under Apache License, Version 2.0   *
 * Copyright (c) 2009, Android Open Handset Alliance. All rights reserved.    *
 *																			  *																			*
 * Except as noted, this code sample is offered under a modified BSD license. *
 * Copyright (C) 2010, Motorola Mobility, Inc. All rights reserved.           *
 * 																			  *
 * For more details, see MOTODEV_Studio_for_Android_LicenseNotices.pdf        * 
 * in your installation folder.                                               *
 ******************************************************************************
 */

package com.awarity;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.awarity.ViewEffortsSqlOpenHelper;

import com.awarity.R;

public class ViewEfforts extends Activity
{
	/* Called when the activity is first created. */

	private final int ONE = 1;
	private TableLayout tbl = null;
	private TableRow row = null;

	private android.widget.TableRow.LayoutParams rowParams = new android.widget.TableRow.LayoutParams(
			android.widget.TableRow.LayoutParams.FILL_PARENT,
			android.widget.TableRow.LayoutParams.WRAP_CONTENT);

	private android.widget.TableRow.LayoutParams tableParams = new android.widget.TableRow.LayoutParams(
			android.widget.TableRow.LayoutParams.FILL_PARENT,
			android.widget.TableRow.LayoutParams.WRAP_CONTENT);

	// set database location
	public String DB_PATH = "/data/data/com.awarity/databases/";
	public String DB_NAME = "AwarityDB.db";

	// column names
	private final String COL_1 = "TaskNumber";
	private final String COL_2 = "ProgressEntryNumber";
	private final String COL_3 = "ElapsedTimeInMinutes";
	private final String COL_4 = "EstimatedCompletitionTimeInMinutes";
	private final String COL_5 = "Obsticals";

	// table name
	private final String SQL_TABLE_NAME = "efforts";
	private boolean DISTINCT_ROWS = true;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.databaselayout);

		tbl = (TableLayout) findViewById(R.id.myTableLayout);
		tbl.setBackgroundColor(Color.BLACK);

		SQLiteDatabase checkDB = null;
		ViewEffortsSqlOpenHelper helper = new ViewEffortsSqlOpenHelper(
				getApplicationContext(), true);
		checkDB = helper.getReadableDatabase();

		try
		{
			loadTable(checkDB);
		}
		catch (Exception e)
		{
			Toast.makeText(getApplicationContext(), e.getMessage(),
					Toast.LENGTH_LONG).show();
		}

		if (checkDB != null)
		{
			checkDB.close();
		}
	}

	public void loadTable(SQLiteDatabase sqliteDatabase)
	{
		Cursor cursor = sqliteDatabase.query(DISTINCT_ROWS, SQL_TABLE_NAME,
				new String[]
				{ COL_1, COL_2, COL_3, COL_4, COL_5 } /* columns */, COL_1 + "="
						+ COL_1 /* selection */, null /* selection args */,
				null /* group by */, null /* having */, null /* order by */,
				null /* limit */);

		if (cursor != null)
		{
			this.startManagingCursor(cursor);

			String[] columns = cursor.getColumnNames();
			createHeader(columns);

			while (cursor.move(ONE))
			{
				Integer col1 = cursor.getInt(cursor.getColumnIndex(COL_1));
				Integer col2 = cursor.getInt(cursor.getColumnIndex(COL_2));
				Integer col3 = cursor.getInt(cursor.getColumnIndex(COL_3));
				Integer col4 = cursor.getInt(cursor.getColumnIndex(COL_4));
				String col5 = cursor.getString(cursor.getColumnIndex(COL_5));

				TableRow row = new TableRow(getApplicationContext());
				row.setLayoutParams(rowParams);

				if (cursor.getPosition() % 2 == 1)
				{
					row.setBackgroundColor(Color.LTGRAY);
				}
				else
				{
					row.setBackgroundColor(Color.WHITE);
				}

				addToRow(col1, row);
				addToRow(col2, row);
				addToRow(col3, row);
				addToRow(col4, row);
				addToRow(col5, row);

				tbl.addView(row, tableParams);
			}
		}
	}

	// adds new value to row
	private void addToRow(Object obj, TableRow row)
	{
		TextView txt = new TextView(this);
		if (obj != null)
			txt.setText(obj.toString());
		else
			txt.setText("");
		txt.setLayoutParams(rowParams);
		txt.setTextColor(Color.BLACK);
		txt.setGravity(Gravity.LEFT);
		txt.setPadding(5, 1, 1, 1);
		row.addView(txt);
	}

	// creates table header
	private void createHeader(String[] names)
	{
		row = new TableRow(getApplicationContext());
		row.setLayoutParams(rowParams);
		row.setBackgroundColor(Color.GRAY);

		for (String column : names)
		{
			TextView txt = new TextView(this);
			txt.setText(column.toUpperCase());
			txt.setLayoutParams(rowParams);
			txt.setWidth(80);
			txt.setTextColor(Color.BLACK);
			txt.setTypeface(Typeface.DEFAULT_BOLD);
			txt.setGravity(Gravity.LEFT);
			// adds new field to row
			row.addView(txt);
		}

		// adds row to table
		tbl.addView(row, tableParams);
	}
}