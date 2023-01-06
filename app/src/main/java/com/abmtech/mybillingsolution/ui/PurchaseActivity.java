package com.abmtech.mybillingsolution.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;

import com.abmtech.mybillingsolution.databinding.ActivityPurchaseBinding;

public class PurchaseActivity extends AppCompatActivity {
    private Activity activity;
    private ActivityPurchaseBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPurchaseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = PurchaseActivity.this;


    }
}