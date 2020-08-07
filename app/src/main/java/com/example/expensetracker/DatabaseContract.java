package com.example.expensetracker;

import android.provider.BaseColumns;


public class DatabaseContract {
    public DatabaseContract() {}

    /* Inner class that defines the table contents */
    public static abstract class Record implements BaseColumns {
        public static final String TABLE_NAME="Transactions";
        public static final String COL_TYPE="Type";
        public static final String COL_AMOUNT="Amount";
        public static final String COL_CATAGORY="Catagory";
        public static final String COL_DATE="Date";
        public static final String COL_NOTE="Note";
        public static final String COL_EMAIL="Email";

    }
    public static abstract class Users implements BaseColumns {
        public static final String TABLE_NAME = "userInfo";
        public static final String COL_NAME = "name";
        public static final String COL_EMAIL = "email";
        public static final String COL_PASSWORD = "password";
        public static final String COL_RETYPE = "retype";
        public static final String COL_PHONE = "phone";

    }
}

    /* Inner class that defines the table contents */
   /* public abstract class Record implements BaseColumns
    {
        public static final String TABLE_NAME="Transactions";
        public static final String COL_TYPE="Type";
        public static final String COL_AMOUNT="Amount";
        public static final String COL_CATAGORY="Catagory";
        public static final String COL_DATE="Date";
        public static final String COL_NOTE="Note";
        public static final String COL_EMAIL="Email";
    }
    public static abstract class Users implements BaseColumns {
        public static final String TABLE_NAME = "userInfo";
        public static final String COL_NAME = "name";
        public static final String COL_EMAIL = "email";
        public static final String COL_PASSWORD = "password";
        public static final String COL_RETYPE = "retype";
        public static final String COL_PHONE = "phone";

    }*/


