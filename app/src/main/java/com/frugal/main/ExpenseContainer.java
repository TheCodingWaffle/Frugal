package com.frugal.main;

import android.content.ContentValues;

import com.frugal.db.SqlDbHelper;

import java.util.Date;

/**
 * Created by Waffles on 7/5/2014.
 */
public class ExpenseContainer extends Container {

    public ExpenseContainer(String cat, double amount) {

        super(SqlDbHelper.TABLE_EXPENSES, cat, amount);

        values = new ContentValues();
        values.put(SqlDbHelper.COL_CAT,cat);
        values.put(SqlDbHelper.COL_AMOUNT,amount);
        values.put(SqlDbHelper.COL_DATE,super.getDate().getTime());
    }

    public ExpenseContainer(double amount, String category, String subCategory, String description, boolean taxable, Date date){

        super(SqlDbHelper.TABLE_EXPENSES,amount,category,subCategory,description,taxable,date);

        values = new ContentValues();
        values.put(SqlDbHelper.COL_CAT,category);
        values.put(SqlDbHelper.COL_SUB_CAT,subCategory);
        values.put(SqlDbHelper.COL_AMOUNT,amount);
        values.put(SqlDbHelper.COL_DESC,description);
        values.put(SqlDbHelper.COL_TAX,taxable);
        values.put(SqlDbHelper.COL_DATE,super.getDate().getTime());

    }
}
