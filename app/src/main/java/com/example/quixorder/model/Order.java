package com.example.quixorder.model;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private String table;
    private Date startTime;
    private Date cookedTime;
    private Date servedTime;
    private ArrayList<DocumentReference> orderItems;
    private ArrayList<MenuItem> orderMenuItems;
    private String server;

    public Order() {

    }

    public Order(DocumentSnapshot snapshot) {
        if(snapshot != null && snapshot.exists()) {
            table = (String) snapshot.get("table");
            server = (String) snapshot.get("server");
            startTime = (Date) snapshot.get("startTime");
            cookedTime = (Date) snapshot.get("cookedTime");
            servedTime = (Date) snapshot.get("servedTime");
            orderItems = (ArrayList<DocumentReference>) snapshot.get("orderItems");

            orderMenuItems = new ArrayList<MenuItem>();
            for (int orderItemCount = 0; orderItemCount < orderItems.size(); orderItemCount++) {
                DocumentReference doc = orderItems.get(orderItemCount);
                final int index = orderItemCount;
                doc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        MenuItem item = documentSnapshot.toObject(MenuItem.class);
                        Log.d("Order: MenuItem", "item created: " + item.toString());
                        orderMenuItems.add(item);
                    }
                });
            }

        } else {
            Log.e("Bad DocumentReference", "Can't create Order object from document reference in constructor");
        }
    }

    public String getTable() {
        return table;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getCookedTime() {
        return cookedTime;
    }

    public Date getServedTime() {
        return servedTime;
    }

    public List<DocumentReference> getOrderItems() {
        return orderItems;
    }

    public String getServer() {
        return server;
    }

    public List<MenuItem> getOrderMenuItems() {
        return orderMenuItems;
    }
}
