package com.frugal.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Waffles on 7/5/2014.
 */
public class SqlDbDataSource {


    SQLiteOpenHelper dbhelper;
    SQLiteDatabase db;

    private int WEEK_IN_MILLI = 604800000;

    private int DAY_IN_MILLI = 86400000;


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

    public Cursor selectAll(String table) {
        return db.rawQuery("SELECT * FROM " + table, null);
    }

    private Cursor getSumWithDate(String query) {
        return db.rawQuery(query, null);
    }

    private StringBuilder getSumStringBuilder(String column, String table) {
        StringBuilder query = new StringBuilder();

        query.append("SELECT SUM(");
        query.append(column);
        query.append(") AS total FROM ");
        query.append(table);
        query.append(" WHERE ");

        return query;
    }

    public Cursor getSumOfDay(String column, String table, Date day) {

        StringBuilder query = getSumStringBuilder(column, table);

        query.append(SqlDbHelper.COL_DATE);
        query.append(" >= ");
        query.append(getStartOfDay(day).getTime());
        query.append(" AND ");
        query.append(SqlDbHelper.COL_DATE);
        query.append(" <= ");
        query.append(getEndOfDay(day).getTime());

        return getSumWithDate(query.toString());
    }

    /**
     * Returns the SUM of the provided column. @param firstDayOfWeek will be
     * used to as the start of the date range + 7 days.
     *
     * @param column
     * @param table
     * @param firstDayOfWeek
     * @return
     */
    public Cursor getSumOfWeek(String column, String table, Date firstDayOfWeek) {

        StringBuilder query = getSumStringBuilder(column, table);

        query.append(SqlDbHelper.COL_DATE);
        query.append(" >= ");
        query.append(getStartOfDay(firstDayOfWeek).getTime());
        query.append(" AND ");
        query.append(SqlDbHelper.COL_DATE);
        query.append(" <= ");
        query.append(getStartOfDay(firstDayOfWeek).getTime() + WEEK_IN_MILLI);

        return getSumWithDate(query.toString());
    }

    public Cursor getSumOfMonth(String column, String table, int calendarMonth, int year) {

        Calendar cal = new GregorianCalendar(year, calendarMonth, 1);

        StringBuilder query = getSumStringBuilder(column, table);

        query.append(SqlDbHelper.COL_DATE);
        query.append(" >= ");
        query.append(getStartOfDay(cal.getTime()).getTime());
        query.append(" AND ");
        query.append(SqlDbHelper.COL_DATE);
        query.append(" <= ");

        cal = new GregorianCalendar(year,calendarMonth,cal.getActualMaximum(Calendar.DAY_OF_MONTH));

        query.append(getEndOfDay(cal.getTime()).getTime());

        return getSumWithDate(query.toString());
    }

    public static Date getEndOfDay(Date date) {
        return DateUtils.addMilliseconds(DateUtils.ceiling(date, Calendar.DATE), -1);
    }

    public static Date getStartOfDay(Date date) {
        return DateUtils.truncate(date, Calendar.DATE);
    }
}
