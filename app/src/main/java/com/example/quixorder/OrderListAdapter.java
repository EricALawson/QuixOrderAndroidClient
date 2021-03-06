package com.example.quixorder;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderViewHolder> {
    private List<Order> orderList;

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        Button btnDone;
        Button btnDelay;
        TextView tableNum;
        RecyclerView orderItemList;
        Order order;


        public OrderViewHolder(View v) {
            super(v);
            btnDone = v.findViewById(R.id.btnDone);
            btnDelay = v.findViewById(R.id.btnDelay);
            tableNum = v.findViewById(R.id.tableNumber);
            orderItemList = v.findViewById(R.id.orderItemList);
            btnDone.setOnClickListener(view -> {
                if (order != null) {
                    DocumentReference docRef = FirebaseFirestore.getInstance().collection("orders").document(order.getDocumentId());
                    docRef.update("cookedTime",  new Date());
                }
            });
        }

        public void bindOrder(Order o) {
            order = o;
            tableNum.setText(order.getTable());
            orderItemList.setLayoutManager(new LinearLayoutManager(this.itemView.getContext()));
            order.getMenuItems().observe( getLifecycleOwner(), (list) -> {
                Log.d("OrderListAdapter", "MenuItem list change observed, size = " + list.size());
                orderItemList.swapAdapter(new OrderItemListAdapter(list), false);
            });
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

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup v = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order, parent, false);
        OrderViewHolder vh = new OrderViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        holder.bindOrder(orderList.get(position));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}