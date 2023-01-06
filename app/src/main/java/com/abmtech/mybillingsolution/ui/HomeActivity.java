package com.abmtech.mybillingsolution.ui;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.abmtech.mybillingsolution.R;
import com.abmtech.mybillingsolution.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {
    private Activity activity;
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = HomeActivity.this;

        binding.bottomNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_home) {
                replaceFragment(new HomeFragment());
            } else if (item.getItemId() == R.id.nav_inventory) {
                replaceFragment(new InventoryFragment());
            } else if (item.getItemId() == R.id.nav_profile) {
                replaceFragment(new ProfileFragment());
            }
            return true;
        });

        replaceFragment(new HomeFragment());
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(binding.container.getId(), fragment);
        ft.commit();
    }
}