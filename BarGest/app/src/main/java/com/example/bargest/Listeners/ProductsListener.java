package com.example.bargest.Listeners;

import android.widget.ArrayAdapter;

import com.example.bargest.Models.Products;
import com.example.bargest.Models.views.ListRequests;

import java.util.ArrayList;

public interface ProductsListener {
     void onRefreshListProducts(ArrayList<Products> products);
     void onRefreshArrayProducts(ArrayList<Products> products);
}
