package com.example.week3day2;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class DatabaseAdapter extends ArrayAdapter<InventoryModel> {
    List<InventoryModel> inventoryModelList;
    private Context context;

    public DatabaseAdapter(@NonNull Context context, List<InventoryModel> inventoryModelList) {
        super(context, R.layout.activity_database_view, inventoryModelList);
        this.context = context;
        this.inventoryModelList = inventoryModelList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_database_view, null);
        }

        TextView name = (TextView) convertView.findViewById(R.id. textview1);
        TextView price = (TextView) convertView.findViewById(R.id.textview2);
        TextView category = (TextView) convertView.findViewById(R.id.textview3);

        name.setText(inventoryModelList.get(position).getProductName());
        price.setText(inventoryModelList.get(position).getProductPrice());
        category.setText((inventoryModelList.get(position).getProductCategory()));

        return convertView;
    }
}

