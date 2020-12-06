package com.example.bargest.Listeners;

import com.example.bargest.Models.Tables;

import java.util.ArrayList;

public interface TableListener {
    void onRefreshListTables(ArrayList<Tables> tables);
    void onUpdateListTables(ArrayList<Tables> tables);
}
