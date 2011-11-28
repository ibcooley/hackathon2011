package com.awarity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbAdapter
{
	public static final String KEY_TASKS_ID = "_id";
	public static final String KEY_TASKS_ISTASKCOMPLETE = "IsTaskComplete";
	public static final String KEY_TASKS_ETAINMINUTES = "ETAInMinutes";
	public static final String KEY_TASKS_TASKNAME = "TaskName";
	
	private static final String[] TASK_KEYS = { 
		KEY_TASKS_ID, 
		KEY_TASKS_ISTASKCOMPLETE, 
		KEY_TASKS_ETAINMINUTES, 
		KEY_TASKS_TASKNAME
		};
	
	
	public static final String KEY_EFFORTS_TASKNUMBER = "TaskNumber";
	public static final String KEY_EFFORTS_DESCRIPTION = "Description";
	public static final String KEY_EFFORTS_ID = "_id";
	public static final String KEY_EFFORTS_ESTIMATEDCOMPLETIONTIMEINMINUTES = "EstimatedCompletionTimeInMinutes";
	public static final String KEY_EFFORTS_ELAPSEDTIMEINMINUTES = "ElapsedTimeInMinutes";
	public static final String KEY_EFFORTS_QUALITYOFPROGRESS = "QualityOfProgress";
	
	private static final String[] EFFORTS_KEYS = { 
		KEY_EFFORTS_TASKNUMBER,
		KEY_EFFORTS_DESCRIPTION,
		KEY_EFFORTS_ID, 
		KEY_EFFORTS_ESTIMATEDCOMPLETIONTIMEINMINUTES, 
		KEY_EFFORTS_ELAPSEDTIMEINMINUTES,
		KEY_EFFORTS_QUALITYOFPROGRESS
		};
	
	
	
	public static final String KEY_OBSTACLES_ID = "_id";
	public static final String KEY_OBSTACLES_EFFORTNUMBER = "EffortNumber";
	public static final String KEY_OBSTACLES_OBSTICLETYPE = "ObsticleType";
	public static final String KEY_OBSTACLES_DESCRIPTION = "Description";
	
	private static final String[] obstacles_KEYS = { 
		KEY_OBSTACLES_ID, 
		KEY_OBSTACLES_EFFORTNUMBER, 
		KEY_OBSTACLES_OBSTICLETYPE, 
		KEY_OBSTACLES_DESCRIPTION
		};
	
	
	
	public static final String KEY_TASKCOMPLETE_ID = "_id";
	public static final String KEY_TASKCOMPLETE_TASKNUMBER = "TaskNumber";
	public static final String KEY_TASKCOMPLETE_PERSONALEFFORT = "PersonalEffort";
	public static final String KEY_TASKCOMPLETE_QUALITYOFOUTCOME = "QualityOfOutcome";
	public static final String KEY_TASKCOMPLETE_TIMEVSEFFORTRATIO = "TimeVsEffortRatio";
	public static final String KEY_TASKCOMPLETE_FUTURECHANGES = "FutureChanges";
	
	private static final String[] TASKCOMPLETE_KEYS = {
		KEY_TASKCOMPLETE_ID, 
		KEY_TASKCOMPLETE_TASKNUMBER, 
		KEY_TASKCOMPLETE_PERSONALEFFORT, 
		KEY_TASKCOMPLETE_QUALITYOFOUTCOME,
		KEY_TASKCOMPLETE_TIMEVSEFFORTRATIO,
		KEY_TASKCOMPLETE_FUTURECHANGES
		};
	
	
	public static final String KEY_DEPENDENCIES_ID = "_id";
	public static final String KEY_DEPENDENCIES_TASKNUMBER = "TaskNumber";
	public static final String KEY_DEPENDENCIES_WHO = "Who";
	
	public static final String[] DEPENDENCIES={KEY_DEPENDENCIES_WHO};
	
	private static final String[] DEPENDENCIES_KEYS = { 
		KEY_DEPENDENCIES_ID, 
		KEY_DEPENDENCIES_TASKNUMBER, 
		KEY_DEPENDENCIES_WHO
		};
	
	
	public static final String KEY_TYPEOFWORK_ID = "_id";
	public static final String KEY_TYPEOFWORK_TASKNUMBER = "TaskNumber";
	public static final String KEY_TYPEOFWORK_TYPE = "Type";
	
	private static final String[] TYPEOFWORK_KEYS = { 
		KEY_TYPEOFWORK_ID, 
		KEY_TYPEOFWORK_TASKNUMBER, 
		KEY_TYPEOFWORK_TYPE 
		};
	
	
//	private static final String[] OBSTACLES =
//	{ "Tools", "People", "Environment", "Personal" };
	

	private static final String TAG = "DbAdapter";
	private DatabaseHelper mDbHelper;	
//	public int getEntryNumber()
//	{
//		return entryNumber;
//	}
	private SQLiteDatabase mDb;



	private static final String DATABASE_NAME = "AwarityDB";
	private static final String DATABASE_TABLE_TASKS = "tasks";
	private static final String DATABASE_TABLE_EFFORTS = "efforts";
	private static final String DATABASE_TABLE_OBSTACLES = "obstacles";
	private static final String DATABASE_TABLE_TASKCOMPLETE = "taskcomplete";
	private static final String DATABASE_TABLE_DEPENDENCIES = "dependencies";
	private static final String DATABASE_TABLE_TYPEOFWORK = "typeofwork";
	private static final int DATABASE_VERSION = 1;
	

	private static final String DATABASE_CREATE_TASKS = "create table tasks (_id integer primary key autoincrement, "
			+ " IsTaskComplete int, ETAInMinutes text, TaskName text);";
	
	private static final String DATABASE_CREATE_EFFORTS = "create table efforts (_id integer primary key autoincrement, "
			+ " TaskNumber int, EstimatedCompletionTimeInMinutes int, ElapsedTimeInMinutes int, "
			+ " QualityOfProgress int, Description text);";
	
	private static final String DATABASE_CREATE_OBSTACLES = "create table obstacles (_id integer primary key autoincrement, "
			+ " EffortNumber int, ObstacleType text, Description text);";
	
	private static final String DATABASE_CREATE_TASKCOMPLETE = "create table taskcomplete (_id integer primary key autoincrement, "
			+ " TaskNumber int, PersonalEffort int, QualityOfOutcome int, "
			+ " TimeVsEffortRatio int, FutureChanges text);";
	
	private static final String DATABASE_CREATE_DEPENDENCIES = "create table dependencies (_id integer primary key autoincrement, "
			+ " TaskNumber int, Who text);";
	
	private static final String DATABASE_CREATE_TYPEOFWORK = "create table typeofwork (_id integer primary key autoincrement, "
			+ " TaskNumber int, Type text);";
	
	
	private final Context mCtx;

	private static class DatabaseHelper extends SQLiteOpenHelper
	{

		DatabaseHelper(Context context)
		{
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db)
		{
			db.execSQL(DATABASE_CREATE_DEPENDENCIES);
			db.execSQL(DATABASE_CREATE_EFFORTS);
			db.execSQL(DATABASE_CREATE_OBSTACLES);
			db.execSQL(DATABASE_CREATE_TASKCOMPLETE);
			db.execSQL(DATABASE_CREATE_TASKS);
			db.execSQL(DATABASE_CREATE_TYPEOFWORK);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
		{
//			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
//					+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS Tasks");
			db.execSQL("DROP TABLE IF EXISTS Efforts");
			db.execSQL("DROP TABLE IF EXISTS Obstacles");
			db.execSQL("DROP TABLE IF EXISTS TaskComplete");
			db.execSQL("DROP TABLE IF EXISTS Dependencies");
			db.execSQL("DROP TABLE IF EXISTS TypeOfWork");
			onCreate(db);
		}	
	}

	/**
	 * Constructor - takes the context to allow the database to be
	 * opened/created
	 * 
	 * @param ctx
	 *            the Context within which to work
	 */
	public DbAdapter(Context ctx)
	{
		this.mCtx = ctx;
	}

	/**
	 * Open the database. If it cannot be opened, try to create a new
	 * instance of the database. If it cannot be created, throw an exception to
	 * signal the failure
	 * 
	 * @return this (self reference, allowing this to be chained in an
	 *         initialization call)
	 * @throws SQLException
	 *             if the database could be neither opened or created
	 */
	public DbAdapter open() throws SQLException
	{
		mDbHelper = new DatabaseHelper(mCtx);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}

	public void close()
	{
		mDbHelper.close();
	}

	/**
	 * Create a new note using the title and body provided. If the note is
	 * successfully created return the new rowId for that note, otherwise return
	 * a -1 to indicate failure.
	 * 
	 * @param title
	 *            the title of the note
	 * @param body
	 *            the body of the note
	 * @return rowId or -1 if failed
	 */
	
	public long createTask(Task task){
		ContentValues args= new ContentValues();
		
		args.put(KEY_TASKS_ETAINMINUTES, task.getETA());
		args.put(KEY_TASKS_ISTASKCOMPLETE, false);
		args.put(KEY_TASKS_TASKNAME, task.getName());
				
		return mDb.insert(DATABASE_TABLE_TASKS, null, args);
	}
	
	public long createEffort(Effort effort){
		ContentValues args= new ContentValues();

		args.put(KEY_EFFORTS_ELAPSEDTIMEINMINUTES, effort.getElapsedTime());
		args.put(KEY_EFFORTS_DESCRIPTION, effort.getDescription());
		args.put(KEY_EFFORTS_ESTIMATEDCOMPLETIONTIMEINMINUTES, effort.getEstCompTime());
		args.put(KEY_EFFORTS_QUALITYOFPROGRESS, effort.getProgressQuality());
		args.put(KEY_EFFORTS_TASKNUMBER, effort.getTaskNumber());
				
		return mDb.insert(DATABASE_TABLE_EFFORTS, null, args);
	}
	
	public long createTaskComplete(CompleteTask taskcomplete){
		ContentValues args= new ContentValues();

//		args.put(KEY_TASKCOMPLETE_ID, taskcomplete.getEntryNumber());
		args.put(KEY_TASKCOMPLETE_FUTURECHANGES, taskcomplete.getChanges());
		args.put(KEY_TASKCOMPLETE_PERSONALEFFORT, taskcomplete.getEffort());
		args.put(KEY_TASKCOMPLETE_QUALITYOFOUTCOME, taskcomplete.getQuality());
		args.put(KEY_TASKCOMPLETE_TASKNUMBER, taskcomplete.getTaskNumber());
		args.put(KEY_TASKCOMPLETE_TIMEVSEFFORTRATIO, taskcomplete.getRatio());
		
		return mDb.insert(DATABASE_TABLE_TASKCOMPLETE, null, args);
	}
	
	public long createDependencies(Dependency dependencies){
		ContentValues args= new ContentValues();

//		args.put(KEY_DEPENDENCIES_ID, dependencies.getEntryNumber());
		args.put(KEY_DEPENDENCIES_TASKNUMBER, dependencies.getTaskNumber());
		args.put(KEY_DEPENDENCIES_WHO, dependencies.getWho());
				
		return mDb.insert(DATABASE_TABLE_EFFORTS, null, args);
	}
	
	public long createTypeOfWork(WorkType typeofwork){
		ContentValues args= new ContentValues();

//		args.put(KEY_TYPEOFWORK_ID, typeofwork.getEntryNumber());
		args.put(KEY_TYPEOFWORK_TASKNUMBER, typeofwork.getTaskNumber());
		args.put(KEY_TYPEOFWORK_TYPE, typeofwork.getType());
				
		return mDb.insert(DATABASE_TABLE_EFFORTS, null, args);
	}
	
	public long createObstacle(Obstacle obstacle){
		ContentValues args= new ContentValues();

		args.put(KEY_OBSTACLES_DESCRIPTION, obstacle.getDescription());
		args.put(KEY_OBSTACLES_EFFORTNUMBER, obstacle.getEffortNumber());
//		args.put(KEY_OBSTACLES_ID, obstacle.getEntryNumber());
		args.put(KEY_OBSTACLES_OBSTICLETYPE, obstacle.getType());
		
		return mDb.insert(DATABASE_TABLE_EFFORTS, null, args);
	}

	/**
	 * Delete the note with the given rowId
	 * 
	 * @param rowId
	 *            id of note to delete
	 * @return true if deleted, false otherwise
	 */
	public boolean deleteTask(long rowId)
	{

		return mDb.delete(DATABASE_TABLE_TASKS,
				KEY_TASKS_ID + "=" + rowId, null) > 0;
	}

	public Cursor fetchTasks(long rowId) throws SQLException
	{
		
		Cursor mCursor =

		mDb.query(true, DATABASE_TABLE_TASKS, TASK_KEYS, KEY_TASKS_ID
				+ "=" + rowId, null, null, null, null, null);
		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public Cursor fetchTasks() throws SQLException
	{
		Cursor mCursor =
		mDb.query(DATABASE_TABLE_TASKS, TASK_KEYS, null, null, null, null, null);
		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}
		return mCursor;
	}
	
	public Cursor fetchEfforts(long rowId) throws SQLException
	{
		Cursor mCursor =

		mDb.query(true, DATABASE_TABLE_EFFORTS, EFFORTS_KEYS, KEY_EFFORTS_TASKNUMBER
				+ "=" + rowId, null, null, null, null, null);
		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public Cursor fetchEfforts() throws SQLException
	{
		Cursor mCursor =

		mDb.query(DATABASE_TABLE_EFFORTS, EFFORTS_KEYS, null, null, null, null, null);
		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public Cursor fetchCompleteTask(long rowId) throws SQLException
	{
		Cursor mCursor =

		mDb.query(true, DATABASE_TABLE_TASKCOMPLETE, TASKCOMPLETE_KEYS, KEY_TASKCOMPLETE_TASKNUMBER
				+ "=" + rowId, null, null, null, null, null);
		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public Cursor fetchCompleteTask() throws SQLException
	{
		Cursor mCursor =

		mDb.query(DATABASE_TABLE_TASKCOMPLETE, TASKCOMPLETE_KEYS, null, null, null, null, null);
		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public Cursor fetchObstacles(long rowId) throws SQLException
	{
		Cursor mCursor =

		mDb.query(true, DATABASE_TABLE_OBSTACLES, obstacles_KEYS, KEY_OBSTACLES_EFFORTNUMBER
				+ "=" + rowId, null, null, null, null, null);
		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public Cursor fetchObstacles() throws SQLException
	{
		Cursor mCursor =

		mDb.query(DATABASE_TABLE_OBSTACLES, obstacles_KEYS, null, null, null, null, null);
		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}
		return mCursor;
	}
	
	public Cursor fetchDependencies(long rowId) throws SQLException
	{
		Cursor mCursor =

		mDb.query(true, DATABASE_TABLE_DEPENDENCIES, DEPENDENCIES_KEYS, KEY_DEPENDENCIES_TASKNUMBER
				+ "=" + rowId, null, null, null, null, null);
		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public Cursor fetchDependencies() throws SQLException
	{
		Cursor mCursor =

		mDb.query(DATABASE_TABLE_DEPENDENCIES, DEPENDENCIES_KEYS, null, null, null, null, null);
		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}
		return mCursor;
	}
	
	public Cursor fetchWorkTypes(long rowId) throws SQLException
	{
		Cursor mCursor =

		mDb.query(true, DATABASE_TABLE_TYPEOFWORK, TYPEOFWORK_KEYS, KEY_TYPEOFWORK_TASKNUMBER
				+ "=" + rowId, null, null, null, null, null);
		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public Cursor fetchWorkTypes() throws SQLException
	{
		Cursor mCursor =

		mDb.query(DATABASE_TABLE_OBSTACLES, obstacles_KEYS, null, null, null, null, null);
		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	
//	public boolean updateTask(long rowId, Task task)
//	{
//		ContentValues args = new ContentValues();
//		//args.put(KEY_PLAYER_CAREER, player.getCareer());
//
//		return mDb.update(DATABASE_TABLE_TASKS, args, KEY_TASKS_TASKNUMBER + "="
//				+ rowId, null) > 0;
//	}
	
	public boolean updateCompleteTask(long rowId, CompleteTask completeTask)
	{
		ContentValues args = new ContentValues();
		args.put(KEY_TASKCOMPLETE_ID, completeTask.getEntryNumber());
		args.put(KEY_TASKCOMPLETE_FUTURECHANGES, completeTask.getChanges());
		args.put(KEY_TASKCOMPLETE_PERSONALEFFORT, completeTask.getEffort());
		args.put(KEY_TASKCOMPLETE_QUALITYOFOUTCOME, completeTask.getQuality());
		args.put(KEY_TASKCOMPLETE_TIMEVSEFFORTRATIO, completeTask.getRatio());

		return mDb.update(DATABASE_TABLE_TASKCOMPLETE, args, KEY_TASKCOMPLETE_TASKNUMBER + "="
				+ rowId, null) > 0;
	}

}
