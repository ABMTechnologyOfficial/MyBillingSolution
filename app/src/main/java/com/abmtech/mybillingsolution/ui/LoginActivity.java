package com.abmtech.mybillingsolution.ui;

import static com.abmtech.mybillingsolution.util.CommonMethods.validEmail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.abmtech.mybillingsolution.databinding.ActivityLoginBinding;
import com.abmtech.mybillingsolution.models.UsersModel;
import com.abmtech.mybillingsolution.util.ProgressDialog;
import com.abmtech.mybillingsolution.util.Session;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "Custom_logs";
    private Activity activity;
    private ActivityLoginBinding binding;
    private Session session;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = LoginActivity.this;

        session = new Session(activity);
        firebaseDatabase = FirebaseDatabase.getInstance();

        binding.textLogin.setOnClickListener(v -> login());
    }

    private void login() {
        if (binding.edtEmail.getText().length() != 0 && validEmail(binding.edtEmail.getText().toString().trim())) {
            if (binding.edtPassword.getText().length() != 0) {
                loginUser(binding.edtEmail.getText().toString().trim(), binding.edtPassword.getText().toString().trim());
            } else {
                binding.edtPassword.setError("Password Cannot be Empty!");
                binding.edtPassword.requestFocus();
            }
        } else {
            binding.edtEmail.setError("Invalid Email!");
            binding.edtEmail.requestFocus();
        }

    }

    private void loginUser(String email, String password) {
        ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.show();

        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {
            String user_id = Objects.requireNonNull(authResult.getUser()).getUid();


            firebaseDatabase.getReference()
                    .child("users")
                    .child(user_id)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            UsersModel model = snapshot.getValue(UsersModel.class);
                            if (model != null) {
                                session.setLogin(true);
                                session.setUserName(model.getUserName());
                                session.setMobile(model.getMobileNumber());
                                session.setEmail(model.getEmail());
                                startActivity(new Intent(activity, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(activity, "Incomplete login!", Toast.LENGTH_SHORT).show();
                            session.setUserId(user_id);
                            startActivity(new Intent(activity, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            progressDialog.dismiss();
                            finish();
                        }
                    });


        }).addOnFailureListener(e -> {
            progressDialog.dismiss();
            Log.e(TAG, "loginUser: ", e);
            if (e.toString().contains("FirebaseAuthInvalidUserException: There is no user record corresponding to this identifier")) {
                Toast.makeText(activity, "New User! Complete Signup First..", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(activity, SignupDetailActivity.class)
                        .putExtra("email", binding.edtEmail.getText().toString().trim())
                        .putExtra("password", binding.edtPassword.getText().toString().trim())
                );
            } else if (e.toString().contains("The password is invalid")) {
                binding.edtPassword.requestFocus();
                binding.edtPassword.setError("Password Incorrect");
            }
        });
    }
}