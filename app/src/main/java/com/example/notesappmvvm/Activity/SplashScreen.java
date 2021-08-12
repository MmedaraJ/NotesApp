package com.example.notesappmvvm.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.notesappmvvm.R;

import java.util.Objects;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //when action bar is on, use this line to turn it off on a particular page
        Objects.requireNonNull(getSupportActionBar()).hide();

        //after 2000 milli secs of this activity running, open the main activity layout
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                //after main activity has opened, close this activity.
                //Do not leave it running in the background
                finish();
            }
        }, 2000);
    }
}