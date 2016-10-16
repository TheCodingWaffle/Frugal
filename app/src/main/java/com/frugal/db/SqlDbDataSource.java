package com.frugal.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Waffles on 7/5/2014.
 */
public class SqlDbDataSource {


    SQLiteOpenHelper dbhelper;
    SQLiteDatabase db;

    public SqlDbDataSource(Context context) {
        dbhelper = new SqlDbHelper(context);
        db = dbhelper.getWritableDatabase();
    }

    public void open() {
        db = dbhelper.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public void dbInsert(ContentValues values, String table) {
        db.insert(table, null, values);
    }

    public Cursor getAll(String table, String[] columns) {
        return db.query(table, columns, null, null, null, null, null);
    }

    public Cursor getAllWhere(String table, String[] columns, String where) {
        return db.query(table, columns, where, null, null, null, null);
    }

    public Cursor selectAll (String table)
    {
        return db.rawQuery("SELECT * FROM " + table,null);
    }
}
