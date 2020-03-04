package com.example.quixorder;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.quixorder.model.MenuItem;
import com.example.quixorder.model.Order;

import java.util.ArrayList;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderViewHolder> {
    private ArrayList<Order> orderItems;

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        public OrderViewHolder(View v) {
            super(v);
            //TODO
        }

    }

    public OrderListAdapter(ArrayList<Order> orderItems) {
        this.orderItems = orderItems;
        //TODO: Maybe
    }
    // Create new views (invoked by the layout manager)
    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup v = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order, parent, false);
        Button btnDone = v.findViewById(R.id.btnDone);
        Button btnDelay = v.findViewById(R.id.btnDelay);
        OrderViewHolder vh = new OrderViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //TODO

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return orderItems.size();
    }
}
