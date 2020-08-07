package com.example.expensetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity2 extends AppCompatActivity {
    String email,password;
    TextView tv1;
    SQLiteDatabase db;
    DatabaseHelper dbHelper;

    Calendar calendar;
    ArrayList<String> amount=new ArrayList<>();
    ArrayList<String> type=new ArrayList<>();
    double[] d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getReadableDatabase();

        Intent intent = getIntent();
        email = intent.getStringExtra("Email");
        password=intent.getStringExtra("password");

        setName();


        setStatistics();

    }

    private void setName(){
        String name="user";
        TextView t=(TextView)findViewById(R.id.textView3) ;
        String[] columns = {DatabaseContract.Users.COL_NAME};
        Cursor c = db.query(DatabaseContract.Users.TABLE_NAME, columns,
                DatabaseContract.Users.COL_EMAIL+" =?",new String[] {email}, null, null, null);

        while (c.moveToNext()) {
            name=c.getString(0);
        }
        c.close();
        t.setText("Hi "+name);

    }


    private void setStatistics(){
        type.clear();
        amount.clear();
        calendar=Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int year=calendar.get(Calendar.YEAR);
        month++;
        Cursor c = db.rawQuery("SELECT * FROM " +
                DatabaseContract.Record.TABLE_NAME + " where " +DatabaseContract.Record.COL_DATE+ " like '%/"+month+"/"+year+"' AND "+ DatabaseContract.Record.COL_EMAIL+"='"+email+"'" , null);
        while (c.moveToNext()) {
            type.add(c.getString(1)) ;
            amount.add(c.getString(2)) ;
        }
        c.close();

        double in,ex;
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
        double bal=in-ex;
        TextView t1=(TextView)findViewById(R.id.textView7);
        t1.setText("Rs."+in);
        TextView t2=(TextView)findViewById(R.id.textView8);
        t2.setText("Rs."+ex);
        TextView t3=(TextView)findViewById(R.id.textView9);
        t3.setText("Rs."+bal);
        if(bal<0){
            TextView t4=(TextView)findViewById(R.id.warning);
            t4.setVisibility(View.VISIBLE);
        }

        else {
            TextView t4=(TextView)findViewById(R.id.warning);
            t4.setVisibility(View.GONE);
        }


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.options,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.Delete:
                Toast.makeText(this,"Delete Selected",Toast.LENGTH_SHORT).show();
                db = dbHelper.getWritableDatabase();
                String[] wherearg = {email};
                Integer i1= db.delete(DatabaseContract.Record.TABLE_NAME, DatabaseContract.Record.COL_EMAIL +"=?",wherearg);
                if (i1 > 0)
                {
                    Toast.makeText(this, i1+"  Records deleted: " , Toast.LENGTH_SHORT).show();
                }
                Integer i2= db.delete(DatabaseContract.Users.TABLE_NAME, DatabaseContract.Users.COL_EMAIL +"=?",wherearg);
                if (i2 > 0)
                {
                    Toast.makeText(this, i1+"  Email deleted: " , Toast.LENGTH_SHORT).show();
                }
                db.close();
                Intent i=new Intent(this,LogIn.class);
                startActivity(i);
                finish();
                return true;
            case R.id.Logout:
                Toast.makeText(this,"LogOut Selected",Toast.LENGTH_SHORT).show();
                Intent il=new Intent(this,LogIn.class);
                startActivity(il);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void generateReport(View view){
        Intent intent=new Intent(this,Report.class);
        intent.putExtra("email",email);
        startActivity(intent);
    }

    public void viewTransactions(View v)
    {
        Intent i=new Intent(this,AllTransactions.class);
        i.putExtra("email",email);
       // startActivity(i);
        startActivityForResult(i,1);
        setStatistics();
    }
    public void addTransaction(View view){
        Intent i=new Intent(this,AddTransaction.class);
        i.putExtra("email",email);
       // startActivity(i);
        startActivityForResult(i,1);

        setStatistics();
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, getIntent());
        if(resultCode==RESULT_OK && requestCode==1){
            setStatistics();
        }
    }

    public void viewSummary(View view){
        Intent intent=new Intent(this,Summary.class);
        intent.putExtra("email",email);
        startActivity(intent);
    }

}