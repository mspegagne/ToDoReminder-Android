package com.Ms.todoreminder.DataBase;

/**
 * @author SPEGAGNE Mathieu on 14/03/15.
 * @author https://github.com/mspegagne
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * DB Creation
 */
public class DBhelper extends SQLiteOpenHelper {

    // Table Name
    public static final String TABLE_NAME = "TODOS";

    // Table columns
    public static final String _ID = "_id";
    public static final String TODO_TITLE = "title";
    public static final String TODO_TEXT = "text";
    public static final String TODO_DATE_YEAR = "year";
    public static final String TODO_DATE_MONTH = "month";
    public static final String TODO_DATE_DAY = "day";
    public static final String TODO_HISTORY = "history";
    public static final String TODO_NOTIF = "notif";

    // Database Information
    static final String DB_NAME = "TODOREMINDER.DB";

    // database version
    static final int DB_VERSION = 2;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TODO_TITLE + " TEXT NOT NULL, " + TODO_TEXT + " TEXT,"
            + TODO_DATE_YEAR + " INT NOT NULL," + TODO_DATE_MONTH + " INT NOT NULL," + TODO_DATE_DAY + " INT NOT NULL,"
            + TODO_HISTORY + " INT NOT NULL," + TODO_NOTIF + " INT NOT NULL);";

    public DBhelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}