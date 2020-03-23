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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class ItemTypeAdapter extends RecyclerView.Adapter<ItemTypeAdapter.ViewHolder> {
    private ArrayList<ItemType> itemTypeList;
    private OnItemTypeListener onItemTypeListener;
    private int selectedItem = RecyclerView.NO_POSITION;
    private int previousItem = RecyclerView.NO_POSITION;

    public ItemTypeAdapter(ArrayList<ItemType> itemTypeList, OnItemTypeListener onItemTypeListener) {
        this.itemTypeList = itemTypeList;
        this.onItemTypeListener = onItemTypeListener;
    }

    @Override
    public int getItemCount() {
        return itemTypeList.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_type_item, viewGroup, false);
        ViewHolder evh = new ViewHolder(view, onItemTypeListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ItemType currentItem = itemTypeList.get(i);

        // Set views
        int backgroundColor;
        int fontColor;
        if (i == selectedItem) {
            backgroundColor = R.color.itemTypeSelected;
            fontColor = R.color.itemTypeSelectedFont;
        } else {
            backgroundColor = (i % 2 == 0) ? R.color.itemTypeUnselected1 : R.color.itemTypeUnselected2;
            fontColor = R.color.itemTypeUnselectedFont;
        }

        viewHolder.cardView.setBackgroundColor(ContextCompat.getColor(viewHolder.cardView.getContext(), backgroundColor));
        viewHolder.textView1.setTextColor(ContextCompat.getColor(viewHolder.textView1.getContext(), fontColor));
        viewHolder.textView1.setText(currentItem.getType());
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private CardView cardView;
        private TextView textView1;
        private OnItemTypeListener onItemTypeListener;

        public ViewHolder(@NonNull View itemView, OnItemTypeListener onItemTypeListener) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            textView1 = itemView.findViewById(R.id.textView1);

            // Load item type click listener
            this.onItemTypeListener = onItemTypeListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // Update items
            previousItem = selectedItem;
            selectedItem = getAdapterPosition();
            notifyItemChanged(previousItem);
            notifyItemChanged(selectedItem);

            onItemTypeListener.onItemTypeClick(getAdapterPosition(), itemTypeList.get(getAdapterPosition()).getType());
        }
    }

    public interface OnItemTypeListener {
        void onItemTypeClick(int position, String itemType);
    }
}
