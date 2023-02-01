package com.abmtech.mybillingsolution.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.abmtech.mybillingsolution.R;
import com.abmtech.mybillingsolution.databinding.FragmentProfileBinding;
import com.abmtech.mybillingsolution.models.UsersModel;
import com.abmtech.mybillingsolution.util.Session;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class ProfileFragment extends Fragment {
    private Activity activity;
    private FragmentProfileBinding binding;
    private DatabaseReference reference;
    private Session session;
    private FirebaseAuth auth;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        activity = requireActivity();
        session = new Session(activity);
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("users");

        getUserProfile();

        binding.logout.setOnClickListener(view -> logout());

        binding.viewEditProfile.setOnClickListener(view -> startActivity(new Intent(activity,EditProfileActivity.class)));

        return binding.getRoot();
    }

    private void getUserProfile() {
        reference.child(session.getUserId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UsersModel data = snapshot.getValue(UsersModel.class);
                if (data != null) {
                    setUserProfile(data);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void setUserProfile(UsersModel data) {
        binding.userName.setText(data.getUserName());
        binding.shopName.setText(data.getShopName());
        binding.textAddress.setText(new StringBuilder().append(data.getAddressLineOne()).append(" , ").append(data.getAddressLineTwo()).append(" , ").append(data.getAddressLineThree()).toString());
        binding.shopAddress.setText(data.getAddressLineThree());
    }


    private void logout() {
        new AlertDialog.Builder(activity)
                .setTitle("Logout?")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    auth.signOut();
                    session.setLogin(false);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public void onResume() {
        super.onResume();

        if(!session.getUser_image().equalsIgnoreCase(""))
            Picasso.get().load(session.getUser_image()).placeholder(R.drawable.ic_profile).into(binding.profileimg);
    }
}