package com.example.bargest.Listeners;

import com.example.bargest.Models.Bills;
import com.example.bargest.Models.Tables;

import java.util.ArrayList;

public interface BillListener {
    void onRefreshListTables(ArrayList<Bills> bills);
    void onUpdateListTables(ArrayList<Bills> bills);
}
