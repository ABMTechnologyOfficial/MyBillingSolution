package com.abmtech.mybillingsolution.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;

import com.abmtech.mybillingsolution.databinding.ActivityAddBillItemBinding;

public class AddBillItemActivity extends AppCompatActivity {
    private ActivityAddBillItemBinding binding;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBillItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;


    }
}