package com.example.quixorder;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quixorder.model.MenuItem;

import java.util.List;

class OrderItemListAdapter extends RecyclerView.Adapter<OrderItemListAdapter.MenuItemViewHolder> {
    private List<MenuItem> items;

    public OrderItemListAdapter(List<MenuItem> orderMenuItems) {
        items = orderMenuItems;
    }

    public static class MenuItemViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;

        public MenuItemViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView);
            name = itemView.findViewById(R.id.menuItemName);
        }

        public void bindMenuItem(MenuItem item) {
            //TODO: Figure out image data type.
            //image.setImageBitmap();
            Log.d("Binding MenuItem", item.toString());
            name.setText(item.getName());
        }
    }

    @NonNull
    @Override
    public MenuItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("OrderItemListAdapter", "onCreateViewHolder called");
        ViewGroup v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_item, parent, false);
        MenuItemViewHolder vh = new MenuItemViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MenuItemViewHolder holder, int position) {
        holder.bindMenuItem(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}