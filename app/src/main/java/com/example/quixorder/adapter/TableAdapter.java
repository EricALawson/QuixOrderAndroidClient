package com.example.quixorder.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quixorder.R;
import com.example.quixorder.model.Account;
import com.example.quixorder.model.Table;

import java.util.ArrayList;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.ExampleViewHolder> {
    private ArrayList<Table> tableList;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView1;

        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView1 = itemView.findViewById(R.id.textView1);
        }
    }

    public TableAdapter(ArrayList<Table> tableList) {
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
        Account currentItem = tableList.get(i);

        exampleViewHolder.imageView.setImageResource(R.drawable.ic_assign_table_blk);
        exampleViewHolder.textView1.setText(currentItem.getUsername());
    }

    @Override
    public int getItemCount() {
        return tableList.size();
    }
}
