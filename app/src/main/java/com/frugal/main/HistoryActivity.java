package com.frugal.main;

import android.app.FragmentTransaction;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.frugal.db.SqlDbHelper;

import java.util.ArrayList;
import java.util.Date;

public class HistoryActivity extends DrawerActivity {

    private ListView historyListView;

    private int timeFragmentSelected = R.id.monthTotal;

    private ArrayList<HistoryItem> visibleHistoryItems = new ArrayList();

    private HistoryListAdapter listAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View contentView = inflater.inflate(R.layout.activity_history, null, false);
        drawerLayout.addView(contentView, 0);

        historyListView = (ListView) findViewById(R.id.history_listview);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            int selectedTime = extras.getInt("time");

            if (selectedTime > 0) {
                timeFragmentSelected = selectedTime;
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        retrieveHistoryItemsFromDb();
        buildTable();
    }

    public void retrieveHistoryItemsFromDb() {
        try (Cursor cursor = dataSource.selectAllGivenTimeId(SqlDbHelper.TABLE_EXPENSES, timeFragmentSelected)) {

            while (cursor.moveToNext()) {

                HistoryItem historyItem = new HistoryItem();
                historyItem.setId(cursor.getInt(cursor.getColumnIndex(SqlDbHelper.COL_ID)));
                historyItem.setAmount(cursor.getDouble(cursor.getColumnIndex(SqlDbHelper.COL_AMOUNT)));
                historyItem.setCategory(cursor.getString(cursor.getColumnIndex(SqlDbHelper.COL_CAT)));
                historyItem.setDate(cursor.getLong(cursor.getColumnIndex(SqlDbHelper.COL_DATE)));
                historyItem.setDescription(cursor.getString(cursor.getColumnIndex(SqlDbHelper.COL_DESC)));
                historyItem.setSubCategory(cursor.getString(cursor.getColumnIndex(SqlDbHelper.COL_SUB_CAT)));

                visibleHistoryItems.add(historyItem);
            }
        }
    }

    public void buildTable() {

        ArrayList<Date> historyItemDates = new ArrayList();

        ArrayList<String> historyItemAmounts = new ArrayList();

        for (HistoryItem item : visibleHistoryItems) {
            historyItemAmounts.add("$ " + item.getAmount());
            historyItemDates.add(item.getDate());
        }

        if (listAdapter == null) {

            listAdapter = new HistoryListAdapter(this, historyItemDates, historyItemAmounts);

            historyListView.setAdapter(listAdapter);
            historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.v("test", "yup it's hitting that shit at ID: " + id + " POS: " + position);

                    HistoryItem historyItem = visibleHistoryItems.get(position);

                    if (historyItem != null) {
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        HistoryFragment frag = new HistoryFragment();

                        Bundle args = new Bundle();

                        args.putInt("id", historyItem.getId());
                        args.putDouble("amount", historyItem.getAmount());
                        args.putString("category", historyItem.getCategory());
                        args.putLong("date", historyItem.getDate().getTime());
                        args.putString("description", historyItem.getDescription());
                        args.putString("subcategory", historyItem.getSubCategory());

                        frag.setArguments(args);
                        frag.show(ft, "txn_tag");
                    }

                }
            });
        } else {
            listAdapter.setArrays(historyItemDates, historyItemAmounts);
        }


        historyListView.deferNotifyDataSetChanged();

    }

    public void removeExpenseById(Integer id) {

        int removeIndex = -1;

        for (HistoryItem item : visibleHistoryItems) {
            if (item != null && item.getId().intValue() == id.intValue()) {
                removeIndex = visibleHistoryItems.indexOf(item);
            }
        }

        if (removeIndex >= 0) {
            visibleHistoryItems.remove(removeIndex);
        }

        buildTable();
    }
}
