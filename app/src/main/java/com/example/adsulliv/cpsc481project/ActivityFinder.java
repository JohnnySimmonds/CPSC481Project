package com.example.adsulliv.cpsc481project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ActivityFinder extends AppCompatActivity {
    //ArrayList<String> activityImages = new ArrayList<String>();       //dynamic list of image names
    //activityImages.add(R.drawable.stockskimap1);

    int drawableIndex = R.drawable.stockhike1;      //int to keep track of which drawable the activity finder is looking at

    int index = 1;                      //current index of activity finder (which image is being viewed)
    int amountActivities = 4;           //amount of activities in activity finder

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finder);

        Button buttonBrowseLeft = (Button) findViewById(R.id.buttonBrowseLeft);  //get instance of left arrow button
        Button buttonBrowseRight = (Button) findViewById(R.id.buttonBrowseRight);  //get instance of right arrow button
        ImageButton buttonRedX = (ImageButton) findViewById(R.id.buttonRedX);       //get instance of red x button
        ImageButton buttonGreenCheck = (ImageButton) findViewById(R.id.buttonGreenCheck);   //get instance of green check button

        buttonBrowseLeft.setOnClickListener(new android.view.View.OnClickListener() {
            ImageView activityImage = (ImageView) findViewById(R.id.activityImage);  //get instance of outdoor activity image

            @Override
            public void onClick(View view) {
                if (index > 0) {
                    index -= 1;
                    drawableIndex -= 1;
                    activityImage.setImageResource(drawableIndex);
                }
            }
        });


        buttonBrowseRight.setOnClickListener(new android.view.View.OnClickListener() {
            ImageView activityImage = (ImageView) findViewById(R.id.activityImage);  //get instance of outdoor activity image

            @Override
            public void onClick(View view) {
                if (index < amountActivities - 1){
                    index += 1;
                    drawableIndex += 1;
                    activityImage.setImageResource(drawableIndex);    //next outdoor activity image
                }
            }

        });


        buttonRedX.setOnClickListener(new android.view.View.OnClickListener() {
            ImageView activityImage = (ImageView) findViewById(R.id.activityImage);  //get instance of outdoor activity image

            @Override
            public void onClick(View view) {
               //
            }

        });

        buttonGreenCheck.setOnClickListener(new android.view.View.OnClickListener() {
            ImageView activityImage = (ImageView) findViewById(R.id.activityImage);  //get instance of outdoor activity image

            @Override
            public void onClick(View view) {
                //
            }

        });

    }
}
