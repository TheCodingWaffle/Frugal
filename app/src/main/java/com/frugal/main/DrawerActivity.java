package com.frugal.main;

import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.frugal.com.frugal.test.DbTestCases;
import com.frugal.db.SqlDbDataSource;


public class DrawerActivity extends ActionBarActivity {

    protected SqlDbDataSource dataSource;
    private ListView navigationListView;
    private String[] navigationItems;
    protected DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerListener;
    private NavigationAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_drawer);
        myAdapter = new NavigationAdapter(this);
        navigationItems = getResources().getStringArray(R.array.navigation);
        navigationListView = (ListView) findViewById(R.id.drawerList);

        navigationListView.setAdapter(myAdapter);
        navigationListView.setOnItemClickListener(new DrawerItemClickListener());

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerListener = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_drawer,
                R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerListener);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dataSource = new SqlDbDataSource(this);

        getSupportActionBar().hide();
        //USE FOR TESTING DB
        //DbTestCases dbTester = new DbTestCases(dataSource);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
