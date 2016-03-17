package com.example.praneethkollareddy.restaurantreservation.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.praneethkollareddy.restaurantreservation.R;

public class LoadingPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_page);

        Thread welcomeThread = new Thread() {

            @Override
            public void run() {
                try {
                    super.run();
                    sleep(2000);
                } catch (Exception e) {

                } finally {

                    Intent i = new Intent(getApplicationContext(),Main_Activity.class);
                    startActivity(i);
                    finish();
                }
            }
        };
        welcomeThread.start();



    }
}
