package hackathon.team08;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: grantlewis
 * Date: 8/27/11
 * Time: 1:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class RetortDB {
    private static String DATABASE_PATH = "/data/data/hackathon.team08/databases/";
    private static final String DATABASE_NAME = "retort.sqlite";
    private static final int DATABASE_VERSION = 2;

    private Context mCtx;
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase retortDB;

    public RetortDB(Context ctx) {
        this.mCtx = ctx;
    }

    public void open() {
        mDbHelper = new DatabaseHelper(mCtx);
        try {
            mDbHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        try {
            // open the database
            String myPath = DATABASE_PATH + DATABASE_NAME;
            retortDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLiteException e) {
            e.printStackTrace(System.out);
        }
        mDbHelper.close();
    }

    public void close() {
        mDbHelper.close();
        retortDB.close();
    }


    public List<Retort> getRetorts(String subject, String mode) {
        List<Retort> retorts = new ArrayList<Retort>();

        Cursor c = retortDB.query("retort", null,
                "subject = ? and style = ?", new String[] {subject, mode},
                null, null, null, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            Retort retort = new Retort();
            retort.setId(c.getInt(c.getColumnIndex("_id")));
            retort.setSubject(c.getString(c.getColumnIndex("subject")));
            retort.setStyle(c.getString(c.getColumnIndex("style")));
            retort.setRetort(c.getString(c.getColumnIndex("retort")));
            retorts.add(retort);
            c.moveToNext();
        }

        return retorts;
    }


    private static class DatabaseHelper extends SQLiteOpenHelper {
        private final Context myContext;
        private SQLiteDatabase myDb;

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.myContext = context;
        }

        public void addRetort(Retort retort) {
            ContentValues rowValues = new ContentValues();
            rowValues.put("subject", retort.getSubject());
            rowValues.put("style", retort.getStyle());
            rowValues.put("retort", retort.getRetort());

            DatabaseUtils.InsertHelper insertHelper =
                    new DatabaseUtils.InsertHelper(myDb, "retort");
            insertHelper.insert(rowValues);
        }

        public void createDataBase() throws IOException {
            boolean dbExist = checkDataBase();

            if (dbExist) {
                //do nothing - database already exist
            } else {
                //By calling this method an empty database will be created into the default system path
                //of your application so we are gonna be able to overwrite that database with our database.
                this.getReadableDatabase();

                try {
                    copyDataBase();

                    myDb = SQLiteDatabase.openDatabase(
                            DATABASE_PATH + DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE);
                    Retort retort = new Retort();
                    retort.setSubject("Case of the Mondays");
                    retort.setStyle("01");
                    retort.setRetort("gayMarriage.mp3");
                    addRetort(retort);
                    retort.setSubject("Cheer Them Up");
                    retort.setStyle("02");
                    retort.setRetort("mormons.mp3");
                    addRetort(retort);
                    retort.setSubject("Pat on the Back");
                    retort.setStyle("03");
                    retort.setRetort("gayMarriage.mp3");
                    addRetort(retort);
                 } catch (IOException e) {
                    e.printStackTrace();
                    throw new Error("Error copying database");
                }
            }
        }

        private boolean checkDataBase() {
            SQLiteDatabase checkDB = null;

            try {
                String myPath = DATABASE_PATH + DATABASE_NAME;
                checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

            } catch (SQLiteException e) {
                //database does't exist yet.
            }

            if (checkDB != null) {
                checkDB.close();
            }

            return checkDB != null ? true : false;
        }

        private void copyDataBase() throws IOException {

            //Open your local db as the input stream
            InputStream myInput = myContext.getAssets().open(DATABASE_NAME);

            // Path to the just created empty db
            String outFileName = DATABASE_PATH + DATABASE_NAME;

            //Open the empty db as the output stream
            OutputStream myOutput = new FileOutputStream(outFileName);

            //transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }

            //Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(DATABASE_NAME, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS demodata");
            onCreate(db);
        }
    }
}
