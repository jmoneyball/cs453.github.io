package edu.csueb.android.assignment3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocationsDB extends SQLiteOpenHelper {
    //todo define the following variables for your LocationsDB class
    public static final String DB_NAME = "locationsDB";
    public static final String DB_TABLE = "locations";
    public static final String DB_KEY = "key";
    public static final String DB_LAT = "lat";
    public static final String DB_LON = "lon";
    public static final String DB_ZOOM = "zoom";

    //todo a callback method that is invoked when the method getWriteableDatabase() is called, executed if the database doesn't exist
    private final SQLiteDatabase db = this.getWritableDatabase();

    public LocationsDB(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String DB_CREATE
                = "create table " + DB_TABLE + " ( "
                + DB_KEY + " integer primary key autoincrement , "
                + DB_LON + " double , "
                + DB_LAT + " double , "
                + DB_ZOOM + " text " + " ) ";
        db.execSQL(DB_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    //todo a method that returns all locations from the table
    public Cursor locationsDB_RETURN() {
        return db.query(DB_TABLE,
                new String[] {DB_KEY, DB_LAT, DB_LON, DB_ZOOM},
                null,null, null, null, null);
    }

    public int locationsDB_DELETE() {
        int i = db.delete(DB_TABLE, null, null);
        return i;
    }

    public long locationsDB_INSERT(ContentValues values) {
        long key = db.insert(DB_TABLE, null, values);
        return key;
    }
}
