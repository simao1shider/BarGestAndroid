package com.example.bargest.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.bargest.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

    }

    @Override
    protected void onStart() {
        super.onStart();
        ImageView logo =(ImageView) findViewById(R.id.splashLogo);
        Animation fadein = AnimationUtils.loadAnimation(this,R.anim.fade_in);
        logo.startAnimation(fadein);

        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                //TODO:Transition between splash and login
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        },5000);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}