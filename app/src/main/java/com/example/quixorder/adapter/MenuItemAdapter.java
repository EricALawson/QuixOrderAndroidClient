package com.example.quixorder.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quixorder.R;
import com.example.quixorder.model.MenuItem;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.ViewHolder> {
    private ArrayList<MenuItem> menuItemList;
    private OnRemoveMenuItemListener onRemoveMenuItemListener;

    public MenuItemAdapter(ArrayList<MenuItem> menuItemList, OnRemoveMenuItemListener onRemoveMenuItemListener) {
        this.menuItemList = menuItemList;
        this.onRemoveMenuItemListener = onRemoveMenuItemListener;
    }

    @Override
    public int getItemCount() {
        return menuItemList.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.menu_item_item, viewGroup, false);
        ViewHolder evh = new ViewHolder(view, onRemoveMenuItemListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        MenuItem currentItem = menuItemList.get(i);

        // Set views
        viewHolder.imageView.setImageResource(R.drawable.ic_restaurant_menu_blk);
        viewHolder.textView1.setText(currentItem.getName());
        viewHolder.textView2.setText(currentItem.getDescription());
        viewHolder.textView3.setText("$" + currentItem.getPrice());
        viewHolder.imageView2.setImageResource(R.drawable.ic_remove_circle_blk);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CardView cardView;
        private ImageView imageView;
        private TextView textView1;
        private TextView textView2;
        private TextView textView3;
        private ImageView imageView2;
        private OnRemoveMenuItemListener onRemoveMenuItemListener;

        public ViewHolder(@NonNull View itemView, OnRemoveMenuItemListener onRemoveMenuItemListener) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            imageView = itemView.findViewById(R.id.imageView1);
            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);
            textView3 = itemView.findViewById(R.id.textView3);
            imageView2 = itemView.findViewById(R.id.imageView2);

            // Load click listeners
            this.onRemoveMenuItemListener = onRemoveMenuItemListener;
            imageView2.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.imageView2:
                    onRemoveMenuItemListener.onRemoveMenuItemListener(getAdapterPosition(), menuItemList.get(getAdapterPosition()).getName());
            }
        }
    }

    public interface OnRemoveMenuItemListener {
        void onRemoveMenuItemListener(int position, String itemName);
    }
}
