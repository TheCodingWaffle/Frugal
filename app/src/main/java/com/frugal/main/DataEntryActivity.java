package com.frugal.main;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DataEntryActivity extends DrawerActivity {

    private String[] buttonList;
    private  EditText moneyInput;
    private Date selectedDate;
    private String category, subCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_data_entry, null, false);
        drawerLayout.addView(contentView, 0);

        Bundle extras = getIntent().getExtras();

        category = extras.getString("class");

        buttonList = selectButtonArray(category);
        fillButtons(buttonList);

        moneyInput = (EditText) findViewById(R.id.money_input);
        moneyInput.addTextChangedListener(new TextWatcher() {

            private String oldString = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                String input = s.toString();

                if (!oldString.equals(input)) {

                    if (input.indexOf('.') >= 0) {
                        StringBuilder temp = new StringBuilder(input);
                        temp.deleteCharAt(input.indexOf('.'));
                        input = temp.toString();
                    }

                    double doubleInput = Double.parseDouble(input);
                    oldString = new DecimalFormat("0.00##").format(doubleInput /= 100);
                    moneyInput.setText(oldString);
                }

                moneyInput.setSelection(moneyInput.getText().length());
            }
        });
    }



    private void fillButtons(String[] buttonList) {
        Button mButton = (Button) this.findViewById(R.id.data_button_0);
        mButton.setText(buttonList[0]);

        mButton = (Button) this.findViewById(R.id.data_button_1);
        mButton.setText(buttonList[1]);

        mButton = (Button) this.findViewById(R.id.data_button_2);
        mButton.setText(buttonList[2]);

        mButton = (Button) this.findViewById(R.id.data_button_3);
        mButton.setText(buttonList[3]);

    }

    private String[] selectButtonArray(String aClass) {

        if (aClass.contentEquals("eat")) {
            return getResources().getStringArray(R.array.eat_buttons);
        } else if (aClass.contentEquals("drink")) {
            return getResources().getStringArray(R.array.drink_buttons);
        } else if (aClass.contentEquals("shop")) {
            return getResources().getStringArray(R.array.shop_buttons);
        } else if (aClass.contentEquals("go")) {
            return getResources().getStringArray(R.array.go_buttons);
        } else if (aClass.contentEquals("play")) {
            return getResources().getStringArray(R.array.play_buttons);
        } else if (aClass.contentEquals("live")) {
            return getResources().getStringArray(R.array.live_buttons);
        }


        return null;
    }


    public void onClick(final View view) {

        if (view.getId() == R.id.input_date) {

            final StringBuffer requestedDate = new StringBuffer();
            final Calendar myCalendar = Calendar.getInstance();
            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker dateView, int year, int monthOfYear, int dayOfMonth) {
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    selectedDate = myCalendar.getTime();

                    String myFormat = "MM/dd/yy"; //In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                    requestedDate.setLength(0);
                    requestedDate.append(sdf.format(myCalendar.getTime()));

                    EditText dateText = (EditText)view.findViewById(R.id.input_date);
                    dateText.setText(requestedDate.toString());
                }


            };

            new DatePickerDialog(view.getContext(), date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }
        else if(view.getId() == R.id.money_input){

            EditText moneyInput = (EditText) findViewById(R.id.money_input);
            moneyInput.setSelection(moneyInput.getText().length());
        }
        else if(view.getId() == R.id.save_button){

            EditText moneyInput = (EditText) findViewById(R.id.money_input);
            String amount = moneyInput.getText().toString();
            amount = amount.equals("") ? "0" : amount;

            subCategory = subCategory == null ? "Other" : subCategory;

            Switch taxSwitch = (Switch) findViewById(R.id.taxable);
            boolean taxable = taxSwitch.isChecked() ? true : false;

            selectedDate = selectedDate == null ? new Date() : selectedDate;

            EditText descriptionInput = (EditText) findViewById(R.id.text_description);
            String description = descriptionInput.getText().toString();

            ExpenseContainer expense = new ExpenseContainer(Double.parseDouble(amount),
                    category, subCategory,description,taxable,selectedDate);

            //ExpenseValidator validator = new ExpenseValidator(amount,category,subCategory,description,taxable,selectedDate,this);

            ExpenseValidator validator = new ExpenseValidator(expense,this);

            if(validator.validateExpense()){
                try {
                    Log.i("Test","INSERTING into db");
                    dataSource.dbInsert(expense.getValues(), expense.getTable());
                    Log.i("Test","inserted!");
                }
                catch (Exception e){
                    Log.i("Test",e.getMessage());
                }
                finally
                {
                    Log.i("Test","FINISHED ADDING");
                    //this.startActivity(new Intent(this, MainActivity.class));
                    this.finish();
                }
            }
            else{
                Log.i("TEST","ERROR: " + validator.getError());
            }
        }
        else if(view.getId() == R.id.cancel_button){
            Log.i("TEST"," CANCEL WAS SELECTED");

            //save state for if wants to undo
            this.finish();
        }
    }
}
