package com.example.quixorder.adapter.cook;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quixorder.R;
import com.example.quixorder.model.MenuItem;

import java.util.List;

public class OrderItemListAdapter extends RecyclerView.Adapter<OrderItemListAdapter.MenuItemViewHolder> {
    private List<MenuItem> items;

    public OrderItemListAdapter(List<MenuItem> orderMenuItems) {
        items = orderMenuItems;
    }

    public static class MenuItemViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;

        MenuItemViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView);
            name = itemView.findViewById(R.id.menuItemName);
        }

        void bindMenuItem(MenuItem item) {
            Picasso.get().load(item.getImage()).into(image);
            Log.d("Binding MenuItem", item.toString());
            name.setText(item.getName());
        }
    }

    @NonNull
    @Override
    public MenuItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("OrderItemListAdapter", "onCreateViewHolder called");
        ViewGroup v = (ViewGroup) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_item, parent, false);

        return new MenuItemViewHolder(v);
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
