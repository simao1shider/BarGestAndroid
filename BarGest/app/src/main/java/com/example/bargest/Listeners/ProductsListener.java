package com.example.bargest.Listeners;

import com.example.bargest.Models.Products;

import java.util.ArrayList;

public interface ProductsListener {
     void onRefreshListProducts(ArrayList<Products> products);
     void onRefreshArrayProducts(ArrayList<Products> products);
}
