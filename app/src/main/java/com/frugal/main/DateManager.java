package com.frugal.main;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Waffles on 7/6/2014.
 */
public class DateManager {

    public Date getTodaysDate(){
       Date date = new Date(Calendar.getInstance().getTimeInMillis());
        return date;
    }

}
