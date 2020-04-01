package com.example.quixorder.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    private OnRemoveItemTypeListener onRemoveItemTypeListener;
    private int selectedItem = RecyclerView.NO_POSITION;
    private int previousItem = RecyclerView.NO_POSITION;

    public ItemTypeAdapter(ArrayList<ItemType> itemTypeList, OnItemTypeListener onItemTypeListener, OnRemoveItemTypeListener onRemoveItemTypeListener) {
        this.itemTypeList = itemTypeList;
        this.onItemTypeListener = onItemTypeListener;
        this.onRemoveItemTypeListener = onRemoveItemTypeListener;
    }

    @Override
    public int getItemCount() {
        return itemTypeList.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_type_item, viewGroup, false);
        ViewHolder evh = new ViewHolder(view, onItemTypeListener, onRemoveItemTypeListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ItemType currentItem = itemTypeList.get(i);

        // Set colors for when clicked
        int backgroundColor;
        int fontColor;
        int removeColor;
        if (i == selectedItem) {
            backgroundColor = R.color.itemTypeSelected;
            fontColor = R.color.itemTypeSelectedFont;
            removeColor = R.drawable.ic_remove_circle_wht;
        } else {
            backgroundColor = (i % 2 == 0) ? R.color.itemTypeUnselected1 : R.color.itemTypeUnselected2;
            fontColor = R.color.itemTypeUnselectedFont;
            removeColor = R.drawable.ic_remove_circle_blk;
        }

        // Set views
        viewHolder.cardView.setBackgroundColor(ContextCompat.getColor(viewHolder.cardView.getContext(), backgroundColor));
        viewHolder.textView1.setTextColor(ContextCompat.getColor(viewHolder.textView1.getContext(), fontColor));
        viewHolder.textView1.setText(currentItem.getType());
        viewHolder.imageView.setImageResource(removeColor);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CardView cardView;
        private TextView textView1;
        private ImageView imageView;
        private OnItemTypeListener onItemTypeListener;
        private OnRemoveItemTypeListener onRemoveItemTypeListener;

        public ViewHolder(@NonNull View itemView, OnItemTypeListener onItemTypeListener, OnRemoveItemTypeListener onRemoveItemTypeListener) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            textView1 = itemView.findViewById(R.id.textView1);
            imageView = itemView.findViewById(R.id.imageView);

            // Load click listeners
            this.onItemTypeListener = onItemTypeListener;
            this.onRemoveItemTypeListener = onRemoveItemTypeListener;
            cardView.setOnClickListener(this);
            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.cardView:
                    // Update items
                    previousItem = selectedItem;
                    selectedItem = getAdapterPosition();
                    notifyItemChanged(previousItem);
                    notifyItemChanged(selectedItem);

                    onItemTypeListener.onItemTypeClick(getAdapterPosition(), itemTypeList.get(getAdapterPosition()).getType());
                    break;

                case R.id.imageView:
                    // Update remove item
                    onRemoveItemTypeListener.onRemoveItemTypeListener(getAdapterPosition(), itemTypeList.get(getAdapterPosition()).getType());
            }

        }
    }

    public interface OnItemTypeListener {
        void onItemTypeClick(int position, String itemType);
    }

    public interface OnRemoveItemTypeListener {
        void onRemoveItemTypeListener(int position, String itemType);
    }
}
