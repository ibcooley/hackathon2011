package com.sparcedge.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseConnector extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME = "EcoDominator";

	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String DATABASE_CREATE_DEVICES = "create table Devices (" +
			"_id integer primary key autoincrement, " +
			"name text not null, " +
			"manufacturer text not null, " +
			"defaultEntry int not null, " +
			"watts REAL not null, " +
			"hours REAL not null, " +
			"usageTerm text not null, " +
			"minWatts REAL not null, " +
			"maxWatts REAL not null, " +
			"description text not null" +
			");";
	private static final String DATABASE_CREATE_ROOMS = "create table Rooms (" +
			"_id integer primary key autoincrement, " +
			"name text not null, " +
			"defaultEntry int not null, " +
			"fans INT not null, " +
			"compLights INT not null, " +
			"incLights INT not null," +
			"description text not null" +
			");";
	
	private static final String DATABASE_CREATE_DEVICEROOM = "create table DeviceRooms (" +
			"_id integer primary key autoincrement, " +
			"roomID integer not null, " +
			"deviceID integer not null"+
			");";
	
	public DatabaseConnector(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE_DEVICES);
		database.execSQL(DATABASE_CREATE_ROOMS);
		database.execSQL(DATABASE_CREATE_DEVICEROOM);
		
		loadSampleData(database);
	}

	private void loadSampleData(SQLiteDatabase database) {
		ContentValues values = new ContentValues();
		
		values.put("_id", 0);
		values.put("name", "Kitchen");
		values.put("defaultEntry", 0);
		values.put("fans", 1);
		values.put("compLights", 1);
		values.put("incLights", 4);
		values.put("description", "The Kitchen");
		
		database.insert("Rooms", null, values);
		
		values = new ContentValues();
		
		values.put("_id", 1);
		values.put("name", "LivingRoom");
		values.put("defaultEntry", 0);
		values.put("fans", 1);
		values.put("compLights", 1);
		values.put("incLights", 4);
		values.put("description", "The Den");
		
		database.insert("Rooms", null, values);
		
		
		values = new ContentValues();
		values.put("_id", 2);
		values.put("name", "Master Bedroom");
		values.put("defaultEntry", 0);
		values.put("fans", 1);
		values.put("compLights", 0);
		values.put("incLights", 4);
		values.put("description", "The Master Bedroom");
		
		database.insert("Rooms", null, values);
		
		
		values = new ContentValues();
		values.put("_id", 0);
		values.put("name", "Microwave");
		values.put("manufacturer", "GE");
		values.put("defaultEntry", 0);
		values.put("watts", 1000);
		values.put("hours", 0.25);
		values.put("usageTerm", "Daily");
		values.put("minWatts", 500);
		values.put("maxWatts", 1500);
		values.put("description", "Chef Mic");
		
		database.insert("Devices", null, values);
		
		values = new ContentValues();
		values.put("_id", 1);
		values.put("name", "Dishwasher");
		values.put("manufacturer", "GE");
		values.put("defaultEntry", 0);
		values.put("watts", 1600);
		values.put("hours", 1.5);
		values.put("usageTerm", "Daily");
		values.put("minWatts", 1000);
		values.put("maxWatts", 3000);
		values.put("description", "The Dishwasher");
		database.insert("Devices", null, values);
		
		values = new ContentValues();
		values.put("_id", 2);
		values.put("name", "Oven");
		values.put("manufacturer", "GE");
		values.put("defaultEntry", 0);
		values.put("watts", 1200);
		values.put("hours", 1);
		values.put("usageTerm", "Daily");
		values.put("minWatts", 800);
		values.put("maxWatts", 2000);
		values.put("description", "The Oven");
		database.insert("Devices", null, values);
		
		values = new ContentValues();
		values.put("_id", 3);
		values.put("name", "Fridge");
		values.put("manufacturer", "GE");
		values.put("defaultEntry", 0);
		values.put("watts", 725);
		values.put("hours", 1);
		values.put("usageTerm", "Daily");
		values.put("minWatts", 500);
		values.put("maxWatts", 1250);
		values.put("description", "The Fridge");
		database.insert("Devices", null, values);
		
		values = new ContentValues();
		values.put("_id", 0);
		values.put("roomID", 0);
		values.put("deviceID", 0);
		database.insert("DeviceRooms", null, values);
		
		values = new ContentValues();
		values.put("_id", 0);
		values.put("roomID", 0);
		values.put("deviceID", 1);
		database.insert("DeviceRooms", null, values);
		
		values = new ContentValues();
		values.put("_id", 0);
		values.put("roomID", 0);
		values.put("deviceID", 2);
		database.insert("DeviceRooms", null, values);
		
		values = new ContentValues();
		values.put("_id", 0);
		values.put("roomID", 0);
		values.put("deviceID", 3);
		database.insert("DeviceRooms", null, values);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		Log.w(DatabaseConnector.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS Devices");
		database.execSQL("DROP TABLE IF EXISTS Rooms");
		database.execSQL("DROP TABLE IF EXISTS DeviceRooms");
		onCreate(database);

	}

}
