package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddTransaction extends AppCompatActivity {

    private Calendar calendar;
    private int year,month ,day;
    private TextView dateView;
    DatabaseHelper dbHelper;
    String type="income";
    String catag,a,n,d;
    SQLiteDatabase db;
    String[] catagories;
    Spinner catagory;
    EditText date;
    String email="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
       setTitle("Add Transaction");
        Intent i=getIntent();
        email=i.getStringExtra("email");
        calendar= Calendar.getInstance();
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);
        day=calendar.get(Calendar.DAY_OF_MONTH);
        dbHelper=new DatabaseHelper(this);

        catagories = getResources().getStringArray(R.array.catagories);
        catagory=(Spinner) findViewById(R.id.catagory);
        ArrayAdapter<String> adapter =new ArrayAdapter(this,android.R.layout.simple_spinner_item,catagories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        catagory.setAdapter(adapter);

        catagory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
            {
                catag=catagories[pos];
                //Toast.makeText(getApplicationContext(), "You Clicked: "  + catagories[pos], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        date=(EditText)findViewById(R.id.date);
        DateFormat df=new SimpleDateFormat("d/M/yyyy");
        String currentdate=df.format(currentdate());
        date.setText(currentdate);

    }

    // method to get current date
    private Date currentdate()
    {
        calendar= Calendar.getInstance();
        calendar.add(Calendar.DATE,0);
        return calendar.getTime();
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
    // method to check type of transaction
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
        //Toast.makeText(getApplicationContext(), "done 1", Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(), "done 2", Toast.LENGTH_SHORT).show();
        db = dbHelper.getWritableDatabase();
        EditText amount = (EditText) findViewById(R.id.amount);
        EditText note = (EditText) findViewById(R.id.note);
        ContentValues values = new ContentValues();
        a = String.valueOf(amount.getText());
        n = String.valueOf(note.getText());
        d = String.valueOf(date.getText());
        if (a.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Amount must be entered", Toast.LENGTH_SHORT).show();
        }

        else
        {

            values.put(DatabaseContract.Record.COL_TYPE, type);
            values.put(DatabaseContract.Record.COL_AMOUNT, a);
            values.put(DatabaseContract.Record.COL_CATAGORY, catag);
            values.put(DatabaseContract.Record.COL_DATE, d);
            values.put(DatabaseContract.Record.COL_NOTE, n);
            values.put(DatabaseContract.Record.COL_EMAIL, email);
            long newRowId = db.insert(DatabaseContract.Record.TABLE_NAME, null, values);
            if (newRowId > 0) {
                //Toast.makeText(this, "New Record Inserted: " + newRowId, Toast.LENGTH_SHORT).show();
            }
            db.close();

            setResult(AddTransaction.RESULT_OK);
            finish();
        }


       /* db = dbHelper.getReadableDatabase();
        String[] columns = { DatabaseContract.Record._ID, DatabaseContract.Record.COL_TYPE, DatabaseContract.Record.COL_AMOUNT,DatabaseContract.Record.COL_CATAGORY,DatabaseContract.Record.COL_DATE,DatabaseContract.Record.COL_NOTE };
        Cursor c = db.query(DatabaseContract.Record.TABLE_NAME, null, null, null, null, null, null);

        while (c.moveToNext()) {

            Long id= c.getLong(0) ;
            String ty= c.getString(1) ;
            String am= c.getString(2);
            String cat= c.getString(3);
            String dat= c.getString(4);
            String not= c.getString(5);
            String res=id+ty+am+cat+dat+not;
            Toast.makeText(this, res , Toast.LENGTH_LONG).show();
        }
*/

    }

    public void Cancel(View v)
    {
        setResult(Activity.RESULT_OK);
        finish();
    }

}