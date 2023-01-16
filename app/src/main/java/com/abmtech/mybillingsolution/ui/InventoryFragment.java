package com.abmtech.mybillingsolution.ui;

import static com.abmtech.mybillingsolution.util.ShowUtils.makeSnackShort;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abmtech.mybillingsolution.R;
import com.abmtech.mybillingsolution.adapters.InventoryAdapter;
import com.abmtech.mybillingsolution.databinding.FragmentInventoryBinding;
import com.abmtech.mybillingsolution.models.InventoryModel;
import com.abmtech.mybillingsolution.util.RecyclerSwipeHelper;
import com.abmtech.mybillingsolution.util.Session;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InventoryFragment extends Fragment {
    private final ArrayList<InventoryModel> inventoryList = new ArrayList<>();
    private Activity activity;
    private FragmentInventoryBinding binding;
    private FirebaseDatabase firebaseDatabase;
    private Session session;
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

        populateRecyclerView();
        enableSwipeToDeleteAndUndo();

        return binding.getRoot();
    }

    private void enableSwipeToDeleteAndUndo() {
        int swipeRightColor = activity.getResources().getColor(R.color.green);
        int swipeLeftColor = activity.getResources().getColor(R.color.red);
        int swipeRightIconResource = R.drawable.ic_edit;
        int swipeLeftIconResource = R.drawable.ic_delete;

        RecyclerSwipeHelper mRecyclerSwipeHelper = new RecyclerSwipeHelper(swipeRightColor, swipeLeftColor, swipeRightIconResource, swipeLeftIconResource, activity) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                //calling the notifyItemChanged() method is absolutely essential to redraw the RecyclerView item and remove the icons we had drawn.
                int position = viewHolder.getAdapterPosition();

                adapter.notifyItemChanged(position);
                final InventoryModel item = adapter.getData().get(position);

                if (direction == ItemTouchHelper.LEFT) {
                    removeItem(position);
                    adapter.removeItem(position);
                    Snackbar snackbar = Snackbar
                            .make(binding.coordinatorLayout, "Product Deleted.!", Snackbar.LENGTH_LONG);
                    snackbar.setAction("UNDO", view -> {
                        restoreItem(item);

                        adapter.restoreItem(item, position);

                        binding.recyclerView.scrollToPosition(position);
                    });
                    snackbar.setActionTextColor(Color.YELLOW);
                    snackbar.show();
                } else {
                    adapter.removeItem(position);
                    Snackbar snackbar = Snackbar
                            .make(binding.coordinatorLayout, "Product Deleted.!", Snackbar.LENGTH_LONG);
                    snackbar.setAction("UNDO", view -> {
                        adapter.restoreItem(item, position);
                        binding.recyclerView.scrollToPosition(position);
                    });
                    snackbar.setActionTextColor(Color.YELLOW);
                    snackbar.show();
                }
            }
        };
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(mRecyclerSwipeHelper);
        mItemTouchHelper.attachToRecyclerView(binding.recyclerView);
    }

    private void removeItem(int position) {
        firebaseDatabase.getReference()
                .child("inventory")
                .child(session.getUserId())
                .child(inventoryList.get(position).getProduct_id())
                .removeValue();
    }

    private void restoreItem(InventoryModel item) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", item.getName());
        map.put("quantity", item.getQuantity());
        map.put("price", item.getPrice());
        map.put("description", item.getDescription());
        map.put("purchase_date", item.getPurchase_date());
        map.put("currentTime", item.getCurrentTime());
        map.put("user_id", item.getUser_id());
        map.put("product_id", item.getProduct_id());

        firebaseDatabase.getReference()
                .child("inventory")
                .child(item.getUser_id())
                .child(item.getProduct_id())
                .setValue(map);
    }

    private void populateRecyclerView() {
        getInventoryList();

        adapter = new InventoryAdapter(activity, inventoryList);

        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));

    }

    private void getInventoryList() {
        firebaseDatabase.getReference()
                .child("inventory")
                .child(session.getUserId()).addValueEventListener(new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        inventoryList.clear();
                        for (DataSnapshot current : snapshot.getChildren()) {
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