package com.example.quixorder.model;

import com.google.firebase.firestore.DocumentReference;

import java.util.Date;

public class Order {
    private String table;
    private Date startTime;
    private Date cookedTime;
    private Date servedTime;
    private DocumentReference[] orderItems;
    private String server;

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

    public DocumentReference[] getOrderItems() {
        return orderItems;
    }

    public String getServer() {
        return server;
    }

    public Order() {

    }
}
