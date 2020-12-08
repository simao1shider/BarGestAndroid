package com.example.bargest.Models;

public class Products  {
    int id;
    String Name;
    float price;

    public Products(int id,String name, float price) {
        this.id=id;
        Name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

}
