package com.example.user.weatherapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
    private static int TIMER=3000;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        textView =findViewById(R.id.TV);
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.animation);
        textView.startAnimation(animation);
        new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                    Intent iuu = new Intent(SplashScreen.this,MainActivity.class);
                    startActivity(iuu);
                    finish();
                }
            }
        }.start();
    }
}
