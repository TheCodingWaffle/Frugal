package com.frugal.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Waffles on 7/10/2014.
 */
public class NavigationAdapter extends BaseAdapter {
    private Context context;
    String navigationList[];
    int navigationImages[] = {R.drawable.home_nav, R.drawable.expense_nav, R.drawable.stat_nav, R.drawable.history_nav};

    public NavigationAdapter(Context context) {
        this.context=context;
        navigationList = context.getResources().getStringArray(R.array.navigation);
    }

    @Override
    public int getCount() {
        return navigationList.length;
    }

    @Override
    public Object getItem(int position) {
        return navigationList[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=inflater.inflate(R.layout.custom_row,parent,false);
        }
        else{
            row=convertView;
        }

        TextView navigationTextView = (TextView) row.findViewById(R.id.navigationTextView);
        ImageView navigationImageView = (ImageView) row.findViewById(R.id.navigationImageView);

        navigationTextView.setText(navigationList[position]);
        navigationImageView.setImageResource(navigationImages[position]);


        return row;
    }
}
