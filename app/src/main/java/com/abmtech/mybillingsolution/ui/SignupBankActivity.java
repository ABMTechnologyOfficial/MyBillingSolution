package com.abmtech.mybillingsolution.ui;

import static com.abmtech.mybillingsolution.util.CommonMethods.validEmail;
import static com.abmtech.mybillingsolution.util.ShowUtils.makeSnackShort;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import com.abmtech.mybillingsolution.R;
import com.abmtech.mybillingsolution.databinding.ActivitySignupBankBinding;
import com.abmtech.mybillingsolution.util.ProgressDialog;
import com.abmtech.mybillingsolution.util.Session;
import com.abmtech.mybillingsolution.util.ShowUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignupBankActivity extends AppCompatActivity {
    private Activity activity;
    private ActivitySignupBankBinding binding;
    private FirebaseDatabase firebaseDatabase;
    private String userName = "";
    private String shopName = "";
    private String mobileNumber = "";
    private String gstNumber = "";
    private String addressLineOne = "";
    private String addressLineTwo = "";
    private String addressLineThree = "";
    private String description = "";
    private String email = "";
    private String password = "";

    private Session session;
    private boolean passwordVisible = false;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBankBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        session = new Session(activity);

        userName = getIntent().getStringExtra("userName");
        shopName = getIntent().getStringExtra("shopName");
        mobileNumber = getIntent().getStringExtra("mobileNumber");
        gstNumber = getIntent().getStringExtra("gstNumber");
        addressLineOne = getIntent().getStringExtra("addressLineOne");
        addressLineTwo = getIntent().getStringExtra("addressLineTwo");
        addressLineThree = getIntent().getStringExtra("addressLineThree");
        description = getIntent().getStringExtra("description");

        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");

        binding.edtEmail.setText(email);
        binding.edtPassword.setText(password);

        binding.icBack.setOnClickListener(view -> onBackPressed());

        binding.textPublish.setOnClickListener(view -> publish());

        binding.imagePasswordHide.setOnClickListener(view -> passwordToggle());
    }

    private void passwordToggle() {
        if (passwordVisible) {
            passwordVisible = false;
            binding.edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            binding.imagePasswordHide.setImageDrawable(AppCompatResources.getDrawable(activity, R.drawable.ic_eye));
        } else {
            passwordVisible = true;
            binding.edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            binding.imagePasswordHide.setImageDrawable(AppCompatResources.getDrawable(activity, R.drawable.ic_eye_crossed));
        }
    }

    private void publish() {
        if (binding.edtBankName.getText() != null && binding.edtBankName.getText().length() != 0) {
            if (binding.edtAccountHolderName.getText() != null && binding.edtAccountHolderName.getText().length() != 0) {
                if (binding.edtAccountNumber.getText() != null && binding.edtAccountNumber.getText().length() != 0) {
                    if (binding.edtBankIfscCode.getText() != null && binding.edtBankIfscCode.getText().length() != 0) {
                        if (binding.edtEmail.getText() != null && binding.edtEmail.getText().length() != 0 && validEmail(binding.edtEmail.getText().toString().trim())) {
                            if (binding.edtPassword.getText() != null && binding.edtPassword.getText().length() != 0) {
                                String bankName = binding.edtBankName.getText().toString().trim();
                                String accountHolderName = binding.edtAccountHolderName.getText().toString().trim();
                                String accountNumber = binding.edtAccountNumber.getText().toString().trim();
                                String bankIfscCode = binding.edtBankIfscCode.getText().toString().trim();
                                String bankBranchAddress = binding.edtBankName.getText().toString().trim();
                                email = binding.edtEmail.getText().toString().trim();
                                password = binding.edtPassword.getText().toString().trim();

                                addToDatabase(bankName, accountHolderName, accountNumber, bankIfscCode, bankBranchAddress);
                            } else {
                                binding.edtPassword.setError("Password Can't be Empty!");
                                binding.edtPassword.requestFocus();
                            }
                        } else {
                            binding.edtEmail.setError("Invalid Email");
                            binding.edtEmail.requestFocus();
                        }
                    } else {
                        binding.edtBankIfscCode.setError("IFSC Code Can't be Empty!");
                        binding.edtBankIfscCode.requestFocus();
                    }
                } else {
                    binding.edtAccountNumber.setError("Account Number Can't be Empty!");
                    binding.edtAccountNumber.requestFocus();
                }
            } else {
                binding.edtAccountHolderName.setError("Account Holder Name Can't be Empty!");
                binding.edtAccountHolderName.requestFocus();
            }
        } else {
            binding.edtBankName.setError("Bank Name Can't be Empty!");
            binding.edtBankName.requestFocus();
        }

    }

    private void addToDatabase(String bankName, String accountHolderName, String accountNumber, String bankIfscCode, String bankBranchAddress) {
        ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult().getUser() != null) {

                            Map<String, Object> map = new HashMap<>();

                            map.put("userId", task.getResult().getUser().getUid());

                            map.put("email", email);
                            map.put("password", password);

                            map.put("userName", userName);
                            map.put("shopName", shopName);
                            map.put("mobileNumber", mobileNumber);
                            map.put("gstNumber", gstNumber);
                            map.put("addressLineOne", addressLineOne);
                            map.put("addressLineTwo", addressLineTwo);
                            map.put("addressLineThree", addressLineThree);
                            map.put("description", description);
                            map.put("bankName", bankName);
                            map.put("accountHolderName", accountHolderName);
                            map.put("accountNumber", accountNumber);
                            map.put("bankIfscCode", bankIfscCode);
                            map.put("bankBranchAddress", bankBranchAddress);

                                firebaseDatabase.getReference()
                                        .child("users")
                                        .child(task.getResult().getUser().getUid())
                                        .setValue(map)
                                        .addOnSuccessListener(unused -> {
                                            makeSnackShort(binding.getRoot(), "Data Saved");
                                            progressDialog.dismiss();

                                            session.setUserId(task.getResult().getUser().getUid());
                                            session.setLogin(true);
                                            session.setEmail(email);
                                            session.setMobile(mobileNumber);
                                            session.setUserName(userName);

                                            startActivity(new Intent(activity, HomeActivity.class)
                                                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                            finish();
                                        });
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    ShowUtils.makeSnackShort(binding.getRoot(), e.getLocalizedMessage());
                    Log.e("SignupActivity", "" + e.getLocalizedMessage());
                });
    }
}