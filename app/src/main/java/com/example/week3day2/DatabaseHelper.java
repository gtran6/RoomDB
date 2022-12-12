package com.example.week3day2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    //table to store product details
    private static final String TABLE_NAME = "inventory";
    private static final String COL1 = "UniqueID";
    private static final String COL2 = "Name";
    private static final String COL3 = "Price";
    private static final String COL4 = "Photo";
    private static final String COL5 = "Category";

    //constructor
    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    //create the table in database
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        /*
        String createTable = "CREATE TABLE " + TABLE_NAME + "("
                + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL2 + " TEXT NOT NULL," + COL3 + " TEXT NOT NULL,"
                 + COL4 + " TEXT NOT NULL)";
         */
        String createTable = "CREATE TABLE " + TABLE_NAME + "(" + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL2
                + " TEXT NOT NULL," + COL3 + " TEXT NOT NULL," + COL4 + " BLOB," + COL5 + " TEXT NOT NULL)";
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
