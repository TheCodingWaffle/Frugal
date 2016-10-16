package com.frugal.main.expenseBuilder;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class SimpleRow implements ExpenseRow {

    private String category, subCategory, description;

    private Double amount;

    private Date date;

    private Boolean taxable;

    Integer id;


    public SimpleRow() {
    }

    @Override
    public TableRow getRowResource(Activity parent) {
        TableRow rowResource = new TableRow(parent);

        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);

        rowResource.setLayoutParams(rowParams);

        buildRowViews(rowResource, parent);

        return rowResource;
    }

    private void buildRowViews(TableRow rowResource, Activity parent) {

        if (date != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("EEE, MMM d, ''yy");

            TextView textView = new TextView(parent);
            textView.setText(formatter.format(date).toString());

            rowResource.addView(textView);
        }

        if (category != null) {
            TextView textView = new TextView(parent);
            textView.setText(category);
            rowResource.addView(textView);
        }

        if (amount != null) {
            TextView textView = new TextView(parent);
            textView.setText(amount.toString());
            rowResource.addView(textView);
        }
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDate(Long dateInMillis) {
        this.date = new Date(dateInMillis);
    }

    public Boolean getTaxable() {
        return taxable;
    }

    public void setTaxable(Boolean taxable) {
        this.taxable = taxable;
    }

    public void setTaxable(int taxableBit) {
        this.taxable = taxableBit == 1 ? true : false;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
