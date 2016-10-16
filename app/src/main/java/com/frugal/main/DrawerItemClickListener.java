package com.frugal.main;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


public class DrawerItemClickListener implements ListView.OnItemClickListener {

    private static final String LOGTAG = "OnClick";

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Activity host = (Activity) view.getContext();
        Intent myIntent = null;

        if (position == 0) {
            myIntent = new Intent(host, MainActivity.class);
        } else if (position == 1) {
            myIntent = new Intent(host, ExpenseActivity.class);
        } else if (position == 2) {
            myIntent = new Intent(host, StatisticsActivity.class);
        }
        else if (position == 3) {
            myIntent = new Intent(host, HistoryActivity.class);
        }

        host.startActivity(myIntent);
        host.overridePendingTransition(0, 0);
    }
}
