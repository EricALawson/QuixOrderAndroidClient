package com.example.quixorder.model;

import com.google.firebase.firestore.DocumentReference;

import java.util.Date;

public class Payment {
    private final DocumentReference orderID;
    private final Date receivedTime;
    private final double total;
    private final String type;

    public Payment() {
        orderID = null;
        receivedTime = null;
        total = 0;
        type = "";
    }

    public DocumentReference getOrderID() {
        return orderID;
    }

    public Date getReceivedTime() {
        return receivedTime;
    }

    public double getTotal() {
        return total;
    }

    public String getType() {
        return type;
    }
}
