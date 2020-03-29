package com.example.quixorder.model;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

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
    private DocumentReference menuItemRefs;
    private MutableLiveData<List<MenuItem>> menuItems;

    public Order() {

    }

    public Order(DocumentSnapshot snapshot) {
        if(snapshot != null && snapshot.exists()) {
            table = (String) snapshot.get("table");
            server = (String) snapshot.get("server");
            startTime = (Date) snapshot.get("startTime");
            cookedTime = (Date) snapshot.get("cookedTime");
            servedTime = (Date) snapshot.get("servedTime");

            menuItemRefs = snapshot.getDocumentReference("orderItems");

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
                        menuItems.setValue(orderMenuItems);
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

    public String getServer() {
        return server;
    }

    public MutableLiveData<List<MenuItem>> getMenuItems() {
        return menuItems;
    }

//    public List<MenuItem> getOrderMenuItems() {
//        return orderMenuItems;
//    }

    public DocumentReference getMenuItemRefs() {
        return menuItemRefs;
    }
}
