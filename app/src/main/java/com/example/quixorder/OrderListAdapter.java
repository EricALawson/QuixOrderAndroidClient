package com.example.quixorder;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.quixorder.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderViewHolder> {
    private List<Order> orderList;

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        public OrderViewHolder(View v) {
            super(v);
            //TODO
        }
    }

    public OrderListAdapter() {

    }

    public void setOrderList(List<Order> orders) {
        this.orderList = orders;
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
        // - get element from your data set at this position
        // - replace the contents of the view with that element
        //TODO
        holder.itemView.findViewById(R.id.btnDone);

    }

    // Return the size of your data set (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
