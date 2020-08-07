package com.example.expensetracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AllTransactions extends AppCompatActivity {
    Spinner sp;
    private Calendar calendar;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    String[] s;
    String selected;
    TextView tv;
    ListView lv;
    String catagory;
    String amount;
    String date1;
    String email="";
    EditText inputEdittext;
    String userinput;
    Button btn1;
    Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_transactions);
        setTitle("Transactions");
        activity = this;
        Intent i1 = getIntent();
        email = i1.getStringExtra("email");
        tv = (TextView) findViewById(R.id.txtview);
        lv = (ListView) findViewById(R.id.listview);
        dbHelper = new DatabaseHelper(this);
        sp = (Spinner) findViewById(R.id.spinner);
        btn1=(Button)findViewById(R.id.btn);
        s = getResources().getStringArray(R.array.transactionhistory);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, s);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                selected = s[pos];
                if (selected.equals("All")) {
                    lv.setVisibility(View.INVISIBLE);
                    btn1.setVisibility(View.INVISIBLE);
                    //Toast.makeText(getApplicationContext(), "You Clicked: " + s[pos], Toast.LENGTH_SHORT).show();
                    allrecord();
                    // Toast.makeText(getApplicationContext(), "You Clicked: " + s[pos], Toast.LENGTH_SHORT).show();
                }
                else if(selected.equals("Day"))
                {
                    lv.setVisibility(View.INVISIBLE);
                    btn1.setVisibility(View.INVISIBLE);
                    //Toast.makeText(getApplicationContext(), "You Clicked: " + s[pos], Toast.LENGTH_SHORT).show();
                    dayrecord();
                }
                else if(selected.equals("Month"))
                {
                    lv.setVisibility(View.INVISIBLE);
                    btn1.setVisibility(View.INVISIBLE);
                    monthrecord();
                    // Toast.makeText(getApplicationContext(), "You Clicked: " + s[pos], Toast.LENGTH_SHORT).show();
                }
                else
                {
                    lv.setVisibility(View.INVISIBLE);
                    btn1.setVisibility(View.INVISIBLE);
                    yearrecord();
                    // Toast.makeText(getApplicationContext(), "You Clicked: " + s[pos], Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }

        });
    }
    public void allrecord() {
        lv.setVisibility(View.VISIBLE);
        db = dbHelper.getReadableDatabase();

        final ArrayList<TransactionData> td = new ArrayList<TransactionData>();


        String[] columns = {DatabaseContract.Record._ID, DatabaseContract.Record.COL_TYPE, DatabaseContract.Record.COL_AMOUNT, DatabaseContract.Record.COL_CATAGORY, DatabaseContract.Record.COL_DATE, DatabaseContract.Record.COL_NOTE, DatabaseContract.Record.COL_EMAIL};
        Cursor c = db.query(DatabaseContract.Record.TABLE_NAME, columns, null, null, null, null, null);

        while (c.moveToNext()) {

            String eml = c.getString(6);
            if (eml.equals(email)) {
                int id = c.getInt(0);
                date1 = c.getString(4);
                catagory = c.getString(3);
                amount = c.getString(2);
                TransactionData Obj = new TransactionData(id, amount, catagory, date1);
                td.add(Obj);
            }
        }

        c.close();
        db.close();
        if (td.size() == 0) {
            tv.setText("No Records Found");
            lv.setAdapter(null);
        }
        else
        {
            tv.setText("All Transactions");
            CustomTransactionList ctl = new CustomTransactionList(activity, td);
            lv.setAdapter(ctl);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                    int tid = td.get(position).getId();
                    Intent intent = new Intent(getApplicationContext(), EditTransaction.class);
                    intent.putExtra("id", tid);
                    startActivity(intent);


                }

            });

        }
    }
    public void dayrecord()
    {

        LayoutInflater li = LayoutInflater.from(this);
        View promptsview = li.inflate(R.layout.promptsday, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptsview);
        inputEdittext = (EditText) promptsview.findViewById(R.id.ed1);
        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                userinput= String.valueOf(inputEdittext.getText());
                searchday(userinput);
                //Toast.makeText(getApplicationContext(), "You entered: " + userinput, Toast.LENGTH_SHORT).show();
            }
        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog  alertDialog=alertDialogBuilder.create();
        alertDialog.show();

    }
    public void searchday(String userinput)
    {
        lv.setVisibility(View.VISIBLE);
        db = dbHelper.getReadableDatabase();


        final ArrayList<TransactionData> td=new ArrayList<TransactionData>();


        String[] columns = {DatabaseContract.Record._ID, DatabaseContract.Record.COL_TYPE, DatabaseContract.Record.COL_AMOUNT, DatabaseContract.Record.COL_CATAGORY, DatabaseContract.Record.COL_DATE, DatabaseContract.Record.COL_NOTE, DatabaseContract.Record.COL_EMAIL};
        Cursor c = db.query(DatabaseContract.Record.TABLE_NAME, columns, null, null, null, null, null);

        while (c.moveToNext()) {

            String eml = c.getString(6);
            if (eml.equals(email)) {
                date1 = c.getString(4);
                Toast.makeText(getApplicationContext(), "You entered: " + userinput, Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(), "You entered: " + userinput, Toast.LENGTH_SHORT).show();
                if(date1.equals(userinput)) {
                    int id=c.getInt(0);
                    String dd=c.getString(4);
                    catagory = c.getString(3);
                    amount = c.getString(2);
                    TransactionData Obj=new  TransactionData(id,amount,catagory,dd);
                    td.add(Obj);
                }
            }
        }

        c.close();
        db.close();

        if(td.size()==0) {
            tv.setText("No Records Found");
            lv.setAdapter(null);
        }
        else {/*
            SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
            String dateInString = userinput;
            Date dat;
            try {
                dat = sdf.parse(dateInString);
                tv.setText(dat+"  Transactions");
            }
            catch(Exception e)
            {

            }
*/   tv.setText(userinput+"  Transactions");
            CustomTransactionList ctl = new CustomTransactionList(activity, td);
            lv.setAdapter(ctl);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                    int tid = td.get(position).getId();
                    Intent intent = new Intent(getApplicationContext(), EditTransaction.class);
                    intent.putExtra("id", tid);
                    startActivity(intent);

                }

            });

        }

        btn1.setVisibility(View.VISIBLE);

    }

    public void monthrecord()
    {

        LayoutInflater li = LayoutInflater.from(this);
        View promptsview = li.inflate(R.layout.promptsmonth, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptsview);
        inputEdittext = (EditText) promptsview.findViewById(R.id.ed1);
        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                userinput= String.valueOf(inputEdittext.getText());
                searchmonth(userinput);
                //Toast.makeText(getApplicationContext(), "You entered: " + userinput, Toast.LENGTH_SHORT).show();
            }
        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog  alertDialog=alertDialogBuilder.create();
        alertDialog.show();

    }
    public void searchmonth(String userinput)
    {

        db = dbHelper.getReadableDatabase();
        lv.setVisibility(View.VISIBLE);

        final ArrayList<TransactionData> td=new ArrayList<TransactionData>();

        String[] columns = {DatabaseContract.Record._ID, DatabaseContract.Record.COL_TYPE, DatabaseContract.Record.COL_AMOUNT, DatabaseContract.Record.COL_CATAGORY, DatabaseContract.Record.COL_DATE, DatabaseContract.Record.COL_NOTE, DatabaseContract.Record.COL_EMAIL};
        Cursor c = db.query(DatabaseContract.Record.TABLE_NAME, columns, null, null, null, null, null);

        while (c.moveToNext()) {

            String eml = c.getString(6);
            if (eml.equals(email)) {
                date1 = c.getString(4);
                Toast.makeText(getApplicationContext(), "You entered: " + userinput, Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(), "You entered: " + userinput, Toast.LENGTH_SHORT).show();
                if(date1.contains(userinput)) {
                    int id=c.getInt(0);
                    String dd=c.getString(4);
                    catagory = c.getString(3);
                    amount = c.getString(2);
                    TransactionData Obj=new  TransactionData(id,amount,catagory,dd);
                    td.add(Obj);
                }
            }
        }

        c.close();
        db.close();

        if(td.size()==0) {
            tv.setText("No Records Found");
            lv.setAdapter(null);
        }
        else {
            tv.setText(userinput+"  Transactions");
            CustomTransactionList ctl = new CustomTransactionList(activity, td);
            lv.setAdapter(ctl);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                    int tid = td.get(position).getId();
                    Intent intent = new Intent(getApplicationContext(), EditTransaction.class);
                    intent.putExtra("id", tid);
                    startActivity(intent);

                }

            });




        }

        btn1.setVisibility(View.VISIBLE);

    }


    public void yearrecord()
    {

        LayoutInflater li = LayoutInflater.from(this);
        View promptsview = li.inflate(R.layout.promptsyear, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptsview);
        inputEdittext = (EditText) promptsview.findViewById(R.id.ed1);
        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                userinput= String.valueOf(inputEdittext.getText());
                searchyear(userinput);
                //Toast.makeText(getApplicationContext(), "You entered: " + userinput, Toast.LENGTH_SHORT).show();
            }
        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog  alertDialog=alertDialogBuilder.create();
        alertDialog.show();

    }
    public void searchyear(String userinput)
    {
        lv.setVisibility(View.VISIBLE);
        db = dbHelper.getReadableDatabase();


        final ArrayList<TransactionData> td=new ArrayList<TransactionData>();

        String[] columns = {DatabaseContract.Record._ID, DatabaseContract.Record.COL_TYPE, DatabaseContract.Record.COL_AMOUNT, DatabaseContract.Record.COL_CATAGORY, DatabaseContract.Record.COL_DATE, DatabaseContract.Record.COL_NOTE, DatabaseContract.Record.COL_EMAIL};
        Cursor c = db.query(DatabaseContract.Record.TABLE_NAME, columns, null, null, null, null, null);

        while (c.moveToNext()) {

            String eml = c.getString(6);
            if (eml.equals(email)) {
                date1 = c.getString(4);
                Toast.makeText(getApplicationContext(), "You entered: " + userinput, Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(), "You entered: " + userinput, Toast.LENGTH_SHORT).show();
                if(date1.contains(userinput)) {
                    int id=c.getInt(0);
                    String dd=c.getString(4);
                    catagory = c.getString(3);
                    amount = c.getString(2);
                    TransactionData Obj=new  TransactionData(id,amount,catagory,dd);
                    td.add(Obj);
                }
            }
        }

        c.close();
        db.close();

        if(td.size()==0) {
            tv.setText("No Records Found");
            lv.setAdapter(null);

        }
        else {
            tv.setText(userinput+"  Transactions");
            CustomTransactionList ctl = new CustomTransactionList(activity, td);
            lv.setAdapter(ctl);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                    int tid = td.get(position).getId();
                    Intent intent = new Intent(getApplicationContext(), EditTransaction.class);
                    intent.putExtra("id", tid);
                    startActivity(intent);

                }

            });

        }

        btn1.setVisibility(View.VISIBLE);


    }

    public void searchagain(View v) {
        lv.setVisibility(View.VISIBLE);
        if (selected.equals("Day")) {
            LayoutInflater li = LayoutInflater.from(this);
            View promptsview = li.inflate(R.layout.promptsday, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setView(promptsview);
            inputEdittext = (EditText) promptsview.findViewById(R.id.ed1);
            alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    userinput = String.valueOf(inputEdittext.getText());
                    searchday(userinput);
                    //Toast.makeText(getApplicationContext(), "You entered: " + userinput, Toast.LENGTH_SHORT).show();
                }
            })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        else if(selected.equals("Month"))
        {
            LayoutInflater li = LayoutInflater.from(this);
            View promptsview = li.inflate(R.layout.promptsmonth, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setView(promptsview);
            inputEdittext = (EditText) promptsview.findViewById(R.id.ed1);
            alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    userinput= String.valueOf(inputEdittext.getText());
                    searchmonth(userinput);
                    //Toast.makeText(getApplicationContext(), "You entered: " + userinput, Toast.LENGTH_SHORT).show();
                }
            })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog  alertDialog=alertDialogBuilder.create();
            alertDialog.show();
        }
        else
        {
            LayoutInflater li = LayoutInflater.from(this);
            View promptsview = li.inflate(R.layout.promptsyear, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setView(promptsview);
            inputEdittext = (EditText) promptsview.findViewById(R.id.ed1);
            alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    userinput= String.valueOf(inputEdittext.getText());
                    searchyear(userinput);
                    //Toast.makeText(getApplicationContext(), "You entered: " + userinput, Toast.LENGTH_SHORT).show();
                }
            })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog  alertDialog=alertDialogBuilder.create();
            alertDialog.show();

        }
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check if the request code is same as what is passed
        if (requestCode == 2) {
            //String message = data.getStringExtra("MESSAGE");
finish();

        }
    }

}