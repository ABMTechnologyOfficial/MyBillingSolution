package com.abmtech.mybillingsolution.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;

import com.abmtech.mybillingsolution.databinding.ActivityBillingBinding;

public class BillingActivity extends AppCompatActivity {
    private Activity activity;
    private ActivityBillingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBillingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = BillingActivity.this;

    }
}