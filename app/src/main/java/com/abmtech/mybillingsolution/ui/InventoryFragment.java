package com.abmtech.mybillingsolution.ui;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abmtech.mybillingsolution.R;
import com.abmtech.mybillingsolution.databinding.FragmentInventoryBinding;

public class InventoryFragment extends Fragment {
    private Activity activity;
    private FragmentInventoryBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentInventoryBinding.inflate(inflater, container, false);
        activity = requireActivity();


        return binding.getRoot();
    }
}