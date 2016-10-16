package com.frugal.main;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.frugal.db.SqlDbHelper;
import com.frugal.main.R;
import com.frugal.main.expenseBuilder.NonImageRow;

public class HistoryActivity extends DrawerActivity {

    private TableLayout table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View contentView = inflater.inflate(R.layout.activity_history, null, false);
        drawerLayout.addView(contentView, 0);

        table = (TableLayout) findViewById(R.id.main_table);

        buildTable();

    }

    private void buildTable() {

        try (Cursor cursor = dataSource.selectAll(SqlDbHelper.TABLE_EXPENSES)) {

            while (cursor.moveToNext()) {
                NonImageRow row = new NonImageRow();

                row.setId(cursor.getInt(cursor.getColumnIndex(SqlDbHelper.COL_ID)));
                row.setAmount(cursor.getDouble(cursor.getColumnIndex(SqlDbHelper.COL_AMOUNT)));
                row.setCategory(cursor.getString(cursor.getColumnIndex(SqlDbHelper.COL_CAT)));
                row.setDate(cursor.getLong(cursor.getColumnIndex(SqlDbHelper.COL_DATE)));
                row.setDescription(cursor.getString(cursor.getColumnIndex(SqlDbHelper.COL_DESC)));
                row.setSubCategory(cursor.getString(cursor.getColumnIndex(SqlDbHelper.COL_SUB_CAT)));
//                row.setTaxable(cursor.getInt(cursor.getColumnIndex(SqlDbHelper.COL_TAX)));

                table.addView(row.getRowResource(this));
            }
        }


    }
}
