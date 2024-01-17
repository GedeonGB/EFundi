package com.example.android.e_fundi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class Sign_out_Activity extends AppCompatActivity {

    Bitmap bitmap;
    public static final int CAMERA_CODE = 12;
    ImageView imgUser;
    public static final int GALLERY_CODE = 112;
    Uri filePath = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_out);

        imgUser = (ImageView) findViewById(R.id.imgUser);
    }

    public void ToCamera(View view) {

        Intent intentCam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intentCam, CAMERA_CODE);
    }

    public void ToGallery(View view) {

        Intent pictureActionIntent = null;

        pictureActionIntent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(
                pictureActionIntent,
                GALLERY_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAMERA_CODE && resultCode == Activity.RESULT_OK && data != null) {

            try {
                bitmap = (Bitmap) data.getExtras().get("data");
                imgUser.setImageBitmap(bitmap);
                imgUser.setVisibility(View.VISIBLE);
//
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (requestCode == GALLERY_CODE && resultCode == RESULT_OK && data != null) {
            filePath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                if (bitmap != null){
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    imgUser.setImageBitmap(bitmap);
                    imgUser.setVisibility(View.VISIBLE);

                } else {
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    imgUser.setImageBitmap(bitmap);
                    imgUser.setVisibility(View.VISIBLE);

                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    public void signoutbtn(View view) {

    }
}
