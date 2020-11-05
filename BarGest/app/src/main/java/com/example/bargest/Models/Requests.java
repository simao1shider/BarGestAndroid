package com.example.bargest.Models;

import java.util.ArrayList;

public class Requests {
     int table;
     int status;
     ArrayList<Products> products;

    public Requests(int table, int status) {
        this.table = table;
        this.status = status;
    }

    public int getTable() {
        return table;
    }

    public void setTable(int table) {
        this.table = table;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ArrayList<Products> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Products> products) {
        this.products = products;
    }
}
