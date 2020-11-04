package com.example.bargest.Models;

public class Requests {
     int table;
     int status;

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
}
