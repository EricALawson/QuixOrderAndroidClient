package com.example.quixorder;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.quixorder.model.MenuItem;
import com.example.quixorder.model.Order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderViewHolder> {
    private List<Order> orderList;

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        Button btnDone;
        Button btnDelay;
        TextView tableNum;
        RecyclerView orderItemList;
        Order order;
        List<MenuItem> menuItems;


        public OrderViewHolder(View v) {
            super(v);
            btnDone = v.findViewById(R.id.btnDone);
            btnDelay = v.findViewById(R.id.btnDelay);
            tableNum = v.findViewById(R.id.tableNumber);
            orderItemList = v.findViewById(R.id.orderItemList);

        }

        public void bindOrder(Order o) {
            order = o;
            tableNum.setText(order.getTable());
            menuItems = new ArrayList<>();
            order.getMenuItems().observe( getLifecycleOwner(), (list) -> {
                orderItemList.setAdapter(new OrderItemListAdapter(menuItems));
            });
            orderItemList.setAdapter(new OrderItemListAdapter(menuItems));
        }

        private LifecycleOwner getLifecycleOwner() {
            Context context = this.itemView.getContext();
            while (!(context instanceof LifecycleOwner)) {
                context = ((ContextWrapper) context).getBaseContext();
            }
            return (LifecycleOwner) context;
        }
    }

    public OrderListAdapter(List<Order> orders) {
        this.orderList = orders;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup v = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order, parent, false);
        OrderViewHolder vh = new OrderViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        // - get element from your data set at this position
        // - replace the contents of the view with that element
        holder.bindOrder(orderList.get(position));
    }

    // Return the size of your data set (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
