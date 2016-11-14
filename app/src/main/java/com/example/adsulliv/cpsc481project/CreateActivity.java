package com.example.adsulliv.cpsc481project;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;



/*
TO DO
add the ability for a person to write a description of the event
add a photo from gallery
store photo as drawable so that the activity finder can view it
*/


public class CreateActivity extends AppCompatActivity implements View.OnClickListener {
    private static int MY_REQUEST_CAMERA = 1; //my request code for accessing the camera
    private static int MY_REQUEST_GALLERY = 2; //my request code for accessing the gallery
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        Button camBut = (Button) findViewById(R.id.camera);
        camBut.setOnClickListener(CreateActivity.this);

    }

    /*open camera when button is pressed*/

    public void onClick(View view) {
        Intent intent;
        switch(view.getId()) {

            case R.id.camera: {
                 intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //create the intent for using the camera


                if (intent.resolveActivity(getPackageManager()) != null) { //Protects app from crashing as if no intent can be handled the app would crash
                    startActivityForResult(intent, MY_REQUEST_CAMERA); //start intent with a result so that we can store the image afterwards

                }
            }
            break;
            case R.id.gallery:
            {
                intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
               // intent.setType("image/*"); dont think this is needed
                if (intent.resolveActivity(getPackageManager()) != null) {

                    startActivityForResult(intent, MY_REQUEST_GALLERY); //view gallery
                }
            }
            break;
        }
    }

    /*stores the image data from the startActivityForResult intent in the imageview so that it can be displayed on screen*/
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImageView pic=null;
        /* Store the photo taken from the camera as a bitmap to be displayed on screen*/
        if (requestCode == MY_REQUEST_CAMERA) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            pic = (ImageView) findViewById(R.id.camImg);
            pic.setImageBitmap(imageBitmap);
            loadImageFromStorage(saveToInternalStorage(imageBitmap));
        }
        /* Store the photo taken from the gallery as a bitmap to be displayed on screen*/
        else if(requestCode == MY_REQUEST_GALLERY)
        {
            Uri selectedImage = data.getData();
            try {

                Bitmap bm = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                pic = (ImageView) findViewById(R.id.camImg);
                pic.setImageBitmap(bm);
                loadImageFromStorage(saveToInternalStorage(bm));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * Save the image as a bitmap in a local directory.
     * @param bitmapImage from camera or gallery.
     * @return imageDir path
     */
    private String saveToInternalStorage(Bitmap bitmapImage) {
        // Get the current context of the application - i.e. the path it will use for creating the dir.
        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());

        // Create the directory to save the image, named imageDir.
        File directory = contextWrapper.getDir("imageDir", Context.MODE_PRIVATE);

        // Give the image it's name.
        File mypath = new File(directory, "activity.jpg");

        FileOutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(mypath);

            // Compress the bitmap to write the image to the output stream.
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    /**
     * Load the image from imageDir.
     * @param path to imageDir.
     */
    private void loadImageFromStorage(String path) {
        try {
            File file = new File(path, "activity.jpg");
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            ImageView imageView = (ImageView)findViewById(R.id.dummy);
            imageView.setImageBitmap(bitmap);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
