package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.expensetracker.DatabaseContract.Users.TABLE_NAME;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;



public class SignUp extends AppCompatActivity {

    DatabaseHelper dbHelper;
    EditText et1, et2,et3,et4,et5;
    SQLiteDatabase db;
    String val2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        dbHelper = new DatabaseHelper(this);


    }

    public void SignUpSuccess(View v) {
        db = dbHelper.getWritableDatabase();

        et1 = (EditText) findViewById(R.id.name);
        et2 = (EditText) findViewById(R.id.email);
        et3 = (EditText) findViewById(R.id.pass);
        et4 = (EditText) findViewById(R.id.retype);
        et5 = (EditText) findViewById(R.id.phone);
        String val1 = et1.getText().toString();
        String email = et2.getText().toString();
        String val3 = et3.getText().toString();
        String val4 = et4.getText().toString();
        String val5 = et5.getText().toString();
        ContentValues values = new ContentValues();

        boolean bool=alreadyExists(email);

        if (TextUtils.isEmpty(val1))
        {
            et1.setError("Field Required");
        }

        else if (TextUtils.isEmpty(email))
        {
            et2.setError("Field Required");
        }
        else if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))
        {
            et2.setError("Invalid Email");
        }

        else if (bool==true)
        {
            et2.setError("Email already exists");
        }

        else if (TextUtils.isEmpty(val3))
        {
            et3.setError("Field Required");
        }
        else if (val3.length()<6)
        {
            et3.setError("Password must be 6 characters long");
        }
        else if (TextUtils.isEmpty(val4))
        {
            et4.setError("Field Required");
        }
        else if (!val3.equals(val4))
        {
            et4.setError("Password and Re-type passwords are not same");
        }
        else if (TextUtils.isEmpty(val5))
        {
            et5.setError("Field Required");
        }
        else if (val5.length()!=11)
        {
            et5.setError("Phone number must contain 11 digits");
        }

        else {
            values.put(DatabaseContract.Users.COL_NAME, val1);
            values.put(DatabaseContract.Users.COL_EMAIL, email);
            values.put(DatabaseContract.Users.COL_PASSWORD, val3);
            values.put(DatabaseContract.Users.COL_RETYPE, val4);
            values.put(DatabaseContract.Users.COL_PHONE, val5);
            long newRowId = db.insert(TABLE_NAME, null, values);
            if (newRowId > 0) {
                Toast.makeText(this, "New Account Created ", Toast.LENGTH_SHORT).show();

            }

            db.close();
            Intent intent = new Intent(this, LogIn.class);
            intent.putExtra("email", email);
            intent.putExtra("password",val3);
            startActivity(intent);

        }


    }

    private boolean alreadyExists(String email) {
        int n=0;
        Cursor c = db.rawQuery("SELECT * FROM " +
                DatabaseContract.Users.TABLE_NAME + " where " + DatabaseContract.Users.COL_EMAIL+"='"+email+"'" , null);
        while (c.moveToNext()) {
            n++;
        }
        c.close();
        if(n==0){
            return false;
        }
        else{
            return true;
        }

    }


}

