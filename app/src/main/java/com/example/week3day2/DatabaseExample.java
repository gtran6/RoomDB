package com.example.week3day2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

public class DatabaseExample extends AppCompatActivity {
    GridView gridView;

    DatabaseDAO databaseDAO;

    DatabaseAdapter databaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_example);

        gridView = (GridView) findViewById(R.id.grid);

        databaseDAO = new DatabaseDAO(this);
        databaseAdapter = new DatabaseAdapter(getApplicationContext(), databaseDAO.getData());

        gridView.setAdapter(databaseAdapter);
    }
}