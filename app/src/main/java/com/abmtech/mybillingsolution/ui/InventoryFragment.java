package com.abmtech.mybillingsolution.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.abmtech.mybillingsolution.adapters.InventoryAdapter;
import com.abmtech.mybillingsolution.databinding.FragmentInventoryBinding;
import com.abmtech.mybillingsolution.models.InventoryModel;
import com.abmtech.mybillingsolution.util.Session;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class InventoryFragment extends Fragment {
    private Activity activity;
    private FragmentInventoryBinding binding;
    private FirebaseDatabase firebaseDatabase;
    private Session session;
    private final ArrayList<InventoryModel> inventoryList = new ArrayList<>();
    private InventoryAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentInventoryBinding.inflate(inflater, container, false);
        activity = requireActivity();

        session = new Session(activity);
        firebaseDatabase = FirebaseDatabase.getInstance();

        binding.fabAdd.setOnClickListener(view -> startActivity(new Intent(activity, PurchaseActivity.class)));

        getInventoryList();

        adapter = new InventoryAdapter(activity, inventoryList);

        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));

        return binding.getRoot();
    }

    private void getInventoryList() {
        firebaseDatabase.getReference()
                .child("inventory")
                .child(session.getUserId()).addValueEventListener(new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        inventoryList.clear();
                        for (DataSnapshot current : snapshot.getChildren()){
                            InventoryModel model = current.getValue(InventoryModel.class);

                            inventoryList.add(model);
                        }
                        binding.progressbar.setVisibility(View.GONE);
                        if (inventoryList.size() > 0) {
                            binding.textEmpty.setVisibility(View.GONE);
                        } else {
                            binding.textEmpty.setVisibility(View.VISIBLE);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}