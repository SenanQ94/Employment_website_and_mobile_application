package com.example.ewejobapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.ewejobapp.R;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 2000;//3200;
    private SharedPreferences sharedPreferences;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedPreferences = getSharedPreferences("Main", Context.MODE_PRIVATE);

        image = findViewById(R.id.image);
        try {
            Glide.with(this).load(R.drawable.splashh1).into(image);
        }catch (Exception e){}
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                if (sharedPreferences.getBoolean("logged",false)){
                    if (sharedPreferences.getString("type","").equals("company")){
                        startActivity(new Intent(SplashActivity.this,CompanyActivity.class));
                        finish();
                    }else if (sharedPreferences.getString("type","").equals("candi")){
                        startActivity(new Intent(SplashActivity.this,CandidateActivity.class));
                        finish();
                    }else {
                        startActivity(new Intent(SplashActivity.this,AdminActivity.class));
                        finish();
                    }
                }else {
                    startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                    finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}