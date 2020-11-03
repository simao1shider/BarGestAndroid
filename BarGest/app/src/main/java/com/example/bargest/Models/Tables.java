package com.example.bargest.Models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.DecimalFormat;

public class Tables {
    int number;
    boolean occupied;
    float money;

    public Tables(int number, boolean occupied, float money) {
        this.number = number;
        this.occupied = occupied;
        this.money = money;
    }

    public int getNumber() {
        return number;
    }

    public boolean isStatus() {
        return occupied;
    }

    public float getMoney() {
        return money;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setStatus(boolean occupied) {
        this.occupied = occupied;
    }

    public void setMoney(float money) {
        this.money = money;
    }
}
