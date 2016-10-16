package com.frugal.main;

import android.content.ContentValues;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Waffles on 7/5/2014.
 */
public class Container {

    private String table;
    private String cat;
    private double amount;
    private String subCat;
    private String desc;
    protected ContentValues values;
    private Date date;
    private boolean taxable;

    public Container(String table, String type, double amount) {
        this.table = table;
        this.cat = type;
        this.amount = amount;
        this.date = new Date(Calendar.getInstance().getTimeInMillis());
    }

    public Container(String table, double amount, String category,
                     String subCategory, String description, boolean taxable, Date date) {
        this.table = table;
        this.cat = category;
        this.subCat = subCategory;
        this.amount = amount;
        this.desc = description;
        this.taxable = taxable;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public ContentValues getValues() {
        return values;
    }

    public void setValues(ContentValues values) {
        this.values = values;
    }

    public String getSubCat() {
        return subCat;
    }

    public void setSubCat(String subCat) {
        this.subCat = subCat;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isTaxable() {
        return taxable;
    }

    public void setTaxable(boolean taxable) {
        this.taxable = taxable;
    }
}
