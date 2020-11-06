package com.example.bargest.Views.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.bargest.Adaptars.CategoriesAdaptar;
import com.example.bargest.Models.Categories;
import com.example.bargest.R;
import com.example.bargest.SingletonBarGest;

import java.util.ArrayList;

public class CategoriesFragment extends Fragment {

    GridView GVCategories;
    private ArrayList<Categories> categories;
    private CategoriesAdaptar adaptarCategories;

    public CategoriesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        GVCategories = view.findViewById(R.id.containerCategory);
        categories = SingletonBarGest.getInstance(getContext()).genereteFakeCategoriesList();
        adaptarCategories = new CategoriesAdaptar(getContext(),categories,getFragmentManager().beginTransaction());
        GVCategories.setAdapter(adaptarCategories);
        return view;
    }
}