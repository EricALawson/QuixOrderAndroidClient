package com.example.quixorder.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quixorder.R;
import com.example.quixorder.item.TableItem;

import java.util.ArrayList;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.ExampleViewHolder> {
    private ArrayList<TableItem> tableList;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView1;
        public TextView textView2;

        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);
        }
    }

    public TableAdapter(ArrayList<TableItem> tableList) {
        this.tableList = tableList;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.table_item, viewGroup, false);
        ExampleViewHolder evh = new ExampleViewHolder(view);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder exampleViewHolder, int i) {
        TableItem currentItem = tableList.get(i);

        exampleViewHolder.imageView.setImageResource(currentItem.getImageResource());
        exampleViewHolder.textView1.setText(currentItem.getText1());
        exampleViewHolder.textView2.setText(currentItem.getText2());
    }

    @Override
    public int getItemCount() {
        return tableList.size();
    }
}
