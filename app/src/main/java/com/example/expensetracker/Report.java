package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Report extends AppCompatActivity {
    String email;
    PieChart pieChart;
    ToggleButton aSwitch;
    TextView prev;
    TextView next;
    TextView currentDate;
    DatabaseHelper dbHelper;
    ArrayList<String> type=new ArrayList<>();
    ArrayList<String> amount=new ArrayList<>();
    ArrayList<String> catagory=new ArrayList<>();
    String cDate;
    Calendar calendar;
    Date ndate;
    SimpleDateFormat dateFormat;
    double[] d;
    SQLiteDatabase db;
    double in=0;double ex=0;
    ArrayList<PieEntry> yEntry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getReadableDatabase();

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        prev=findViewById(R.id.prevDate);
        next=findViewById(R.id.nextDate);
        currentDate=findViewById(R.id.date);
        aSwitch=findViewById(R.id.switch1);
        pieChart= findViewById(R.id.pieChart);

        calendar=Calendar.getInstance();
        setMonth();

        //pieChart.invalidate();
    }

    private void setPieChart() {
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(0);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setText("Transaction Summary");
        pieChart.setNoDataText("No Transactions Found");
        pieChart.animateXY(1500,1500);
    }

    public void previousMonth(View view){
        calendar.add(Calendar.MONTH, -1);
        setMonth();

    }

    private void setMonth() {
        dateFormat = new SimpleDateFormat("MMMM yyyy");
        ndate=calendar.getTime();
        cDate = dateFormat.format(ndate);
        currentDate.setText(cDate);
        getdbValues();
        setData();
        setPieChart();

    }

    public void nextMonth(View view){
        calendar.add(Calendar.MONTH, 1);
        setMonth();
    }

    public void ChangeData(View view){
        setData();
    }

    private void setData(){
        if(amount.size()>0){
            if (aSwitch.isChecked()){
                addCategoryData();
            }
            else{
                addTypeData();
            }
        }
        else {
            if(pieChart.getData() != null){

                pieChart.getData().clearValues();
            }
            pieChart.clear();
        }
    }

    private void addTypeData() {
        if(pieChart.getData() != null){
            pieChart.getData().clearValues();
        }
        pieChart.clear();
        in=0;ex=0;
        d=new double[amount.size()];

        for (int i=0;i<amount.size();i++){
            d[i]=Double.parseDouble(amount.get(i));
            if (type.get(i).equals("income")){
                in=in+d[i];
            }
            else{
                ex=ex+d[i];
            }

        }

        yEntry=new ArrayList<>();
        yEntry.add(new PieEntry((float) in,"Income"));
        yEntry.add(new PieEntry((float) ex,"Expense"));


        PieDataSet pieDataSet=new PieDataSet(yEntry,"Types");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(16);
        pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        Legend legend=pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);

        PieData pieData=new PieData(pieDataSet);
        pieData.setValueFormatter(new PercentFormatter(pieChart));
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    private void addCategoryData(){
        yEntry=new ArrayList<>();
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
            yEntry.add(new PieEntry((float) d[i],category.get(i)));
        }

        PieDataSet pieDataSet=new PieDataSet(yEntry,"Categories");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(16);
        pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        Legend legend=pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        PieData pieData=new PieData(pieDataSet);
        pieData.setValueFormatter(new PercentFormatter(pieChart));
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    private void getdbValues(){

        type.clear();
        amount.clear();
        catagory.clear();
        int month = calendar.get(Calendar.MONTH);
        month++;
        int year = calendar.get(Calendar.YEAR);

        Cursor c = db.rawQuery("SELECT * FROM " +
                DatabaseContract.Record.TABLE_NAME + " where " +DatabaseContract.Record.COL_DATE+" like '%/"+month+"/"+year+"' AND "+ DatabaseContract.Record.COL_EMAIL+"='"+email+"'", null);
        while (c.moveToNext()) {
            type.add(c.getString(1)) ;
            amount.add(c.getString(2)) ;
            catagory.add(c.getString(3));
        }
        c.close();

    }
}