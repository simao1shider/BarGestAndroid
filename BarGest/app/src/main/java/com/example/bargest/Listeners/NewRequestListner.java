package com.example.bargest.Listeners;

import com.example.bargest.Models.Products;

import java.util.ArrayList;

public interface NewRequestListner {
    void onRefreshListProducts(ArrayList<Products> products);
}
