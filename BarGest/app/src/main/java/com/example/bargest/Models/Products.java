package com.example.bargest.Models;

import androidx.annotation.Nullable;

public class Products  {
    int id;
    String Name;
    float price;
    int quantity;

    public Products(int id,String name, float price, @Nullable int quantity) {
        this.id=id;
        Name = name;
        this.price = price;
        this.quantity = quantity;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
