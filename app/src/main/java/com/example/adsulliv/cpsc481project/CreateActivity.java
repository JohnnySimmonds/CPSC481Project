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
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;



/*
TO DO
add the ability for a person to write a description of the event
add a photo from gallery
store photo as drawable so that the activity finder can view it
*/


public class CreateActivity extends AppCompatActivity implements View.OnClickListener {

    /* Global variables*/
    public static int index = 0; // way of organizing images to be accessed later
    private static int MY_REQUEST_CAMERA = 1; //my request code for accessing the camera
    private static int MY_REQUEST_GALLERY = 2; //my request code for accessing the gallery
    Bitmap imageBitmap = null;
    ImageView pic = null;
    EditText info = null;
    EditText activity =  null;
    EditText price = null;
    File directory = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        Button camBut = (Button) findViewById(R.id.camera);
        camBut.setOnClickListener(CreateActivity.this);

        pic =  (ImageView) findViewById(R.id.camImg);
        info = (EditText) findViewById(R.id.description);
        activity = (EditText) findViewById(R.id.activity);
        price = (EditText) findViewById(R.id.price);
        /*
        EditText editText = (EditText) findViewById(R.id.description);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                TextView description = (TextView) findViewById(R.id.test);
                description.setText(v.getText());

                return false;
            }
        });
        */

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
            /* when activity is posted save to file and clear all fields*/
            case R.id.postActivity:
            {

                pic.setVisibility(View.VISIBLE);
                loadImageFromStorage(saveToInternalStorage(imageBitmap));

                readFile();

                pic.setImageBitmap(null);
                pic.setVisibility(View.INVISIBLE);
                info.setText(null);
                activity.setText(null);
                price.setText(null);
            }
            break;
            default:
        }
    }

    private void readFile()
    {
        //----------------------------------------------------------------------------------
               //Reads from a file and from all text fields.
        TextView test = (TextView) findViewById(R.id.test);
        File file = new File(directory.getAbsolutePath(), "/activity" + --index + ".jpg");

        try {

            String testString = "";
            FileInputStream instream = new FileInputStream(file);
            if (instream != null) {
                Bitmap bitmap = BitmapFactory.decodeStream(instream);
                while(instream.available() > 0)
                {


                    testString += Character.toString((char)instream.read()); //gets one char

                }
                test.setText(testString);
            }
            instream.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        //----------------------------------------------------------------------------------
    }
    /*stores the image data from the startActivityForResult intent in the imageview so that it can be displayed on screen*/
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImageView pic=null;
        /* Store the photo taken from the camera as a bitmap to be displayed on screen*/
        if (resultCode == RESULT_CANCELED)
            return;

        if (requestCode == MY_REQUEST_CAMERA) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            pic = (ImageView) findViewById(R.id.camImg);
            pic.setImageBitmap(imageBitmap);
            pic.setVisibility(View.VISIBLE);
          //  loadImageFromStorage(saveToInternalStorage(imageBitmap));
        }
        /* Store the photo taken from the gallery as a bitmap to be displayed on screen*/
        else if(requestCode == MY_REQUEST_GALLERY) {
            Uri selectedImage = data.getData();
            
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                pic = (ImageView) findViewById(R.id.camImg);
                pic.setImageBitmap(imageBitmap);
                pic.setVisibility(View.VISIBLE);
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
        directory = contextWrapper.getDir("imageDir", Context.MODE_PRIVATE);

        // Give the image it's name.
        File mypath = new File(directory, "activity" + index + ".jpg");
        index++;
        FileOutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(mypath);

            // Compress the bitmap to write the image to the output stream.
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.write(info.getText().toString().getBytes()); //stores the string in info into byte to be accessed later
            outputStream.write(activity.getText().toString().getBytes());//stores the string in activity into byte to be accessed later
            outputStream.write(price.getText().toString().getBytes());//stores the string in price into byte to be accessed later
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
            File file = new File(path, "activity" + --index + ".jpg");
            index++;
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            ImageView imageView = (ImageView)findViewById(R.id.dummy);
            imageView.setImageBitmap(bitmap);
            imageView.setVisibility(View.VISIBLE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
