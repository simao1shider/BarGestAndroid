package com.example.bargest.Models.views;

import java.time.LocalDateTime;

public class ListRequests {
    private int id;
    private int status;
    private int table_number;
    private String dateTime;

    public ListRequests(int id, int status, int table_number, String dateTime) {
        this.id = id;
        this.status = status;
        this.table_number = table_number;
        this.dateTime = dateTime;
    }

    public int getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public int getTable_number() {
        return table_number;
    }

    public String getDateTime() {
        return dateTime;
    }
}
