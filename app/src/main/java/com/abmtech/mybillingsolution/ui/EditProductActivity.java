package com.abmtech.mybillingsolution.ui;

import static com.abmtech.mybillingsolution.util.ShowUtils.makeSnackShort;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;

import com.abmtech.mybillingsolution.databinding.ActivityEditProductBinding;
import com.abmtech.mybillingsolution.databinding.ActivityPurchaseBinding;
import com.abmtech.mybillingsolution.models.InventoryModel;
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

public class EditProductActivity extends AppCompatActivity {
    private static final String TAG = "CustomLogs";
    private Activity activity;
    private ActivityEditProductBinding binding;
    private final int MINUS = 0;
    private final int PLUS = 1;
    private FirebaseDatabase firebaseDatabase;
    private Session session;
    private String name = "", quantity = "", price = "", purchaseDate = "", description = "";
    private InventoryModel inventoryModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;

        firebaseDatabase = FirebaseDatabase.getInstance();
        session = new Session(activity);

        inventoryModel = (InventoryModel) getIntent().getSerializableExtra("item");

        binding.icMinus.setOnClickListener(view -> quantity(MINUS));
        binding.icPlus.setOnClickListener(view -> quantity(PLUS));

        binding.textPublish.setOnClickListener(view -> publish());
        binding.llPurchaseDate.setOnClickListener(view -> datePicker());

        setData();
    }

    private void setData() {
        binding.edtProductName.setText(inventoryModel.getName());
        binding.edtQuantity.setText(inventoryModel.getQuantity());
        binding.edtPrice.setText(inventoryModel.getPrice());
        binding.edtDescription.setText(inventoryModel.getDescription());
        binding.edtPurchaseDate.setText(inventoryModel.getPurchase_date());
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
        map.put("user_id", inventoryModel.getUser_id());
        map.put("product_id", inventoryModel.getProduct_id());

        firebaseDatabase.getReference()
                .child("inventory")
                .child(session.getUserId())
                .child(inventoryModel.getProduct_id())
                .updateChildren(map)
                .addOnSuccessListener(unused -> {
                    makeSnackShort(binding.getRoot(), "Data Updated");
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