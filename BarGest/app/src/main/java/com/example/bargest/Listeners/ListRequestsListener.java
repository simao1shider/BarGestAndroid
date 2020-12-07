package com.example.bargest.Listeners;

import com.example.bargest.Models.Bills;
import com.example.bargest.Models.views.ListRequests;

import java.util.ArrayList;

public interface ListRequestsListener {
    void onRefreshListTables(ArrayList<ListRequests> requests);
}
