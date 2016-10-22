package com.frugal.com.frugal.test;

import android.database.Cursor;
import android.util.Log;

import com.frugal.db.SqlDbDataSource;
import com.frugal.db.SqlDbHelper;
import com.frugal.main.BillContainer;
import com.frugal.main.DateManager;
import com.frugal.main.ExpenseContainer;

import java.util.Calendar;
import java.util.Date;

public class DbTestCases {

    private static final String LOGTAG = "TEST: ";

    SqlDbDataSource dataSource;

    public DbTestCases(SqlDbDataSource dataSource) {

        this.dataSource = dataSource;

        if (this.dataSource != null) {
            //testBillInsert();
            testExpensesInsert();
            //testGetDatabaseContents();
            //testGetDatabaseWhereContents();
        } else {
            Log.i(LOGTAG, " DATASOURCE IS NULL");
        }
    }

    private void testBillInsert() {
        BillContainer bill = new BillContainer("rent", 100, true);

        dataSource.dbInsert(bill.getValues(), bill.getTable());
        Log.i(LOGTAG, " INSERT INTO BILLS TABLE");
    }

    private void testExpensesInsert() {
        ExpenseContainer expense = new ExpenseContainer( 10,"play","movie","Sooo Goooood",
                false,new Date(Calendar.getInstance().getTimeInMillis()));

        try {
            dataSource.dbInsert(expense.getValues(), expense.getTable());
        }
        catch (Exception e)
        {
            Log.i(LOGTAG, e.getMessage());
        }

        Log.i(LOGTAG, " INSERT INTO EXPENSES TABLE");
    }

    private void testGetDatabaseContents() {
        Cursor cursor = dataSource.getAll(SqlDbHelper.TABLE_EXPENSES, SqlDbHelper.EXPENSES_COLLUMNS);

        if (cursor.getCount() > 0) {
            Log.i(LOGTAG, " GETTING ALL");
            while (cursor.moveToNext()) {
                Log.i(LOGTAG, cursor.getInt(cursor.getColumnIndex(SqlDbHelper.COL_ID)) + "," +
                        cursor.getString(cursor.getColumnIndex(SqlDbHelper.COL_CAT)) + "," +
                        new Date(cursor.getLong(cursor.getColumnIndex(SqlDbHelper.COL_DATE))).toString());
                //Log.i(LOGTAG, cursor.getDouble(cursor.getColumnIndex(SqlDbHelper.COL_AMOUNT)) + "");
                //Log.i(LOGTAG, cursor.getLong(cursor.getColumnIndex(SqlDbHelper.COL_DATE)) + "");
                /*Log.i(LOGTAG, "THERE ARE " + cursor.getColumnNames().length + " COLUMNS");
                for (int i = 0; i < cursor.getColumnNames().length; i++)
                    Log.i(LOGTAG, "COLUMN NAMES ARE: " + (cursor.getColumnNames())[i]);*/
            }

        }
        cursor.close();
    }

    private void testGetDatabaseWhereContents() {
        Cursor cursor = dataSource.getAllWhere(SqlDbHelper.TABLE_EXPENSES, SqlDbHelper.EXPENSES_COLLUMNS, "TYPE like 'movie'");

        Log.i(LOGTAG, " GETTING ALL WHERE");
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Log.i(LOGTAG, cursor.getInt(cursor.getColumnIndex(SqlDbHelper.COL_ID)) + "," +
                        cursor.getString(cursor.getColumnIndex(SqlDbHelper.COL_CAT)) + "," +
                        cursor.getString(cursor.getColumnIndex(SqlDbHelper.COL_AMOUNT)));
            }
        }
        cursor.close();
    }

    private void testTodaysDate() {
        DateManager dateManager = new DateManager();
        Date date = dateManager.getTodaysDate();
        Log.i(LOGTAG, "TODAY'S DATE IS (depricatred):" + date.toGMTString());
        Log.i(LOGTAG, "TODAY'S DATE IS (toString):" + date.toString());
        Log.i(LOGTAG, "TODAY'S DATE IS (in milli):" + Calendar.getInstance().getTimeInMillis());

    }
}
