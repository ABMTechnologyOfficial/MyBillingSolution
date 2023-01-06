package com.abmtech.mybillingsolution.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.abmtech.mybillingsolution.databinding.ActivitySplashBinding;
import com.abmtech.mybillingsolution.util.Session;

public class SplashActivity extends AppCompatActivity {
    private Activity activity;
    private ActivitySplashBinding binding;
    public static final int SPLASH_TIMER = 2 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = SplashActivity.this;

        Session session = new Session(activity);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (session.isLoggedIn()) {
                    startActivity(new Intent(activity, HomeActivity.class));
                } else {
                    startActivity(new Intent(activity, LoginActivity.class));
                }
                finish();
            }
        }, SPLASH_TIMER);
    }
}