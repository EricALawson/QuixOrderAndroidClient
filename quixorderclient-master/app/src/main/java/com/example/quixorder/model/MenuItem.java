package com.example.quixorder.model;

import java.util.UUID;

public class MenuItem
{
    private final UUID id;
    private final String name;
    private final double price;

    public MenuItem(UUID id, String name, double pri)
    {
        this.id = id;
        this.name = name;
        this.price = pri;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
