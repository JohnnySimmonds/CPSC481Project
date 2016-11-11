package com.example.adsulliv.cpsc481project;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/*
TO DO
add the ability for a person to write a description of the event
add a photo from gallery
store photo as drawable so that the activity finder can view it
*/


public class CreateActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        Button camBut = (Button) findViewById(R.id.camera);
        camBut.setOnClickListener(CreateActivity.this);

    }

    /*open camera when button is pressed*/

    public void onClick(View view) {

        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);

    }
    /*stores the image data from the startActivityForResult intent in the imageview so that it can be displayed on screen*/
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bundle extras = data.getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get("data");
        if (requestCode == 1) {
            ImageView pic = (ImageView) findViewById(R.id.camImg);
            pic.setImageBitmap(imageBitmap);
        }
    }

}
