package com.example.bargest.Models;

import java.util.ArrayList;

public class Bills {
    String productName;
    int id;
    float total;
    int idMesa;

    public Bills(String productName, float total, int id, int idMesa) {
        this.productName = productName;
        this.id = id;
        this.total = total;
        this.idMesa = idMesa;
    }


    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getTotal() {
        return this.total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public int getIdMesa() {
        return this.idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }
}
