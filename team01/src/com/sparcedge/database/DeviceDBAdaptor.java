package com.sparcedge.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.sparcedge.model.api.Device;

public class DeviceDBAdaptor {

	// Database fields
	
	public static final String KEY_ROWID = "_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_MANUFACTURER = "manufacturer";
	public static final String KEY_DEFAULT = "defaultEntry";
	public static final String KEY_WATTS = "watts";
	public static final String KEY_HOURS = "hours";
	public static final String KEY_USAGETERM = "usageTerm";
	public static final String KEY_MINWATTS = "minWatts";
	public static final String KEY_MAXWATTS = "maxWatts";
	public static final String KEY_DESCRIPTION = "description";
	private static final String DATABASE_TABLE = "Devices";
	private Context context;
	private SQLiteDatabase database;
	private DatabaseConnector dbHelper;

	public DeviceDBAdaptor(Context context) {
		this.context = context;
	}
	
	public DeviceDBAdaptor open() throws SQLException {
		dbHelper = new DatabaseConnector(context);
		database = dbHelper.getWritableDatabase();
		return this;
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public long createDevice(Device device) {
		ContentValues values = new ContentValues();
		
		values.put(KEY_NAME, device.getName());
		values.put(KEY_MANUFACTURER, device.getManufacturer());
		values.put(KEY_DEFAULT, 1);
		values.put(KEY_WATTS, device.getPowerUsage());
		values.put(KEY_HOURS, device.getUsage());
		values.put(KEY_USAGETERM, device.getUsageType());
		values.put(KEY_MINWATTS, device.getMinUsage());
		values.put(KEY_MAXWATTS, device.getMaxUsage());
		values.put(KEY_DESCRIPTION, device.getDescription());
		
		return database.insert(DATABASE_TABLE, null, values);
	}
	
	public boolean updateDevice(Device device)
	{
		ContentValues values = new ContentValues();
		
		values.put(KEY_NAME, device.getName());
		values.put(KEY_MANUFACTURER, device.getManufacturer());
		values.put(KEY_WATTS, device.getPowerUsage());
		values.put(KEY_HOURS, device.getUsage());
		values.put(KEY_USAGETERM, device.getUsageType());
		values.put(KEY_DESCRIPTION, device.getDescription());
		
		return database.update(DATABASE_TABLE, values, KEY_ROWID + "=" + device.getId(), null) > 0;
	}
	
	public boolean deleteDevice(Device device) {
		return database.delete(DATABASE_TABLE, KEY_ROWID + "=" + device.getId() + " AND " + KEY_DEFAULT + "!=0", null) > 0;
	}
	
	public ArrayList<Device> getDevice(long rowId) throws SQLException {
		ArrayList<Device> deviceArray = new ArrayList<Device>();
		Cursor mCursor = database.query(
				true, 
				DATABASE_TABLE, 
				new String[] {
						KEY_ROWID, 
						KEY_NAME, 
						KEY_MANUFACTURER, 
						KEY_DEFAULT,
						KEY_WATTS,
						KEY_HOURS,
						KEY_USAGETERM,
						KEY_MINWATTS,
						KEY_MAXWATTS,
						KEY_DESCRIPTION 
						},
				KEY_ROWID + "=" + rowId, null, null, null, null, null);
		if (mCursor.moveToFirst()) 
		{
			do
			{
				Device tmp = new Device();
				tmp.setId(mCursor.getInt(mCursor.getColumnIndex(KEY_ROWID)));
				tmp.setName((mCursor.getString(mCursor.getColumnIndex(KEY_NAME))));
				tmp.setManufacturer(mCursor.getString(mCursor.getColumnIndex(KEY_MANUFACTURER)));
				tmp.setPowerUsage(mCursor.getFloat(mCursor.getColumnIndex(KEY_WATTS)));
				tmp.setUsage(mCursor.getFloat(mCursor.getColumnIndex(KEY_HOURS)));
				tmp.setUsageType(mCursor.getString(mCursor.getColumnIndex(KEY_USAGETERM)));
				tmp.setMinUsage(mCursor.getFloat(mCursor.getColumnIndex(KEY_MINWATTS)));
				tmp.setMaxUsage(mCursor.getFloat(mCursor.getColumnIndex(KEY_MAXWATTS)));
				tmp.setDescription(mCursor.getString(mCursor.getColumnIndex(KEY_DESCRIPTION)));
				deviceArray.add(tmp);
				
			} while (mCursor.moveToNext());
		}
		return deviceArray;
	}
	
	public ArrayList<Device> getAllDevices() throws SQLException {
		ArrayList<Device> deviceArray = new ArrayList<Device>();
		Cursor mCursor = database.query(
				true, 
				DATABASE_TABLE, 
				new String[] {
						KEY_ROWID, 
						KEY_NAME, 
						KEY_MANUFACTURER, 
						KEY_DEFAULT,
						KEY_WATTS,
						KEY_HOURS,
						KEY_USAGETERM,
						KEY_MINWATTS,
						KEY_MAXWATTS,
						KEY_DESCRIPTION 
						},
				null, null, null, null, null, null);
		if (mCursor.moveToFirst()) 
		{
			do
			{
				Device tmp = new Device();
				tmp.setId(mCursor.getInt(mCursor.getColumnIndex(KEY_ROWID)));
				tmp.setName((mCursor.getString(mCursor.getColumnIndex(KEY_NAME))));
				tmp.setManufacturer(mCursor.getString(mCursor.getColumnIndex(KEY_MANUFACTURER)));
				tmp.setPowerUsage(mCursor.getFloat(mCursor.getColumnIndex(KEY_WATTS)));
				tmp.setUsage(mCursor.getFloat(mCursor.getColumnIndex(KEY_HOURS)));
				tmp.setUsageType(mCursor.getString(mCursor.getColumnIndex(KEY_USAGETERM)));
				tmp.setMinUsage(mCursor.getFloat(mCursor.getColumnIndex(KEY_MINWATTS)));
				tmp.setMaxUsage(mCursor.getFloat(mCursor.getColumnIndex(KEY_MAXWATTS)));
				tmp.setDescription(mCursor.getString(mCursor.getColumnIndex(KEY_DESCRIPTION)));
				deviceArray.add(tmp);
				
			} while (mCursor.moveToNext());
		}
		return deviceArray;
	}
	
	public ArrayList<Device> getDeviceByRoom(int roomID) throws SQLException {
		ArrayList<Device> deviceArray = new ArrayList<Device>();
		Cursor mCursor = database.rawQuery("Select " +
				"Devices." + KEY_ROWID + "," +
				"Devices." + KEY_NAME +"," +
				"Devices." + KEY_MANUFACTURER +"," +
				"Devices." + KEY_DEFAULT +"," +
				"Devices." + KEY_WATTS +"," +
				"Devices." + KEY_HOURS +"," +
				"Devices." + KEY_USAGETERM +"," +
				"Devices." + KEY_MINWATTS +"," +
				"Devices." + KEY_MAXWATTS +"," +
				"Devices." + KEY_DESCRIPTION +
				" from Devices join DeviceRooms ON Devices." + KEY_ROWID + "=DeviceRooms.DeviceID " +
						"where DeviceRooms.roomID=?" , new String[]{String.valueOf(roomID)});
		if (mCursor.moveToFirst()) 
		{
			do
			{
				Device tmp = new Device();
				tmp.setId(mCursor.getInt(mCursor.getColumnIndex(KEY_ROWID)));
				tmp.setName((mCursor.getString(mCursor.getColumnIndex(KEY_NAME))));
				tmp.setManufacturer(mCursor.getString(mCursor.getColumnIndex(KEY_MANUFACTURER)));
				tmp.setPowerUsage(mCursor.getFloat(mCursor.getColumnIndex(KEY_WATTS)));
				tmp.setUsage(mCursor.getFloat(mCursor.getColumnIndex(KEY_HOURS)));
				tmp.setUsageType(mCursor.getString(mCursor.getColumnIndex(KEY_USAGETERM)));
				tmp.setMinUsage(mCursor.getFloat(mCursor.getColumnIndex(KEY_MINWATTS)));
				tmp.setMaxUsage(mCursor.getFloat(mCursor.getColumnIndex(KEY_MAXWATTS)));
				tmp.setDescription(mCursor.getString(mCursor.getColumnIndex(KEY_DESCRIPTION)));
				deviceArray.add(tmp);
				
			} while (mCursor.moveToNext());
		}
		return deviceArray;
	}
	
	public ArrayList<Device> getDefaultDeviceByRoom(int roomID) throws SQLException {
		ArrayList<Device> deviceArray = new ArrayList<Device>();
		Cursor mCursor = database.rawQuery("Select " +
				"Devices." + KEY_ROWID + "," +
				"Devices." + KEY_NAME +"," +
				"Devices." + KEY_MANUFACTURER +"," +
				"Devices." + KEY_DEFAULT +"," +
				"Devices." + KEY_WATTS +"," +
				"Devices." + KEY_HOURS +"," +
				"Devices." + KEY_USAGETERM +"," +
				"Devices." + KEY_MINWATTS +"," +
				"Devices." + KEY_MAXWATTS +"," +
				"Devices." + KEY_DESCRIPTION +
				" from Devices join DeviceRooms ON Devices." + KEY_ROWID + "=DeviceRooms.DeviceID " +
						"where DeviceRooms.roomID=? and Devices." +  KEY_DEFAULT + "=1", new String[]{String.valueOf(roomID)});
		if (mCursor.moveToFirst()) 
		{
			do
			{
				Device tmp = new Device();
				tmp.setId(mCursor.getInt(mCursor.getColumnIndex(KEY_ROWID)));
				tmp.setName((mCursor.getString(mCursor.getColumnIndex(KEY_NAME))));
				tmp.setManufacturer(mCursor.getString(mCursor.getColumnIndex(KEY_MANUFACTURER)));
				tmp.setPowerUsage(mCursor.getFloat(mCursor.getColumnIndex(KEY_WATTS)));
				tmp.setUsage(mCursor.getFloat(mCursor.getColumnIndex(KEY_HOURS)));
				tmp.setUsageType(mCursor.getString(mCursor.getColumnIndex(KEY_USAGETERM)));
				tmp.setMinUsage(mCursor.getFloat(mCursor.getColumnIndex(KEY_MINWATTS)));
				tmp.setMaxUsage(mCursor.getFloat(mCursor.getColumnIndex(KEY_MAXWATTS)));
				tmp.setDescription(mCursor.getString(mCursor.getColumnIndex(KEY_DESCRIPTION)));
				deviceArray.add(tmp);
				
			} while (mCursor.moveToNext());
		}
		return deviceArray;
	}
}
