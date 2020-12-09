package com.example.bargest.Listeners;

import com.example.bargest.Models.Categories;

import java.util.ArrayList;

public interface CategoriesListener {
    void onRefreshCategories(ArrayList<Categories> categories);
}
