package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import static com.example.expensetracker.DatabaseContract.Users.TABLE_NAME;

public class LogIn extends AppCompatActivity {
    DatabaseHelper dbHelper;
    EditText et1, et2;
    SQLiteDatabase db;
    CheckBox checkBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        /*checkBox=(CheckBox) findViewById(R.id.rem);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked())
                {
                    SharedPreferences sharedPreferences=getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("remember","true");
                    editor.apply();
                    Toast.makeText(LogIn.this,"Checked",Toast.LENGTH_SHORT).show();

                }
                else if (!buttonView.isChecked())
                {

                    SharedPreferences sharedPreferences=getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("remember","false");
                    editor.apply();
                    Toast.makeText(LogIn.this,"unChecked",Toast.LENGTH_SHORT).show();

                }
            }
        });
        */

    }
    public void LoginSuccess(View view)
    {

        et1 = (EditText) findViewById(R.id.email);
        et2 = (EditText) findViewById(R.id.pass);
        String val1 = et1.getText().toString();
        String val2 = et2.getText().toString();
        dbHelper = new DatabaseHelper(this);

        db = dbHelper.getReadableDatabase();

       boolean bool=successfulLogin(val1,val2);

        TextView failedLogin=findViewById(R.id.loginFailed);

        db.close();
        if (TextUtils.isEmpty(val1))
        {
            et1.setError("Field Required");
        }
        else if (TextUtils.isEmpty(val2))
        {
            et2.setError("Field Required");
        }
       else if (!val1.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))
        {
            et1.setError("Invalid Email");
        }
        else if (bool==false)
        {
            failedLogin.setVisibility(View.VISIBLE);
        }
        else {
            failedLogin.setVisibility(View.GONE);
            Intent intent = new Intent(this, MainActivity2.class);
            intent.putExtra("Email", val1);
            startActivity(intent);
            finish();
        }

    }
    public void signup(View v)
    {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }
    private boolean successfulLogin(String val1, String val2) {

        Cursor cursor = db.rawQuery("SELECT * FROM " +
                DatabaseContract.Users.TABLE_NAME + " where " + DatabaseContract.Record.COL_EMAIL+"='"+val1+"' AND "+DatabaseContract.Users.COL_PASSWORD +"='"+val2+"'" , null);
        if (cursor.getCount()==1) {
            cursor.moveToFirst();
            //String email = cursor.getString(2);
            //et2.setText(email);
            return true;
        }
        else{
            //et2.setText("No record found");
            return false;
        }


    }

}
