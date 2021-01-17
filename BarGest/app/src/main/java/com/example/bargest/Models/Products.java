package com.example.bargest.Models;

import androidx.annotation.Nullable;

public class Products  {
    int id;
    String name;
    float price;
    int quantity;
    int category_id;

    public Products(int id,String name, float price, @Nullable int quantity, int category_id) {
        this.id=id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category_id = category_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }
}
