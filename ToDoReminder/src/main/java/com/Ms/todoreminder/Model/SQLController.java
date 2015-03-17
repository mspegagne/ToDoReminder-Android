package com.Ms.todoreminder.Model;

/**
 * @author SPEGAGNE Mathieu on 14/03/15.
 * @author https://github.com/mspegagne
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class SQLController {

    private DBhelper dbHelper;
    private Context ourcontext;
    private SQLiteDatabase database;

    public SQLController(Context c) {
        ourcontext = c;
    }

    public SQLController open() throws SQLException {
        dbHelper = new DBhelper(ourcontext);
        database = dbHelper.getWritableDatabase();
        return this;

    }

    public void close() {
        dbHelper.close();
    }

    public long insert(String title, String text, int year, int month, int day, Boolean history, Boolean notif) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DBhelper.TODO_TITLE, title);
        contentValue.put(DBhelper.TODO_TEXT, text);
        contentValue.put(DBhelper.TODO_DATE_YEAR, year);
        contentValue.put(DBhelper.TODO_DATE_MONTH, month);
        contentValue.put(DBhelper.TODO_DATE_DAY, day);
        contentValue.put(DBhelper.TODO_HISTORY, history);
        contentValue.put(DBhelper.TODO_NOTIF, notif);
       return database.insert(DBhelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[] {
                DBhelper._ID,
                DBhelper.TODO_TITLE,
                DBhelper.TODO_TEXT,
                DBhelper.TODO_DATE_YEAR,
                DBhelper.TODO_DATE_MONTH,
                DBhelper.TODO_DATE_DAY,
                DBhelper.TODO_HISTORY,
                DBhelper.TODO_NOTIF };

        String orderBy = "year ASC, month ASC, day ASC";

        Cursor cursor = database.query(DBhelper.TABLE_NAME, columns, null,
                null, null, null, orderBy);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long _id, String title, String text, int year, int month, int day, Boolean history, Boolean notif) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBhelper.TODO_TITLE, title);
        contentValues.put(DBhelper.TODO_TEXT, text);
        contentValues.put(DBhelper.TODO_DATE_YEAR, year);
        contentValues.put(DBhelper.TODO_DATE_MONTH, month);
        contentValues.put(DBhelper.TODO_DATE_DAY, day);
        contentValues.put(DBhelper.TODO_HISTORY, history);
        contentValues.put(DBhelper.TODO_DATE_DAY, notif);
        int i = database.update(DBhelper.TABLE_NAME, contentValues,
                DBhelper._ID + " = " + _id, null);
        return i;
    }

    public void delete(long _id) {
        database.delete(DBhelper.TABLE_NAME, DBhelper._ID + "=" + _id, null);
    }
}