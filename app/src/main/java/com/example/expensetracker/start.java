package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class start extends AppCompatActivity {

    private static int TIME_OUT=1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Handler handler=new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(start.this,LogIn.class);
                startActivity(intent);
                finish();
            }
        },TIME_OUT);
    }
    public void LogIn(View view)
    {
        Intent intent=new Intent(this,LogIn.class);
        startActivity(intent);

    }
    public void SignUp(View view)
    {
        Intent intent =new Intent(this,SignUp.class);
        startActivity(intent);

    }
}