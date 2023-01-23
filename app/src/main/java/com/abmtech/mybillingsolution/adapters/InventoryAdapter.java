package com.abmtech.mybillingsolution.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abmtech.mybillingsolution.databinding.ItemInventoryLayBinding;
import com.abmtech.mybillingsolution.models.InventoryModel;
import com.abmtech.mybillingsolution.ui.InventoryItemDetailsActivity;

import java.util.ArrayList;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<InventoryModel> data;

    public InventoryAdapter(Context context, ArrayList<InventoryModel> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemInventoryLayBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        InventoryModel current = data.get(position);

        holder.binding.textProductName.setText(current.getName());
       // holder.binding.textProductId.setText(current.getProduct_id());
        holder.binding.textPrice.setText(current.getPrice());
       // holder.binding.textPurchaseDate.setText(current.getPurchase_date());
        holder.binding.textQuantity.setText(current.getQuantity());
        holder.binding.textDiscription.setText(current.getDescription());
        holder.binding.itemDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, InventoryItemDetailsActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ItemInventoryLayBinding binding;

        public ViewHolder(@NonNull ItemInventoryLayBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }

    public void removeItem(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(InventoryModel item, int position) {
        data.add(position, item);
        notifyItemInserted(position);
    }

    public ArrayList<InventoryModel> getData() {
        return data;
    }


}
