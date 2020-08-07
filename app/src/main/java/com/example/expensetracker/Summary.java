package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Summary extends AppCompatActivity {
    String email;
    ArrayList<TransactionData> summaryData=new ArrayList<>();
    ArrayList<String> amount=new ArrayList<>();
    ArrayList<String> catagory=new ArrayList<>();
    String cDate;
    Calendar calendar;
    Date ndate;
    SimpleDateFormat dateFormat;
    TextView prev;
    TextView next;
    TextView currentDate;
    SQLiteDatabase db;
    DatabaseHelper dbHelper;
    double[] d;
    ListView lv;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getReadableDatabase();
        activity = this;
        lv=(ListView)findViewById(R.id.summarylistView);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        prev=findViewById(R.id.prevDate);
        next=findViewById(R.id.nextDate);
        currentDate=findViewById(R.id.date);
        calendar=Calendar.getInstance();
        setMonth();
    }

    private void setMonth() {
        lv.setAdapter(null);
        summaryData.clear();
        amount.clear();
        catagory.clear();
        dateFormat = new SimpleDateFormat("MMMM yyyy");
        ndate=calendar.getTime();
        cDate = dateFormat.format(ndate);
        currentDate.setText(cDate);
        getdbValues();
        setValues();
    }
    private void getdbValues(){

        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        month++;
        Cursor c = db.rawQuery("SELECT * FROM " +
                DatabaseContract.Record.TABLE_NAME + " where " +DatabaseContract.Record.COL_DATE+" like '%/"+month+"/"+year+"' AND "+ DatabaseContract.Record.COL_EMAIL+"='"+email+"'", null);
        while (c.moveToNext()) {
            amount.add(c.getString(2)) ;
            catagory.add(c.getString(3));
        }
        c.close();

    }

    private void setValues(){

        ArrayList<String> category=new ArrayList<>();
        ArrayList<String> temp=catagory;
        for (int i = 0; i < catagory.size(); i++) {
            if(!category.contains(temp.get(i))){
                category.add(temp.get(i));
            }
        }

        d=new double[category.size()];

        for (int i = 0; i < category.size(); i++) {
            d[i]=0;
            for (int j=0;j<catagory.size();j++){
                if(category.get(i).equals(catagory.get(j))){
                    d[i]=d[i]+Double.parseDouble(amount.get(j));
                }
            }
        }


        for (int i=0;i<category.size();i++){

            TransactionData Obj = new TransactionData( String.valueOf(d[i]), catagory.get(i));
            summaryData.add(Obj);
        }
        TextView tv=(TextView)findViewById(R.id.no_Transaction);
        if (summaryData.size() == 0) {

            tv.setVisibility(View.VISIBLE);
        }
        else{
            tv.setVisibility(View.GONE);
            CustomSummaryList summaryList = new CustomSummaryList(activity,summaryData);
            lv.setAdapter(summaryList);
        }
    }

    public void nextMonth(View view){
        calendar.add(Calendar.MONTH, 1);
        setMonth();
    }
    public void previousMonth(View view){
        calendar.add(Calendar.MONTH, -1);
        setMonth();

    }
}