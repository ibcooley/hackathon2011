package com.sparcedge.database;

import java.util.ArrayList;

import com.sparcedge.model.api.Room;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class RoomDBAdaptor {
	
	public static final String KEY_ROWID = "_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_DEFAULT = "defaultEntry";
	public static final String KEY_FANS = "fans";
	public static final String KEY_COMP_LIGHTS = "compLights";
	public static final String KEY_INC_LIGHTS = "incLights";
	public static final String KEY_DESCRIPTION = "description";
	private static final String DATABASE_TABLE = "Rooms";
	
	private Context context;
	private SQLiteDatabase database;
	private DatabaseConnector dbHelper;
	
	public RoomDBAdaptor(Context context) {
		this.context = context;
	}
	
	public RoomDBAdaptor open() throws SQLException {
		dbHelper = new DatabaseConnector(context);
		database = dbHelper.getWritableDatabase();
		return this;
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public long createRoom(Room room) {
		ContentValues values = new ContentValues();
		
		values.put(KEY_NAME, room.getName());
		values.put(KEY_DEFAULT, 1);
		values.put(KEY_FANS, room.getFanCount());
		values.put(KEY_COMP_LIGHTS, room.getCflLightCount());
		values.put(KEY_INC_LIGHTS, room.getIncandescantLightCount());
		values.put(KEY_DESCRIPTION, room.getDescription());
		
		return database.insert(DATABASE_TABLE, null, values);
	}
	
	public boolean updateRoom(Room room)
	{
		ContentValues values = new ContentValues();
		
		values.put(KEY_NAME, room.getName());
		values.put(KEY_FANS, room.getFanCount());
		values.put(KEY_COMP_LIGHTS, room.getCflLightCount());
		values.put(KEY_INC_LIGHTS, room.getIncandescantLightCount());
		values.put(KEY_DESCRIPTION, room.getDescription());
		
		return database.update(DATABASE_TABLE, values, KEY_ROWID + "=" + room.getId(), null) > 0;
	}
	
	public boolean deleteRoom(Room room) {
		return database.delete(DATABASE_TABLE, KEY_ROWID + "=" + room.getId() + " AND " + KEY_DEFAULT + "!=0", null) > 0;
	}
	
	public ArrayList<Room> getRoom(long rowId) throws SQLException {
		ArrayList<Room> roomArray = new ArrayList<Room>();
		Cursor mCursor = database.query(
				true, 
				DATABASE_TABLE, 
				new String[] {
						KEY_ROWID, 
						KEY_NAME, 
						KEY_DEFAULT,
						KEY_FANS,
						KEY_COMP_LIGHTS,
						KEY_INC_LIGHTS,
						KEY_DESCRIPTION 
						},
				KEY_ROWID + "=" + rowId, null, null, null, null, null);
		if (mCursor.moveToFirst()) 
		{
			do
			{
				Room tmp = new Room();
				tmp.setId(mCursor.getInt(mCursor.getColumnIndex(KEY_ROWID)));
				tmp.setName((mCursor.getString(mCursor.getColumnIndex(KEY_NAME))));
				tmp.setFanCount(mCursor.getInt(mCursor.getColumnIndex(KEY_FANS)));
				tmp.setCflLightCount(mCursor.getInt(mCursor.getColumnIndex(KEY_COMP_LIGHTS)));
				tmp.setIncandescantLightCount(mCursor.getInt(mCursor.getColumnIndex(KEY_INC_LIGHTS)));
				tmp.setDescription(mCursor.getString(mCursor.getColumnIndex(KEY_DESCRIPTION)));
				roomArray.add(tmp);
				
			} while (mCursor.moveToNext());
		}
		return roomArray;
	}
	
	public ArrayList<Room> getDefaultRooms() throws SQLException {
		ArrayList<Room> roomArray = new ArrayList<Room>();
		Cursor mCursor = database.query(
				true, 
				DATABASE_TABLE, 
				new String[] {
						KEY_ROWID, 
						KEY_NAME, 
						KEY_DEFAULT,
						KEY_FANS,
						KEY_COMP_LIGHTS,
						KEY_INC_LIGHTS,
						KEY_DESCRIPTION 
						},
						KEY_DEFAULT + "=0", null, null, null, null, null);
		if (mCursor.moveToFirst()) 
		{
			do
			{
				Room tmp = new Room();
				tmp.setId(mCursor.getInt(mCursor.getColumnIndex(KEY_ROWID)));
				tmp.setName((mCursor.getString(mCursor.getColumnIndex(KEY_NAME))));
				tmp.setFanCount(mCursor.getInt(mCursor.getColumnIndex(KEY_FANS)));
				tmp.setCflLightCount(mCursor.getInt(mCursor.getColumnIndex(KEY_COMP_LIGHTS)));
				tmp.setIncandescantLightCount(mCursor.getInt(mCursor.getColumnIndex(KEY_INC_LIGHTS)));
				tmp.setDescription(mCursor.getString(mCursor.getColumnIndex(KEY_DESCRIPTION)));
				roomArray.add(tmp);
				
			} while (mCursor.moveToNext());
		}
		return roomArray;
	}
	
	
	public ArrayList<Room> getAllRooms() throws SQLException {
		ArrayList<Room> roomArray = new ArrayList<Room>();
		Cursor mCursor = database.query(
				true, 
				DATABASE_TABLE, 
				new String[] {
						KEY_ROWID, 
						KEY_NAME, 
						KEY_DEFAULT,
						KEY_FANS,
						KEY_COMP_LIGHTS,
						KEY_INC_LIGHTS,
						KEY_DESCRIPTION 
						},
					null, null, null, null, null, null);
		if (mCursor.moveToFirst()) 
		{
			do
			{
				Room tmp = new Room();
				tmp.setId(mCursor.getInt(mCursor.getColumnIndex(KEY_ROWID)));
				tmp.setName((mCursor.getString(mCursor.getColumnIndex(KEY_NAME))));
				tmp.setFanCount(mCursor.getInt(mCursor.getColumnIndex(KEY_FANS)));
				tmp.setCflLightCount(mCursor.getInt(mCursor.getColumnIndex(KEY_COMP_LIGHTS)));
				tmp.setIncandescantLightCount(mCursor.getInt(mCursor.getColumnIndex(KEY_INC_LIGHTS)));
				tmp.setDescription(mCursor.getString(mCursor.getColumnIndex(KEY_DESCRIPTION)));
				roomArray.add(tmp);
				
			} while (mCursor.moveToNext());
		}
		return roomArray;
	}
}
