package com.example.week3day2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class DatabaseDAO extends MainActivity {
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;

    public DatabaseDAO (Context context) {
        databaseHelper = new DatabaseHelper(context);
        //open a database that will be used for reading and writing
        //the first time this is called, the database will be opened
        db = databaseHelper.getWritableDatabase();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //DAO class will have all IRUD opertation method
    public boolean insertData (String name, String price, byte[] photo, String category) {
        //class that matches a value to a String key
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", name);
        contentValues.put("Price", price);
        contentValues.put("Photo", photo);
        contentValues.put("Category", category);

        long result = db.insert("inventory", null, contentValues);

        if (result == -1) {
            return false;
        }
        return true;
    }

    public Integer deleteData(String name) {
        int result = db.delete("inventory", "Name= ?", new String[] {name});
        return result;
    }
    public List getData() {
        db = databaseHelper.getReadableDatabase();

        List detailsList = new ArrayList();

        String[] tableColumns = new String[]{"UniqueID", "Name", "Price", "Category"};

        //contain the result set of a query made against a database in Android.
        //the cursor class has an API that allows an app to read
        //the columns that were returned from the query
        //as well as iterate over the rows of the result set.
        //select * from table_name -> result set after run button
        Cursor cursor = db.query("inventory", tableColumns, null,
                null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            InventoryModel details = new InventoryModel();
            details.setProductName(cursor.getString(1));
            details.setProductPrice(cursor.getString(2));
            details.setPhoto(cursor.getBlob(3));
            details.setProductCategory(cursor.getString(4));
            detailsList.add(details);
            cursor.moveToNext();
        }
        return detailsList;
    }
}
