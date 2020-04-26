package com.example.quixorder.model;

public class MenuItem
{
    private final String description;
    private final String image;
    private final String name;
    private final double price;
    //private final int qty;
    private final String type;

    public MenuItem() {
        description = "";
        image = "";
        name = "";
        price = 0;
        //qty = 0;
        type = "";
    }

    public MenuItem(String description, String image, String name, double price,/* int quantity,*/ String type) {
        this.description = description;
        this.image = image;
        this.name = name;
        this.price = price;
        //this.qty = quantity;
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    /*public int getQty()
    {
        return qty;
    }*/

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                //", qty="+ qty +
                ", type='" + type + '\'' +
                '}';
    }
}
