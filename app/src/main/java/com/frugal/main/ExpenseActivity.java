package com.frugal.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.frugal.main.R;

public class ExpenseActivity extends DrawerActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_expense, null, false);
        drawerLayout.addView(contentView, 0);

    }

    public void onClick(View view){

        Intent intent = new Intent(this,DataEntryActivity.class);
       switch(view.getId()){
           case R.id.eat:
               intent.putExtra("class","eat");
               break;
           case R.id.drink:
               intent.putExtra("class","drink");
               break;
           case R.id.shop:
               intent.putExtra("class","shop");
               break;
           case R.id.go:
               intent.putExtra("class","go");
               break;
           case R.id.play:
               intent.putExtra("class","play");
               break;
           case R.id.live:
               intent.putExtra("class","live");
               break;
       }

        this.startActivity(intent);
        this.overridePendingTransition(0, 0);
        //this.finish();
    }

}
