package com.abmtech.mybillingsolution.ui;

import static com.abmtech.mybillingsolution.util.ShowUtils.makeSnackShort;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.abmtech.mybillingsolution.databinding.ActivityPurchaseBinding;
import com.abmtech.mybillingsolution.util.ProgressDialog;
import com.abmtech.mybillingsolution.util.Session;
import com.abmtech.mybillingsolution.util.ShowUtils;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PurchaseActivity extends AppCompatActivity {
    private static final String TAG = "CustomLogs";
    private final int MINUS = 0;
    private final int PLUS = 1;
    private Activity activity;
    private ActivityPurchaseBinding binding;
    private FirebaseDatabase firebaseDatabase;
    private Session session;
    private String name = "", quantity = "", price = "", purchaseDate = "", description = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPurchaseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = PurchaseActivity.this;

        firebaseDatabase = FirebaseDatabase.getInstance();
        session = new Session(activity);

        binding.icBack.setOnClickListener(view -> onBackPressed());

        binding.icMinus.setOnClickListener(view -> quantity(MINUS));
        binding.icPlus.setOnClickListener(view -> quantity(PLUS));

        binding.textPublish.setOnClickListener(view -> publish());
        binding.llPurchaseDate.setOnClickListener(view -> datePicker());
    }

    private void publish() {
        if (binding.edtProductName.getText() != null && binding.edtProductName.getText().length() != 0) {
            if (binding.edtPrice.getText() != null && binding.edtPrice.getText().length() != 0) {
                if (binding.edtQuantity.getText() != null && binding.edtQuantity.getText().length() != 0 && !binding.edtQuantity.getText().toString().equalsIgnoreCase("0")) {
                    if (binding.edtDescription.getText() != null && binding.edtDescription.getText().length() != 0) {
                        if (binding.edtPurchaseDate.getText() != null && binding.edtPurchaseDate.getText().length() != 0) {
                            name = binding.edtProductName.getText().toString();
                            quantity = binding.edtQuantity.getText().toString();
                            price = binding.edtPrice.getText().toString();
                            description = binding.edtDescription.getText().toString();
                            purchaseDate = binding.edtPurchaseDate.getText().toString();

                            addToDatabase();
                        } else {
                            ShowUtils.makeSnackShort(binding.getRoot(), "Purchase date cannot be Empty!");
                            binding.llPurchaseDate.performClick();
                        }
                    } else {
                        binding.edtDescription.setError("Description Cannot be Empty!");
                        binding.edtDescription.requestFocus();
                    }
                } else {
                    binding.edtQuantity.setError("Quantity cannot be Zero!");
                    binding.edtQuantity.requestFocus();
                }
            } else {
                binding.edtPrice.setError("Price cannot be Empty!");
                binding.edtPrice.requestFocus();
            }
        } else {
            binding.edtProductName.setError("Name cannot be Empty!");
            binding.edtProductName.requestFocus();
        }
    }

    private void addToDatabase() {
        ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.show();

        Map<String, Object> map = new HashMap<>();

        String currentTime = getCurrentTimeStamp();

        Log.e(TAG, "addItem() called with: name = [" + name + "], quantity = [" + quantity + "]," +
                " price = [" + price + "], currentTime = [" + currentTime + "]," +
                " description = [" + description + "], purchaseDate = [" + purchaseDate + "]");

        map.put("name", name);
        map.put("quantity", quantity);
        map.put("price", price);
        map.put("description", description);
        map.put("purchase_date", purchaseDate);
        map.put("currentTime", currentTime);
        map.put("user_id", session.getUserId());


        String mGroupId = firebaseDatabase.getReference().push().getKey();

        map.put("product_id", mGroupId);

        if (mGroupId != null)
            firebaseDatabase.getReference()
                    .child("inventory")
                    .child(session.getUserId())
                    .child(mGroupId)
                    .setValue(map)
                    .addOnSuccessListener(unused -> {
                        makeSnackShort(binding.getRoot(), "Data Saved");
                        progressDialog.dismiss();
                        finish();
                    });
    }

    private void quantity(int mode) {
        int currentQuantity = Integer.parseInt(binding.edtQuantity.getText().toString());
        switch (mode) {
            case PLUS:
                binding.edtQuantity.setText(String.valueOf(currentQuantity + 1));
                binding.icMinus.setAlpha(1f);
                break;

            case MINUS:
                if (currentQuantity > 0) {
                    currentQuantity = currentQuantity - 1;
                    binding.edtQuantity.setText(String.valueOf(currentQuantity));
                }

                if (currentQuantity == 0)
                    binding.icMinus.setAlpha(.3f);
                break;
        }
    }

    public String getCurrentTimeStamp() {
        return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(new Date());
    }

    private void datePicker() {
        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = (view, year, month, day) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, day);
            String myFormat = "MM/dd/yy";
            SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.getDefault());
            binding.edtPurchaseDate.setText(dateFormat.format(myCalendar.getTime()));
        };
        new DatePickerDialog(activity, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}