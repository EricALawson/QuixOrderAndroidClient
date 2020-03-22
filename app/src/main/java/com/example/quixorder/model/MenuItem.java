package com.example.quixorder.model;

public class MenuItem {
    private final String description;
    private final String image;
    private final String name;
    private final double price;
    private final String type;

    public MenuItem() {
        description = "";
        image = "";
        name = "";
        price = 0;
        type = "";
    }

    public MenuItem(String description, String image, String name, double price, String type) {
        this.description = description;
        this.image = image;
        this.name = name;
        this.price = price;
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

    public String getType() {
        return type;
    }
}
