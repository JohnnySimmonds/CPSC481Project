package com.example.adsulliv.cpsc481project;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;

public class ActivityDetail extends AppCompatActivity {
    TextView titleField;
    TextView infoField;
    TextView priceField;

    File directory = new File("/data/user/0/com.example.adsulliv.cpsc481project/", "app_imageDir");
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView imageView = (ImageView)findViewById(R.id.detail_image);
        imageView.setImageBitmap(getBitmap());

        titleField = (TextView)findViewById(R.id.title_view);
        infoField = (TextView)findViewById(R.id.info_view);
        priceField = (TextView)findViewById(R.id.price_view);

        readFile();
    }

    /**
     * Get the bitmap for images in the imageDir.
     * @return bitmap of the image found in the imageDir directory.
     */
    private Bitmap getBitmap() {
        File file = new File(directory.getAbsolutePath(), "/activity" + index + ".jpg");
        //index++;

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

    private void readFile() {
        //Reads from a file and from all text fields.
        TextView test = (TextView) findViewById(R.id.test);

        //int temp = index - 1;
        File file = new File(directory.getAbsolutePath(), "/activity" + index + ".jpg");

        try {

            String infoString = "";
            String priceString = "";
            String actString = "";
            char input;
            int count;
            FileInputStream instream = new FileInputStream(file);

            //reads and parses the string order  image->activity->info->price
            if (instream != null) {
                Bitmap bitmap = BitmapFactory.decodeStream(instream);
                count = instream.available();

                while(count > 0)
                {
                    input = (char) instream.read();
                    count--;
                    while( input != '\n') {

                        actString += Character.toString(input); //gets one char
                        input = (char) instream.read();
                        count--;

                    }
                    input = (char)instream.read();
                    count--;
                    while( input != '\n') {

                        infoString += Character.toString(input); //gets one char
                        input = (char) instream.read();
                        count--;
                    }
                    input = (char)instream.read();
                    count--;
                    while( input != '\n') {

                        priceString += Character.toString(input); //gets one char
                        input = (char) instream.read();
                        count--;
                    }


                }
                titleField.setText(actString);
                infoField.setText(infoString);
                priceField.setText("$" + priceString);//too see if text is working for each string
            }
            instream.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
