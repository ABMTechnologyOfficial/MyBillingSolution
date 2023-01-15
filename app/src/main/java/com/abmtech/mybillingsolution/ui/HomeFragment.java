package com.abmtech.mybillingsolution.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.abmtech.mybillingsolution.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {
    private Activity activity;
    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        activity = requireActivity();

        binding.textAddPurchase.setOnClickListener(view -> startActivity(new Intent(activity, PurchaseActivity.class)));
        binding.textGenerateBill.setOnClickListener(view -> startActivity(new Intent(activity, BillingActivity.class)));

        return binding.getRoot();
    }
}