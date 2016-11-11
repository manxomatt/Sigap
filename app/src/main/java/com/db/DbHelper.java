package com.db;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    static final String TAG = "DbHelper";
    static final String DB_NAME = "db_sigap";
    static final int DB_VERSION = 1;
    static Activity activity;
    static Context ctx;

    public DbHelper(Context ctx) {
        super(ctx, DB_NAME, null, DB_VERSION);
        //activity = act;
        //act.getApplicationContext()
        ctx = ctx;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS members("
                + "id INTEGER PRIMARY KEY,"
                + "identity_number TEXT,"
                + "full_name TEXT,"
                + "address TEXT,"
                + "email TEXT,"
                + "phone_number TEXT,"
                + "hash_login TEXT)");

        db.execSQL("CREATE TABLE IF NOT EXISTS tbl_places("
                + "id INTEGER PRIMARY KEY,"
                + "type INTEGER,"
                + "name TEXT,"
                + "phone_number TEXT,"
                + "lat FLOAT,"
                + "lon FLOAT,"
                + "pic_place"
                +")");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS members");
    }
}