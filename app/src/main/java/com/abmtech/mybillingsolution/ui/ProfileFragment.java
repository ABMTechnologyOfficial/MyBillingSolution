package com.abmtech.mybillingsolution.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.abmtech.mybillingsolution.R;
import com.abmtech.mybillingsolution.databinding.FragmentProfileBinding;


public class ProfileFragment extends Fragment {
    private Activity activity;
    private FragmentProfileBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        activity = requireActivity();


        binding.profileDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),ProfileDetailsActivity.class);
                startActivity(intent);
            }
        });


        binding.totalInputOutput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),TotalInputOutputActivity.class);
                startActivity(intent);
            }
        });

        binding.vendorList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),VenderListActivity.class);
                startActivity(intent);
            }
        });

        binding.customerService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),CustomerSupportActivity.class);
                startActivity(intent);
            }
        });



        return binding.getRoot();
    }
}