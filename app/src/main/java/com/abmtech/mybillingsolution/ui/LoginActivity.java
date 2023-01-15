package com.abmtech.mybillingsolution.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private Activity activity;
    private ActivityLoginBinding binding;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = LoginActivity.this;

        session = new Session(activity);

        binding.textLogin.setOnClickListener(v -> login());
    }

    private void login() {
        if (binding.edtEmail.getText().length() != 0) {
            if (binding.edtPassword.getText().length() != 0) {
                startActivity(new Intent(activity, HomeActivity.class));
//                loginUser(binding.edtEmail.getText().toString().trim(), binding.edtPassword.getText().toString().trim());
                session.setUserId("1");
                session.setLogin(true);
                finish();
            } else {
                binding.edtPassword.setError("Password Cannot be Empty!");
                binding.edtPassword.requestFocus();
            }
        } else {
            binding.edtEmail.setError("Email Cannot be Empty!");
            binding.edtEmail.requestFocus();
        }

    }

    private void loginUser(String email, String password) {
        ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.show();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {
            String user_id = Objects.requireNonNull(authResult.getUser()).getUid();

            Session session = new Session(activity);
            session.setUserId(user_id);

            FirebaseDatabase database = FirebaseDatabase.getInstance();

            DatabaseReference reference = database.getReference().child("users");

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    UsersModel usersModel = new UsersModel();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        usersModel = snapshot1.getValue(UsersModel.class);
                    }

                    Map<String, String> map = new HashMap<>();
                    map.put("address", "");
                    map.put("email", binding.edtEmail.getText().toString().trim());
                    map.put("fcm_id", "token_fcm_id");
                    map.put("lat", "");
                    map.put("lang", "");
                    map.put("name", usersModel.getName());
                    map.put("password", binding.edtPassword.getText().toString().trim());

                    reference.child(user_id).setValue(map).addOnSuccessListener(unused -> {
                        progressDialog.dismiss();
                    });

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }).addOnFailureListener(e -> {
            progressDialog.dismiss();
            Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
        });
    }
}