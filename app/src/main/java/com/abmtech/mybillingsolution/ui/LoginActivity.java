package com.abmtech.mybillingsolution.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.abmtech.mybillingsolution.databinding.ActivityLoginBinding;
import com.abmtech.mybillingsolution.util.Session;

public class LoginActivity extends AppCompatActivity {
    private Activity activity;
    private ActivityLoginBinding binding;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = LoginActivity.this;

        session = new Session(activity);

        binding.textLogin.setOnClickListener(v -> login());
    }

    private void login() {
        startActivity(new Intent(activity, HomeActivity.class));
        session.setLogin(true);
        finish();
    }
}