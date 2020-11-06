package com.example.bargest.Models;

public class Products  {

    String Name;
    float price;
    int quantidade;

    public Products(String name, float price,int quantidade) {
        Name = name;
        this.price = price;
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

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
