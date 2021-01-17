package com.example.bargest.Listeners;

import com.example.bargest.Models.Products;
import com.example.bargest.Models.ProductsToBePaid;

import java.util.ArrayList;

public interface ProductsToBePaidListener {
     void onRefreshListProducts(ArrayList<ProductsToBePaid> productstobepaid);
     void onRefreshArrayProducts(ArrayList<ProductsToBePaid> productstobepaid);
}
