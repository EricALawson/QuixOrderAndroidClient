package com.example.quixorder.model;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

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
    private MutableLiveData<List<MenuItem>> menuItems;
    private String documentId;
    private String status;

    public Order() {

    }

    public Order(DocumentSnapshot snapshot) {
        if(snapshot != null && snapshot.exists()) {
            table = snapshot.getString("table");
            server = snapshot.getString("server");
            status = snapshot.getString("status");
            Timestamp startTS = snapshot.getTimestamp("startTime");
            if (startTS != null) startTime = startTS.toDate();
            Timestamp servedTS = snapshot.getTimestamp("servedTime");
            if (servedTime != null) servedTime = servedTS.toDate();
            Timestamp cookedTS = snapshot.getTimestamp("cookedTime");
            if (cookedTime != null) cookedTime = cookedTS.toDate();

            //noinspection unchecked
            orderItems = (ArrayList<DocumentReference>) snapshot.get("orderItems");
            menuItems = new MutableLiveData<>();
            menuItems.setValue(new ArrayList<>());

            documentId = snapshot.getId();

            orderMenuItems = new ArrayList<MenuItem>();
            for (int orderItemCount = 0; orderItemCount < orderItems.size(); orderItemCount++) {
                DocumentReference doc = orderItems.get(orderItemCount);
                doc.get().addOnSuccessListener(documentSnapshot -> {
                    MenuItem item = documentSnapshot.toObject(MenuItem.class);
                    Log.d("Order: MenuItem", "item created: " + item.toString());
                    orderMenuItems.add(item);
                    menuItems.setValue(orderMenuItems);
                });
            }

        } else {
            Log.e("Bad DocumentReference", "Can't create Order object from document reference in constructor");
        }
    }

    public String getDocumentId() {
        return documentId;
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

    public String getServer() {
        return server;
    }

    public MutableLiveData<List<MenuItem>> getMenuItems() {
        return menuItems;
    }

    public String getStatus() { return status; }
}
