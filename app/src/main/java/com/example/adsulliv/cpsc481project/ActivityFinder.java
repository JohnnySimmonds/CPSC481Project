package com.example.adsulliv.cpsc481project;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

public class ActivityFinder extends AppCompatActivity {

    ArrayList<Bitmap> activityImages = new ArrayList<Bitmap>();       //dynamic list of image names

    //int drawableIndex = R.drawable.stockhike1;      //int to keep track of which drawable the activity finder is looking at
    File directory = new File("/data/user/0/com.example.adsulliv.cpsc481project/", "app_imageDir");     //instantiate a file directory where images are store


    int currentIndex = 0;                      //current index of activity finder (which image is being viewed)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finder);

        loadImages();       //grab images to show in the activity finder

        //File mypath = new File(directory, "activity" + index + ".jpg");

        Button buttonBrowseLeft = (Button) findViewById(R.id.buttonBrowseLeft);  //get instance of left arrow button
        Button buttonBrowseRight = (Button) findViewById(R.id.buttonBrowseRight);  //get instance of right arrow button
        ImageButton buttonRedX = (ImageButton) findViewById(R.id.buttonRedX);       //get instance of red x button
        ImageButton buttonGreenCheck = (ImageButton) findViewById(R.id.buttonGreenCheck);   //get instance of green check button

        buttonBrowseLeft.setOnClickListener(new View.OnClickListener() {
            ImageView theImage = (ImageView) findViewById(R.id.activityImage);  //get instance of outdoor activity image

            @Override
            public void onClick(View view) {
                if (currentIndex > 0) {
                    currentIndex -= 1;
                    theImage.setImageBitmap(activityImages.get(currentIndex));
                }
            }
        });


        buttonBrowseRight.setOnClickListener(new View.OnClickListener() {
            ImageView theImage = (ImageView) findViewById(R.id.activityImage);  //get instance of outdoor activity image

            @Override
            public void onClick(View view) {
                if (currentIndex < activityImages.size()-1){
                    currentIndex += 1;
                    theImage.setImageBitmap(activityImages.get(currentIndex));    //next outdoor activity image
                }
            }

        });


        buttonRedX.setOnClickListener(new View.OnClickListener() {
            ImageView activityImage = (ImageView) findViewById(R.id.activityImage);  //get instance of outdoor activity image

            @Override
            public void onClick(View view) {
               //
            }

        });

        buttonGreenCheck.setOnClickListener(new View.OnClickListener() {
            ImageView activityImage = (ImageView) findViewById(R.id.activityImage);  //get instance of outdoor activity image

            @Override
            public void onClick(View view) {
                // send current activity photo to favorites
            }

        });

    }

    //method to grab a bitmap from a directory
    private Bitmap getBitmap(int imageIndex) {
        File file = new File(directory.getAbsolutePath(), "/activity" + imageIndex + ".jpg");

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.jack);

        try {
            FileInputStream inputStream = new FileInputStream(file);
            bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public void loadImages() {
        int activityNum = 0;
        for (File file : directory.listFiles()) {
            activityImages.add(getBitmap(activityNum));
            activityNum++;
        }
    }


}
