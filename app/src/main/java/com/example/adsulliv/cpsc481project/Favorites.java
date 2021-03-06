package com.example.adsulliv.cpsc481project;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileInputStream;

public class Favorites extends AppCompatActivity {
    ImageView imageView;
    RoundImage roundImage;

    File directory = new File("/data/user/0/com.example.adsulliv.cpsc481project/", "app_imageDir");
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        LinearLayout firstLayout = (LinearLayout)findViewById(R.id.firstRow);
        loadImages(firstLayout);
        roundImages(firstLayout);

        LinearLayout secondLayout = (LinearLayout)findViewById(R.id.secondRow);
        roundJacks(secondLayout, R.drawable.squareski);

        LinearLayout thirdLayout = (LinearLayout)findViewById(R.id.thirdRow);
        roundJacks(thirdLayout, R.drawable.rafting);

        LinearLayout fourthLayout = (LinearLayout)findViewById(R.id.fourthRow);
        roundJacks(fourthLayout, R.drawable.rockclimbing);

        LinearLayout fifthLayout = (LinearLayout)findViewById(R.id.fifthRow);
        roundJacks(fifthLayout, R.drawable.scubadiving);

        LinearLayout sixthLayout = (LinearLayout)findViewById(R.id.sixthRow);
        roundJacks(sixthLayout, R.drawable.skydiving);
    }

    /**
     * Populate linearLayout with imageViews.
     * @param linearLayout to insert images into
     */
    public void loadImages(LinearLayout linearLayout) {
        for (File file : directory.listFiles()) {
            ImageView image = new ImageView(this);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 0, 10, 0);
            image.setLayoutParams(params);
            image.getLayoutParams().height = 250;
            image.getLayoutParams().width = 250;
            image.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view){
                    beginActivityDetail(view);
                }
            });

            linearLayout.addView(image);
        }
    }

    /**
     * Get images from directory and set them to the imageViews. Round the image corners.
     * @param linearLayout traversed to find imageViews.
     */
    public void roundImages(LinearLayout linearLayout) {
        final int childCount = linearLayout.getChildCount();

        for (int i = 0; i < childCount; i++) {
            imageView = (ImageView) linearLayout.getChildAt(i);
            roundImage = new RoundImage(getBitmap());
            imageView.setImageDrawable(roundImage);
        }

        index = 0;
    }

    public void roundJacks(LinearLayout linearLayout, int imageDrawable) {
        final int childCount = linearLayout.getChildCount();

        for (int i = 0; i < childCount; i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imageDrawable);
            imageView = (ImageView) linearLayout.getChildAt(i);
            roundImage = new RoundImage(bitmap);
            imageView.setImageDrawable(roundImage);
        }

    }

    /**
     * Get the bitmap for images in the imageDir.
     * @return bitmap of the image found in the imageDir directory.
     */
    private Bitmap getBitmap() {
        File file = new File(directory.getAbsolutePath(), "/activity" + index + ".jpg");
        index++;

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

    /**
     * Begin the activity detail page.
     * @param view for current context.
     */
    public void beginActivityDetail(View view){
        Intent intent = new Intent(this, ActivityDetail.class);
        startActivity(intent);
    }
}
