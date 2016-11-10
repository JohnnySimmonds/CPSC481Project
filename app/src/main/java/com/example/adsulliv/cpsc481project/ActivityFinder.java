package com.example.adsulliv.cpsc481project;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import static com.example.adsulliv.cpsc481project.R.id.activityImage;
import static com.example.adsulliv.cpsc481project.R.id.image;
import static com.example.adsulliv.cpsc481project.R.styleable.View;

public class ActivityFinder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finder);

        Button buttonBrowseLeft = (Button)findViewById(R.id.buttonBrowseLeft);  //get instance of button to work on

        buttonBrowseLeft.setOnClickListener(new android.view.View.OnClickListener() {
            ImageView activityImage = (ImageView)findViewById(R.id.activityImage);  //get instance of image to work on
            //Drawable nextimage = getResources().getDrawable(R.drawable.stockskimap1);

            @Override
            public void onClick(View view) {
                //activityImage.setImageResource(R.drawable.stockskimap1);
                //activityImage.setBackground(nextimage);
            }
        });
    }



}
