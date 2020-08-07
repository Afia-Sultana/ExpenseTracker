package com.example.expensetracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "EXPENSE.db";
    private static final String CREATE_TBL_RECORD = "CREATE TABLE "
            + DatabaseContract.Record.TABLE_NAME + " ("
            + DatabaseContract.Record._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DatabaseContract.Record.COL_TYPE+ " TEXT NOT NULL,"
            + DatabaseContract.Record.COL_AMOUNT + " TEXT NOT NULL,"
            + DatabaseContract.Record.COL_CATAGORY + " TEXT NOT NULL,"
            + DatabaseContract.Record.COL_DATE+ " TEXT NOT NULL,"
            + DatabaseContract.Record.COL_NOTE + " TEXT,"
            + DatabaseContract.Record.COL_EMAIL + " TEXT )";
    private static final String CREATE_TBL_USERS = "CREATE TABLE "
            + DatabaseContract.Users.TABLE_NAME + " ("
            + DatabaseContract.Users._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DatabaseContract.Users.COL_NAME + " TEXT NOT NULL, "
            + DatabaseContract.Users.COL_EMAIL + " TEXT NOT NULL,"
            + DatabaseContract.Users.COL_PASSWORD + " TEXT NOT NULL,"
            + DatabaseContract.Users.COL_RETYPE + " TEXT NOT NULL,"
            + DatabaseContract.Users.COL_PHONE + " TEXT NOT NULL)";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TBL_USERS);
        db.execSQL(CREATE_TBL_RECORD);


        // TODO Auto-generated method stub
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
    }

}


   /* private static final String CREATE_TBL_RECORD = "CREATE TABLE "
            + DatabaseContract.Record.TABLE_NAME + " ("
            + DatabaseContract.Record._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DatabaseContract.Record.COL_TYPE+ " TEXT NOT NULL,"
            + DatabaseContract.Record.COL_AMOUNT + " TEXT NOT NULL,"
            + DatabaseContract.Record.COL_CATAGORY + " TEXT NOT NULL,"
            + DatabaseContract.Record.COL_DATE+ " TEXT NOT NULL,"
            + DatabaseContract.Record.COL_NOTE + " TEXT,"
            + DatabaseContract.Record.COL_EMAIL + " TEXT )";
    private static final String CREATE_TBL_USERS = "CREATE TABLE "
            + DatabaseContract.Users.TABLE_NAME + " ("
            + DatabaseContract.Users._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DatabaseContract.Users.COL_NAME + " TEXT NOT NULL, "
            + DatabaseContract.Users.COL_EMAIL + " TEXT NOT NULL,"
            + DatabaseContract.Users.COL_PASSWORD + "TEXT NOT NULL,"
            + DatabaseContract.Users.COL_RETYPE + "TEXT NOT NULL,"
            + DatabaseContract.Users.COL_PHONE + "TEXT NOT NULL)";*/


