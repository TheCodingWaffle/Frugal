package com.frugal.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SqlDbHelper extends SQLiteOpenHelper {

    static SqlDbHelper instance;

    private static final String LOGTAG = "DbHelper";

    private static final String DATABASE_NAME = "frugal.db";
    private static final int DATBASE_VERSION = 13;

    /*
      TABLES
     */
    public static final String TABLE_BILLS = "bills";
    public static final String TABLE_EXPENSES = "expenses";

    /*
      COLUMNS
     */
    public static final String COL_CAT = "category";
    public static final String COL_SUB_CAT = "subcategory";
    public static final String COL_AMOUNT = "amount";
    public static final String COL_MONTHLY = "monthly";
    public static final String COL_DATE = "date";
    public static final String COL_ID = "id";
    public static final String COL_DESC = "description";
    public static final String COL_TAX = "taxable";

    public static final String[] BILLS_COLLUMNS = {
            COL_ID, COL_CAT, COL_AMOUNT, COL_MONTHLY, COL_DATE
    };
    public static final String[] EXPENSES_COLLUMNS = {
            COL_ID, COL_CAT, COL_SUB_CAT, COL_AMOUNT, COL_DESC, COL_TAX, COL_DATE
    };


    private static final String CREATE_TABLE_BILLS =
            "CREATE TABLE " + TABLE_BILLS + " (" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_CAT + " TEXT, " + COL_AMOUNT + " REAL, "
                    + COL_MONTHLY + " INTEGER, " + COL_DATE + " LONG "
                    + ")";

    private static final String CREATE_TABLE_EXPENSES =
            "CREATE TABLE " + TABLE_EXPENSES + " (" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_CAT + " TEXT, " + COL_SUB_CAT + " TEXT, " + COL_AMOUNT + " REAL, "
                    + COL_DESC + " TEXT, " + COL_TAX + " BOOLEAN, " + COL_DATE + " LONG" + ")";

    private SqlDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATBASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE_BILLS);
            db.execSQL(CREATE_TABLE_EXPENSES);
        } catch (Exception e) {
            Log.i(LOGTAG, e.getMessage());
        }

        Log.i(LOGTAG, " : CREATED DATABASE");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_BILLS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);
        } catch (Exception e) {
            Log.i(LOGTAG, e.getMessage());
        }

        Log.i(LOGTAG, " : DROPPED TABLES");

        onCreate(db);
    }

    public static synchronized SqlDbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new SqlDbHelper(context.getApplicationContext());
        }
        return instance;
    }
}

