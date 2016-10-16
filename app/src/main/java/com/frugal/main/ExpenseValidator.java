package com.frugal.main;

import android.content.Context;

import java.util.Date;

public class ExpenseValidator {

    private static final double BIG_AMOUNT = 1000;
    private static final int MAX_DESCRIPTION_SIZE = 32;
    private final Context parent;
    private ExpenseContainer expense;
    private String error;

    public ExpenseValidator(String amount, String category, String subCategory, String description, boolean taxable, Date date, Context parent) {
        expense = new ExpenseContainer(Double.parseDouble(amount),category,subCategory,description,taxable,date);
        this.parent = parent;
    }

    public ExpenseValidator(ExpenseContainer expense,Context parent)
    {
        this.expense = expense;
        this.parent = parent;
    }

    public boolean validateExpense(){
        if(validAmount()){
            if(validDescription()){
                return true;
            }
        }

        return false;
    }

    private boolean validDescription() {

        if(expense.getDesc().length()>= MAX_DESCRIPTION_SIZE)
            error = parent.getString(R.string.descriptionSizeToBig);
        else
            return true;

        return false;
    }

    private boolean validAmount() {

        if(expense.getAmount()>=BIG_AMOUNT) {
            error = parent.getString(R.string.amountToLargeError);
        }
        else if(expense.getAmount()<=0) {
            error = parent.getString(R.string.amountZeroError);
        }
        else {
            return true;
        }

        return false;
    }

    public String getError(){
        return this.error == null ? "" : this.error;
    }
}
