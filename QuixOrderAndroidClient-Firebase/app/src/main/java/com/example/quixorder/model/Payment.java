package com.example.quixorder.model;

import androidx.annotation.NonNull;

//import java.util.UUID;

public class Payment
{
    private final String id;
    private final Order orderNum;
    private final String pay;
    private final double total;

    public Payment(String id, Order oNum, String pType, double tot)
    {
        this.id = id;
        this.orderNum = oNum;
        this.pay = pType;
        this.total = tot;
    }

    public String getId() {
        return id;
    }

    public String getPaymentType() {
        return pay;
    }

    public Order getOrder() {
        return orderNum;
    }

    public double getTotal() {
        return total;
    }

    @NonNull
    @Override
    public String toString() {
        return "Payment: "+this.id+" Order: "+this.orderNum;
    }
}

