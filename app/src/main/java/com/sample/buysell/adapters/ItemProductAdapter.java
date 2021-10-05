package com.sample.buysell.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sample.buysell.R;
import com.sample.buysell.adapters.model.ItemProduct;

import java.util.List;
import java.util.Locale;

public class ItemProductAdapter extends RecyclerView.Adapter<ItemProductAdapter.ItemProductsViewHolder> {

    private final List<ItemProduct> itemProducts;
    private final Context context;

    private OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ItemProductAdapter(Context context, List<ItemProduct> itemProducts) {
        this.itemProducts = itemProducts;
        this.context = context;
    }

    public static class ItemProductsViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvTime, tvProductName, tvQty, tvPurchaseAmount, tvSellAmount;

        public ItemProductsViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvQty = itemView.findViewById(R.id.tvQty);
            tvPurchaseAmount = itemView.findViewById(R.id.tvPurchaseAmount);
            tvSellAmount = itemView.findViewById(R.id.tvSellAmount);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public ItemProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemProductsViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_products,
                        parent, false), listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemProductsViewHolder holder, int position) {
        ItemProduct currentItem = itemProducts.get(position);
        holder.tvDate.setText(currentItem.getDate());
        holder.tvTime.setText(currentItem.getTime());
        holder.tvProductName.setText(currentItem.getProductName());
        String qty = currentItem.getProductQty() + " " + currentItem.getUnit();
        holder.tvQty.setText(qty);
        holder.tvPurchaseAmount.setText("");
        String sellPrice = String.format(Locale.getDefault(), "%.2f",
                currentItem.getProductQty() * currentItem.getProductPrice());
        holder.tvSellAmount.setText(sellPrice);
    }

    @Override
    public int getItemCount() {
        return itemProducts.size();
    }
}
