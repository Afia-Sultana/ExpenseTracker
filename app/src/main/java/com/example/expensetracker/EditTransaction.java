package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class EditTransaction extends AppCompatActivity {

    private Calendar calendar;
    private int year, month, day;
    private TextView dateView;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    String type, amount, catagory, date, note;
    RadioButton income;
    RadioButton expense;
    EditText edamount;
    Spinner edcatagory;
    EditText eddate;
    EditText ednote;
    String[] catagories;
    int tid;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_transaction);
        setTitle(" Edit Transaction");
        Intent i = getIntent();
        tid = i.getIntExtra("id", 1);
        income = (RadioButton) findViewById(R.id.income);
        expense = (RadioButton) findViewById(R.id.expense);
        edamount = (EditText) findViewById(R.id.amount);
        edcatagory = (Spinner) findViewById(R.id.catagory);
        eddate = (EditText) findViewById(R.id.date);
        ednote = (EditText) findViewById(R.id.note);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        catagories = getResources().getStringArray(R.array.catagories);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, catagories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edcatagory.setAdapter(adapter);

        edcatagory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                catagory = catagories[pos];
                //Toast.makeText(getApplicationContext(), "You Clicked: "  + catagories[pos], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0)
            {
                // TODO Auto-generated method stub

            }
        });
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getReadableDatabase();
        String[] columns = {DatabaseContract.Record._ID, DatabaseContract.Record.COL_TYPE, DatabaseContract.Record.COL_AMOUNT, DatabaseContract.Record.COL_CATAGORY, DatabaseContract.Record.COL_DATE, DatabaseContract.Record.COL_NOTE, DatabaseContract.Record.COL_EMAIL};
        Cursor c = db.query(DatabaseContract.Record.TABLE_NAME, columns, null, null, null, null, null);

        while (c.moveToNext()) {

            int id = c.getInt(0);
            if (id == tid) {
                type = c.getString(1);
                amount = c.getString(2);
                catagory = c.getString(3);
                date = c.getString(4);
                note = c.getString(5);
                email = c.getString(6);

            }
        }
        if (type.equals("expense"))
        {
            expense.setChecked(true);
        }
        if (type.equals("income"))
        {
            income.setChecked(true);
        }

        edamount.setText(amount);
        //edcatagory;
        eddate.setText(date);
        ednote.setText(note);
        if (catagory != null) {
            int spinnerPosition = adapter.getPosition(catagory);
            edcatagory.setSelection(spinnerPosition);
        }
    }



    @SuppressWarnings("deprecation")
    public void chooseDate(View view) {
        dateView = (TextView) view;
        showDialog(999);
        // Toast.makeText(getApplicationContext(), "calander", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    dateView.setText(new StringBuilder().append(arg3).append("/").append(arg2+1).append("/").append(arg1));
                    // showDate(arg1, arg2+1, arg3);
                }
            };



    public void setType(View v)
    {
        boolean checked = ((RadioButton) v).isChecked();
        switch(v.getId()) {
            case R.id.income:
                if (checked)
                    type="income";
                break;
            case R.id.expense:
                if (checked)
                    type="expense";
                break;
        }

    }
    public void Done(View v) {


        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        amount = String.valueOf(edamount.getText());
        note = String.valueOf(ednote.getText());
        date = String.valueOf(eddate.getText());
        if (amount.equals("")) {
            Toast.makeText(getApplicationContext(), "Amount must be entered", Toast.LENGTH_SHORT).show();
        } else {

            //Toast.makeText(getApplicationContext(), "Type"+type, Toast.LENGTH_SHORT).show();
            values.put(DatabaseContract.Record.COL_TYPE, type);
            values.put(DatabaseContract.Record.COL_AMOUNT, amount);
            values.put(DatabaseContract.Record.COL_CATAGORY, catagory);
            values.put(DatabaseContract.Record.COL_DATE, date);
            values.put(DatabaseContract.Record.COL_NOTE, note);
            String sid = String.valueOf(tid);
            String[] wherearg = {sid};
            Integer count = db.update(DatabaseContract.Record.TABLE_NAME, values, DatabaseContract.Record._ID + "=?", wherearg);
            if (count > 0) {
                //Toast.makeText(this, count + "  Records updated: ", Toast.LENGTH_SHORT).show();
            }
            db.close();


        }
        Intent ed = new Intent(getApplicationContext(),AllTransactions.class);
        ed.putExtra("email",email);
        startActivity(ed);
        finish();
    }
    public void Cancel(View v)
    {

        finish();

    }
    public void Delete(View v)
    {

        String sid = String.valueOf(tid);

        db = dbHelper.getWritableDatabase();
        String[] wherearg = {sid};
        Integer i1= db.delete(DatabaseContract.Record.TABLE_NAME, DatabaseContract.Record._ID +"=?",new String[] {sid});
        if (i1 > 0)
        {
            //Toast.makeText(this, i1+"  Records deleted: " , Toast.LENGTH_SHORT).show();
        }
        db.close();

        Intent ed = new Intent(getApplicationContext(),AllTransactions.class);
        ed.putExtra("email",email);
        startActivity(ed);
        finish();
    }



}