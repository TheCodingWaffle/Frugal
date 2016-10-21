package com.frugal.main;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.frugal.db.SqlDbHelper;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.commons.io.IOUtils;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends DrawerActivity {


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_main, null, false);

        drawerLayout.addView(contentView, 0);

        updateAmounts();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void updateAmounts() {
        updateDayAmount();
        updateWeekAmount();
        updateMonthAmount();
    }

    private void updateMonthAmount() {
        TextView tv1 = (TextView) findViewById(R.id.monthTotal);
        Cursor cursor = null;
        try {
            Calendar cal = Calendar.getInstance();

            cursor = dataSource.getSumOfMonth(SqlDbHelper.COL_AMOUNT, SqlDbHelper.TABLE_EXPENSES, cal.get(Calendar.MONTH), cal.get(Calendar.YEAR));

            NumberFormat formatter = NumberFormat.getCurrencyInstance();

            double totalDouble;

            if (cursor.moveToNext())
                totalDouble = cursor.getDouble(0);
            else
                totalDouble = 0;

            tv1.setText(formatter.format(totalDouble));

        } catch (Exception e) {
            Log.v("MainActivity", e.getMessage());
        } finally {
            IOUtils.closeQuietly(cursor);
        }
    }

    private void updateWeekAmount() {
        TextView tv1 = (TextView) findViewById(R.id.weekTotal);
        Cursor cursor = null;
        try {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

            cursor = dataSource.getSumOfWeek(SqlDbHelper.COL_AMOUNT, SqlDbHelper.TABLE_EXPENSES, c.getTime());


            NumberFormat formatter = NumberFormat.getCurrencyInstance();

            double totalDouble;

            if (cursor.moveToNext())
                totalDouble = cursor.getDouble(0);
            else
                totalDouble = 0;

            tv1.setText(formatter.format(totalDouble));

        } catch (Exception e) {
            Log.v("MainActivity", e.getMessage());
        } finally {
            IOUtils.closeQuietly(cursor);
        }
    }

    private void updateDayAmount() {
        TextView tv1 = (TextView) findViewById(R.id.dayTotal);
        Cursor cursor = null;
        try {
            cursor = dataSource.getSumOfDay(SqlDbHelper.COL_AMOUNT, SqlDbHelper.TABLE_EXPENSES, new Date());


            NumberFormat formatter = NumberFormat.getCurrencyInstance();

            double totalDouble;

            if (cursor.moveToNext())
                totalDouble = cursor.getDouble(0);
            else
                totalDouble = 0;

            tv1.setText(formatter.format(totalDouble));

        } catch (Exception e) {
            Log.v("MainActivity", e.getMessage());
        } finally {
            IOUtils.closeQuietly(cursor);
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
