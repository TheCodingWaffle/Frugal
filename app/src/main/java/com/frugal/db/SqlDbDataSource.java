package com.frugal.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.frugal.main.R;

import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class SqlDbDataSource {


    SqlDbHelper dbhelper;
    SQLiteDatabase db;

    public SqlDbDataSource(Context context) {
        dbhelper = SqlDbHelper.getInstance(context);
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

    public Cursor selectAllGivenTimeId(String table, int timeFragmentSelected) {

        StringBuilder sql = new StringBuilder("SELECT * FROM ");
        sql.append(table);
        sql.append(" WHERE ");


        if (timeFragmentSelected == R.id.dayTitle || timeFragmentSelected == R.id.dayTotal) {
            sql.append(buildDateWhere(new Date(), new Date()));
        } else if (timeFragmentSelected == R.id.weekTitle || timeFragmentSelected == R.id.weekTotal) {
            sql.append(getWeekTime(new Date()));
        } else if (timeFragmentSelected == R.id.monthTitle || timeFragmentSelected == R.id.monthTotal) {
            sql.append(getCurrentMonthTime());
        }

        return db.rawQuery(sql.toString(), null);

    }

    private Cursor getSumWithDate(String query) {
        return db.rawQuery(query, null);
    }

    public Cursor getSumOfDay(String column, String table, Date day) {

        StringBuilder query = getSumStringBuilder(column, table);

        query.append(buildDateWhere(day, day));

        return getSumWithDate(query.toString());
    }

    /**
     * Returns the SUM of the provided column. @param firstDayOfWeek will be
     * used to as the start of the date range + 7 days.
     *
     * @param column
     * @param table
     * @param dayOfTheWeek
     * @return
     */
    public Cursor getSumOfWeek(String column, String table, Date dayOfTheWeek) {

        StringBuilder query = getSumStringBuilder(column, table);

        query.append(getWeekTime(dayOfTheWeek));

        return getSumWithDate(query.toString());
    }

    public Cursor getSumOfMonth(String column, String table, int calendarMonth, int year) {

        StringBuilder query = getSumStringBuilder(column, table);

        query.append(getMonthTime(calendarMonth, year));

        return getSumWithDate(query.toString());
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

    private String getWeekTime(Date dayOfTheWeek) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dayOfTheWeek);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());


        Date beginningDate = getStartOfDay(cal.getTime());

        cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);

        Date endDate = cal.getTime();

        return buildDateWhere(beginningDate, endDate);
    }

    private String getCurrentMonthTime() {

        Calendar cal = Calendar.getInstance();
        return getMonthTime(cal.get(Calendar.MONTH), cal.get(Calendar.YEAR));
    }

    private String getMonthTime(int calendarMonth, int year) {

        Calendar cal = new GregorianCalendar(year, calendarMonth, 1);

        Date beginningDate = cal.getTime();

        cal = new GregorianCalendar(year, calendarMonth, cal.getActualMaximum(Calendar.DAY_OF_MONTH));

        Date endDate = cal.getTime();

        return buildDateWhere(beginningDate, endDate);

    }

    private String buildDateWhere(Date greaterThan, Date lessThan) {
        StringBuilder query = new StringBuilder();
        query.append(SqlDbHelper.COL_DATE);
        query.append(" >= ");
        query.append(getStartOfDay(greaterThan).getTime());
        query.append(" AND ");
        query.append(SqlDbHelper.COL_DATE);
        query.append(" <= ");
        query.append(getEndOfDay(lessThan).getTime());

        return query.toString();
    }

    public static Date getEndOfDay(Date date) {
        return DateUtils.addMilliseconds(DateUtils.ceiling(date, Calendar.DATE), -1);
    }

    public static Date getStartOfDay(Date date) {
        return DateUtils.truncate(date, Calendar.DATE);
    }

    public boolean removeExpenseRecord(int recordId) {

        String whereClause = SqlDbHelper.COL_ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(recordId)};

        return db.delete(SqlDbHelper.TABLE_EXPENSES, whereClause, whereArgs) > 0;
    }
}
