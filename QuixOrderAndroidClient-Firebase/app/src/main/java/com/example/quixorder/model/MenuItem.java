package com.example.quixorder.model;

//import java.util.UUID;

public class MenuItem
{
    //private final UUID id;
    public int id;
    public String name;
    public double price;
    public int pic;

    public MenuItem(int id, String name, double pri, int img)
    {
        this.id = id;
        this.name = name;
        this.price = pri;
        this.pic = img;

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getImg() { return pic; }
}

