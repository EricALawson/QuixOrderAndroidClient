package com.example.quixorder.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.quixorder.R;
import com.example.quixorder.model.ItemType;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ItemTypeAdapter extends RecyclerView.Adapter<ItemTypeAdapter.ViewHolder> {
    private ArrayList<ItemType> itemTypeList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView textView1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            textView1 = itemView.findViewById(R.id.textView1);
        }
    }

    public ItemTypeAdapter(ArrayList<ItemType> itemTypeList) {
        this.itemTypeList = itemTypeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_type_item, viewGroup, false);
        ViewHolder evh = new ViewHolder(view);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ItemType currentItem = itemTypeList.get(i);

        if (i % 2 == 0) {
            viewHolder.cardView.setCardBackgroundColor(Color.parseColor("#efe7c3"));
        }
        viewHolder.textView1.setText(currentItem.getType());
    }

    @Override
    public int getItemCount() {
        return itemTypeList.size();
    }

}
