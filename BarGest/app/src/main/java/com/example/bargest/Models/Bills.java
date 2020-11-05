package com.example.bargest.Models;

import java.util.ArrayList;

public class Bills {
    String productName;
    float price;
    int quantidade;

    public Bills(String productName, float price, int quantidade) {
        this.productName = productName;
        this.price = price;
        this.quantidade = quantidade;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }


}
