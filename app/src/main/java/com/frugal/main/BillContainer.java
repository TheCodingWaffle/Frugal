package com.frugal.main;

import android.content.ContentValues;

import com.frugal.db.SqlDbHelper;

/**
 * Created by Waffles on 7/5/2014.
 */
public class BillContainer extends Container {

    public BillContainer(String type, double amount,boolean monthly) {
        super(SqlDbHelper.TABLE_BILLS, type, amount);
        ContentValues values = new ContentValues();
        values.put(SqlDbHelper.COL_CAT,type);
        values.put(SqlDbHelper.COL_AMOUNT,amount);
        values.put(SqlDbHelper.COL_MONTHLY,((monthly==true)?1:0));
        values.put(SqlDbHelper.COL_DATE,super.getDate().getTime());
        super.setValues(values);
    }
}
