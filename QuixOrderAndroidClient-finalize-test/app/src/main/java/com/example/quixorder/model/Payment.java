package com.example.quixorder.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

import java.util.Calendar;
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
    public Payment(DocumentReference id, Date start, double amt, String form) {
        this.orderID = id;
        this.receivedTime = start;
        this.total = amt;
        this.type = form;
    }

    public String getOrderID() {
        return orderID.getId();
    }

    public Date getReceivedTime() {
        return receivedTime;
    }

    public Calendar getCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getReceivedTime());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    public double getTotal() {
        return total;
    }

    public String getType() {
        return type;
    }
}
