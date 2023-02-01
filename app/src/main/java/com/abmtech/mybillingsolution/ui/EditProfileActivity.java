package com.abmtech.mybillingsolution.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.abmtech.mybillingsolution.R;
import com.abmtech.mybillingsolution.databinding.ActivityEditProfileBinding;
import com.abmtech.mybillingsolution.models.UsersModel;
import com.abmtech.mybillingsolution.util.ProgressDialog;
import com.abmtech.mybillingsolution.util.Session;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {

    private DatabaseReference  reference;
    private Session session ;
    private  EditProfileActivity activity ;
    private ActivityEditProfileBinding binding ;
    private int PICK_IMAGE_REQUEST = 100;
    private Uri filePath =  null;
    FirebaseStorage storage;
    StorageReference storageReference;
    private String imageUrl = "";
    private FirebaseDatabase database ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;
        session = new Session(activity);
        reference = FirebaseDatabase.getInstance().getReference();


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        database = FirebaseDatabase.getInstance();

        binding.profileImage.setOnClickListener(view -> SelectImage());
        binding.editProfileImage.setOnClickListener(view -> SelectImage());

        getUserProfile();

        binding.textLogin.setOnClickListener(view -> updateData());

    }

    private void updateData() {

        ProgressDialog dialog = new ProgressDialog(activity);
        dialog.show();

        Map<String, Object> map = new HashMap<>();
        map.put("userName" , binding.editUserName.getText().toString());
        map.put("shopName" , binding.editShopName.getText().toString());
        map.put("mobileNumber" , binding.editPhoneNumber.getText().toString());
        map.put("gstNumber" , binding.editGstNumber.getText().toString());
        map.put("addressLineOne" , binding.editAddress1.getText().toString());
        map.put("addressLineTwo" , binding.editAddress2.getText().toString());
        map.put("addressLineThree" , binding.editAddress3.getText().toString());
        map.put("description" , binding.editAbout.getText().toString());
        map.put("bankName" , binding.editBankName.getText().toString());
        map.put("accountHolderName" , binding.editAccountHolderName.getText().toString());
        map.put("accountNumber" , binding.editAccountNumber.getText().toString());
        map.put("bankIfscCode" , binding.editIfscCode.getText().toString());
        map.put("bankBranchAddress" , binding.editBankAddress.getText().toString());

        reference.child("users").child(session.getUserId()).updateChildren(map).addOnSuccessListener(unused -> {
            dialog.dismiss();
            Toast.makeText(activity, "Successfully Updated..", Toast.LENGTH_SHORT).show();
            finish();
        });

    }


    private void getUserProfile() {
        reference.child("users").child(session.getUserId()).addListenerForSingleValueEvent(new ValueEventListener() {
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
        binding.editUserName.setText(data.getUserName());
        binding.editShopName.setText(data.getShopName());
        binding.editAbout.setText(data.getDescription());
        binding.editAddress1.setText(data.getAddressLineOne());
        binding.editAddress2.setText(data.getAddressLineTwo());
        binding.editAddress3.setText(data.getAddressLineThree());
        binding.edtEmail.setText(data.getEmail());
        binding.editPhoneNumber.setText(data.getMobileNumber());
        binding.editBankAddress.setText(data.getBankBranchAddress());
        binding.editBankName.setText(data.getBankName());
        binding.editAccountNumber.setText(data.getAccountNumber());
        binding.editAccountHolderName.setText(data.getAccountHolderName());
        binding.editIfscCode.setText(data.getBankIfscCode());
        binding.editGstNumber.setText(data.getGstNumber());


        if(data.getImage() != null){
            Picasso.get().load(data.getImage()).into(binding.profileImage);
        }

    }


    private void SelectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image from here..."), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                uploadImage(bitmap);
            }
            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    private void uploadImage(Bitmap bitmap)
    {
        if (filePath != null) {
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.editProfileImage.setVisibility(View.GONE);
            StorageReference ref = storageReference.child("images/" + session.getUserId());
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            taskSnapshot -> ref.getDownloadUrl().addOnSuccessListener(uri -> {
                                imageUrl = uri.toString();
                                session.setUser_image(imageUrl);
                                Map<String,Object> map = new HashMap<>();
                                map.put("image",imageUrl);

                                database.getReference().child("users").child(session.getUserId()).updateChildren(map).addOnSuccessListener(unused -> {
                                    binding.progressBar.setVisibility(View.GONE);
                                    binding.editProfileImage.setVisibility(View.VISIBLE);
                                    binding.profileImage.setImageBitmap(bitmap);
                                });


                            }))

                    .addOnFailureListener(e -> Toast.makeText(activity, "Failed..", Toast.LENGTH_SHORT).show());
        }
    }


}