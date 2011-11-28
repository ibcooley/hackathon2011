package com.sparcedge.groceryscanner;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.*;
import android.database.sqlite.*;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.widget.Toast;

public class DBAdapter {
	private static final String DATABASE_NAME = "groceryscanner.db";
	private static final String DATABASE_TABLE = "deviceTable";
	private static final int DATABASE_VERSION = 2;
	
	// The index (key) column name for use in where clauses
	public static final String KEY_ID="_id";
	
	// The name and column index of each column in your database
	public static final String KEY_LIST="list";  
	public static final int LIST_COLUMN = 1;
	public static final String KEY_NAME="name";            // device address
	public static final int NAME_COLUMN = 2;
	public static final String KEY_UPC="upc";   // device admin passwd
	public static final int UPC_COLUMN = 3;
	public static final String KEY_IMG="img";               // device bt pin
	public static final int IMG_COLUMN = 4;
	public static final String KEY_QTY="qty";          // device alias
	public static final int QTY_COLUMN = 5;
	
	// SQL to create a new db
	private static final String DATABASE_CREATE = "create table " + 
	  DATABASE_TABLE + " (" + KEY_ID + 
	  " integer primary key autoincrement, " +
	  KEY_LIST + " string not null, " +
	  KEY_NAME + " string not null, " +
	  KEY_UPC + " string not null, " +
	  KEY_IMG + " string not null, " +
	  KEY_QTY + " string not null);";
	
	// var to hold db instance
	private SQLiteDatabase db;
	
	// Context of the app using the db
	private final Context context;
	
	// Db open/upgrade helper
	private myDbHelper dbHelper;
	
	//@DONE
	public DBAdapter (Context _context) {
		context = _context;
		dbHelper = new myDbHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	//@DONE
	public DBAdapter open() throws SQLException {
		db = dbHelper.getWritableDatabase();
		if(db == null) {
			Toast.makeText(context, "DBAdapter.open(): null", Toast.LENGTH_SHORT);
		}
		return this;
	}
	
	//@DONE
	public void close() {
		db.close();
	}
	
	//@DONE
	public long insertEntry(ItemInfo _myObject) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(KEY_LIST, _myObject.getList());
		contentValues.put(KEY_NAME, _myObject.getName());
		contentValues.put(KEY_UPC, _myObject.getUPC());
		contentValues.put(KEY_IMG, _myObject.getImg());
		contentValues.put(KEY_QTY, _myObject.getQty());
		return db.insert(DATABASE_TABLE, null, contentValues);
	}
	
	//@DONE
	public Boolean removeEntry(ItemInfo i) {
		return db.delete(DATABASE_TABLE, 
                KEY_LIST + " = '" + i.getList() + "' AND " + KEY_NAME + " = '" + i.getName() + "'", null) > 0;
	}
	
	//@DONE
	public Boolean deleteList(String list_name) {
		return db.delete(DATABASE_TABLE,
				KEY_LIST + " = '" + list_name + "'", null) > 0;
	}
	
	public ArrayList<String> getListNames() {
		ArrayList<String> lists = new ArrayList<String>();

		Cursor cursor = db.rawQuery("select list from deviceTable", null); 
		
		if((cursor.getCount() == 0) || !cursor.moveToFirst()) {
			cursor.close();
			GroceryScanner.LOG("Crap. getListNames()");
			return null;
		}
		cursor.moveToFirst();
		while(cursor.isAfterLast() == false) {
			String xlist = cursor.getString(0);
			if(! lists.contains(xlist)) {
				lists.add(xlist);
			}
       	    cursor.moveToNext();
		}
		return lists;
	}
	
	//@DONE
	public ArrayList<ItemInfo> getList(String name) {
		ArrayList<ItemInfo> list = new ArrayList<ItemInfo>();
		
		Cursor cursor = db.query(true, DATABASE_TABLE,
				new String[] {KEY_LIST, KEY_NAME, KEY_UPC, KEY_IMG, KEY_QTY},
				KEY_LIST + "= '" + name + "'",
				null, null, null, null, null);
		if((cursor.getCount() == 0) || !cursor.moveToFirst()) {		
			GroceryScanner.LOG( "No shopping list found for: " + name);
			cursor.close();
			return null;
		}	
		
		cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
        	String xlist = "";
        	try {
        		xlist = cursor.getString(LIST_COLUMN);
        	} catch (Exception e) {}
        	String xname = "";
        	try {
        		xname = cursor.getString(NAME_COLUMN);
        	} catch (Exception e) {} 
        	String xupc = "";
        	try {
        		xupc = cursor.getString(UPC_COLUMN);
        	} catch (Exception e) {} 
        	String ximg = "";
        	try {
        		ximg = cursor.getString(IMG_COLUMN);
        	} catch (Exception e) {} 
        	String xqty = "";
        	try {
        		xqty = cursor.getString(QTY_COLUMN);
        	} catch (Exception e) {} 
        	
        	ItemInfo i = new ItemInfo(xlist, xname, xupc, ximg, xqty);
        	list.add(i);
       	    cursor.moveToNext();
        }
        cursor.close();
        
		return list;
	}
	
	//@DONE
	public boolean removeEntry(long _rowIndex) {
		return db.delete(DATABASE_TABLE, KEY_ID + "=" + _rowIndex, null) > 0;
	}
				
	//@DONE
	public void wipetable() {
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
		db.execSQL(DATABASE_CREATE);
	}
	
	//@DONE
	public int getRecordCount() {
		Cursor cursor = db.rawQuery("select addr from deviceTable", null); 
		int count = cursor.getCount();
		cursor.close();
		return count;
	}
	
	//@DONE
	private static class myDbHelper extends SQLiteOpenHelper {
		public myDbHelper(Context context, String name, 
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}
		
		// called when no db exists in disk and helper class 
		// needs to create a new one
		@Override
		public void onCreate(SQLiteDatabase _db) {
			_db.execSQL(DATABASE_CREATE);
		}

		// called when there is a db version mismatch meaning that
		// the version of the db on disk needs to be upgraded to 
		// the current version
		@Override
		public void onUpgrade(SQLiteDatabase _db, int _oldVersion,
				int _newVersion) {
			// log the version upgrade
			GroceryScanner.LOG( "Upgrading DB from version " + 
					_oldVersion + " to " + 
					_newVersion + 
					", which will destroy all old data");
			
			// upgrade existing db to conform to new version.
			// simple case is to drop old table and create a new one
			_db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			// craete new one
			onCreate(_db);
		}
	}
}
