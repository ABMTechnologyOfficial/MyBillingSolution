package com.abmtech.mybillingsolution.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.abmtech.mybillingsolution.databinding.ActivitySignupDetailBinding;

public class SignupDetailActivity extends AppCompatActivity {
    private Activity activity;
    private ActivitySignupDetailBinding binding;

    private String email = "";
    private String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;

        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");

        binding.icBack.setOnClickListener(view -> onBackPressed());

        binding.textPublish.setOnClickListener(view -> publish());
    }

    private void publish() {
        if (binding.edtUserName.getText() != null && binding.edtUserName.getText().length() != 0) {
            if (binding.edtShopName.getText() != null && binding.edtShopName.getText().length() != 0) {
                if (binding.edtMobileNumber.getText() != null && binding.edtMobileNumber.getText().length() != 0) {
                    if (binding.edtGst.getText() != null && binding.edtGst.getText().length() != 0) {
                        if (binding.edtAddressLineOne.getText() != null && binding.edtAddressLineOne.getText().length() != 0) {
                            if (binding.edtAddressLineTwo.getText() != null && binding.edtAddressLineTwo.getText().length() != 0) {
                                if (binding.edtAddressLineThree.getText() != null && binding.edtAddressLineThree.getText().length() != 0) {
                                    if (binding.edtDescription.getText() != null && binding.edtDescription.getText().length() != 0) {
                                        String userName = binding.edtUserName.getText().toString().trim();
                                        String shopName = binding.edtShopName.getText().toString().trim();
                                        String mobileNumber = binding.edtMobileNumber.getText().toString().trim();
                                        String gstNumber = binding.edtGst.getText().toString().trim();
                                        String addressLineOne = binding.edtAddressLineOne.getText().toString().trim();
                                        String addressLineTwo = binding.edtAddressLineTwo.getText().toString().trim();
                                        String addressLineThree = binding.edtAddressLineThree.getText().toString().trim();
                                        String description = binding.edtDescription.getText().toString().trim();

                                        startActivity(new Intent(activity, SignupBankActivity.class)
                                                .putExtra("userName", userName)
                                                .putExtra("shopName", shopName)
                                                .putExtra("mobileNumber", mobileNumber)
                                                .putExtra("gstNumber", gstNumber)
                                                .putExtra("addressLineOne", addressLineOne)
                                                .putExtra("addressLineTwo", addressLineTwo)
                                                .putExtra("addressLineThree", addressLineThree)
                                                .putExtra("description", description)
                                                .putExtra("email", email)
                                                .putExtra("password", password)
                                        );
                                    } else {
                                        binding.edtDescription.setError("Description Can't be Empty!");
                                        binding.edtDescription.requestFocus();
                                    }
                                } else {
                                    binding.edtAddressLineThree.setError("Address Can't be Empty!");
                                    binding.edtAddressLineThree.requestFocus();
                                }
                            } else {
                                binding.edtAddressLineTwo.setError("Address Can't be Empty!");
                                binding.edtAddressLineTwo.requestFocus();
                            }
                        } else {
                            binding.edtAddressLineOne.setError("Address Can't be Empty!");
                            binding.edtAddressLineOne.requestFocus();
                        }
                    } else {
                        binding.edtGst.setError("GST Number Can't be Empty!");
                        binding.edtGst.requestFocus();
                    }
                } else {
                    binding.edtMobileNumber.setError("Mobile Number Can't be Empty!");
                    binding.edtMobileNumber.requestFocus();
                }
            } else {
                binding.edtShopName.setError("Shop Name Can't be Empty!");
                binding.edtShopName.requestFocus();
            }
        } else {
            binding.edtUserName.setError("Name Can't be Empty!");
            binding.edtUserName.requestFocus();
        }
    }
}