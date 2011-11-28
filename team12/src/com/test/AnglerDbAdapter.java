package com.test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AnglerDbAdapter {

	public static final String KEY_ROWID = "_id";
    public static final String FISH_NAME = "name";
    public static final String BAIT_NAME = "name";
    
    public static final String SPOT_NAME = "name";
    public static final String SPOT_LAT = "lat";
    public static final String SPOT_LON = "lon";
    public static final String SPOT_PHOTO = "photo";

    public static final String CAUGHT_PHOTO = "photo";
    public static final String CAUGHT_WEATHER = "weather";
    public static final String CAUGHT_WATER = "water";
    public static final String CAUGHT_BAIT_ID = "baitId";
    public static final String CAUGHT_SPOT_ID = "spotId";
    public static final String CAUGHT_FISH_ID = "fishId";
    
    public static final String CAUGHT_TIME = "time";
    public static final String CAUGHT_LAT = "lat";
    public static final String CAUGHT_LON = "lon";
    
    
    public static final String FISH_TABLE = "fish";
    public static final String BAIT_TABLE = "bait";
    public static final String SPOT_TABLE = "fishingSpot";
    public static final String FISHCAUGHT_TABLE = "fishingCaught";
    
    private static final String TAG = "AnglerDbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    
    /**
     * Database creation sql statement
     */
    private static final String FISHINGSPOT_CREATE =
        "create table " + SPOT_TABLE + " (_id integer primary key autoincrement, "
        + "name text not null, lat double not null, lon double not null, photo text not null);";
    
    // TODO: foreign keys
    private static final String FISHCAUGHT_CREATE =
            "create table " + FISHCAUGHT_TABLE + " (_id integer primary key autoincrement, "
            + "name text not null, lat long not null, lon long not null, bait text not null, fish text not null, spotId integer not null, photo text not null, time long not null, water double);";
    
    /*
    private static final String BAIT_CREATE =
            "create table " + BAIT_TABLE + " (_id integer primary key autoincrement, "
            + "name text not null);";
    
    private static final String FISH_CREATE =
            "create table " + FISH_TABLE + " (_id integer primary key autoincrement, "
            + "name text not null);";
    */

    
    private static final String DATABASE_NAME = "data";
    //private static final String DATABASE_TABLE = "notes";
    private static final int DATABASE_VERSION = 3;
    
    private final Context mCtx;
    
    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
        	// Create our db tables
            db.execSQL(FISHINGSPOT_CREATE);
            //db.execSQL(BAIT_CREATE);
            //db.execSQL(FISH_CREATE);
            db.execSQL(FISHCAUGHT_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            
            db.execSQL("DROP TABLE IF EXISTS " + FISHCAUGHT_TABLE);
            //db.execSQL("DROP TABLE IF EXISTS " + BAIT_TABLE);
            //db.execSQL("DROP TABLE IF EXISTS " + FISH_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + SPOT_TABLE);
            onCreate(db);
        }
    }

    public AnglerDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }
    
    public AnglerDbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        
        populateDefault();
        
        return this;
    }
    
    private void populateDefault() {
    	if(!isFishingSpot()) {
    		// Make sure we have a default fishing spot
    		createFishingSpot("Default Fishing Spot", 0, 0, "");
    	}
    }

    public void close() {
        mDbHelper.close();
    }
    
    /*
    public long createFish(String name) {
    	ContentValues initialValues = new ContentValues();
        initialValues.put(FISH_NAME, name);
        return mDb.insert(FISH_TABLE, null, initialValues);
    }
    
    public long createBait(String name) {
    	ContentValues initialValues = new ContentValues();
        initialValues.put(BAIT_NAME, name);
        return mDb.insert(BAIT_TABLE, null, initialValues);
    }
    */
    
    public long createFishingSpot(String name, double lat, double lon, String photo) {
    	ContentValues initialValues = new ContentValues();
        initialValues.put(SPOT_NAME, name);
        initialValues.put(SPOT_LAT, lat);
        initialValues.put(SPOT_LON, lon);
        initialValues.put(SPOT_PHOTO, photo);
        return mDb.insert(SPOT_TABLE, null, initialValues);
    }

    public long createFishCaught(String bait, long spotId, String photo, String fish, String weather, long time, double lat, double lon, double water) {
    	ContentValues initialValues = new ContentValues();
    	
    	initialValues.put(CAUGHT_PHOTO, photo);
    	initialValues.put(CAUGHT_BAIT_ID, bait);
    	initialValues.put(CAUGHT_FISH_ID, fish);
    	initialValues.put(CAUGHT_SPOT_ID, spotId);
    	initialValues.put(CAUGHT_WEATHER, weather);
        initialValues.put(CAUGHT_TIME, time);
        initialValues.put(CAUGHT_LAT, lat);
        initialValues.put(CAUGHT_LON, lon);
        initialValues.put(CAUGHT_WATER, water);
        
        return mDb.insert(FISHCAUGHT_TABLE, null, initialValues);
    }
    
    /******************* Delete methods **********************/
    public boolean deleteFish(long rowId) {
        return mDb.delete(FISH_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }
    
    public boolean deleteBait(long rowId) {
        return mDb.delete(BAIT_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }
    
    public boolean deleteFishCaught(long rowId) {
        return mDb.delete(FISHCAUGHT_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }
    
    public boolean deleteFishingSpot(long rowId) {
        return mDb.delete(FISHCAUGHT_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }
    
    /******************* Fetch All methods **********************/

    public Cursor fetchAllFish() {
        return mDb.query(FISH_TABLE, new String[] {KEY_ROWID, FISH_NAME }, null, null, null, null, null);
    }
    
    public Cursor fetchAllBait() {
        return mDb.query(BAIT_TABLE, new String[] {KEY_ROWID, BAIT_NAME }, null, null, null, null, null);
    }
    
    public Cursor fetchAllFishingSpots() {
        return mDb.query(SPOT_TABLE, new String[] {KEY_ROWID, SPOT_NAME, SPOT_LAT, SPOT_LON, SPOT_PHOTO }, null, null, null, null, null);
    }
    
    public Cursor fetchAllFishCaught() {
        return mDb.query(FISHCAUGHT_TABLE, new String[] {KEY_ROWID, CAUGHT_BAIT_ID, CAUGHT_FISH_ID, CAUGHT_SPOT_ID, CAUGHT_WEATHER, CAUGHT_TIME, CAUGHT_LAT, CAUGHT_LON, CAUGHT_PHOTO }, null, null, null, null, null);
    }
    
    /******************* Individual Fetch methods **********************/
    
    public Cursor fetchFish(long rowId) throws SQLException {
        Cursor mCursor = mDb.query(true, FISH_TABLE, new String[] {KEY_ROWID, FISH_NAME}, KEY_ROWID + "=" + rowId, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    
    public Cursor fetchBait(long rowId) throws SQLException {
        Cursor mCursor = mDb.query(true, BAIT_TABLE, new String[] {KEY_ROWID, BAIT_NAME}, KEY_ROWID + "=" + rowId, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    
    public Cursor fetchFishingSpot(long rowId) throws SQLException {
        Cursor mCursor = mDb.query(true, SPOT_TABLE, new String[] {KEY_ROWID, SPOT_NAME, SPOT_LAT, SPOT_LON, SPOT_PHOTO}, KEY_ROWID + "=" + rowId, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    
    public boolean isFishingSpot() {
    	try {
    		Cursor mCursor = mDb.query(true, SPOT_TABLE, new String[] {KEY_ROWID, SPOT_NAME, SPOT_LAT, SPOT_LON, SPOT_PHOTO}, KEY_ROWID + "!=" + 0, null, null, null, null, null);
    		if (mCursor != null) {
    			return true;
    		}
    	}
    	catch(SQLException e) {}
    	return false;
    }
    
    public Cursor fetchFishCaught(long rowId) throws SQLException {
        Cursor mCursor = mDb.query(true, SPOT_TABLE, new String[] {KEY_ROWID, CAUGHT_BAIT_ID, CAUGHT_FISH_ID, CAUGHT_SPOT_ID, CAUGHT_WEATHER, CAUGHT_TIME, CAUGHT_LAT, CAUGHT_LON, CAUGHT_PHOTO }, KEY_ROWID + "=" + rowId, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    
    /****************** Update methods *************************/
    public boolean updateFish(long rowId, String name) {
        ContentValues args = new ContentValues();
        args.put(FISH_NAME, name);
        return mDb.update(FISH_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
    
    public boolean updateBait(long rowId, String name) {
        ContentValues args = new ContentValues();
        args.put(BAIT_NAME, name);
        return mDb.update(BAIT_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
    
    public boolean updateFishingSpot(long rowId, String name, Double lat, Double lon, String photo) {
        ContentValues args = new ContentValues();
        args.put(SPOT_NAME, name);
        args.put(SPOT_LAT, lat);
        args.put(SPOT_LON, lon);
        args.put(SPOT_PHOTO, photo);
        return mDb.update(SPOT_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
    
    public boolean updateFishCaught(long rowId, String bait, long spotId, String photo, String fish, String weather, long time, double lat, double lon, double water) {
        ContentValues args = new ContentValues();
        args.put(CAUGHT_WEATHER, weather);
        args.put(CAUGHT_TIME, time);
        args.put(CAUGHT_LAT, lat);
        args.put(CAUGHT_LON, lon);
        
        args.put(CAUGHT_PHOTO, photo);
        args.put(CAUGHT_BAIT_ID, bait);
        args.put(CAUGHT_FISH_ID, fish);
        args.put(CAUGHT_SPOT_ID, spotId);
        args.put(CAUGHT_WATER, water);
        
        return mDb.update(FISH_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
}