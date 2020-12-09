package com.example.bargest.Models;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Requests {

    int id;
    float price;
    String name;
    int quantity;


    public Requests(@Nullable int id, float price, String name, int quantity) {
        this.id = id;

        this.price = price;
        this.name = name;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
