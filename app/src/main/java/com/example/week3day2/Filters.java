package com.example.week3day2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Filters extends AppCompatActivity {
    ImageView original;
    Button upload, paris, dubai, india;

    Bitmap image, bmb;

    String picturePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        original = (ImageView) findViewById(R.id.imageview);
        upload = (Button) findViewById(R.id.uploadphoto);
        paris = (Button) findViewById(R.id.button1);
        dubai = (Button) findViewById(R.id.button2);
        india = (Button) findViewById(R.id.button3);
    }

    public void upload(View view) {
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

            original.setImageBitmap(image);
        }
    }

    public void dubai(View view) {
        Bitmap operation;
        operation = Bitmap.createBitmap(image.getWidth(), image.getHeight(), image.getConfig());

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int p = image.getPixel(i, j);
                int r = Color.red(p);
                int g = Color.green(p);
                int b = Color.blue(p);
                int alpha = Color.alpha(p);

                int intensity = (r + g + b) / 3;
                int INTENSITY_FACTOR = 120;

                if (intensity > INTENSITY_FACTOR) {
                    operation.setPixel(i, j, Color.argb(alpha, 255, 255, 255));
                } else if (intensity > 100) {
                    operation.setPixel(i, j, Color.argb(alpha, 150, 150, 150));
                } else {
                    operation.setPixel(i, j, Color.argb(alpha, 0, 0, 0));
                }
            }
        }
        original.setImageBitmap(operation);
    }
}