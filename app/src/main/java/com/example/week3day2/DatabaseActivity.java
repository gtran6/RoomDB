package com.example.week3day2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class DatabaseActivity extends AppCompatActivity {
    TextView itemName;
    TextView itemPrice;
    TextView itemCategory;

    EditText editName;
    EditText editPrice;
    EditText editCategory;

    Button submit;

    DatabaseDAO databaseDAO;

    ImageView imageView;

    Button uploadPhoto;

    byte[] imageconverted;
    Bitmap image;
    String picturePath;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        itemName = (TextView) findViewById(R.id.itemName);
        itemPrice = (TextView) findViewById(R.id.itemPrice);
        itemCategory = (TextView) findViewById(R.id.itemCategory);
        editName = (EditText) findViewById(R.id.editItemName);
        editPrice = (EditText) findViewById(R.id.editItemPrice);
        editCategory = (EditText) findViewById(R.id.editItemCategory);
        submit = (Button) findViewById(R.id.submitButton);

        databaseDAO = new DatabaseDAO(getApplicationContext());

        imageView = (ImageView) findViewById(R.id.imagePhoto);
        uploadPhoto = (Button) findViewById(R.id.uploadImageButton);
    }

    public void deleteData (View view) {
        Integer deleteRows = databaseDAO.deleteData(itemName.getText().toString());

        if (deleteRows > 0) {
            Toast.makeText(getApplicationContext(), "DELETED", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "DELETED UNSUCCESSFULLY", Toast.LENGTH_SHORT).show();
        }
    }

    public void AddDatatoDB (View view) {
        boolean result = databaseDAO.insertData(editName.getText().toString(),
                editPrice.getText().toString(), imageconverted , editCategory.getText().toString());

        if (result) {
            Toast.makeText(getApplicationContext(), " DETAILS ADDED SUCCESSFULLY", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(DatabaseActivity.this, DatabaseExample.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "DETAILS FAILED ADDED", Toast.LENGTH_SHORT).show();
        }
    }

    public void addImageFromGalary(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            //specify the uri
            Uri selectedImage = data.getData();

            String[] filePath = {MediaStore.Images.Media.DATA};

            Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);

            c.moveToFirst();

            int columnIndex = c.getColumnIndex(filePath[0]);

            picturePath = c.getString(columnIndex);

            c.close();

            image = (BitmapFactory.decodeFile(picturePath));
            Log.i("path", picturePath + "");

            imageView.setImageBitmap(image);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 40, stream);
            imageconverted = stream.toByteArray();
        }
    }
}