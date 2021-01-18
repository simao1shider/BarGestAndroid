package com.example.bargest.Listeners;

import com.example.bargest.Models.ListRequests;

import java.util.ArrayList;

public interface ListRequestsListener {
    void onRefreshListTables(ArrayList<ListRequests> requests);
}
