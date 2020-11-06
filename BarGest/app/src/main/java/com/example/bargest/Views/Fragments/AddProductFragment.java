package com.example.bargest.Views.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;

import com.example.bargest.Adaptars.AddProductAdaptar;
import com.example.bargest.Adaptars.CategoriesAdaptar;
import com.example.bargest.Models.Bills;
import com.example.bargest.Models.Categories;
import com.example.bargest.R;
import com.example.bargest.SingletonBarGest;

import java.util.ArrayList;


public class AddProductFragment extends Fragment {

    GridView GVAddProduct;
    private ArrayList<Bills> bills;
    private AddProductAdaptar adaptarCategories;

    public AddProductFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_product, container, false);
        GVAddProduct = view.findViewById(R.id.GVAddProduct);
        ImageButton backCategories = view.findViewById(R.id.BtnBackToCategories);
        bills = SingletonBarGest.getInstance(getContext()).generateFakeDetailsBills();
        adaptarCategories = new AddProductAdaptar(getContext(),bills);
        GVAddProduct.setAdapter(adaptarCategories);

        backCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.conteinerAddProduct,new CategoriesFragment()).commit();
            }
        });

        return view;
    }

}