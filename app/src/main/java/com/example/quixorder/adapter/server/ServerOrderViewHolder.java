package com.example.quixorder.adapter.server;

import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quixorder.R;
import com.example.quixorder.adapter.cook.OrderItemListAdapter;
import com.example.quixorder.model.Order;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;

public class ServerOrderViewHolder extends RecyclerView.ViewHolder {
    Button btnDone;
    TextView tableNum;
    RecyclerView orderItemList;
    Order order;


    public ServerOrderViewHolder(View v) {
        super(v);
        btnDone = v.findViewById(R.id.btnDone);
        tableNum = v.findViewById(R.id.tableNumber);
        orderItemList = v.findViewById(R.id.orderItemList);
        btnDone.setOnClickListener(view -> {
            if (order != null) {
                DocumentReference docRef = FirebaseFirestore.getInstance().collection("orders").document(order.getDocumentId());
                HashMap<String, Object> data = new HashMap<>();
                data.put("servedTime", new Date());
                data.put("status", "served");
                docRef.update(data);
            }
        });
    }

    public void bindOrder(Order o) {
        order = o;
        tableNum.setText(order.getTable());
        orderItemList.setLayoutManager(new LinearLayoutManager(this.itemView.getContext()));
        order.getMenuItems().observe( getLifecycleOwner(), (list) -> {
            //Log.d("ServerOrderViewHolder", "MenuItem list change observed, size = " + list.size());
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