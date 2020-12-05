package com.example.bargest.Models;

import java.util.ArrayList;

public class Bills {
    String productName;
    int id;
    float total;

    public Bills(String productName, float total, int id) {
        this.productName = productName;
        this.id = id;
        this.total = total;
    }


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
