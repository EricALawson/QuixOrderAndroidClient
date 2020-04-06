package com.example.quixorder.model;

import com.google.firebase.firestore.DocumentReference;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Date;

public class Order {
    private final Date cookedTime;
    private final ArrayList<DocumentReference> orderItems;
    private final Date servedTime;
    private final String server;
    private final Date startTime;
    private final String table;

    public Order() {
        cookedTime = null;
        orderItems = null;
        servedTime = null;
        server = "";
        startTime = null;
        table = "";
    }

    public Date getCookedTime() {
        return cookedTime;
    }

    public ArrayList<DocumentReference> getOrderItems() {
        return orderItems;
    }

    public Date getServedTime() {
        return servedTime;
    }

    public String getServer() {
        return server;
    }

    public Date getStartTime() {
        return startTime;
    }

    public String getTable() {
        return table;
    }
}
