package com.example.bargest.Models;

import androidx.annotation.Nullable;

public class ProductsToBePaid {
    int id;
    String name;
    float price;
    int quantity;
    int category_id;
    int account_id;
    int request_id;

    public ProductsToBePaid(int id, String name, float price, @Nullable int quantity,@Nullable int account_id,@Nullable int request_id ,@Nullable int category_id) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.account_id = account_id;
        this.request_id = request_id;
        this.category_id = category_id;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRequest_id() {
        return this.request_id;
    }

    public void setRequest_id(int request_id) {
        this.request_id = request_id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public float getPrice() {
        return this.price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getAccount_id() {
        return this.account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public int getCategory_id() {
        return this.category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }
}
