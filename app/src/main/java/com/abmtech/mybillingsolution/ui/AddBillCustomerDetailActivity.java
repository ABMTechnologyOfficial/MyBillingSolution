package com.abmtech.mybillingsolution.ui;

import static com.abmtech.mybillingsolution.util.ShowUtils.makeSnackShort;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.abmtech.mybillingsolution.databinding.ActivityAddBillCustomerDetailBinding;
import com.abmtech.mybillingsolution.databinding.DialogNewPercentLayBinding;
import com.abmtech.mybillingsolution.util.ShowUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddBillCustomerDetailActivity extends AppCompatActivity {
    private ActivityAddBillCustomerDetailBinding binding;
    private Activity activity;
    private boolean isSelected = false;
    private FirebaseDatabase firebaseDatabase;
    private final ArrayList<String> gstList = new ArrayList<>();
    private String gstPercentage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBillCustomerDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;

        firebaseDatabase = FirebaseDatabase.getInstance();

        getGstList();

        binding.textPublish.setOnClickListener(view -> publish());
        binding.icBack.setOnClickListener(view -> onBackPressed());

        binding.spinnerGstPercent.setOnItemSelectedListener((view, position, id, item) -> {
            if (item.toString().equalsIgnoreCase("Add New Percentage")) {
                isSelected = false;

                addNewPercentage();
            } else {
                isSelected = true;

                gstPercentage = item.toString().trim();
            }
        });
    }

    private void addNewPercentage() {
        Dialog dialog = new Dialog(activity);
        DialogNewPercentLayBinding dialogBinding = DialogNewPercentLayBinding.inflate(LayoutInflater.from(activity));
        dialog.setContentView(dialogBinding.getRoot());

        dialogBinding.cardSubmit.setOnClickListener(view -> {
            if (dialogBinding.edtText.getText().length() != 0) {
                    firebaseDatabase.getReference()
                            .child("gst_percentage")
                            .push()
                            .setValue(dialogBinding.edtText.getText().toString())
                            .addOnSuccessListener(unused -> {
                                makeSnackShort(binding.getRoot(), "Data Saved");
                                dialog.dismiss();
                            });
            } else {
                dialogBinding.edtText.requestFocus();
                dialogBinding.edtText.setError("Field Can't be Empty!");
            }
        });

        dialog.show();
    }

    private void getGstList() {
        firebaseDatabase.getReference()
                .child("gst_percentage")
                .addValueEventListener(new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        gstList.clear();
                        for (DataSnapshot current : snapshot.getChildren()) {
                            if (current.getValue() != null) {
                                String value = current.getValue().toString();

                                gstList.add(value);
                            }
                        }
                        gstList.add(gstList.size(), "Add New Percentage");

                        binding.spinnerGstPercent.setAdapter(new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, gstList));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void publish() {
        if (binding.edtCustomerName.getText() != null && binding.edtCustomerName.getText().length() != 0) {
            if (binding.edtCustomerMobile.getText() != null && binding.edtCustomerMobile.getText().length() != 0) {
                if (binding.edtCustomerAddress.getText() != null && binding.edtCustomerAddress.getText().length() != 0) {
                    if (binding.edtDueDate.getText() != null && binding.edtDueDate.getText().length() != 0) {
                        if (isSelected) {
                            String edtCustomerName = binding.edtCustomerName.getText().toString().trim();
                            String edtCustomerMobile = binding.edtCustomerMobile.getText().toString().trim();
                            String edtCustomerAddress = binding.edtCustomerAddress.getText().toString().trim();
                            String edtDueDate = binding.edtDueDate.getText().toString().trim();

                            startActivity(new Intent(activity, AddBillItemActivity.class)
                                    .putExtra("customerName", edtCustomerName)
                                    .putExtra("customerMobile", edtCustomerMobile)
                                    .putExtra("customerAddress", edtCustomerAddress)
                                    .putExtra("dueDate", edtDueDate)
                                    .putExtra("gstPercentage", gstPercentage)
                            );
                        } else {
                            ShowUtils.makeSnackShort(binding.getRoot(), "Select GST Percent");
                        }
                    } else {
                        binding.edtDueDate.setError("Due Date Cannot be Empty!");
                        binding.dueDateLay.performClick();
                    }
                } else {
                    binding.edtCustomerAddress.setError("Address Cannot be Empty");
                    binding.edtCustomerAddress.requestFocus();
                }
            } else {
                binding.edtCustomerMobile.setError("Mobile cannot be Empty!");
                binding.edtCustomerMobile.requestFocus();
            }
        } else {
            binding.edtCustomerName.setError("Name cannot be Empty!");
            binding.edtCustomerName.requestFocus();
        }
    }

}