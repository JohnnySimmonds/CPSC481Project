package com.example.adsulliv.cpsc481project;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Favorites extends AppCompatActivity {
    ImageView imageView;
    RoundImage roundImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        LinearLayout ll1 = (LinearLayout)findViewById(R.id.firstRow);
        roundImages(ll1);
        LinearLayout ll2 = (LinearLayout)findViewById(R.id.secondRow);
        roundImages(ll2);
    }

    public void roundImages(LinearLayout ll) {
        final int childCount = ll.getChildCount();
        for (int i = 0; i < childCount; i++) {
            imageView = (ImageView) ll.getChildAt(i);

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.jack);
            roundImage = new RoundImage(bitmap);
            imageView.setImageDrawable(roundImage);
        }
    }
}
