package com.example.quixorder.adapter;

import androidx.annotation.NonNull;
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

import com.example.quixorder.R;
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


        OrderViewHolder(View v) {
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

        void bindOrder(Order o) {
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

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup v = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order, parent, false);
        return new OrderViewHolder(v);
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
